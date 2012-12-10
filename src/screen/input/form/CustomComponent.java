package screen.input.form;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;

import screen.Sender;

/**
 * @author Neo-Nevz
 * 
 */
public class CustomComponent implements CommandListener {
	public String label;
	public String value;

	public boolean bold;
	public boolean checked;
	public boolean highlighted;

	public int textColor;
	public int highlightedTextColor;
	public int index;
	public int componentIndex;
	public int xArea;
	public int yArea;
	public int widthArea;
	public int height = 0;
	public int type;

	private Image edit;
	private Image check;
	private Image box;

	private List items;
	public Vector radioElement;
	private TextBox textBox;

	private Command CMD_OK_01;
	private Command CMD_CANCEL_01;
	private Command CMD_OK_02;

	private Image image;

	// Fonts
	private Font plainfonts;
	private Font boldfonts;

	public static final int TYPE_CHECK_BOX = 1;
	public static final int TYPE_COMBO_BOX = 2;
	public static final int TYPE_INPUT_TEXT = 3;
	public static final int TYPE_RADIO = 4;
	public static final int TYPE_IMAGES = 5;

	public CustomComponent(String label, int textColor, int highlightedTextColor, boolean bold, String value, Image image, int type) {
		this.label = label;
		this.textColor = textColor;
		this.highlightedTextColor = highlightedTextColor;
		this.bold = bold;
		this.value = value;
		this.image = image;
		this.type = type;

		try {
			edit = Image.createImage("/Form/edit.png");
			check = Image.createImage("/Form/ok.png");
			box = Image.createImage("/Form/box.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		plainfonts = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		boldfonts = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);

		radioElement = new Vector();
		String[] temp = {};

		items = new List(label, Choice.IMPLICIT, temp, null);
		CMD_OK_01 = new Command("Ok", Command.ITEM, 1);
		CMD_CANCEL_01 = new Command("Cancel", Command.ITEM, 2);
		items.addCommand(CMD_OK_01);
		items.addCommand(CMD_CANCEL_01);
		items.setCommandListener(this);

		textBox = new TextBox(label, value, 1000, TextField.PHONENUMBER);
		CMD_OK_02 = new Command("Ok", Command.ITEM, 1);
		textBox.addCommand(CMD_OK_02);
		textBox.setCommandListener(this);
	}

	public void setArea(int xArea, int yArea, int widthArea) {
		this.xArea = xArea;
		this.yArea = yArea;
		this.widthArea = widthArea;
	}

	public void setXW(int x, int w) {
		if (type == 4) {
			for (int i = 0; i < radioElement.size(); i++) {
				((RadioButton) radioElement.elementAt(i)).setXW(x, w);
			}
		} else {
			this.xArea = x;
			this.widthArea = w;
		}
	}

	// Method untuk menggambar komponen Check Box ke layar
	private void drawCheckBox(Graphics g) {
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
		g.setColor(0x444444);
		g.fillRect(xArea + 5, yArea + 5, 14, 14);
		gradientBox(g, 0xCCCCCC, 0xFFFFFF, xArea + 7, yArea + 7, 10, 10, 1);
		if (checked == true) {
			g.drawImage(check, xArea + 5, yArea + 3, Graphics.TOP | Graphics.LEFT);
		}
		int col;
		if (index == componentIndex) {
			highlighted = true;
			col = highlightedTextColor;
		} else {
			col = textColor;
		}
		drawCutText(g, splitString(label), f, col, xArea + 25, yArea + 3, widthArea);
	}

	// Method untuk menggambar komponen Combo Box ke layar
	private void drawComboBox(Graphics g) {
		Font f;
		if (bold == true) {
			f = boldfonts;
		} else {
			f = plainfonts;
		}
		if (index == componentIndex) {
			highlighted = true;
			gradientBox(g, 0xE670E8, 0xA849A3, xArea, yArea, widthArea, (f.getHeight() + 8) * 2, 1);
		}
		g.setColor(0x444444);
		g.fillRect(xArea + 5, yArea + f.getHeight() + 4, widthArea - box.getWidth() - 8 - 5, f.getHeight() + 8);
		gradientBox(g, 0xCCCCCC, 0xFFFFFF, xArea + 7, yArea + 2 + f.getHeight() + 4, widthArea - box.getWidth() - 17, f.getHeight() + 4, 1);
		if (value != "") {
			drawCutText(g, splitString(value), plainfonts, 0x222222, xArea + 10, yArea + 4 + f.getHeight() + 4, widthArea - box.getWidth() - 20);
		}
		g.drawImage(box, xArea + 5 + widthArea - 10, yArea + 3 + f.getHeight() + 4, Graphics.TOP | Graphics.RIGHT);
		int col;
		if (index == componentIndex) {
			highlighted = true;
			col = highlightedTextColor;
		} else {
			col = textColor;
		}
		drawCutText(g, splitString(label), f, col, xArea + 5, yArea + 3, widthArea - 5);
	}

	// Method untuk menggambar komponen Input Text ke layar
	private void drawInputText(Graphics g, String value) {
		Font f;
		if (bold == true) {
			f = boldfonts;
		} else {
			f = plainfonts;
		}
		if (index == componentIndex) {
			highlighted = true;
			gradientBox(g, 0xE670E8, 0xA849A3, xArea, yArea, widthArea, (f.getHeight() + 10) * 2, 1);
		}
		g.setColor(0x444444);
		g.fillRect(xArea + 10 + edit.getWidth(), yArea + f.getHeight() + 6, widthArea - edit.getWidth() - 8 - 5, f.getHeight() + 8);
		gradientBox(g, 0xCCCCCC, 0xFFFFFF, xArea + 12 + edit.getWidth(), yArea + 4 + f.getHeight() + 4, widthArea - edit.getWidth() - 17, f.getHeight() + 4, 1);
		if (value != "") {
			drawCutText(g, splitString(value), plainfonts, 0x222222, xArea + 15 + edit.getWidth(), yArea + 6 + f.getHeight() + 4, widthArea - edit.getWidth() - 20);
		}
		g.drawImage(edit, xArea + 5, yArea + 5 + f.getHeight() + 4, Graphics.TOP | Graphics.LEFT);
		int col;
		if (index == componentIndex) {
			highlighted = true;
			col = highlightedTextColor;
		} else {
			col = textColor;
		}
		drawCutText(g, splitString(label), f, col, xArea + 5, yArea + 3, widthArea - 5);
	}

	// Method untuk menggambar komponen Radio ke layar
	private void drawRadio(Graphics g) {
		for (int i = 0; i < radioElement.size(); i++) {
			((RadioButton) radioElement.elementAt(i)).index = index;
		}
		Font f;
		if (this.bold == true) {
			f = boldfonts;
		} else {
			f = plainfonts;
		}
		drawCutText(g, splitString(label), f, textColor, xArea, yArea, widthArea);
		int temp = f.getHeight();
		for (int i = 0; i < radioElement.size(); i++) {
			((RadioButton) radioElement.elementAt(i)).yArea = this.yArea + temp + ((f.getHeight() + 8) * i);
			((RadioButton) radioElement.elementAt(i)).paint(g);
		}
	}

	// Method untuk menggambar images
	private void drawImages(Graphics g, Image image) {
		g.drawImage(image, xArea - 5, yArea - 75, Graphics.LEFT | Graphics.TOP);
		value = "";
	}

	// Method untuk melakukan kalkulasi terhadap nilai ketinggian satu komponen
	public void calculateHeight() {
		Font f;
		if (this.bold == true) {
			f = boldfonts;
		} else {
			f = plainfonts;
		}
		switch (type) {
		case 1:
			this.height = (f.getHeight() + 8);
			break;
		case 2:
			this.height = (f.getHeight() + 8) * 2;
			break;
		case 3:
			this.height = (f.getHeight() + 8) * 2;
			break;
		case 4:
			this.height = (f.getHeight() + 6) + ((f.getHeight() + 8) * radioElement.size());
			break;
		}
	}

	// Method untuk melakukan set terhadap nilai index
	public void setIndex(int index) {
		this.index = index;
	}

	public void paint(Graphics g) {
		switch (type) {
		case 1:
			drawCheckBox(g);
			break;
		case 2:
			drawComboBox(g);
			break;
		case 3:
			drawInputText(g, value);
			break;
		case 4:
			drawRadio(g);
			break;
		case 5:
			drawImages(g, image);
			break;
		}
	}

	public void keyPressed(int keyCode) {
		switch (type) {
		case 1:
			if (index == componentIndex) {
				if (keyCode == -5) {
					if (this.checked == true) {
						this.checked = false;
						value = "";
					} else {
						this.checked = true;
						value = label;
					}
				}
			}
			break;
		case 2:
			if (index == componentIndex) {
				if (keyCode == -5) {
					Sender.midlet.setDisplayable(Sender.display.getCurrent());
					Sender.display.setCurrent(items);
				}
			}
			break;
		case 3:
			if (index == componentIndex) {
				if (keyCode == -5) {
					Sender.midlet.setDisplayable(Sender.display.getCurrent());
					Sender.display.setCurrent(textBox);
				}
			}
			break;
		case 4:
			for (int i = 0; i < radioElement.size(); i++) {
				((RadioButton) radioElement.elementAt(i)).keyPressed(keyCode);
			}
			switch (keyCode) {
			case -5:
				if (isGroupSelected(index, radioElement)) {
					for (int i = 0; i < radioElement.size(); i++) {
						if (((RadioButton) radioElement.elementAt(i)).componentIndex != index) {
							((RadioButton) radioElement.elementAt(i)).dotted = false;
						} else {
							value = ((RadioButton) radioElement.elementAt(i)).label;
						}
					}
				}
				break;
			}
			break;
		}
	}

	public void commandAction(Command c, Displayable d) {
		switch (type) {
		case 1:

			break;
		case 2:
			if (c == CMD_OK_01) {
				value = items.getString(items.getSelectedIndex());
				d = Sender.midlet.getDisplayable();
				if (value == null) {
					Alert a = new Alert("Error", "Field harus di isi", null, AlertType.INFO);
					Sender.display.setCurrent(a);
				} else {
					Sender.display.setCurrent(d);
				}
			}
			break;
		case 3:
			if (c == CMD_OK_02) {
				value = textBox.getString();
				d = Sender.midlet.getDisplayable();
				if (value.equals("")) {
					Alert a = new Alert("Error", "Field kosong harus diisi", null, AlertType.WARNING);
					Sender.display.setCurrent(a);
				} else {
					Sender.display.setCurrent(d);

				}
			}
			break;
		case 4:

			break;
		}
	}

	public void add(RadioButton rb) {
		radioElement.addElement(rb);
	}

	public void add(String str) {
		items.append(str, null);
	}

	public boolean isGroupSelected(int highlightedIndex, Vector radioElement) {
		boolean temp = false;
		int i = 0;
		while (i < radioElement.size()) {
			if (((RadioButton) radioElement.elementAt(i)).componentIndex == index) {
				temp = true;
				break;
			}
			i++;
		}
		return temp;
	}

	public void setProperties(int x, int y, int w) {
		this.xArea = x;
		this.yArea = y;
		this.widthArea = w;
		if (type == 4) {
			for (int i = 0; i < radioElement.size(); i++) {
				((RadioButton) radioElement.elementAt(i)).setXW(x, w);
			}
		}
	}

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
		return result;
	}

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
		}
		if (i < words.length - 1) {
			g.drawString("...", currentPos, yArea, Graphics.LEFT | Graphics.TOP);
		}
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