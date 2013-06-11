package wwe.dialog;

import java.io.File;

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

import wwe.Activator;

public class SaveAsToolElement extends TitleAreaDialog {

	private Text nameText;
	private Text fileNameText;
	private String name;

	public SaveAsToolElement(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Save as ToolElement");
		// Set the message
		setMessage(
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam.",
				IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		// layout.horizontalAlignment = GridData.FILL;
		parent.setLayout(layout);

		// The text fields will grow with the size of the dialog
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;

		Label label3 = new Label(parent, SWT.NONE);
		label3.setText("Name:");
		// You should not re-use GridData
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		nameText = new Text(parent, SWT.BORDER);
		nameText.setText("");
		nameText.setLayoutData(gridData);

		Label label4 = new Label(parent, SWT.NONE);
		label4.setText("File name:");
		// You should not re-use GridData
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		fileNameText = new Text(parent, SWT.BORDER);
		fileNameText.setText("");
		fileNameText.setLayoutData(gridData);

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
		createOkButton(parent, OK, "Save", true);

		// Create Cancel button
		Button cancelButton = createButton(parent, CANCEL, "Cancel", false);
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
		if (nameText.getText().length() == 0) {
			setErrorMessage("Please maintain name");
			valid = false;
		}
		if (fileNameText.getText().length() == 0) {
			setErrorMessage("Please maintain name");
			valid = false;
		}
		if (new File(getFileName()).exists()) {
			setErrorMessage("File Exists");
			valid = false;
		}
		return valid;
	}

	public String getFileName() {
		String filename = fileNameText.getText().replace(" ", "_") + ".xml";
		filename = Activator.getDefault().getPreferenceStore()
				.getString("PATH")
				+ "\\" + filename;
		return filename;
	}

	@Override
	protected boolean isResizable() {
		return false;
	}

	// Coyy textFields because the UI gets disposed
	// and the Text Fields are not accessible any more.
	private void saveInput() {
		name = nameText.getText();
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getName() {
		return name;
	}
}
