package screen.input.form;

import javax.microedition.lcdui.Graphics;

/**
 * @author Neo-Nevz
 *
 */
public class CustomScrollBar {
	public int xArea;
	public int yArea;
	public int height;
	public int barHeight;
	public int yBar;
	public int width;

	public CustomScrollBar(int yArea, int height, int width) {
		this.yArea = yArea;
		this.height = height;
		this.width = width;
	}

	public void setBarProperties(int yContent, int currYContent, int contentHeight, int windowHeight) {
		if (contentHeight > windowHeight) {
			barHeight = (windowHeight * height) / contentHeight;
			yBar = (((yContent - currYContent) * height) / contentHeight) + yArea;
		} else {
			barHeight = height;
			yBar = yArea;
		}
	}

	public void paint(Graphics g) {
		gradientBox(g, 0xAAAAAA, 0xDDDDDD, xArea, yArea, width, height, 0);
		gradientBox(g, 0xE670E8, 0xA849A3, xArea, yBar, width, barHeight, 0);
	}

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

	public int midColor(int color1, int color2, int prop, int max) {
		int red = (((color1 >> 16) & 0xff) * prop + ((color2 >> 16) & 0xff) * (max - prop)) / max;
		int green = (((color1 >> 8) & 0xff) * prop + ((color2 >> 8) & 0xff) * (max - prop)) / max;
		int blue = (((color1 >> 0) & 0xff) * prop + ((color2 >> 0) & 0xff) * (max - prop)) / max;
		int color = red << 16 | green << 8 | blue;
		return color;
	}
};