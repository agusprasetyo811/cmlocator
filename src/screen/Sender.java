package screen;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * @author Neo-Nevz
 * 
 */
public class Sender extends MIDlet {

	public static Display display;
	public static Sender midlet;
	public static Image images;
	public static boolean keepgoing = true;
	Displayable displayable;
	Canvas loading;

	public Sender() {
		midlet = this;
		display = Display.getDisplay(this);

		try {
			images = Image.createImage("/SplashScreen/splashscreen.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

	}

	protected void pauseApp() {

	}

	protected void startApp() throws MIDletStateChangeException {
		loading();
		display.setCurrent(loading);
		new Thread() {
			public void run() {
				Display.getDisplay(midlet).setCurrent(new BaseMenuSender(1));
			};
		}.start();
	}

	public Displayable getDisplayable() {
		return displayable;
	}

	public void setDisplayable(Displayable displayable) {
		this.displayable = displayable;
	}

	// Loading Awal
	public void loading() {
		loading = new Canvas() {
			protected void paint(Graphics g) {
				setFullScreenMode(true);
				g.setColor(0x000000);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.drawImage(images, getWidth() / 2, getHeight() / 2, Graphics.HCENTER | Graphics.VCENTER);
				g.setColor(0xffffff);
				g.drawString("Loading...", getWidth() / 2, getHeight() - 60, Graphics.HCENTER | Graphics.TOP);
			}
		};
	}
}
