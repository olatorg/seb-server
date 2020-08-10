/*
 * Copyright (c) 2020 ETH Zürich, Educational Development and Technology (LET)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ch.ethz.seb.sebserver.gui.content;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ch.ethz.seb.sebserver.gbl.Constants;
import ch.ethz.seb.sebserver.gbl.api.API;
import ch.ethz.seb.sebserver.gbl.api.API.BulkActionType;
import ch.ethz.seb.sebserver.gbl.api.EntityType;
import ch.ethz.seb.sebserver.gbl.model.EntityDependency;
import ch.ethz.seb.sebserver.gbl.model.EntityKey;
import ch.ethz.seb.sebserver.gbl.model.EntityProcessingReport;
import ch.ethz.seb.sebserver.gbl.model.user.UserInfo;
import ch.ethz.seb.sebserver.gbl.profile.GuiProfile;
import ch.ethz.seb.sebserver.gbl.util.Utils;
import ch.ethz.seb.sebserver.gui.content.action.ActionDefinition;
import ch.ethz.seb.sebserver.gui.form.Form;
import ch.ethz.seb.sebserver.gui.form.FormBuilder;
import ch.ethz.seb.sebserver.gui.form.FormHandle;
import ch.ethz.seb.sebserver.gui.service.i18n.I18nSupport;
import ch.ethz.seb.sebserver.gui.service.i18n.LocTextKey;
import ch.ethz.seb.sebserver.gui.service.page.PageContext;
import ch.ethz.seb.sebserver.gui.service.page.PageService;
import ch.ethz.seb.sebserver.gui.service.page.event.ActionEvent;
import ch.ethz.seb.sebserver.gui.service.page.impl.ModalInputWizard;
import ch.ethz.seb.sebserver.gui.service.page.impl.ModalInputWizard.WizardAction;
import ch.ethz.seb.sebserver.gui.service.page.impl.ModalInputWizard.WizardPage;
import ch.ethz.seb.sebserver.gui.service.page.impl.PageAction;
import ch.ethz.seb.sebserver.gui.service.remote.webservice.api.RestCall;
import ch.ethz.seb.sebserver.gui.service.remote.webservice.api.useraccount.DeleteUserAccount;
import ch.ethz.seb.sebserver.gui.service.remote.webservice.api.useraccount.GetUserAccount;
import ch.ethz.seb.sebserver.gui.service.remote.webservice.api.useraccount.GetUserDependencies;
import ch.ethz.seb.sebserver.gui.table.ColumnDefinition;
import ch.ethz.seb.sebserver.gui.widget.WidgetFactory.CustomVariant;

@Lazy
@Component
@GuiProfile
public class UserAccountDeletePopup {

    private static final Logger log = LoggerFactory.getLogger(UserAccountDeletePopup.class);

    private final static String ARG_WITH_CONFIGS = "WITH_CONFIGS";
    private final static String ARG_WITH_EXAMS = "WITH_EXAMS";

    private final static LocTextKey FORM_TITLE =
            new LocTextKey("sebserver.useraccount.delete.form.title");
    private final static LocTextKey FORM_INFO =
            new LocTextKey("sebserver.useraccount.delete.form.info");
    private final static LocTextKey FORM_REPORT_INFO =
            new LocTextKey("sebserver.useraccount.delete.form.report.info");
    private final static LocTextKey FORM_REPORT_LIST_TYPE =
            new LocTextKey("sebserver.useraccount.delete.form.report.list.type");
    private final static LocTextKey FORM_REPORT_LIST_NAME =
            new LocTextKey("sebserver.useraccount.delete.form.report.list.name");
    private final static LocTextKey FORM_REPORT_LIST_DESC =
            new LocTextKey("sebserver.useraccount.delete.form.report.list.description");

    private final static LocTextKey FORM_REPORT_NONE =
            new LocTextKey("sebserver.useraccount.delete.form.report.empty");
    private final static LocTextKey FORM_NAME =
            new LocTextKey("sebserver.useraccount.delete.form.accountName");
    private final static LocTextKey FORM_CONFIGS =
            new LocTextKey("sebserver.useraccount.delete.form.deleteAlsoConfigs");
    private final static LocTextKey FORM_EXAMS =
            new LocTextKey("sebserver.useraccount.delete.form.deleteAlsoExams");
    private final static LocTextKey ACTION_DELETE =
            new LocTextKey("sebserver.useraccount.delete.form.action.delete");
    private final static LocTextKey ACTION_REPORT =
            new LocTextKey("sebserver.useraccount.delete.form.action.report");

    private final static LocTextKey DELETE_CONFIRM_TITLE =
            new LocTextKey("sebserver.useraccount.delete.confirm.title");

    private final PageService pageService;

    protected UserAccountDeletePopup(final PageService pageService) {
        this.pageService = pageService;
    }

    public Function<PageAction, PageAction> deleteWizardFunction(final PageContext pageContext) {
        return action -> {

            final ModalInputWizard<PageContext> wizard =
                    new ModalInputWizard<PageContext>(
                            action.pageContext().getParent().getShell(),
                            this.pageService.getWidgetFactory())
                                    .setVeryLargeDialogWidth();

            final String page1Id = "DELETE_PAGE";
            final String page2Id = "REPORT_PAGE";
            final Predicate<PageContext> callback = pc -> doDelete(this.pageService, pc);
            final BiFunction<PageContext, Composite, Supplier<PageContext>> composePage1 =
                    (prefPageContext, content) -> composeDeleteDialog(content,
                            (prefPageContext != null) ? prefPageContext : pageContext);
            final BiFunction<PageContext, Composite, Supplier<PageContext>> composePage2 =
                    (prefPageContext, content) -> composeReportDialog(content,
                            (prefPageContext != null) ? prefPageContext : pageContext);

            final WizardPage<PageContext> page1 = new WizardPage<>(
                    page1Id,
                    true,
                    composePage1,
                    new WizardAction<>(ACTION_DELETE, callback),
                    new WizardAction<>(ACTION_REPORT, page2Id));

            final WizardPage<PageContext> page2 = new WizardPage<>(
                    page2Id,
                    false,
                    composePage2,
                    new WizardAction<>(ACTION_DELETE, callback));

            wizard.open(FORM_TITLE, Utils.EMPTY_EXECUTION, page1, page2);

            return action;
        };
    }

    private boolean doDelete(
            final PageService pageService,
            final PageContext pageContext) {

        try {
            final boolean withConfigs = BooleanUtils.toBoolean(pageContext.getAttribute(ARG_WITH_CONFIGS));
            final boolean withExams = BooleanUtils.toBoolean(pageContext.getAttribute(ARG_WITH_EXAMS));
            final EntityKey entityKey = pageContext.getEntityKey();
            final UserInfo currentUser = pageService.getCurrentUser().get();
            final boolean ownAccount = currentUser.getModelId().equals(entityKey.modelId);
            final UserInfo userToDelete = this.pageService.getRestService().getBuilder(GetUserAccount.class)
                    .withURIVariable(API.PARAM_MODEL_ID, entityKey.modelId)
                    .call()
                    .getOrThrow();

            final RestCall<EntityProcessingReport>.RestCallBuilder restCallBuilder = this.pageService.getRestService()
                    .getBuilder(DeleteUserAccount.class)
                    .withURIVariable(API.PARAM_MODEL_ID, entityKey.modelId)
                    .withQueryParam(API.PARAM_BULK_ACTION_TYPE, BulkActionType.HARD_DELETE.name())
                    .withQueryParam(API.PARAM_BULK_ACTION_ADD_INCLUDES, Constants.TRUE_STRING);

            if (withConfigs) {
                restCallBuilder.withQueryParam(
                        API.PARAM_BULK_ACTION_INCLUDES,
                        EntityType.CONFIGURATION_NODE.name());
            }
            if (withExams) {
                restCallBuilder.withQueryParam(
                        API.PARAM_BULK_ACTION_INCLUDES,
                        EntityType.EXAM.name());
            }

            final EntityProcessingReport report = restCallBuilder.call().getOrThrow();

            if (ownAccount) {
                pageService.logout(pageContext);
            } else {
                final PageAction action = this.pageService.pageActionBuilder(pageContext)
                        .newAction(ActionDefinition.USER_ACCOUNT_VIEW_LIST)
                        .create();

                this.pageService.firePageEvent(
                        new ActionEvent(action),
                        action.pageContext());
            }

            final String userName = userToDelete.toName().name;
            final List<EntityKey> dependencies = report.results.stream()
                    .filter(key -> !key.equals(entityKey))
                    .collect(Collectors.toList());
            pageContext.publishPageMessage(
                    DELETE_CONFIRM_TITLE,
                    new LocTextKey(
                            "sebserver.useraccount.delete.confirm.message",
                            userName,
                            dependencies.size(),
                            (report.errors.isEmpty()) ? "no" : String.valueOf((report.errors.size()))));
            return true;
        } catch (final Exception e) {
            log.error("Unexpected error while trying to delete User Account:", e);
            pageContext.notifyUnexpectedError(e);
            return false;
        }
    }

    private Supplier<PageContext> composeDeleteDialog(
            final Composite parent,
            final PageContext pageContext) {

        final Composite grid = this.pageService.getWidgetFactory()
                .createPopupScrollComposite(parent);

        final Label title = this.pageService.getWidgetFactory()
                .labelLocalized(grid, CustomVariant.TEXT_H3, FORM_INFO);
        final GridData gridData = new GridData();
        gridData.horizontalIndent = 10;
        gridData.verticalIndent = 10;
        title.setLayoutData(gridData);

        final EntityKey entityKey = pageContext.getEntityKey();
        final UserInfo userInfo = this.pageService.getRestService()
                .getBuilder(GetUserAccount.class)
                .withURIVariable(API.PARAM_MODEL_ID, entityKey.modelId)
                .call()
                .get();

        final FormHandle<?> formHandle = this.pageService.formBuilder(
                pageContext.copyOf(grid))
                .readonly(false)
                .withDefaultSpanLabel(3)
                .withDefaultSpanInput(4)
                .addField(FormBuilder.text(
                        "USE_NAME",
                        FORM_NAME,
                        userInfo.toName().name)
                        .readonly(true))

                .addField(FormBuilder.checkbox(
                        ARG_WITH_CONFIGS,
                        FORM_CONFIGS))

                .addField(FormBuilder.checkbox(
                        ARG_WITH_EXAMS,
                        FORM_EXAMS))
                .build();

        final Form form = formHandle.getForm();
        return () -> pageContext
                .withAttribute(ARG_WITH_CONFIGS, form.getFieldValue(ARG_WITH_CONFIGS))
                .withAttribute(ARG_WITH_EXAMS, form.getFieldValue(ARG_WITH_EXAMS));
    }

    private Supplier<PageContext> composeReportDialog(
            final Composite parent,
            final PageContext pageContext) {

        final Composite grid = this.pageService.getWidgetFactory()
                .createPopupScrollCompositeFilled(parent);
        final I18nSupport i18nSupport = this.pageService.getI18nSupport();

        final Label title = this.pageService.getWidgetFactory()
                .labelLocalized(grid, CustomVariant.TEXT_H3, FORM_REPORT_INFO);
        final GridData gridData = new GridData();
        gridData.horizontalIndent = 10;
        gridData.verticalIndent = 10;
        title.setLayoutData(gridData);

        try {
            // get selection
            final boolean withConfigs = BooleanUtils.toBoolean(pageContext.getAttribute(ARG_WITH_CONFIGS));
            final boolean withExams = BooleanUtils.toBoolean(pageContext.getAttribute(ARG_WITH_EXAMS));

            // get dependencies
            final EntityKey entityKey = pageContext.getEntityKey();
            final RestCall<Set<EntityDependency>>.RestCallBuilder restCallBuilder = this.pageService.getRestService()
                    .getBuilder(GetUserDependencies.class)
                    .withURIVariable(API.PARAM_MODEL_ID, entityKey.modelId)
                    .withQueryParam(API.PARAM_BULK_ACTION_TYPE, BulkActionType.HARD_DELETE.name())
                    .withQueryParam(API.PARAM_BULK_ACTION_ADD_INCLUDES, Constants.TRUE_STRING);

            if (withConfigs) {
                restCallBuilder.withQueryParam(
                        API.PARAM_BULK_ACTION_INCLUDES,
                        EntityType.CONFIGURATION_NODE.name());
            }
            if (withExams) {
                restCallBuilder.withQueryParam(
                        API.PARAM_BULK_ACTION_INCLUDES,
                        EntityType.EXAM.name());
            }

            final Set<EntityDependency> dependencies = restCallBuilder.call().getOrThrow();
            final List<EntityDependency> list = dependencies.stream().sorted().collect(Collectors.toList());
            this.pageService.<EntityDependency> staticListTableBuilder(list, null)
                    .withEmptyMessage(FORM_REPORT_NONE)
                    .withColumn(new ColumnDefinition<>(
                            "FORM_REPORT_LIST_TYPE",
                            FORM_REPORT_LIST_TYPE,
                            dep -> i18nSupport
                                    .getText("sebserver.overall.types.entityType." + dep.self.entityType.name()) +
                                    " (" + dep.self.getModelId() + ")"))
                    .withColumn(new ColumnDefinition<>(
                            "FORM_REPORT_LIST_NAME",
                            FORM_REPORT_LIST_NAME,
                            dep -> dep.name))
                    .withColumn(new ColumnDefinition<>(
                            "FORM_REPORT_LIST_DESC",
                            FORM_REPORT_LIST_DESC,
                            dep -> dep.description))
                    .compose(pageContext.copyOf(grid));

            return () -> pageContext;
        } catch (final Exception e) {
            log.error("Error while trying to compose User Account delete report page: ", e);
            pageContext.notifyUnexpectedError(e);
            throw e;
        }
    }

}