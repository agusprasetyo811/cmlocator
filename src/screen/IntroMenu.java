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
public class IntroMenu extends Canvas {

	public int menuPos, index;
	public int nbMenu = 6;
	public int nbArrow = 2;

	private Font mediumBoldFont;
	private Image[] icon, menus, arrowsLeft, arrowsRight;
	private Image highlight, content;
	private String[] menuLabel;

	public IntroMenu(int index) {
		this.index = index;

		// Fonts -------------------------------------------------------------
		mediumBoldFont = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);

		// Icon and Arrow ----------------------------------------------------
		icon = new Image[nbMenu];
		arrowsLeft = new Image[nbArrow];
		arrowsRight = new Image[nbArrow];

		// Images ------------------------------------------------------------
		try {
			highlight = Image.createImage("/Highlight/Highlight_Skin_4.png");
			content = Image.createImage("/Layout/content_1.png");
			icon[0] = Image.createImage("/Menu/control.png");
			icon[1] = Image.createImage("/Menu/setting.png");
			icon[2] = Image.createImage("/Menu/log.png");
			icon[3] = Image.createImage("/Menu/help.png");
			icon[4] = Image.createImage("/Menu/about.png");
			icon[5] = Image.createImage("/Menu/exit.png");
			arrowsLeft[0] = Image.createImage("/Arrow/arrowLeft.png");
			arrowsLeft[1] = Image.createImage("/Arrow/arrowLeftHover.png");
			arrowsRight[0] = Image.createImage("/Arrow/arrowRight.png");
			arrowsRight[1] = Image.createImage("/Arrow/arrowRightHover.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Add Icon ----------------------------------------------------------
		menus = new Image[5];
		menus[0] = icon[4];
		menus[1] = icon[5];
		menus[2] = icon[0];
		menus[3] = icon[1];
		menus[4] = icon[2];

		menuPos = -getWidth() / 2;

		// Add Icon Label ----------------------------------------------------
		menuLabel = new String[nbMenu];
		menuLabel[0] = "KONTROL HP";
		menuLabel[1] = "PENGATURAN";
		menuLabel[2] = "LOG";
		menuLabel[3] = "BANTUAN";
		menuLabel[4] = "TENTANG";
		menuLabel[5] = "KELUAR";

	}

	// Draw ArrowLeft
	public void arrowLeftSet(Graphics g, int x, int y) {
		for (int i = 0; i < 2; i++) {
			g.drawImage(arrowsLeft[i], x, y, Graphics.TOP | Graphics.HCENTER);
		}
	}

	// Draw Arrow Right
	public void arrowRightSet(Graphics g, int x, int y) {
		for (int i = 0; i < 2; i++) {
			g.drawImage(arrowsRight[i], x, y, Graphics.TOP | Graphics.HCENTER);
		}
	}

	// Draw Icon
	public void icons(Graphics g, int hegiht) {

		for (int i = 0; i < 5; i++) {
			menus[i] = icon[(index + (nbMenu - 3) + i) % nbMenu];
			g.drawImage(menus[i], menuPos + (getWidth() / 2 * i), hegiht, Graphics.BOTTOM | Graphics.HCENTER);
		}
	}

	// Draw Highlight
	public void highlight(Graphics g, int width, int height) {
		g.drawImage(highlight, width, height, Graphics.BOTTOM | Graphics.HCENTER);
	}

	// Draw Menu label
	public void menulabel(Graphics g, int width, int height) {
		g.setColor(0xcdcdcd);
		g.setFont(mediumBoldFont);
		g.drawString(menuLabel[index - 1], width + 1, height + 1, Graphics.TOP | Graphics.HCENTER);
		g.setColor(0x181818);
		g.drawString(menuLabel[index - 1], width, height, Graphics.TOP | Graphics.HCENTER);
	}

	// Draw Content
	public void content(Graphics g, int width, int height) {
		g.drawImage(content, width, height, Graphics.TOP | Graphics.HCENTER);
	}

	protected void paint(Graphics g) {

	}

}
