package fhv.eclipse2013.wwe.contract.scope;

public interface IStepChangedEventListener {
	public enum Type {
		prepare, next, back, reset;
	}

	void handlePrepareNextState();

	void handleNextStep();

	void handleBackStep();

	void handleReset();
}
