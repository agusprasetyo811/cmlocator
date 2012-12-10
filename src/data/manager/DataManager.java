package data.manager;

public class DataManager {

	private static DataManager manager = new DataManager();

	// Singleton
	private DataManager() {
		// TODO Auto-generated constructor stub
	}

	public static DataManager getManager() {
		return manager;
	}

	double latitude;
	double longitude;
	String datamentah;

	public void setDatamentah(String datamentah) {
		this.datamentah = datamentah;
	}

	public String getDatamentah() {
		return datamentah;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLongitude() {
		return longitude;
	}
};