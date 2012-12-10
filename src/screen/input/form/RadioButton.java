package screen.input.form;

import java.io.IOException;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * @author Neo-Nevz
 * 
 */
public class RadioButton {
	public String label;
	public boolean dotted;
	public boolean highlighted;
	public int textColor;
	public int highlightedTextColor;
	public int index;
	public int componentIndex;
	public int xArea;
	public int yArea;
	public int widthArea;
	public boolean bold;
	private Image dot;
	public int height;

	// Fonts
	private Font plainfonts;
	private Font boldfonts;

	public RadioButton(String label, boolean dotted, int textColor, int highlightedTextColor, boolean bold) {
		this.label = label;
		this.dotted = dotted;
		this.textColor = textColor;
		this.highlightedTextColor = highlightedTextColor;
		this.bold = bold;

		// Fonts
		plainfonts = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		boldfonts = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
		Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_ITALIC, Font.SIZE_SMALL);

		try {
			dot = Image.createImage("/Form/radio.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setXW(int x, int w) {
		this.xArea = x;
		this.widthArea = w;
	}

	public void paint(Graphics g) {
		Font f;
		if (bold == true) {
			f = boldfonts;
		} else {
			f = plainfonts;
		}
		if (index == componentIndex) {
			highlighted = true;
			gradientBox(g, 0xE670E8, 0xA849A3, xArea, yArea, widthArea, f.getHeight() + 8, 1);
		}
		g.drawImage(dot, xArea + 5, yArea + 3, Graphics.TOP | Graphics.LEFT);
		if (dotted == true) {
			g.setColor(0x07af03);
			g.fillArc(xArea + 9, yArea + 7, 7, 7, 0, 360);
		}
		int col;
		if (index == componentIndex) {
			highlighted = true;
			col = highlightedTextColor;
		} else {
			col = textColor;
		}

		drawCutText(g, splitString(label), f, col, xArea + 25, yArea + 3, widthArea);
		calculateArea(g, splitString(label), f, 1, xArea, yArea, widthArea, false);
	}

	public void keyPressed(int keyCode) {
		if (index == componentIndex) {
			if (keyCode == -5) {
				if (this.dotted == false) {
					this.dotted = true;
				}
			}
		}
	}

	/*
	 * Split one lateral String parameter into array of words, blank characters are ignored.
	 * 
	 * @param: String str
	 * 
	 * @return: String[] result
	 * 
	 * @Andik, 2010-08-26
	 */
	private String[] splitString(String str) {
		int strLength = str.length();
		int nbWords = 0;
		boolean startCounting = false;
		for (int i = 0; i < strLength; i++) {
			if (str.charAt(i) == ' ') {
				if (startCounting == true) {
					nbWords++;
					startCounting = false;
				}
			} else {
				startCounting = true;
			}
			if (i == strLength - 1) {
				if (str.charAt(strLength - 1) != ' ') {
					nbWords++;
					startCounting = false;
				}
			}
		}
		// for debugging purpose only ------
		// System.out.println("Jumlah Kata = " + nbWords);
		// ------------
		String[] result = new String[nbWords];
		for (int i = 0; i < nbWords; i++) {
			result[i] = "";
		}
		int resultIndex = 0;
		startCounting = false;
		for (int i = 0; i < strLength; i++) {
			if (str.charAt(i) == ' ') {
				if (startCounting == true) {
					startCounting = false;
					resultIndex++;
				}
			} else {
				startCounting = true;
				result[resultIndex] += str.charAt(i);
			}
		}
		// for debugging purpose only ------
		// for (int i = 0; i < nbWords; i++) {
		// System.out.println(result[i]);
		// }
		// ------------
		return result;
	}

	/*
	 * Draw one line text into graphics context from array of words with certain properties and area parameters. Assumption: the value of area's width is reasonable enough compared to the width of each words.
	 * 
	 * @param: Graphics g String[] words Font f int color int space int xArea int yArea int widthArea
	 * 
	 * @Andik, 2010-08-26
	 */
	public void drawCutText(Graphics g, String[] words, Font f, int color, int xArea, int yArea, int widthArea) {
		int currentPos = xArea;
		int currentWidth = f.stringWidth(words[0]);
		int tempWidth = 0;
		int threePointWidth = f.stringWidth(" ...");
		g.setFont(f);
		g.setColor(color);
		int i = 0;
		while (i <= words.length - 1) {
			if (tempWidth + f.stringWidth(words[i] + threePointWidth) > widthArea) {
				break;
			}
			g.drawString(words[i], currentPos, yArea, Graphics.LEFT | Graphics.TOP);
			// System.out.println(words[i]);
			currentWidth = f.stringWidth(words[i]) + 3;
			currentPos += currentWidth;
			tempWidth = currentPos - xArea;
			i++;
			// for debugging purpose only ------
			// System.out.println(currentWidth);
			// System.out.println(words[i]);
			// ------------
		}
		if (i < words.length - 1) {
			g.drawString("...", currentPos, yArea, Graphics.LEFT | Graphics.TOP);
		}
	}

	/*
	 * Calculate text area.
	 * 
	 * @param: Graphics g String[] words Font f int color int space int xArea int yArea int widthArea
	 * 
	 * @Andik, 2010-08-27
	 */
	public void calculateArea(Graphics g, String[] words, Font f, int space, int xArea, int yArea, int widthArea, boolean multiLine) {
		int firstLine = yArea;
		int fontHeight = f.getHeight();
		int interval = fontHeight * space;
		int currentPos = xArea;
		int currentWidth = f.stringWidth(words[0]);
		int tempWidth = 0;
		g.setFont(f);
		if (multiLine == true) {
			for (int i = 0; i < words.length; i++) {
				if (tempWidth + f.stringWidth(words[i]) >= widthArea) {
					yArea += interval;
					currentPos = xArea;
				}
				currentWidth = f.stringWidth(words[i]) + 3;
				currentPos += currentWidth;
				tempWidth = currentPos - xArea;
			}
			this.height = yArea - firstLine + fontHeight + 10;
		} else {
			// this.height = interval;
			this.height = f.getHeight() + 8;
		}
		// for debugging purpose only ------
		// System.out.println("Tingginya = " + this.height);
		// ------------
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