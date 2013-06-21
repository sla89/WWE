package wwe.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fhv.eclipse2013.wwe.impl.toolbox.Toolbox;

public class CreateElementDialog extends TitleAreaDialog {

	private Text nameText;
	private Combo categoryText;
	private String name;
	private String category;

	public CreateElementDialog(Shell parentShell) {
		this(parentShell, "");
	}

	public CreateElementDialog(Shell parentShell, String name) {
		super(parentShell);
		this.name = name;
	}

	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Create Element Title");
		// Set the message
		setMessage("Create Element description", IMessageProvider.INFORMATION);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		// layout.horizontalAlignment = GridData.FILL;
		parent.setLayout(layout);

		Label label3 = new Label(parent, SWT.NONE);
		label3.setText("Name");
		// You should not re-use GridData
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		nameText = new Text(parent, SWT.BORDER);
		nameText.setText(this.name);
		nameText.setLayoutData(gridData);

		Label label2 = new Label(parent, SWT.NONE);
		label2.setText("Category");
		// You should not re-use GridData
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		categoryText = new Combo(parent, SWT.BORDER);
		categoryText.setItems(getCategories());
		categoryText.setText("");
		categoryText.setLayoutData(gridData);

		return parent;
	}

	private String[] getCategories() {
		return Toolbox.getCategories().toArray(new String[0]);
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
		createOkButton(parent, OK, "Save", true);
		// Add a SelectionListener

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
			setErrorMessage("Name is not valid");
			valid = false;
		}
		// TODO Category validation

		return valid;
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	// Coyy textFields because the UI gets disposed
	// and the Text Fields are not accessible any more.
	private void saveInput() {
		name = nameText.getText();
		category = categoryText.getText();
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getCategory() {
		return category;
	}

	public String getName() {
		return name;
	}
}