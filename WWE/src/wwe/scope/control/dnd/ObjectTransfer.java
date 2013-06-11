package wwe.scope.control.dnd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import fhv.eclipse2013.wwe.contract.toolbox.IToolElement;
import fhv.eclipse2013.wwe.impl.toolbox.ToolElement;

public class ObjectTransfer extends ByteArrayTransfer {

	public static ObjectTransfer elementTransfer = new ObjectTransfer(
			IToolElement.class.getName());

	private String[] typeNames;
	private int[] typeIDs;

	public ObjectTransfer(String typeName) {
		this.typeNames = new String[] { typeName };
		this.typeIDs = new int[] { registerType(typeName) };
	}

	public ObjectTransfer(String[] typeNames) {
		this.typeNames = typeNames;
		int i = 0;
		this.typeIDs = new int[typeNames.length];
		for (String string : typeNames) {
			this.typeIDs[i] = registerType(string);
			i++;
		}
	}

	@Override
	public void javaToNative(Object object, TransferData transferData) {
		if ((object == null)) {
			return;
		}

		if (this.isSupportedType(transferData)) {
			IToolElement element = (IToolElement) object;
			try {
				// write data to a byte array and then ask super to convert to
				// pMedium
				ByteArrayOutputStream out = new ByteArrayOutputStream();

				out.write(element.getXml().getBytes());

				out.close();

				super.javaToNative(out.toByteArray(), transferData);

			} catch (IOException e) {
			}
		}
	}

	@Override
	public Object nativeToJava(TransferData transferData) {

		if (this.isSupportedType(transferData)) {

			byte[] buffer = (byte[]) super.nativeToJava(transferData);
			if (buffer == null) {
				return null;
			}

			IToolElement todo = null;
			try {
				ByteArrayInputStream in = new ByteArrayInputStream(buffer);
				byte[] bytes = new byte[in.available()];
				int i = 0;
				while (in.available() > 0) {
					bytes[i] = (byte) in.read();
					i++;
				}
				String x = new String(bytes);
				in.close();
				todo = (IToolElement) ToolElement.loadString(x);
			} catch (IOException ex) {
				return null;
			}
			return todo;
		}

		return null;
	}

	@Override
	protected String[] getTypeNames() {
		return this.typeNames;
	}

	@Override
	protected int[] getTypeIds() {
		return this.typeIDs;
	}
}
