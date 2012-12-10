package map.manager;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

import screen.Sender;

import com.jappit.midmaps.googlemaps.GoogleMaps;
import com.jappit.midmaps.googlemaps.GoogleMapsCoordinates;
import com.jappit.midmaps.googlemaps.GoogleMapsMarker;
import com.jappit.midmaps.googlemaps.GoogleStaticMap;
import com.jappit.midmaps.googlemaps.GoogleStaticMapHandler;

/**
 * @author Neo-Nevz
 * 
 */
public class MapManager extends Canvas implements GoogleStaticMapHandler {

	public GoogleStaticMap map;
	public GoogleMaps gMaps;

	public MapManager() {

	}

	public void showMap(double latitude, double longitude, int width, int height) {
		gMaps = new GoogleMaps();
		map = gMaps.createMap(width, height, GoogleStaticMap.TYPE_ROADMAP);
		map.setHandler(this);
		GoogleMapsMarker marker = new GoogleMapsMarker(new GoogleMapsCoordinates(latitude, longitude));
		marker.setColor(GoogleStaticMap.COLOR_RED);
		marker.setSize(GoogleMapsMarker.SIZE_MID);
		map.addMarker(marker);
		map.setCenter(new GoogleMapsCoordinates(latitude, longitude));
		map.setZoom(10);
		map.update();
	}

	private void ShowError(String message) {
		Alert error = new Alert("Error", message, null, AlertType.ERROR);
		Sender.display.setCurrent(error);
	}

	public void mapPaint(Graphics g, int width, int height, int anchor) {
		map.draw(g, width, height, anchor);
	}

	public void GoogleStaticMapUpdateError(GoogleStaticMap map, int errorCode, String errorMessage) {
		ShowError("ERROR MAP : " + errorCode + "," + errorMessage);
	}

	public void GoogleStaticMapUpdated(GoogleStaticMap gsm) {
		repaint();
	}

	public void keyPressed(int key) {
		// Get Navigator map
		int gameAction = getGameAction(key);
		if (gameAction == Canvas.UP || gameAction == Canvas.DOWN || gameAction == Canvas.LEFT || gameAction == Canvas.RIGHT) {
			map.move(gameAction);
		}
	}

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
	}
};