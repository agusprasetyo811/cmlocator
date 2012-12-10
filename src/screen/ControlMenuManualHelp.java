package screen;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ControlMenuManualHelp extends Canvas {

	// Help Content
	public Image map;

	public ControlMenuManualHelp() {

		// Content Images
		try {
			map = Image.createImage("/Content/Map/mapHelp.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showHelp(Graphics g, int width, int height) {
		g.drawImage(map, width, height, Graphics.TOP | Graphics.HCENTER);
	}

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
	}
}
