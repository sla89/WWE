package fhv.eclipse2013.wwe.impl.field;

import java.awt.Point;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.IFieldNeighbours;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

public class MooreNeighbours extends AbstractNeighbours implements
		IFieldNeighbours {

	public MooreNeighbours(ISimulationScope scope, IField field,
			Point coordinate) {
		super(scope, field, coordinate);
	}

	@Override
	public void init() {
		this.fields = new IField[8];
		int i = 0;
		// Above left
		this.initField(-1, -1, i++);

		// Above
		this.initField(0, -1, i++);

		// Above right
		this.initField(+1, -1, i++);

		// Left
		this.initField(-1, 0, i++);

		// Right
		this.initField(+1, 0, i++);

		// Below left
		this.initField(-1, +1, i++);

		// Below
		this.initField(0, +1, i++);

		// Below right
		this.initField(+1, +1, i++);
	}

}
