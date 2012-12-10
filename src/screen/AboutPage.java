package screen;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class AboutPage extends Canvas {
	public Image ContentAboutValue;
	public Image header;

	public AboutPage() {
		try {
			header = Image.createImage("/Layout/header.png");
			ContentAboutValue = Image.createImage("/Content/about/about.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Content-Value-About
	public void aboutValue(Graphics g) {
		g.drawImage(ContentAboutValue, getWidth() / 2, header.getHeight() + 1, Graphics.TOP | Graphics.HCENTER);
	}

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
	}

}
