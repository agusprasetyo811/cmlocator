package screen.input.form;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;

public class CustomPage {
	public String name;
	public int x;
	public int y;
	public int currY;
	public int width;
	public int height;
	public int currentIndex;
	public int leftPadding;
	public int topPadding;
	public int rightPadding;
	public int bottomPadding;
	public int windowHeight;

	public CustomForm cf;

	public CustomScrollBar cs;

	public CustomPage(String name, int x, int y, int width, int currentIndex) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.currentIndex = currentIndex;
		this.currY = y;
		this.windowHeight = 220;
	}

	public void setPadding(int l, int t, int r, int b) {
		this.leftPadding = l;
		this.topPadding = t;
		this.rightPadding = r;
		this.bottomPadding = b;
	}

	public void add(CustomForm cf) {
		this.cf = cf;
		cf.xArea = x + leftPadding;
		cf.yArea = y + topPadding;
		cf.widthArea = width - rightPadding;
		cf.setXW(cf.xArea, cf.widthArea);
		cf.setPosition();
		cf.calculateHeight();
		height = topPadding + bottomPadding + cf.height;
	}

	public void setProperties(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.width = w;
		cf.setProperties(x, y, w);
		cf.xArea = x + leftPadding;
		cf.yArea = y + topPadding;
		cf.widthArea = width - rightPadding;
		cf.setPosition();
		cf.calculateHeight();
		height = topPadding + bottomPadding + cf.height;
		cs.xArea = this.x + this.width - 7;
	}

	public int getNbComponents() {
		return cf.getNbComponents();
	}

	public void setIndexes(int index) {
		cf.setIndexes(index);
	}

	public void add(CustomScrollBar cs) {
		this.cs = cs;
		cs.setBarProperties(y, currY, height, windowHeight);
		cs.xArea = this.x + this.width - 7;
	}

	public void paint(Graphics g) {
		cf.setPosition();
		if (cf.getYHighlightedComponent() < y) {
			currY = currY + (y - cf.getYHighlightedComponent());
		} else if (cf.getYHighlightedComponent() > y + windowHeight - cf.getHeightHighlightedComponent()) {
			currY = currY - (cf.getYHighlightedComponent() - (y + windowHeight - cf.getHeightHighlightedComponent()));
		}
		cf.setProperties(cf.xArea, currY, cf.widthArea);
		cf.yArea = currY + topPadding;
		cf.setPosition();
		cf.paint(g);
		if (height > windowHeight) {
			cs.setBarProperties(y, currY, height, windowHeight);
			cs.paint(g);
		}
	}

	public void keyPressed(int keyCode) {
		cf.keyPressed(keyCode);
		switch (keyCode) {
		case -1:
			if (currentIndex > 1) {
				currentIndex--;
			}
			break;
		case -2:
			if (currentIndex < cf.getNbComponents()) {
				currentIndex++;
			}
			break;
		case -6:
			// cf.submit();
			break;
		}
		cf.setIndexes(currentIndex);
	}

	public void commandAction(Command c, Displayable d) {
		cf.commandAction(c, d);
	}
};