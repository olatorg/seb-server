/*
 * Copyright (c) 2020 ETH Zürich, Educational Development and Technology (LET)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ch.ethz.seb.sebserver.webservice.servicelayer.session.impl.proctoring;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ch.ethz.seb.sebserver.ClientHttpRequestFactoryService;
import ch.ethz.seb.sebserver.gbl.Constants;
import ch.ethz.seb.sebserver.gbl.api.API;
import ch.ethz.seb.sebserver.gbl.api.APIMessage;
import ch.ethz.seb.sebserver.gbl.api.APIMessage.APIMessageException;
import ch.ethz.seb.sebserver.gbl.api.APIMessage.FieldValidationException;
import ch.ethz.seb.sebserver.gbl.model.exam.Exam;
import ch.ethz.seb.sebserver.gbl.model.exam.ProctoringRoomConnection;
import ch.ethz.seb.sebserver.gbl.model.exam.ProctoringServiceSettings;
import ch.ethz.seb.sebserver.gbl.model.exam.ProctoringServiceSettings.ProctoringServerType;
import ch.ethz.seb.sebserver.gbl.model.session.ClientConnectionData;
import ch.ethz.seb.sebserver.gbl.model.session.ClientInstruction;
import ch.ethz.seb.sebserver.gbl.profile.WebServiceProfile;
import ch.ethz.seb.sebserver.gbl.util.Cryptor;
import ch.ethz.seb.sebserver.gbl.util.Result;
import ch.ethz.seb.sebserver.gbl.util.Tuple;
import ch.ethz.seb.sebserver.gbl.util.Utils;
import ch.ethz.seb.sebserver.webservice.servicelayer.authorization.AuthorizationService;
import ch.ethz.seb.sebserver.webservice.servicelayer.session.ExamProctoringService;
import ch.ethz.seb.sebserver.webservice.servicelayer.session.ExamSessionService;

@Lazy
@Service
@WebServiceProfile
public class JitsiProctoringService implements ExamProctoringService {

    private static final Logger log = LoggerFactory.getLogger(JitsiProctoringService.class);

    private static final String TOKEN_ENCODE_ALG = "HmacSHA256";
    private static final String SEB_SERVER_KEY = "seb-server";
    private static final String SEB_CLIENT_KEY = "seb-client";

    private static final String JITSI_ACCESS_TOKEN_HEADER =
            "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";

    private static final String JITSI_ACCESS_TOKEN_PAYLOAD =
            "{\"context\":{\"user\":{\"name\":\"%s\"}},\"iss\":\"%s\",\"aud\":\"%s\",\"sub\":\"%s\",\"room\":\"%s\"%s%s}";

    private static final Map<String, String> SEB_API_NAME_INSTRUCTION_NAME_MAPPING = Utils.immutableMapOf(Arrays.asList(
            new Tuple<>(
                    API.EXAM_PROCTORING_ATTR_RECEIVE_AUDIO,
                    ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_PROCTORING.JITSI_RECEIVE_AUDIO),
            new Tuple<>(
                    API.EXAM_PROCTORING_ATTR_RECEIVE_VIDEO,
                    ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_PROCTORING.JITSI_RECEIVE_VIDEO),
            new Tuple<>(
                    API.EXAM_PROCTORING_ATTR_ALLOW_CHAT,
                    ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_PROCTORING.JITSI_ALLOW_CHAT))
            .stream().collect(Collectors.toMap(Tuple::get_1, Tuple::get_2)));

    private static final Map<String, String> SEB_INSTRUCTION_DEFAULTS = Utils.immutableMapOf(Arrays.asList(
            new Tuple<>(
                    ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_PROCTORING.JITSI_RECEIVE_AUDIO,
                    Constants.FALSE_STRING),
            new Tuple<>(
                    ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_PROCTORING.JITSI_RECEIVE_VIDEO,
                    Constants.FALSE_STRING),
            new Tuple<>(
                    ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_PROCTORING.JITSI_ALLOW_CHAT,
                    Constants.FALSE_STRING))
            .stream().collect(Collectors.toMap(Tuple::get_1, Tuple::get_2)));

    private final AuthorizationService authorizationService;
    private final ExamSessionService examSessionService;
    private final Cryptor cryptor;
    private final ClientHttpRequestFactoryService clientHttpRequestFactoryService;

    protected JitsiProctoringService(
            final AuthorizationService authorizationService,
            final ExamSessionService examSessionService,
            final Cryptor cryptor,
            final ClientHttpRequestFactoryService clientHttpRequestFactoryService) {

        this.authorizationService = authorizationService;
        this.examSessionService = examSessionService;
        this.cryptor = cryptor;
        this.clientHttpRequestFactoryService = clientHttpRequestFactoryService;
    }

    @Override
    public ProctoringServerType getType() {
        return ProctoringServerType.JITSI_MEET;
    }

    @Override
    public Result<Boolean> testExamProctoring(final ProctoringServiceSettings proctoringSettings) {

        if (log.isDebugEnabled()) {
            log.debug("Test proctoring service connection: {}", proctoringSettings);
        }

        return Result.tryCatch(() -> {
            if (proctoringSettings.serverURL != null && proctoringSettings.serverURL.contains("?")) {
                throw new FieldValidationException(
                        "serverURL",
                        "proctoringSettings:serverURL:invalidURL");
            }

            final ClientHttpRequestFactory clientHttpRequestFactory = this.clientHttpRequestFactoryService
                    .getClientHttpRequestFactory()
                    .getOrThrow();

            try {
                final RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
                final ResponseEntity<String> result =
                        restTemplate.getForEntity(proctoringSettings.serverURL, String.class);
                if (result.getStatusCode() != HttpStatus.OK) {
                    throw new APIMessageException(APIMessage.ErrorMessage.BINDING_ERROR);
                }
            } catch (final Exception e) {
                log.error("Failed to access proctoring service: {}", e.getMessage());
                throw new APIMessageException(APIMessage.ErrorMessage.BINDING_ERROR, e.getMessage());
            }

            return true;
        });
    }

    @Override
    public Result<ProctoringRoomConnection> getProctorRoomConnection(
            final ProctoringServiceSettings proctoringSettings,
            final String roomName,
            final String subject) {

        return this.createProctorPublicRoomConnection(
                proctoringSettings,
                roomName,
                StringUtils.isNoneBlank(subject) ? subject : roomName);
    }

    @Override
    public Result<Void> disposeServiceRoomsForExam(
            final Long examId,
            final ProctoringServiceSettings proctoringSettings) {
        // NOTE: Since Jitsi rooms are generated and disposed automatically we don't need to do anything here
        return Result.EMPTY;
    }

    @Override
    public Result<NewRoom> newCollectingRoom(
            final ProctoringServiceSettings proctoringSettings,
            final Long roomNumber) {

        return Result.of(new NewRoom(
                UUID.randomUUID().toString(),
                "Room " + (roomNumber + 1)));
    }

    @Override
    public Result<NewRoom> newBreakOutRoom(
            final ProctoringServiceSettings proctoringSettings,
            final String subject) {

        return Result.of(new NewRoom(
                UUID.randomUUID().toString(),
                subject));
    }

    @Override
    public Result<Void> disposeBreakOutRoom(
            final ProctoringServiceSettings proctoringSettings,
            final String roomName) {

        return Result.EMPTY;
    }

    @Override
    public Map<String, String> getDefaultInstructionAttributes() {
        return SEB_INSTRUCTION_DEFAULTS;
    }

    @Override
    public Map<String, String> getInstructionAttributes(final Map<String, String> attributes) {
        final Map<String, String> result = attributes
                .entrySet()
                .stream()
                .map(entry -> new Tuple<>(
                        SEB_API_NAME_INSTRUCTION_NAME_MAPPING.getOrDefault(entry.getKey(), entry.getKey()),
                        entry.getValue()))
                .collect(Collectors.toMap(Tuple::get_1, Tuple::get_2));

        if (BooleanUtils.isTrue(Boolean.valueOf(attributes.get(API.EXAM_PROCTORING_ATTR_RECEIVE_VIDEO)))) {
            final String username = this.authorizationService
                    .getUserService()
                    .getCurrentUser()
                    .getUsername();
            attributes.put(
                    ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_RECONFIGURE_SETTINGS.JITSI_PIN_USER_ID,
                    username);
        }

        return result;
    }

    @Override
    public Map<String, String> createJoinInstructionAttributes(final ProctoringRoomConnection proctoringConnection) {
        final Map<String, String> attributes = new HashMap<>();

        attributes.put(
                ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_PROCTORING.SERVICE_TYPE,
                ProctoringServiceSettings.ProctoringServerType.JITSI_MEET.name());
        attributes.put(
                ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_PROCTORING.METHOD,
                ClientInstruction.ProctoringInstructionMethod.JOIN.name());
        attributes.put(
                ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_PROCTORING.JITSI_URL,
                proctoringConnection.serverURL);
        attributes.put(
                ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_PROCTORING.JITSI_ROOM,
                proctoringConnection.roomName);
        if (StringUtils.isNotBlank(proctoringConnection.subject)) {
            attributes.put(
                    ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_PROCTORING.JITSI_ROOM_SUBJECT,
                    proctoringConnection.subject);
        }
        attributes.put(
                ClientInstruction.SEB_INSTRUCTION_ATTRIBUTES.SEB_PROCTORING.JITSI_TOKEN,
                String.valueOf(proctoringConnection.accessToken));

        return attributes;
    }

    private Result<ProctoringRoomConnection> createProctorPublicRoomConnection(
            final ProctoringServiceSettings proctoringSettings,
            final String roomName,
            final String subject) {

        return Result.tryCatch(() -> {
            return createProctoringConnection(
                    null,
                    proctoringSettings.serverURL,
                    proctoringSettings.appKey,
                    proctoringSettings.getAppSecret(),
                    this.authorizationService.getUserService().getCurrentUser().getUsername(),
                    SEB_SERVER_KEY,
                    roomName,
                    subject,
                    forExam(proctoringSettings),
                    true)
                            .getOrThrow();
        });
    }

    @Override
    public Result<ProctoringRoomConnection> getClientRoomConnection(
            final ProctoringServiceSettings proctoringSettings,
            final String connectionToken,
            final String roomName,
            final String subject) {

        return Result.tryCatch(() -> {
            final ClientConnectionData clientConnection = this.examSessionService
                    .getConnectionData(connectionToken)
                    .getOrThrow();

            return createProctoringConnection(
                    null,
                    proctoringSettings.serverURL,
                    proctoringSettings.appKey,
                    proctoringSettings.getAppSecret(),
                    clientConnection.clientConnection.userSessionId,
                    SEB_CLIENT_KEY,
                    roomName,
                    subject,
                    forExam(proctoringSettings),
                    false)
                            .getOrThrow();
        });
    }

    protected Result<ProctoringRoomConnection> createProctoringConnection(
            final String connectionToken,
            final String url,
            final String appKey,
            final CharSequence appSecret,
            final String clientName,
            final String clientKey,
            final String roomName,
            final String subject,
            final Long expTime,
            final boolean moderator) {

        return Result.tryCatch(() -> {

            final String host = UriComponentsBuilder.fromHttpUrl(url)
                    .build()
                    .getHost();

            final CharSequence decryptedSecret = this.cryptor
                    .decrypt(appSecret)
                    .getOrThrow();

            final String token = internalCreateAccessToken(
                    appKey,
                    decryptedSecret,
                    clientName,
                    clientKey,
                    roomName,
                    expTime,
                    host,
                    moderator);

            return new ProctoringRoomConnection(
                    ProctoringServerType.JITSI_MEET,
                    connectionToken,
                    host,
                    url,
                    roomName,
                    subject,
                    token,
                    null,
                    null,
                    null,
                    clientName);
        });
    }

    protected String internalCreateAccessToken(
            final String appKey,
            final CharSequence appSecret,
            final String clientName,
            final String clientKey,
            final String roomName,
            final Long expTime,
            final String host,
            final boolean moderator) throws NoSuchAlgorithmException, InvalidKeyException {

        final StringBuilder builder = new StringBuilder();
        final Encoder urlEncoder = Base64.getUrlEncoder().withoutPadding();

        final String jwtHeaderPart = urlEncoder
                .encodeToString(JITSI_ACCESS_TOKEN_HEADER.getBytes(StandardCharsets.UTF_8));
        final String jwtPayload = createPayload(appKey, clientName, clientKey, roomName, expTime, host, moderator);
        final String jwtPayloadPart = urlEncoder
                .encodeToString(jwtPayload.getBytes(StandardCharsets.UTF_8));
        final String message = jwtHeaderPart + "." + jwtPayloadPart;

        final Mac sha256_HMAC = Mac.getInstance(TOKEN_ENCODE_ALG);
        final SecretKeySpec secret_key =
                new SecretKeySpec(Utils.toByteArray(appSecret), TOKEN_ENCODE_ALG);
        sha256_HMAC.init(secret_key);
        final String hash = urlEncoder.encodeToString(sha256_HMAC.doFinal(Utils.toByteArray(message)));

        builder.append(message)
                .append(".")
                .append(hash);

        return builder.toString();
    }

    protected String createPayload(
            final String appKey,
            final String clientName,
            final String clientKey,
            final String roomName,
            final Long expTime,
            final String host,
            final boolean moderator) {

        final String jwtPayload = String.format(
                JITSI_ACCESS_TOKEN_PAYLOAD.replaceAll(" ", "").replaceAll("\n", ""),
                clientName,
                appKey,
                clientKey,
                host,
                roomName,
                (moderator)
                        ? ",\"moderator\":true"
                        : ",\"moderator\":false",
                (expTime != null)
                        ? String.format(",\"exp\":%s", String.valueOf(expTime))
                        : "");
        return jwtPayload;
    }

    private long forExam(final ProctoringServiceSettings examProctoring) {
        if (examProctoring.examId == null) {
            throw new IllegalStateException("Missing exam identifier from ExamProctoring data");
        }

        long expTime = System.currentTimeMillis() + Constants.DAY_IN_MILLIS;
        if (this.examSessionService.isExamRunning(examProctoring.examId)) {
            final Exam exam = this.examSessionService.getRunningExam(examProctoring.examId)
                    .getOrThrow();
            if (exam.endTime != null) {
                expTime = exam.endTime.getMillis();
            }
        }
        return expTime;
    }

}