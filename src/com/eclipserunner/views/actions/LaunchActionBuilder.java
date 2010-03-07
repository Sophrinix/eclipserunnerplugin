package com.eclipserunner.views.actions;

import static com.eclipserunner.Messages.Message_addNewCategory;
import static com.eclipserunner.Messages.Message_addNewCategoryTooltip;
import static com.eclipserunner.Messages.Message_collapseAll;
import static com.eclipserunner.Messages.Message_collapseAllTooltip;
import static com.eclipserunner.Messages.Message_debugConfiguration;
import static com.eclipserunner.Messages.Message_debugConfigurationTooltip;
import static com.eclipserunner.Messages.Message_expandAll;
import static com.eclipserunner.Messages.Message_expandAllTooltip;
import static com.eclipserunner.Messages.Message_openDebugConfigurationsDialog;
import static com.eclipserunner.Messages.Message_openDebugConfigurationsDialogTooltip;
import static com.eclipserunner.Messages.Message_openRunConfigurationsDialog;
import static com.eclipserunner.Messages.Message_openRunConfigurationsDialogTooltip;
import static com.eclipserunner.Messages.Message_remove;
import static com.eclipserunner.Messages.Message_removeTooltip;
import static com.eclipserunner.Messages.Message_rename;
import static com.eclipserunner.Messages.Message_renameTooltip;
import static com.eclipserunner.Messages.Message_runConfiguration;
import static com.eclipserunner.Messages.Message_runConfigurationTooltip;
import static org.eclipse.debug.ui.IDebugUIConstants.ID_DEBUG_LAUNCH_GROUP;
import static org.eclipse.debug.ui.IDebugUIConstants.ID_RUN_LAUNCH_GROUP;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.eclipserunner.RunnerPlugin;
import com.eclipserunner.model.IRunnerModel;
import com.eclipserunner.views.ILaunchConfigurationSelection;

/**
 * Builder creates Actions related to test launching
 * 
 * @author vachacz
 */
public final class LaunchActionBuilder {

	private static final String IMG_RUN                  = "run.gif";
	private static final String IMG_RUN_CONFIGURATIONS   = "run_configuration.gif";
	private static final String IMG_DEBUG                = "debug.gif";
	private static final String IMG_DEBUG_CONFIGURATIONS = "debug_configuration.gif";
	private static final String IMG_HELP                 = "help.gif";
	private static final String IMG_NEW_CATEGORY         = "category-new.gif";
	private static final String IMG_EXPAND_ALL           = "expandall.gif";

	private ILaunchConfigurationSelection launchConfigurationSelection;
	private IRunnerModel runnerModel;

	private LaunchActionBuilder() {
		// use factory method instead
	}

	public static final LaunchActionBuilder newInstance() {
		return new LaunchActionBuilder();
	}

	public LaunchActionBuilder withLaunchConfigurationSelection(ILaunchConfigurationSelection launchConfigurationSelection) {
		this.launchConfigurationSelection = launchConfigurationSelection;
		return this;
	}
	
	public LaunchActionBuilder withRunnerModel(IRunnerModel model) {
		this.runnerModel = model;
		return this;
	}

	public Action createShowRunConfigurationDialogAction() {
		Action action = new ShowLaunchConfigurationsDialogAction(launchConfigurationSelection, ID_RUN_LAUNCH_GROUP);
		configureAction(action, Message_openRunConfigurationsDialog, Message_openRunConfigurationsDialogTooltip, IMG_RUN_CONFIGURATIONS);
		return action;
	}

	public Action createShowDebugConfigurationDialogAction() {
		Action action = new ShowLaunchConfigurationsDialogAction(launchConfigurationSelection, ID_DEBUG_LAUNCH_GROUP);
		configureAction(action, Message_openDebugConfigurationsDialog, Message_openDebugConfigurationsDialogTooltip, IMG_DEBUG_CONFIGURATIONS);
		return action;
	}

	public Action createRunConfigurationAction() {
		Action action = new LaunchConfigurationAction(launchConfigurationSelection, ID_RUN_LAUNCH_GROUP);
		configureAction(action, Message_runConfiguration, Message_runConfigurationTooltip, IMG_RUN);
		return action;
	}

	public Action createDebugConfigurationAction() {
		Action action = new LaunchConfigurationAction(launchConfigurationSelection, ID_DEBUG_LAUNCH_GROUP);
		configureAction(action, Message_debugConfiguration, Message_debugConfigurationTooltip, IMG_DEBUG);
		return action;
	}
	
	public Action createAddNewCategoryAction() {
		Action action = new AddNewCategoryAction(runnerModel);
		configureAction(action, Message_addNewCategory, Message_addNewCategoryTooltip, IMG_NEW_CATEGORY);
		return action;
	}

	public Action createCollapseAllAction(TreeViewer viewer) {
		Action action = new CollapseAllAction(viewer);
		configureAction(action, Message_collapseAll, Message_collapseAllTooltip, getSharedImage(ISharedImages.IMG_ELCL_COLLAPSEALL));
		return action;
	}

	public Action createExpandAllAction(TreeViewer viewer) {
		Action action = new ExpandAllAction(viewer);
		configureAction(action, Message_expandAll, Message_expandAllTooltip, IMG_EXPAND_ALL);
		return action;
	}
	
	public Action createRenameAction() {
		Action action = new RenameConfigOrCategoryAction(launchConfigurationSelection, runnerModel);
		configureAction(action, Message_rename, Message_renameTooltip);
		return action;
	}
	
	public Action createRemoveAction() {
		Action action = new RemoveConfigOrCategoryAction(launchConfigurationSelection, runnerModel);
		configureAction(action, Message_remove, Message_removeTooltip, getSharedImage(ISharedImages.IMG_ETOOL_DELETE));
		return action;
	}

	private final void configureAction(Action action, String title, String tooltip, String imageFileName) {
		configureAction(action, title, tooltip, RunnerPlugin.getDefault().getImageDescriptor(imageFileName));
	}
	
	private final void configureAction(Action action, String title, String tooltip, ImageDescriptor imageDescriptor) {
		action.setImageDescriptor(imageDescriptor);
		configureAction(action, title, tooltip);
	}
	
	private final void configureAction(Action action, String title, String tooltip) {
		action.setText(title);
		action.setToolTipText(tooltip);
	}
	
	private ImageDescriptor getSharedImage(String image) {
		return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(image);
	}

	// TODO LWA BARY
	public Action createAboutAction() {
		Action action = new Action() {
			@Override
			public void run() {
				MessageDialog.openInformation(
						RunnerPlugin.getRunnerShell(), "Eclipse Runner View", "About action executed!"
				);
			}
		};
		configureAction(action, "About ...", "About ...", IMG_HELP);
		return action;
	}

}
