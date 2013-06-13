package wwe.dialog;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import wwe.util.SimulationScopeHandler;

public class CreateScopeDialog extends TitleAreaDialog {

	private Text widthText;
	private Text nameText;
	private Text heightText;
	private int width;
	private int height;
	private String name;
	private String createButtonText;

	public CreateScopeDialog(Shell parentShell) {
		this(parentShell, Messages.CreateScopeDialog_0, SimulationScopeHandler.WORLD_SIZE,
				SimulationScopeHandler.WORLD_SIZE, ""); //$NON-NLS-1$
	}

	public CreateScopeDialog(Shell parentShell, String createButtonText, int w,
			int h, String name) {
		super(parentShell);
		this.createButtonText = createButtonText;
		this.width = w;
		this.height = h;
		this.name = name;
	}

	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle(Messages.CreateScopeDialog_2);
		// Set the message
		setMessage(
				Messages.CreateScopeDialog_3,
				IMessageProvider.INFORMATION);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		// layout.horizontalAlignment = GridData.FILL;
		parent.setLayout(layout);

		Label label3 = new Label(parent, SWT.NONE);
		label3.setText(Messages.CreateScopeDialog_4);
		// You should not re-use GridData
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		nameText = new Text(parent, SWT.BORDER);
		nameText.setText(this.name);
		nameText.setLayoutData(gridData);
		
		// The text fields will grow with the size of the dialog
		 gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;

		Label label1 = new Label(parent, SWT.NONE);
		label1.setText(Messages.CreateScopeDialog_5);

		widthText = new Text(parent, SWT.BORDER);
		widthText.setText(this.width + ""); //$NON-NLS-1$
		widthText.setLayoutData(gridData);

		Label label2 = new Label(parent, SWT.NONE);
		label2.setText(Messages.CreateScopeDialog_7);
		// You should not re-use GridData
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		heightText = new Text(parent, SWT.BORDER);
		heightText.setText(this.height + ""); //$NON-NLS-1$
		heightText.setLayoutData(gridData);

		return parent;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.CENTER;

		parent.setLayoutData(gridData);
		// Create Add button
		// Own method as we need to overview the SelectionAdapter
		createOkButton(parent, OK, this.createButtonText, true);
		// Add a SelectionListener

		// Create Cancel button
		Button cancelButton = createButton(parent, CANCEL, Messages.CreateScopeDialog_9, false);
		// Add a SelectionListener
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setReturnCode(CANCEL);
				close();
			}
		});
	}

	protected Button createOkButton(Composite parent, int id, String label,
			boolean defaultButton) {
		// increment the number of columns in the button bar
		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.PUSH);
		button.setText(label);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(id));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (isValidInput()) {
					okPressed();
				}
			}
		});
		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
		}
		setButtonLayoutData(button);
		return button;
	}

	private boolean isValidInput() {
		boolean valid = true;
		if (widthText.getText().length() == 0) {
			setErrorMessage(Messages.CreateScopeDialog_10);
			valid = false;
		}
		if (nameText.getText().length() == 0) {
			setErrorMessage(Messages.CreateScopeDialog_11);
			valid = false;
		}
		if (heightText.getText().length() == 0) {
			setErrorMessage(Messages.CreateScopeDialog_12);
			valid = false;
		}
		if (!tryParseInt(widthText.getText())
				&& !tryParseInt(heightText.getText())) {
			setErrorMessage(Messages.CreateScopeDialog_13);
			valid = false;
		}
		return valid;
	}

	private boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	// Coyy textFields because the UI gets disposed
	// and the Text Fields are not accessible any more.
	private void saveInput() {
		width = Integer.parseInt(widthText.getText());
		height = Integer.parseInt(heightText.getText());
		name = nameText.getText();
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}
}