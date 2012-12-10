package screen;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class TabTextArea extends Canvas {

	public int align = ControlMenuManual.xMenu + 5;
	public int jumpY;
	public int jumpX;
	public int row;
	public int top;
	public int maxWidth = getWidth() - (2 * ControlMenuManual.xMenu);
	public int stringWidth;
	public int wordSpace = 8;
	public int newLine = 13;
	private String[] itemDescription;

	public TabTextArea() {

	}

	// Draw Wrap Text
	public void drawWrapText(Graphics g, String input, Font f, int color) {
		itemDescription = split(input, ' ');
		int i = 0;
		while (itemDescription[i] != "*") {
			stringWidth = f.stringWidth(itemDescription[i]);
			row = jumpX + stringWidth + wordSpace;
			g.setColor(color);
			g.setFont(f);
			if (row <= maxWidth) {
				g.drawString(itemDescription[i], align + jumpX, top + jumpY, Graphics.TOP | Graphics.LEFT);
				jumpX = jumpX + stringWidth + wordSpace;
			} else {
				jumpY = jumpY + newLine;
				g.drawString(itemDescription[i], align, top + jumpY, Graphics.TOP | Graphics.LEFT);
				jumpX = stringWidth + wordSpace;
			}
			i++;
		}
	}

	// String Split
	public static String[] split(String input, char marker) {
		int fromIndex = 0;
		int startPos = 0;
		int endPos = 0;
		int iMarker = 1;
		int iResult = 0;

		endPos = input.indexOf(marker, fromIndex);
		if (endPos > -1) {
			while (true) {
				fromIndex = endPos + 1;
				endPos = input.indexOf(marker, fromIndex);
				iMarker++;
				if (endPos < 0) {
					break;
				}
			}
			iResult = iMarker;
		} else {
			iResult = 1;
		}

		int indexArray = 0;
		String[] result = new String[iResult];
		if (iResult > 1) {
			fromIndex = 0;
			endPos = input.indexOf(marker, fromIndex);
			while (true) {
				if (endPos < 0) {
					endPos = input.length();
					result[indexArray] = input.substring(startPos, endPos);
					break;
				}
				result[indexArray] = input.substring(startPos, endPos);
				startPos = endPos + 1;
				fromIndex = endPos + 1;
				endPos = input.indexOf(marker, fromIndex);
				indexArray++;
			}
			result[indexArray] = "*";
		} else {
			result[0] = input;
			result[1] = "*";
		}
		return result;
	}

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub

	}
}
