package com.eclipserunner.views.actions;

import org.eclipse.jface.action.Action;

import com.eclipserunner.RunnerPlugin;
import com.eclipserunner.views.IRunnerView;

/**
 * @author vachacz
 */
public class ToggleFilterAction extends Action {

	private final String preferenceProperty;
	private final IRunnerView runnerView;

	public ToggleFilterAction(String preferenceProperty, IRunnerView runnerView) {
		this.preferenceProperty = preferenceProperty;
		this.runnerView = runnerView;

		boolean active = RunnerPlugin.getDefault().getPreferenceStore().getBoolean(preferenceProperty);
		setChecked(active);
	}

	@Override
	public void run() {
		RunnerPlugin.getDefault().getPreferenceStore().setValue(preferenceProperty, isChecked());

		runnerView.refresh();
	}

}
