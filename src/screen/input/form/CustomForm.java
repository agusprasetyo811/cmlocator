package screen.input.form;

import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import screen.FontLibs;
import data.manager.RMS;

/**
 * @author Neo-Nevz
 * 
 */
public class CustomForm {
	public String name;
	public String[] postedData;
	public String[] sender;

	public Vector elements;

	public int xArea;
	public int yArea;
	public int widthArea;
	public int height;

	public CustomForm(String name) {
		this.name = name;
		this.elements = new Vector();
	}

	public void add(CustomComponent cc) {
		cc.calculateHeight();
		if (cc.type == 4) {
			// set nilai sembarang untuk index komponen Radio Group
			cc.componentIndex = 0;
			// set nilai masing-masing index komponen Radio Button
			int temp = getNbComponents() + cc.radioElement.size();
			int lastIndex = cc.radioElement.size();
			for (int i = lastIndex - 1; i >= 0; i--) {
				((RadioButton) cc.radioElement.elementAt(i)).componentIndex = temp;
				temp--;
			}
			;
		} else {
			cc.componentIndex = getNbComponents() + 1;
		}
		elements.addElement(cc);
		// set Area
		((CustomComponent) elements.elementAt(elements.size() - 1)).setArea(xArea, yArea, widthArea);
		// set posisi tiap komponen
		setPosition();
		calculateHeight();
	}

	public void calculateHeight() {
		int temp = 0;
		for (int i = 0; i < elements.size(); i++) {
			((CustomComponent) elements.elementAt(i)).calculateHeight();
			temp += ((CustomComponent) elements.elementAt(i)).height;
		}
		this.height = temp;
	}

	public int getNbComponents() {
		int temp = 0;
		for (int i = 0; i < elements.size(); i++) {
			if (((CustomComponent) elements.elementAt(i)).type == 4) {
				temp += ((CustomComponent) elements.elementAt(i)).radioElement.size();
			} else {
				temp++;
			}
		}
		return temp;
	}

	public void setPosition() {
		int firstLine = this.yArea;
		int currentLine = firstLine;
		for (int i = 0; i < elements.size(); i++) {
			((CustomComponent) elements.elementAt(i)).yArea = currentLine;
			int temp = ((CustomComponent) elements.elementAt(i)).height;
			currentLine += temp;
		}
	}

	public void setProperties(int x, int y, int w) {
		for (int i = 0; i < elements.size(); i++) {
			((CustomComponent) elements.elementAt(i)).setProperties(x, y, w);
		}
	}

	public void setXW(int x, int w) {
		for (int i = 0; i < elements.size(); i++) {
			((CustomComponent) elements.elementAt(i)).xArea = x;
			((CustomComponent) elements.elementAt(i)).widthArea = w;
			((CustomComponent) elements.elementAt(i)).setXW(x, w);
		}
	}

	public int getYHighlightedComponent() {
		int temp = yArea;
		for (int i = 0; i < elements.size(); i++) {
			if (((CustomComponent) elements.elementAt(i)).index == ((CustomComponent) elements.elementAt(i)).componentIndex) {
				if (((CustomComponent) elements.elementAt(i)).type != 4) {
					temp = ((CustomComponent) elements.elementAt(i)).yArea;
				} else {
					for (int j = 0; j < ((CustomComponent) elements.elementAt(i)).radioElement.size(); j++) {
						if (((RadioButton) ((CustomComponent) elements.elementAt(i)).radioElement.elementAt(j)).index == ((RadioButton) ((CustomComponent) elements.elementAt(i)).radioElement.elementAt(j)).componentIndex) {
							Font f;
							if (((RadioButton) ((CustomComponent) elements.elementAt(i)).radioElement.elementAt(j)).bold == true) {
								f = FontLibs.boldfonts;
							} else {
								f = FontLibs.plainfonts;
							}
							temp = ((CustomComponent) elements.elementAt(i)).yArea + f.getHeight() + ((f.getHeight() + 8) * j);
						}
					}
				}
			}
		}
		return temp;
	}

	public int getHeightHighlightedComponent() {
		int temp = 0;
		for (int i = 0; i < elements.size(); i++) {
			if (((CustomComponent) elements.elementAt(i)).index == ((CustomComponent) elements.elementAt(i)).componentIndex) {
				temp = ((CustomComponent) elements.elementAt(i)).height;
			}
		}
		return temp;
	}

	public void paint(Graphics g) {
		for (int i = 0; i < elements.size(); i++) {
			((CustomComponent) elements.elementAt(i)).paint(g);
		}
	}

	public void keyPressed(int keyCode) {
		for (int i = 0; i < elements.size(); i++) {
			((CustomComponent) elements.elementAt(i)).keyPressed(keyCode);
		}
	}

	public void commandAction(Command c, Displayable d) {
		for (int i = 0; i < elements.size(); i++) {
			((CustomComponent) elements.elementAt(i)).commandAction(c, d);
		}
	}

	public void setIndexes(int index) {
		for (int i = 0; i < elements.size(); i++) {
			((CustomComponent) elements.elementAt(i)).setIndex(index);
		}
	}

	public void submit() {
		postedData = new String[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			postedData[i] = ((CustomComponent) elements.elementAt(i)).value;
			if (postedData[i].equals("")) {
				System.out.println("Data Kosong");
			} else {
				Hashtable data = new Hashtable();
				data.put("data-" + i, postedData[i]);
				RMS.save("DataSementara", data);
				System.out.println(postedData[i]);
			}
		}
	}
};