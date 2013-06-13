package fhv.eclipse2013.wwe.contract.scope;

import fhv.eclipse2013.wwe.contract.IField;

public interface IFieldAddedEventListener {
	void handleFieldAdded(int x, int y, IField field);

	void handleFieldDeleted(int x, int y, IField field);
}
