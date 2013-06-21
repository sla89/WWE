package fhv.eclipse2013.wwe.impl.toolbox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;

import fhv.eclipse2013.wwe.contract.toolbox.ITool;
import fhv.eclipse2013.wwe.contract.toolbox.IToolCategory;

public class Toolbox implements IToolCategory {

	private static Toolbox instance;
	private static List<String> categories;

	public static Toolbox getInstance() {
		return instance;
	}

	public static List<String> getCategories() {
		return categories;
	}

	public static Toolbox load(String folderName) {
		categories = new ArrayList<>();
		File folder = new File(folderName);
		Toolbox toolbox = new Toolbox("", folder.getAbsolutePath());
		if (folder.exists()) {
			IObservableList elements = loadFolder(folderName, toolbox);
			toolbox.elements = elements;
		}

		instance = toolbox;
		return toolbox;
	}

	private static IObservableList loadFolder(String folderName, ITool parent) {
		IObservableList elements = new WritableList();
		File folder = new File(folderName);
		if (folder.exists()) {
			for (String fileName : folder.list()) {
				File file = new File(folder.getAbsoluteFile() + "\\" + fileName);
				if (file.isDirectory()) {
					ToolCategory category = new ToolCategory(parent,
							file.getName());
					categories.add(category.getPath());
					category.setElements(loadFolder(file.getAbsolutePath(),
							category));
					elements.add(category);
				} else if (getExtension(fileName).equals(".xml")) {
					elements.add(ToolElement.loadFile(file.getAbsolutePath()));
				}
			}
		}
		return elements;
	}

	private static String getExtension(String fileName) {
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			return fileName.substring(i);
		} else {
			return "";
		}
	}

	private String name;
	private String folderName;
	private IObservableList elements;

	private Toolbox(String name, String folderName) {
		this.name = name;
		this.folderName = folderName;
		init();
	}

	private void init() {

	}

	@Override
	public String getName() {
		return name;
	}

	public String getFolderName() {
		return folderName;
	}

	@Override
	public ITool getParent() {
		return null;
	}

	@Override
	public boolean hasChildren() {
		return elements.size() > 0;
	}

	@Override
	public IObservableList getChildren() {
		return elements;
	}

	@Override
	public String getPath() {
		return "";
	}

}
