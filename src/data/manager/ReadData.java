package data.manager;

/**
 * @author Neo-Nevz
 *
 */
public class ReadData {
	private String data;

	public ReadData() {
		data = "";
	}

	public String getData() {
		return data;
	}

	// Set data
	public void setData(String data) {
		this.data = data;
	}

	// Set data in String Array
	public void setData(String[] data) {
		for (int i = 0; i < data.length; i++) {
			this.data += data[i] + "#";
		}
	}

	// Get Value
	public String getValue(int index) {
		if (data == "") {
			return data;
		}
		int a = -1;
		for (int i = 0; i < index; i++) {
			a = data.indexOf("#", a + 1);
		}
		return data.substring(a + 1, data.indexOf("#", a + 1));
	}

	// Get number Of Value
	public int getNumberOfValue() {
		int counter = 0;
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == '#') {
				counter++;
			}
		}
		return counter;

	}
}
