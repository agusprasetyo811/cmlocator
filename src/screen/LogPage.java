package screen;

import java.io.IOException;
import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import data.manager.RMS;

public class LogPage {

	public String latitudeValue;
	public String longitudeValue;
	private String phoneLog;
	private String timeSearch;
	private Image header;

	public LogPage() {
		// Images
		try {
			header = Image.createImage("/Layout/header.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logValue() {
		// Load Phone Number Target
		Hashtable loadPhoneNumber = RMS.load("senderData");
		if (loadPhoneNumber != null) {
			phoneLog = loadPhoneNumber.get("noSender").toString();
		} else {
			phoneLog = "-";
		}

		// Load Recent Time Location
		Hashtable loadRecentTime = RMS.load("dataTimeSender");
		if (loadRecentTime != null) {
			timeSearch = loadRecentTime.get("recentDate").toString().substring(0, 19);
		} else {
			timeSearch = "-";
		}

		// Mengambil nilai data lokasi real untuk ditampilkan di log
		Hashtable loadDataLokasiReal = RMS.load("dataLokasiReal");
		if (loadDataLokasiReal != null) {
			latitudeValue = loadDataLokasiReal.get("latitude").toString();
			longitudeValue = loadDataLokasiReal.get("longitude").toString();
		} else {
			latitudeValue = "0.0";
			longitudeValue = "0.0";
		}
	}

	public void logContent(Graphics g) {
		// Memanggil method logValue
		logValue();

		String phoneTargetLabel = "Mobile receiver : ";
		String recentTimeSearchLabel = "Pencarian Terakhir : ";
		String recentLocateLabel = "Lokasi Terakhir ";
		String longitudeLabel = "Longitude : ";
		String latitudeLabel = "Latitude : ";

		// LoadData Lokasi Real
		g.setFont(FontLibs.plainfonts);
		g.drawString(phoneTargetLabel, 20, header.getHeight() + 3, Graphics.LEFT | Graphics.TOP);
		g.setFont(FontLibs.plainfontsitalic);
		g.drawString(phoneLog, 40, header.getHeight() + 24, Graphics.LEFT | Graphics.TOP);
		g.setFont(FontLibs.plainfonts);
		g.drawString(recentTimeSearchLabel, 20, header.getHeight() + 46, Graphics.LEFT | Graphics.TOP);
		g.setFont(FontLibs.plainfontsitalic);
		g.drawString(timeSearch, 40, header.getHeight() + 66, Graphics.LEFT | Graphics.TOP);
		g.setFont(FontLibs.plainfonts);
		g.drawString(recentLocateLabel, 20, header.getHeight() + 87, Graphics.LEFT | Graphics.TOP);
		g.setFont(FontLibs.plainfontsitalic);
		g.drawString(latitudeLabel, 40, header.getHeight() + 108, Graphics.LEFT | Graphics.TOP);
		g.drawString(latitudeValue, 130, header.getHeight() + 108, Graphics.LEFT | Graphics.TOP);
		g.drawString(longitudeLabel, 40, header.getHeight() + 129, Graphics.LEFT | Graphics.TOP);
		g.drawString(longitudeValue, 145, header.getHeight() + 129, Graphics.LEFT | Graphics.TOP);
	}

}
