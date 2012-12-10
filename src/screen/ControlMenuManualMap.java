package screen;

import java.util.Hashtable;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

import map.manager.MapManager;
import data.manager.RMS;

public class ControlMenuManualMap extends Canvas {

	public MapManager mapManager;
	public double latitude = 0.0;
	public double longitude = 0.0;
	public String dataLokasiMentah;
	public String latitudeValue;
	public String longitudeValue;
	public int mapWidth = 295;
	public int mapHeight = 153;
	public String contentMap;
	public boolean allowPressNavigator;

	public ControlMenuManualMap(boolean navigate) {
		this.allowPressNavigator = navigate;

		mapManager = new MapManager();

		// Load data Lokasi Real
		Hashtable loadDataLokasiReal = RMS.load("dataLokasiReal");
		if (loadDataLokasiReal != null) {
			latitudeValue = loadDataLokasiReal.get("latitude").toString();
			longitudeValue = loadDataLokasiReal.get("longitude").toString();
			System.out.println("Data MAP Diload");
		} else {
			latitudeValue = "0.0";
			longitudeValue = "0.0";
			System.out.println("Data MAP Kosong");
		}

		// Inisialisasi nilai longitude dan latitude
		this.latitude = Double.parseDouble(latitudeValue);
		this.longitude = Double.parseDouble(longitudeValue);

		// Tampilkan MAP
		if (latitude != 0.0 || longitude != 0.0) {
			new Thread() {
				public void run() {
					mapManager.showMap(latitude, longitude, mapWidth, mapHeight);
				};
			}.start();
		} else {
			contentMap = "Data Masih kosong";
		}
	}

	public void showMaps(Graphics g, int width, int height) {
		if (latitude != 0.0 && longitude != 0.0) {
			mapManager.mapPaint(g, width, height, Graphics.TOP | Graphics.HCENTER);
		} else {
			g.drawString(contentMap, getWidth() / 2, getHeight() / 2, Graphics.TOP | Graphics.HCENTER);
		}
	}

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub

	}
}
