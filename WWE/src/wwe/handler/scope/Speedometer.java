package wwe.handler.scope;

import java.rmi.NotBoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import wwe.util.EditorHandler;

public class Speedometer extends WorkbenchWindowControlContribution {

    public Speedometer() {
    }

    public Speedometer(String id) {
	super(id);
    }

    @Override
    protected Control createControl(Composite parent) {
	Scale speedSlider = new Scale(parent, SWT.HORIZONTAL);

	speedSlider.setMinimum(10);
	speedSlider.setMaximum(1000);
	speedSlider.setIncrement(10);
	speedSlider.setPageIncrement(10);
	speedSlider.setSelection(100);

	speedSlider.addSelectionListener(new SelectionListener() {

	    @Override
	    public void widgetSelected(SelectionEvent e) {
		Scale spinner = (Scale) e.getSource();
		int value = spinner.getSelection();
		try {
		    EditorHandler.getCurrentEditor().getScope()
			    .setTimerIntervall(value);
		} catch (NotBoundException e1) {
		    e1.printStackTrace();
		}
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	    }
	});

	return null;
    }

}
