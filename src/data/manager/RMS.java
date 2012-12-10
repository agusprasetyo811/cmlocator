package data.manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

/**
 * @author Neo-Nevz
 *
 */
public class RMS {

	// Saving data
	public static void save(String RMSname, Hashtable data) {

		try {
			RecordStore rs = RecordStore.openRecordStore(RMSname, true);
			ByteArrayOutputStream aos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(aos);
			serialize(dos, data);
			try {
				rs.setRecord(1, aos.toByteArray(), 0, aos.size());
			} catch (InvalidRecordIDException e) {
				rs.addRecord(aos.toByteArray(), 0, aos.size());
			}
			rs.closeRecordStore();

		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Load data
	public static Hashtable load(String RMSname) {
		try {
			RecordStore rs = RecordStore.openRecordStore(RMSname, true);
			RecordEnumeration e = rs.enumerateRecords(null, null, false);
			if (e.hasNextElement()) {
				byte[] buff = e.nextRecord();
				ByteArrayInputStream ais = new ByteArrayInputStream(buff);
				DataInputStream dis = new DataInputStream(ais);
				return (unselialize(dis));
			}
			rs.closeRecordStore();

		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Hashtable unselialize(DataInputStream in) throws IOException {
		Hashtable dataProvider = new Hashtable();
		while (in.available() > 0) {
			String key = in.readUTF();
			String value = in.readUTF();
			dataProvider.put(key, value);
		}
		return dataProvider;
	}

	private static final void serialize(DataOutputStream out, Hashtable data) throws IOException {
		Enumeration e = data.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = (String) data.get(key);
			out.writeUTF(key);
			out.writeUTF(value);
		}
	}

	// Clear data
	public static final void clear(String RMSname) {
		try {
			RecordStore.deleteRecordStore(RMSname);

		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}
}
