/*
 * Copyright (c) 2022 ETH Zürich, Educational Development and Technology (LET)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ch.ethz.seb.sebserver.gbl.model.exam;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.ethz.seb.sebserver.gbl.Constants;
import ch.ethz.seb.sebserver.gbl.api.EntityType;
import ch.ethz.seb.sebserver.gbl.api.POSTMapper;
import ch.ethz.seb.sebserver.gbl.model.Domain.CLIENT_GROUP;
import ch.ethz.seb.sebserver.gbl.model.exam.ClientGroup.ClientGroupType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientGroupTemplate implements ClientGroupData {

    public static final String ATTR_EXAM_TEMPLATE_ID = "examTemplateId";

    @JsonProperty(CLIENT_GROUP.ATTR_ID)
    public final Long id;

    @JsonProperty(ATTR_EXAM_TEMPLATE_ID)
    public final Long examTemplateId;

    @JsonProperty(CLIENT_GROUP.ATTR_NAME)
    @NotNull(message = "clientGroup:name:notNull")
    @Size(min = 3, max = 255, message = "clientGroup:name:size:{min}:{max}:${validatedValue}")
    public final String name;

    @JsonProperty(CLIENT_GROUP.ATTR_TYPE)
    @NotNull(message = "clientGroup:type:notNull")
    public final ClientGroupType type;

    @JsonProperty(CLIENT_GROUP.ATTR_COLOR)
    public final String color;

    @JsonProperty(CLIENT_GROUP.ATTR_ICON)
    public final String icon;

    @JsonProperty(ClientGroup.ATTR_IP_RANGE_START)
    public final String ipRangeStart;

    @JsonProperty(ClientGroup.ATTR_IP_RANGE_END)
    public final String ipRangeEnd;

    @JsonProperty(ClientGroup.ATTR_CLIENT_OS)
    public final String clientOS;

    @JsonCreator
    public ClientGroupTemplate(
            @JsonProperty(CLIENT_GROUP.ATTR_ID) final Long id,
            @JsonProperty(CLIENT_GROUP.ATTR_EXAM_ID) final Long examTemplateId,
            @JsonProperty(CLIENT_GROUP.ATTR_NAME) final String name,
            @JsonProperty(CLIENT_GROUP.ATTR_TYPE) final ClientGroupType type,
            @JsonProperty(CLIENT_GROUP.ATTR_COLOR) final String color,
            @JsonProperty(CLIENT_GROUP.ATTR_ICON) final String icon,
            @JsonProperty(ClientGroup.ATTR_IP_RANGE_START) final String ipRangeStart,
            @JsonProperty(ClientGroup.ATTR_IP_RANGE_END) final String ipRangeEnd,
            @JsonProperty(ClientGroup.ATTR_CLIENT_OS) final String clientOS) {

        super();
        this.id = id;
        this.examTemplateId = examTemplateId;
        this.name = name;
        this.type = type;
        this.color = color;
        this.icon = icon;
        this.ipRangeStart = ipRangeStart;
        this.ipRangeEnd = ipRangeEnd;
        this.clientOS = clientOS;
    }

    public ClientGroupTemplate(final Long id, final Long examTemplateId, final POSTMapper postParams) {
        super();
        this.id = id;
        this.examTemplateId = examTemplateId;
        this.name = postParams.getString(CLIENT_GROUP.ATTR_NAME);
        this.type = postParams.getEnum(CLIENT_GROUP.ATTR_TYPE, ClientGroupType.class);
        this.color = postParams.getString(CLIENT_GROUP.ATTR_COLOR);
        this.icon = postParams.getString(CLIENT_GROUP.ATTR_ICON);
        this.ipRangeStart = postParams.getString(ClientGroup.ATTR_IP_RANGE_START);
        this.ipRangeEnd = postParams.getString(ClientGroup.ATTR_IP_RANGE_END);
        this.clientOS = postParams.getString(ClientGroup.ATTR_CLIENT_OS);
    }

    public ClientGroupTemplate(final Long id, final ClientGroupTemplate other) {
        super();
        this.id = id;
        this.examTemplateId = other.examTemplateId;
        this.name = other.name;
        this.type = other.type;
        this.color = other.color;
        this.icon = other.icon;
        this.ipRangeStart = other.ipRangeStart;
        this.ipRangeEnd = other.ipRangeEnd;
        this.clientOS = other.clientOS;
    }

    @Override
    public String getModelId() {
        return (this.id == null) ? null : String.valueOf(this.id);
    }

    @Override
    public EntityType entityType() {
        return EntityType.CLIENT_GROUP;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public ClientGroupType getType() {
        return this.type;
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    public Long getExamTemplateId() {
        return this.examTemplateId;
    }

    @Override
    public String getIpRangeStart() {
        return this.ipRangeStart;
    }

    @Override
    public String getIpRangeEnd() {
        return this.ipRangeEnd;
    }

    @Override
    public String getClientOS() {
        return this.clientOS;
    }

    @JsonIgnore
    public String getData() {
        switch (this.type) {
            case IP_V4_RANGE: {
                return this.ipRangeStart + Constants.EMBEDDED_LIST_SEPARATOR + this.ipRangeEnd;
            }
            case CLIENT_OS: {
                return this.clientOS;
            }
            default: {
                return StringUtils.EMPTY;
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ClientGroupTemplate [id=");
        builder.append(this.id);
        builder.append(", examTemplateId=");
        builder.append(this.examTemplateId);
        builder.append(", name=");
        builder.append(this.name);
        builder.append(", type=");
        builder.append(this.type);
        builder.append(", color=");
        builder.append(this.color);
        builder.append(", icon=");
        builder.append(this.icon);
        builder.append(", ipRangeStart=");
        builder.append(this.ipRangeStart);
        builder.append(", ipRangeEnd=");
        builder.append(this.ipRangeEnd);
        builder.append(", clientOS=");
        builder.append(this.clientOS);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ClientGroupTemplate other = (ClientGroupTemplate) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        } else if (!this.id.equals(other.id))
            return false;
        if (this.type != other.type)
            return false;
        return true;
    }

}
