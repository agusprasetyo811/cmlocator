package screen;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class SubMenu extends Canvas {

	public String[] subMenuLabel;
	public int nbSubmenu = 6;
	public Image subMenu;
	public int indexSubMenu = 1;

	public SubMenu() {

		// Sub Menu label
		subMenuLabel = new String[nbSubmenu];
		subMenuLabel[0] = "Perbesar ( + )";
		subMenuLabel[1] = "Perkecil ( - )";
		subMenuLabel[2] = "Navigator ON";
		subMenuLabel[3] = "Navigator OFF";
		subMenuLabel[4] = "Reset Posisi Awal";
		subMenuLabel[5] = "Kembali";

	}

	// Sub Menu
	public void drawSubMenu(Graphics g, int nbSubmenu, Image img, int xBottom, int yBottom, int color1, int color2, int indexSubMenu, int barColor1, int barColor2, String[] subMenuLabel, Font f, int highlightedTextColor, int normalTextColor) {
		int itemHeight = f.getHeight() + 10;
		int menuHeigh = itemHeight * nbSubmenu;
		int menuWidth = 0;
		int tempWidth = 0;
		for (int i = 0; i < nbSubmenu; i++) {
			if (tempWidth < f.stringWidth(subMenuLabel[i])) {
				tempWidth = f.stringWidth(subMenuLabel[i]);
			}
		}
		menuWidth = tempWidth + 10;
		img = Image.createImage(menuWidth, menuHeigh);
		Graphics tempGraphics = img.getGraphics();
		gradientBox(tempGraphics, color1, color2, 0, 0, menuWidth, menuHeigh, 1);
		int tempY = 0;
		tempGraphics.setFont(f);
		for (int i = 0; i < nbSubmenu; i++) {
			if (indexSubMenu == i + 1) {
				gradientBox(tempGraphics, barColor1, barColor2, 0, tempY, menuWidth, itemHeight, 1);
				tempGraphics.setColor(highlightedTextColor);
			} else {
				tempGraphics.setColor(normalTextColor);
			}
			tempGraphics.drawString(subMenuLabel[i], 4, tempY + 3, Graphics.LEFT | Graphics.TOP);
			tempY += itemHeight;
		}
		g.drawImage(img, xBottom, yBottom, Graphics.BOTTOM | Graphics.LEFT);
	}

	// GradientBox Submenu
	public void gradientBox(Graphics g, int color1, int color2, int left, int top, int width, int height, int orientation) {
		int max = 0;
		if (orientation == 1) {
			max = height;
		} else if (orientation == 0) {
			max = width;
		}
		;

		for (int i = 0; i < max; i++) {
			int color = midColor(color1, color2, max * (max - 1 - i) / (max - 1), max);
			g.setColor(color);
			if (orientation == 1) {
				g.drawLine(left, top + i, left + width - 1, top + i);
			} else if (orientation == 0) {
				g.drawLine(left + i, top, left + i, top + height - 1);
			}
		}
	}

	// MidColor Submenu
	public int midColor(int color1, int color2, int prop, int max) {
		int red = (((color1 >> 16) & 0xff) * prop + ((color2 >> 16) & 0xff) * (max - prop)) / max;
		int green = (((color1 >> 8) & 0xff) * prop + ((color2 >> 8) & 0xff) * (max - prop)) / max;
		int blue = (((color1 >> 0) & 0xff) * prop + ((color2 >> 0) & 0xff) * (max - prop)) / max;
		int color = red << 16 | green << 8 | blue;
		return color;
	}

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub

	}
}
