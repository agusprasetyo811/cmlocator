package screen;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import sms.manager.ActionMessageSender;
import sms.manager.SMSManagerListenerSender;
import sms.manager.SMSManagerSender;
import data.manager.RMS;
import data.manager.ReadData;

/**
 * @author Neo-Nevz
 * 
 */

public class ControlMenuManual extends Canvas implements Runnable, SMSManagerListenerSender {

	private int nbMenu = 3;
	private int index;
	private int indexListMenu;
	private int nbControlmenu = 3;
	private int topPosition = 7;
	public static int xMenu = 10;
	private int selectedPos = 1;
	// String Array
	private String[] menuLabel;
	private String[] showedTab;
	// Images
	private Image activeTab;
	private Image inactiveTab;
	private Image content;
	private Image header;
	// List menu Control
	private String[] menulabel;
	private Vector menus;
	private ControlMenuManualList listmenu;
	// Loading Animation
	public LoadingAnimation animation;
	// TabTextArea
	public TabTextArea textArea;
	// Map
	public ControlMenuManualMap maps;
	// Boolean Navigator
	public boolean allowPressNavigator = true;
	// Help
	public ControlMenuManualHelp help;
	// SubMenu
	public SubMenu subMenu;
	public boolean hide = true;
	// Read Data
	private ReadData readData;
	// Label Menu
	public String leftMenu = "";
	public String rightMenu = "";
	public String centerMenu = "";
	// Set Text Label Information
	public String textLabelSettng;

	public ControlMenuManual(int index, int indexListMenu) {
		this.index = index;
		this.indexListMenu = indexListMenu;

		// Read Data
		readData = new ReadData();
		// Loading Animation
		animation = new LoadingAnimation();
		// TabTextArea
		textArea = new TabTextArea();
		// Maps
		maps = new ControlMenuManualMap(allowPressNavigator);
		// Help
		help = new ControlMenuManualHelp();
		// Sub Menu
		subMenu = new SubMenu();

		// Listener Message
		new Thread() {
			public void run() {
				SMSManagerSender.receiver(ControlMenuManual.this, ":108");
			};
		}.start();

		// Images
		try {
			activeTab = Image.createImage("/ControlMenu/aktiveTab.png");
			inactiveTab = Image.createImage("/ControlMenu/inaktiveTab.png");
			content = Image.createImage("/ControlMenu/content_02.png");
			header = Image.createImage("/ControlMenu/header_01.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Tab Menu label
		menuLabel = new String[nbMenu];
		menuLabel[0] = "Lokasi";
		menuLabel[1] = "Map";
		menuLabel[2] = "Bantuan";
		showedTab = new String[3];
		showedTab[0] = menuLabel[0];
		showedTab[1] = menuLabel[1];
		showedTab[2] = menuLabel[2];

		// Control Menu Label
		menulabel = new String[nbControlmenu];
		menulabel[0] = "Posisi On";
		menulabel[1] = "Timer Posisi On";
		menulabel[2] = "Stop Timer Posisi";
		menus = new Vector();
		for (int i = 0; i < nbControlmenu; i++) {
			listmenu = new ControlMenuManualList(5, 47 + (i * 47), i + 1, indexListMenu, i + 1, menulabel[i]);
			menus.addElement(listmenu);
		}
	}

	// Jika Menerima Request Dari Receiver
	public void SMSManagerListenerSenderEvent(String message) {
		// Set Kondisi Untuk Menghentikan Loading
		if (message.equals(message)) {
			// Menghentikan Animasi Loading
			animation.loadingProgress = true;
			System.out.println("Request Location Accept");
			animation.showLoadingSave = true;
			textLabelSettng = "Lokasi Baru, Cek MAP..!!";
			animation.allowToPressSave = false;
			animation.loadingProgressSave = false;
			animation.endLoadingSave = System.currentTimeMillis() + animation.intervalLoadingSave;
			// Memberikan tanda getar selama 2 detik
			Sender.display.vibrate(2000);
		}

		// Saving Data Message
		Hashtable saveDataSenderMessage = new Hashtable();
		saveDataSenderMessage.put("dataLokasiMentah", message);
		RMS.save("dataLokasiSementara", saveDataSenderMessage);

		// Load Data dan Parsing Data
		Hashtable loadDataLocationSementara = RMS.load("dataLokasiSementara");
		if (loadDataLocationSementara != null) {
			maps.dataLokasiMentah = loadDataLocationSementara.get("dataLokasiMentah").toString();

			// Melakukan parsing data
			readData.setData(maps.dataLokasiMentah);
			String latitudeValue = readData.getValue(0);
			String longitudeValue = readData.getValue(1);

			// Melakukan Penyimpanan data lokasi
			Hashtable simpanDataLokasi = new Hashtable();
			simpanDataLokasi.put("latitude", latitudeValue);
			simpanDataLokasi.put("longitude", longitudeValue);
			RMS.save("dataLokasiReal", simpanDataLokasi);
		} else {
			maps.dataLokasiMentah = "-";
		}

		// load Data Lokasi Real
		Hashtable loadDataLokasiReal = RMS.load("dataLokasiReal");
		if (loadDataLokasiReal != null) {
			maps.latitudeValue = loadDataLokasiReal.get("latitude").toString();
			maps.longitudeValue = loadDataLokasiReal.get("longitude").toString();
		} else {
			maps.latitudeValue = "0.0";
			maps.longitudeValue = "0.0";
		}

		// Inisialisasi nilai latitude dan longitude
		maps.latitude = Double.parseDouble(maps.latitudeValue);
		maps.longitude = Double.parseDouble(maps.longitudeValue);

		// Menampilkan Map
		maps.mapManager.showMap(maps.latitude, maps.longitude, maps.mapWidth, maps.mapHeight);
	}

	// Tab Pages
	public void tabPages(final Graphics g) {
		// Header Control Manual Menu
		g.drawImage(header, getWidth() / 2, 0, Graphics.TOP | Graphics.HCENTER);

		// InActive Tab
		g.setColor(0x686868);
		g.setFont(FontLibs.plainfonts);
		g.drawImage(inactiveTab, getWidth() / 2 - 80, topPosition, Graphics.TOP | Graphics.HCENTER);
		g.drawString(showedTab[0], getWidth() / 2 - 80, topPosition, Graphics.TOP | Graphics.HCENTER);
		g.drawImage(inactiveTab, getWidth() / 2, topPosition, Graphics.TOP | Graphics.HCENTER);
		g.drawString(showedTab[1], getWidth() / 2, topPosition, Graphics.TOP | Graphics.HCENTER);
		g.drawImage(inactiveTab, getWidth() / 2 + 80, topPosition, Graphics.TOP | Graphics.HCENTER);
		g.drawString(showedTab[2], getWidth() / 2 + 80, topPosition, Graphics.TOP | Graphics.HCENTER);

		// Background Content
		g.drawImage(content, getWidth() / 2, 37, Graphics.TOP | Graphics.HCENTER);

		// Active Tab
		g.setColor(0x58470E);
		switch (selectedPos) {
		case 1:
			g.drawImage(activeTab, getWidth() / 2 - 80, topPosition + 2, Graphics.TOP | Graphics.HCENTER);
			g.drawString(showedTab[0], getWidth() / 2 - 80, topPosition + 8, Graphics.TOP | Graphics.HCENTER);
			break;
		case 2:
			g.drawImage(activeTab, getWidth() / 2, topPosition + 2, Graphics.TOP | Graphics.HCENTER);
			g.drawString(showedTab[1], getWidth() / 2, topPosition + 8, Graphics.TOP | Graphics.HCENTER);
			break;
		case 3:
			g.drawImage(activeTab, getWidth() / 2 + 80, topPosition + 2, Graphics.TOP | Graphics.HCENTER);
			g.drawString(showedTab[2], getWidth() / 2 + 80, topPosition + 8, Graphics.TOP | Graphics.HCENTER);
			break;
		}

		// Control Tab Pages
		switch (index) {
		case 1: // Control Pages ---------------------------------------------------------------------------------------------------
			textArea.jumpY = 0;
			textArea.jumpX = 0;
			textArea.row = 0;
			textArea.top = 65;
			textArea.align = xMenu + 5;
			// Menu Label
			leftMenu = "";
			rightMenu = "Kembali";
			centerMenu = "Geser";
			textArea.maxWidth = getWidth() - (2 * xMenu);

			// Mengeluarkan Menu List
			for (int i = 0; i < menus.size(); i++) {
				((ControlMenuManualList) menus.elementAt(i)).indexListMenu = this.indexListMenu;
				((ControlMenuManualList) menus.elementAt(i)).x = 5;
				((ControlMenuManualList) menus.elementAt(i)).paint(g);
			}

			break;
		case 2: // Map pages ------------------------------------------------------------------------------------------------------
			textArea.jumpY = 0;
			textArea.jumpX = 0;
			textArea.row = 0;
			textArea.top = 65;
			textArea.align = xMenu + 5;
			leftMenu = "Menu";
			textArea.maxWidth = getWidth() - (2 * xMenu);
			maps.showMaps(g, getWidth() / 2, header.getHeight() + 2);
			if (!hide) {
				System.out.println("hehehee");
				subMenu.drawSubMenu(g, subMenu.nbSubmenu, subMenu.subMenu, 2, getHeight() + 5, 0x323030, 0x191818, subMenu.indexSubMenu, 0x444444, 0x777777, subMenu.subMenuLabel, FontLibs.plainfonts, 0xFFFFFF, 0xFFFFFF);
			}
			break;
		case 3: // Help Pages ----------------------------------------------------------------------------------------------------
			textArea.jumpY = 0;
			textArea.jumpX = 0;
			textArea.row = 0;
			textArea.top = 65;
			textArea.align = xMenu + 5;
			leftMenu = "";
			rightMenu = "Kembali";
			centerMenu = "Geser";
			textArea.maxWidth = getWidth() - (2 * xMenu);
			help.showHelp(g, getWidth() / 2, 37);
			break;
		}

		// Mengeluarkan Loading Information
		animation.loadingSaveField(g, textLabelSettng, getWidth() / 2, getHeight() / 2, Graphics.HCENTER | Graphics.VCENTER, getWidth() / 2, getHeight() / 2 - 10, Graphics.HCENTER | Graphics.TOP);
	}

	// Action Key
	protected void keyPressed(int keyCode) {
		if (maps.allowPressNavigator == true) {
			switch (keyCode) {
			case RIGHT:
				if (index < nbMenu) {
					if (selectedPos < 3) {
						selectedPos++;
						index++;
					} else {
						index++;
						for (int i = 0; i < 3; i++) {
							showedTab[i] = menuLabel[index - selectedPos + i];
						}
					}
				}
				break;
			case LEFT:
				if (index > 1) {
					if (selectedPos > 1) {
						selectedPos--;
						index--;
					} else {
						index--;
						for (int i = 0; i < 3; i++) {
							showedTab[i] = menuLabel[index - selectedPos + i];
						}
					}
				}
				break;
			}
		}
		switch (index) {
		case 1: // Control Pages Key Action --------------------------------------------------------------------------------------
			switch (keyCode) {
			case UP:
				if (indexListMenu > 1) {
					indexListMenu--;
				} else {
					indexListMenu = nbControlmenu;
				}
				break;
			case DOWN:
				if (indexListMenu < nbControlmenu) {
					indexListMenu++;
				} else {
					indexListMenu = 1;
				}
				break;
			case FIRE:
				if (indexListMenu == 1) {
					if (animation.allowToPress == true) {
						ActionMessageSender.gps();
						animation.loadingText = "Control Location";
						animation.allowToPress = false;
						animation.loadingProgress = false;
						animation.showLoadingArrow = true;
						animation.endLoading = System.currentTimeMillis() + animation.intervalLoading;
					}
				}
				if (indexListMenu == 2) {
					if (animation.allowToPress == true) {
						ActionMessageSender.alarm();
						animation.loadingText = "Control Timer delay 60s ";
						animation.allowToPress = false;
						animation.loadingProgress = false;
						animation.showLoadingArrow = true;
						animation.endLoading = System.currentTimeMillis() + animation.intervalLoadingSave;
					}
				}
				if (indexListMenu == 3) {
					if (animation.allowToPress == true) {
						ActionMessageSender.vibrate();
						animation.loadingText = "Stop Timer";
						animation.allowToPress = false;
						animation.loadingProgress = false;
						animation.showLoadingArrow = true;
						animation.endLoading = System.currentTimeMillis() + animation.intervalLoadingSave;
					}
				}
				break;
			}
			break;
		case 2: // Map Pages Key Action ------------------------------------------------------------------------------------------
			switch (keyCode) {
			case UP:
				if (maps.allowPressNavigator == false) {
					maps.mapManager.map.move(UP);
					System.out.println("Haha");
				}
				if (!hide) {
					if (subMenu.indexSubMenu > 1) {
						subMenu.indexSubMenu--;
					} else {
						subMenu.indexSubMenu = subMenu.nbSubmenu;
					}
				}
				break;
			case DOWN:
				if (maps.allowPressNavigator == false) {
					maps.mapManager.map.move(DOWN);
				}
				if (!hide) {
					if (subMenu.indexSubMenu < subMenu.nbSubmenu) {
						subMenu.indexSubMenu++;
					} else {
						subMenu.indexSubMenu = 1;
					}
				}
				break;
			case LEFT:
				if (maps.allowPressNavigator == false) {
					maps.mapManager.map.move(LEFT);
				}
				break;
			case RIGHT:
				if (maps.allowPressNavigator == false) {
					maps.mapManager.map.move(RIGHT);
				}
				break;
			case FIRE:
				switch (subMenu.indexSubMenu) {
				case 1:
					maps.mapManager.map.zoomIn();
					if (hide == false) {
						hide = true;
						leftMenu = "Menu";
						rightMenu = "Kembali";
					}
					break;
				case 2:
					maps.mapManager.map.zoomOut();
					if (hide == false) {
						hide = true;
						leftMenu = "Menu";
						rightMenu = "Kembali";
					}
					break;
				case 3:
					maps.allowPressNavigator = false;
					if (hide == false) {
						hide = true;
						leftMenu = "Menu";
						rightMenu = "Kembali";
					}
					break;
				case 4:
					maps.allowPressNavigator = true;
					if (hide == false) {
						hide = true;
						leftMenu = "Menu";
						rightMenu = "Kembali";
					}
					break;
				case 5:
					maps.mapManager.showMap(maps.latitude, maps.longitude, maps.mapWidth, maps.mapHeight);
					if (hide == false) {
						hide = true;
						leftMenu = "Menu";
						rightMenu = "Kembali";
					}
					break;
				case 6:
					if (hide == false) {
						hide = true;
						leftMenu = "Menu";
						rightMenu = "Kembali";
					}
					break;
				}
				break;
			}
		case 3: // Help Pages Key Action -----------------------------------------------------------------------------------------
			break;
		}
	}

	// Show Loading Arrow field
	public void loadingArrow(Graphics g, int width, int height, int anchor) {
		if (animation.allowToPress == false) {
			if (animation.showLoadingArrow == true) {
				animation.loadingArrow(g, width, height, anchor);
			}
		}
	}

	public void run() {
		animation.run();
	}

	protected void paint(Graphics g) {

	}

};