package screen;

import java.io.IOException;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * @author Neo-Nevz
 * 
 */
public class ControlMenu {

	public int x;
	public int y;
	public int type;
	public int mathindex;
	public int width;
	public int height;
	public int index;
	private String label;
	private String deskription;
	private Font planfonts;
	private Font largeboldfonts;
	private Image highlight, icon1, icon3;

	public ControlMenu(int x, int y, int type, int index, int mathindex, String label, String deskription) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.index = index;
		this.mathindex = mathindex;
		this.label = label;
		this.deskription = deskription;

		// Fonts -------------------------------------------------------------
		planfonts = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		largeboldfonts = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE);

		// Images ------------------------------------------------------------
		try {
			highlight = Image.createImage("/ControlMenu/highlight.png");
			icon1 = Image.createImage("/ControlMenu/manual.png");
			icon3 = Image.createImage("/ControlMenu/back.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.height = icon1.getHeight() + 20;
		this.width = highlight.getWidth();
	}

	// Draw Icon Menu --------------------------------------------------------
	public void paint(Graphics g) {
		if (index == mathindex) {
			g.drawImage(highlight, this.x, this.y, Graphics.TOP | Graphics.LEFT);
			g.setColor(0xff0055);
			g.setFont(largeboldfonts);
			g.drawString(label, this.x + icon1.getWidth() + 10, this.y + 2, Graphics.TOP | Graphics.LEFT);
			g.setColor(0xdddddd);
			g.setFont(planfonts);
			g.drawString(deskription, this.x + icon1.getWidth() + 10, this.y + 25, Graphics.TOP | Graphics.LEFT);
		} else {
			g.setColor(0x000000);
			g.setFont(largeboldfonts);
			g.drawString(label, this.x + icon1.getWidth() + 10, this.y + 2, Graphics.TOP | Graphics.LEFT);
			g.setColor(0xdddddd);
			g.setFont(planfonts);
			g.drawString(deskription, this.x + icon1.getWidth() + 10, this.y + 25, Graphics.TOP | Graphics.LEFT);
		}

		switch (type) {
		case 1:
			g.drawImage(icon1, this.x + 7, this.y + 7, Graphics.TOP | Graphics.LEFT);
			break;
		case 2:
			g.drawImage(icon3, this.x + 7, this.y + 7, Graphics.TOP | Graphics.LEFT);
			break;
		}
	}
}
