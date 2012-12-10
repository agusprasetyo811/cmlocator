package screen;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * @author Neo-Nevz
 * 
 */
public class ControlMenuManualList extends Canvas {

	public int x;
	public int y;
	public int type;
	public int mathindex;
	public int width;
	public int height;
	public int indexListMenu;
	private String label;
	private Font largeboldfonts;
	private Image highlight;
	private Image icon1;
	private Image icon2;
	private Image icon3;

	public ControlMenuManualList(int x, int y, int type, int indexListMenu, int mathindex, String label) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.indexListMenu = indexListMenu;
		this.mathindex = mathindex;
		this.label = label;

		// Fonts --------------------------------------------------------------
		largeboldfonts = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE);

		// Images -------------------------------------------------------------
		try {
			highlight = Image.createImage("/ControlMenu/list_control_menu.png");
			icon1 = Image.createImage("/ControlMenu/location.png");
			icon2 = Image.createImage("/ControlMenu/vibrate.png");
			icon3 = Image.createImage("/ControlMenu/alarm.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.height = icon1.getHeight() + 20;
		this.width = highlight.getWidth();
	}

	// Draw Label and Icon
	public void paint(Graphics g) {
		if (indexListMenu == mathindex) {
			g.drawImage(highlight, getWidth() / 2, this.y, Graphics.TOP | Graphics.HCENTER);
			g.setColor(0xff0055);
			g.setFont(largeboldfonts);
			g.drawString(label, this.x + icon1.getWidth() + 40, this.y + 2, Graphics.TOP | Graphics.LEFT);
		} else {
			g.setColor(0x000000);
			g.setFont(largeboldfonts);
			g.drawString(label, this.x + icon1.getWidth() + 40, this.y + 2, Graphics.TOP | Graphics.LEFT);
		}

		switch (type) {
		case 1:
			g.drawImage(icon1, getWidth() / 2 - 105, this.y + 2, Graphics.TOP | Graphics.HCENTER);
			break;
		case 2:
			g.drawImage(icon2, getWidth() / 2 - 105, this.y + 2, Graphics.TOP | Graphics.HCENTER);
			break;
		case 3:
			g.drawImage(icon3, getWidth() / 2 - 105, this.y + 2, Graphics.TOP | Graphics.HCENTER);
			break;
		}
	}
}
