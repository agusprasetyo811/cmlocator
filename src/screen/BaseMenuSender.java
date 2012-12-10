package screen;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import sms.manager.ActionMessageSender;

import data.manager.RMS;
import data.manager.ReadData;

public class BaseMenuSender extends Canvas implements Runnable {

	private int idHalaman; // ID Default Halaman
	private int index = 1;
	private int indexListMenu = 1;
	private int nbControlmenu = 2;
	private int move = 0;
	private int movementSpeed = 25;
	private boolean allowToAction = true;

	// Images
	public Image background;
	public Image header;
	public Image footer;
	public Image bHighlight;
	public Image content;
	public Image ContentAboutValue;
	public Image panahKiri;
	public Image panahKanan;
	public Image keluar;

	private String[] menulabel, menudeskription;
	private Vector menus;
	private ControlMenu controlmenu;
	private IntroMenu menu;
	private ControlMenuManual menuManual;

	private boolean exitQuestion = false;

	// Setting Page
	private SettingPage settingPage;
	public String textLabelSettng;
	// Log Page
	private LogPage logPage;
	// Help Pages
	private HelpPage helpPage;
	// About Page
	private AboutPage aboutPage;
	// Animation
	private LoadingAnimation animation;
	// Read data
	public ReadData readData = new ReadData();
	public String dataLocation;
	// SubMenuLabel
	public SubMenu subMenu;
	// Font Libs
	public FontLibs fontLibs;

	public BaseMenuSender(int idHalaman) {
		setFullScreenMode(true);
		this.idHalaman = idHalaman;

		// Image
		try {
			// Layout Images
			header = Image.createImage("/Layout/header.png");
			bHighlight = Image.createImage("/Layout/B_highlight.png");
			background = Image.createImage("/Layout/background-1.png");
			footer = Image.createImage("/Layout/footer.png");
			panahKiri = Image.createImage("/Arrow/panah-kiri.png");
			panahKanan = Image.createImage("/Arrow/panah-kanan.png");
			content = Image.createImage("/Content/content.png");
			keluar = Image.createImage("/Layout/keluar.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Intro Menu
		menu = new IntroMenu(index);
		new Thread(this).start();

		// Control Menu & Control Menu Label
		menuManual = new ControlMenuManual(index, indexListMenu);
		menulabel = new String[nbControlmenu];
		menulabel[0] = "Kontrol Lokasi";
		menulabel[1] = "Kembali";
		menudeskription = new String[nbControlmenu];
		menudeskription[0] = "Kontrol Lokasi";
		menudeskription[1] = "Kembali Ke Menu Utama";
		menus = new Vector();
		for (int i = 0; i < nbControlmenu; i++) {
			controlmenu = new ControlMenu(5, 47 + (i * 47), i + 1, index, i + 1, menulabel[i], menudeskription[i]);
			menus.addElement(controlmenu);
		}

		// Setting Page
		settingPage = new SettingPage();
		// Log Page
		logPage = new LogPage();
		// Help Page
		helpPage = new HelpPage();
		// About Page
		aboutPage = new AboutPage();
		// Animation
		animation = new LoadingAnimation();
		// SubMenu
		subMenu = new SubMenu();
		// Font Libs
		fontLibs = new FontLibs();
	}

	// Command Footer Label Menu
	private void CommandMenuLabel(Graphics g, String leftmenu, String centermenu, String rightmenu) {
		g.setColor(0x000000);
		g.setFont(FontLibs.plainfonts);
		if (leftmenu != null) {
			g.drawString(leftmenu, 4, getHeight() - 5, Graphics.BOTTOM | Graphics.LEFT);
		}
		if (rightmenu != null) {
			g.drawString(rightmenu, getWidth() - 4, getHeight() - 5, Graphics.BOTTOM | Graphics.RIGHT);
		}
		g.setFont(FontLibs.boldfonts);
		if (centermenu != null) {
			g.drawString(centermenu, getWidth() / 2, getHeight() - 5, Graphics.BOTTOM | Graphics.HCENTER);
		}
	}

	// Background
	private void background(Graphics g) {
		g.setColor(0xffffff);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(background, getWidth() / 2, 0, Graphics.TOP | Graphics.HCENTER);
	}

	// Header
	private void header(Graphics g) {
		g.drawImage(header, getWidth() / 2, 0, Graphics.TOP | Graphics.HCENTER);
		g.drawImage(bHighlight, getWidth() / 2, 3, Graphics.TOP | Graphics.HCENTER);
	}

	// Content
	private void content(Graphics g, Image content) {
		g.drawImage(content, getWidth() / 2, header.getHeight() - 4, Graphics.TOP | Graphics.HCENTER);
	}

	// Footer
	public void footer(Graphics g) {
		g.drawImage(footer, getWidth() / 2, getHeight(), Graphics.BOTTOM | Graphics.HCENTER);
	}

	// Arrow Slider Footer
	public void arrowSlider(Graphics g) {
		g.drawImage(panahKiri, getWidth() / 2 - 40, getHeight() - 3, Graphics.BOTTOM | Graphics.HCENTER);
		g.drawImage(panahKanan, getWidth() / 2 + 40, getHeight() - 3, Graphics.BOTTOM | Graphics.HCENTER);
	}

	// Exit Question
	public void exitQuestion(Graphics g) {
		g.drawImage(keluar, getWidth() / 2, getHeight(), Graphics.BOTTOM | Graphics.HCENTER);
	}

	protected void paint(Graphics g) {
		Sender.display.callSerially(this);
		// Base Pages
		switch (idHalaman) {
		case 0: // SplashScreen Pages -----------------------------------------------------------------------------------------------
			g.setColor(0x000000);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(Sender.images, getWidth() / 2, getHeight() / 2, Graphics.HCENTER | Graphics.VCENTER);
			break;
		case 1: // Base Pages -------------------------------------------------------------------------------------------------------
			// Background
			background(g);
			// Header
			header(g);
			menu.arrowLeftSet(g, getWidth() / 2 - 112, 7);
			menu.arrowRightSet(g, getWidth() / 2 + 112, 7);
			menu.menulabel(g, getWidth() / 2, 7);
			// Content
			menu.content(g, getWidth() / 2, 50);
			// Footer
			menu.highlight(g, getWidth() / 2, getHeight());
			menu.icons(g, getHeight() - 13);
			if (exitQuestion == true) {
				exitQuestion(g);
			} else {
				exitQuestion = false;
			}
			break;
		case 2: // Phone Control List Pages -----------------------------------------------------------------------------------------
			// Background
			background(g);
			// Header
			header(g);
			menu.menulabel(g, getWidth() / 2, 7);
			// Content
			for (int i = 0; i < menus.size(); i++) {
				((ControlMenu) menus.elementAt(i)).index = this.index;
				((ControlMenu) menus.elementAt(i)).x = 5;
				((ControlMenu) menus.elementAt(i)).paint(g);
			}
			// Footer
			footer(g);
			CommandMenuLabel(g, null, "Pilih", "Kembali");
			break;
		case 3: // Setting Pages ----------------------------------------------------------------------------------------------------
			// Background
			background(g);
			// Header
			header(g);
			menu.menulabel(g, getWidth() / 2, 7);
			// Content
			settingPage.setSettingPagePosition();
			// Footer
			settingPage.paintOffScreenSetting(g, getWidth() / 2, getHeight(), 4, getHeight() - 5, getWidth() - 4, getHeight() - 5, getWidth() / 2, getHeight() - 5, getWidth() / 2 - 40, getHeight() - 3, getWidth() / 2 + 40, getHeight() - 3, Graphics.BOTTOM | Graphics.HCENTER);
			// loading save
			animation.loadingSaveField(g, textLabelSettng, getWidth() / 2, getHeight() / 2, Graphics.HCENTER | Graphics.VCENTER, getWidth() / 2, getHeight() / 2 - 10, Graphics.HCENTER | Graphics.TOP);
			break;
		case 4: // Log Pages --------------------------------------------------------------------------------------------------------
			// Background
			background(g);
			// Header
			header(g);
			menu.menulabel(g, getWidth() / 2, 7);
			// Content
			content(g, content);
			logPage.logContent(g);
			// Footer
			footer(g);
			CommandMenuLabel(g, null, null, "Kembali");
			break;
		case 5: // Help Pages -------------------------------------------------------------------------------------------------------
			// Background
			background(g);
			// Header
			header(g);
			menu.menulabel(g, getWidth() / 2, 7);
			// Content
			helpPage.setHelpPagePosition();
			helpPage.paintOffScreenHelp(g);
			// Footer
			footer(g);
			CommandMenuLabel(g, null, "Geser", "Kembali");
			arrowSlider(g);
			break;
		case 6:
			// About Pages ----------------------------------------------------------------------------------------------------------
			// Background
			background(g);
			// Header
			header(g);
			menu.menulabel(g, getWidth() / 2, 7);
			// Content
			content(g, content);
			aboutPage.aboutValue(g);
			// Footer
			footer(g);
			CommandMenuLabel(g, null, null, "Kembali");
			break;
		case 7: // Control Tab Pages ------------------------------------------------------------------------------------------------
			// Background
			background(g);
			// Header
			// Content
			menuManual.tabPages(g);
			// Footer
			footer(g);
			CommandMenuLabel(g, menuManual.leftMenu, menuManual.centerMenu, menuManual.rightMenu);
			arrowSlider(g);
			// Loading Pages
			menuManual.loadingArrow(g, getWidth() / 2, getHeight() / 2, Graphics.HCENTER | Graphics.VCENTER);
			break;
		case 8: // Exit Question ------------------------------------------------------------------------------------------------
			exitQuestion = true;
			break;
		}
	}

	public void run() {
		// SplashScreen Animation
		while (Sender.keepgoing) {
			try {
				Thread.sleep(2000);
				idHalaman = 1; // Setelah selesai akan dilempar ke halaman dengan idHalaman 1
				repaint();
				Sender.keepgoing = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println(e);
			}
		}

		// // Loading Arrow Animation ControlMenuManual
		if (animation.action == 1) {
			if (animation.frameIndex < animation.jmlFrame - 1) {
				animation.x = animation.x - animation.frameWidth;
				animation.frameIndex++;
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				animation.x = animation.xClip;
				animation.frameIndex = 0;
			}
		}
		repaint();

		// Base Menu Animation
		if (allowToAction == false) {
			if (move == 1) {
				if (menu.menuPos > -getWidth()) {
					menu.menuPos -= movementSpeed;
				} else {
					menu.menuPos = -getWidth() / 2;
					move = 0;
					if (menu.index < menu.nbMenu) {
						menu.index++;
					} else {
						menu.index = 1;
					}
					allowToAction = true;
				}
			} else if (move == -1) {
				if (menu.menuPos < 0) {
					menu.menuPos += movementSpeed;
				} else {
					menu.menuPos = -getWidth() / 2;
					move = 0;
					if (menu.index > 1) {
						menu.index--;
					} else {
						menu.index = menu.nbMenu;
					}
					allowToAction = true;
				}
			}
			repaint();
		}

		// Sertting Pages Silde Animation
		settingPage.run();

		// Help Pages Slides Animation
		helpPage.run();
	}

	// Key Action
	protected void keyPressed(int keyCode) {
		switch (idHalaman) {
		case 1: // Base Menu Pages Action ------------------------------------------------------------------------------------------
			if (exitQuestion == true) {
				if (keyCode == -7) {
					exitQuestion = false;
				}
				if (keyCode == -6) {
					Sender.midlet.notifyDestroyed();
				}
			} else {
				switch (getGameAction(keyCode)) {
				case UP:
					break;
				case DOWN:
					break;
				case FIRE: // OK
					if (menu.index == 1) {
						idHalaman = 2;
					}
					if (menu.index == 2) {
						idHalaman = 3;
					}
					if (menu.index == 3) {
						idHalaman = 4;
					}
					if (menu.index == 4) {
						idHalaman = 5;
					}
					if (menu.index == 5) {
						idHalaman = 6;
					}
					if (menu.index == 6) {
						exitQuestion = true;
					}
					break;
				case LEFT:
					if (allowToAction == true) {
						move = -1;
						allowToAction = false;
					}
					break;
				case RIGHT:
					if (allowToAction == true) {
						move = 1;
						allowToAction = false;
					}
					break;
				}
				if (keyCode == -7) {
					Sender.display.setCurrent(null);
				}
			}
			repaint();
			break;
		case 2: // Control Phone Pages List Action ----------------------------------------------------------------------------------
			int gameCodeControl = getGameAction(keyCode);
			switch (gameCodeControl) {
			case UP:
				if (index > 1) {
					index--;
				} else {
					index = nbControlmenu;
				}
				break;
			case DOWN:
				if (index < nbControlmenu) {
					index++;
				} else {
					index = 1;
				}
				break;
			case FIRE:
				if (index == 1) {
					idHalaman = 7;
				}
				if (index == 2) {
					idHalaman = 1;
				}
				break;
			}
			if (keyCode == -7) {
				idHalaman = 1;
			}
			repaint();
			break;
		case 3: // Setting Pages Action ----------------------------------------------------------------------------------------------
			switch (settingPage.settingPage) {
			case 1:
				settingPage.settingPage_01.keyPressed(keyCode);
				settingPage.paintOffScreenSetting(settingPage.graphSetting, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				if (keyCode == -6) {
					if (animation.allowToPressSave == true) {
						settingPage.settingForm_01.submit();
						// Load RMS table DataSementara
						Hashtable LoadDataSementara = RMS.load("DataSementara");
						if (LoadDataSementara != null) {
							textLabelSettng = "Simpan Receiver Target..";
							String PhoneNumperSender = LoadDataSementara.get("data-1").toString();
							Hashtable savedataSender = new Hashtable();
							savedataSender.put("noSender", PhoneNumperSender);
							RMS.save("senderData", savedataSender);
						} else {
							textLabelSettng = "Field Kosong";
						}
						animation.allowToPressSave = false;
						animation.loadingProgressSave = false;
						animation.showLoadingSave = true;
						animation.endLoadingSave = System.currentTimeMillis() + animation.intervalLoadingSave;
						// Testing
						System.out.println(System.currentTimeMillis());
						System.out.println(animation.endLoadingSave);
					}
				}
				break;
			case 2:
				settingPage.settingPage_02.keyPressed(keyCode);
				settingPage.paintOffScreenSetting(settingPage.graphSetting, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				if (keyCode == -6) {
					if (animation.allowToPressSave == true) {
						settingPage.settingForm_02.submit();
						// Load RMS table DataSementara
						Hashtable LoadDataSementara = RMS.load("DataSementara");
						if (LoadDataSementara != null) {
							textLabelSettng = "Simpan Sender Phone..";
							String PhoneNumperSender = LoadDataSementara.get("data-1").toString();
							Hashtable savedataSender = new Hashtable();
							savedataSender.put("noReceiver", PhoneNumperSender);
							RMS.save("receiverData", savedataSender);
							Hashtable sendDataReceiver = RMS.load("receiverData");
							if (sendDataReceiver != null) {
								textLabelSettng = "Kirim Data..";
								String dataReceiver = sendDataReceiver.get("noReceiver").toString();
								ActionMessageSender.setting(dataReceiver);
							} else {
								textLabelSettng = "Gagal..";
							}
						} else {
							textLabelSettng = "Field Kosong";
						}
						animation.allowToPressSave = false;
						animation.loadingProgressSave = false;
						animation.showLoadingSave = true;
						animation.endLoadingSave = System.currentTimeMillis() + animation.intervalLoadingSave;
					}
				}
				break;
			case 3:
				settingPage.settingPage_03.keyPressed(keyCode);
				settingPage.paintOffScreenSetting(settingPage.graphSetting, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				if (keyCode == -6) {
					if (animation.allowToPressSave == true) {
						settingPage.settingForm_03.submit();
						// Load RMS table DataSementara
						Hashtable LoadDataSementara = RMS.load("DataSementara");
						if (LoadDataSementara != null) {
							String screenReceiver = LoadDataSementara.get("data-1").toString();
							Hashtable saveScreenReceiver = new Hashtable();
							saveScreenReceiver.put("screen", screenReceiver);
							RMS.save("screenReceiver", saveScreenReceiver);
							Hashtable sendScreenReceiver = RMS.load("screenReceiver");
							if (sendScreenReceiver != null) {
								String screenType = sendScreenReceiver.get("screen").toString();
								textLabelSettng = "Simpan " + screenType + " Screen..";
								ActionMessageSender.settingScreen(screenType);
							}
						} else {
							textLabelSettng = "Field Kosong";
						}
						animation.allowToPressSave = false;
						animation.loadingProgressSave = false;
						animation.showLoadingSave = true;
						animation.endLoadingSave = System.currentTimeMillis() + animation.intervalLoadingSave;
						// Testing
						System.out.println(System.currentTimeMillis());
						System.out.println(animation.endLoadingSave);
					}
				}
				break;
			}
			switch (keyCode) {
			case -3:
				if (settingPage.settingPage > 1) {
					if (settingPage.allowToPressSetting == true) {
						settingPage.settingPage_01.currY = settingPage.settingPage_01.y;
						settingPage.settingPage_02.currY = settingPage.settingPage_02.y;
						settingPage.settingPage_03.currY = settingPage.settingPage_03.y;
						settingPage.xBreakSetting = settingPage.settingPage_01.x + settingPage.settingPage_01.width;
						settingPage.movePageSetting = 1;
						settingPage.allowToPressSetting = false;
					}
				}
				break;
			case -4:
				if (settingPage.settingPage < 3) {
					if (settingPage.allowToPressSetting == true) {
						settingPage.settingPage_01.currY = settingPage.settingPage_01.y;
						settingPage.settingPage_02.currY = settingPage.settingPage_02.y;
						settingPage.settingPage_03.currY = settingPage.settingPage_03.y;
						settingPage.xBreakSetting = settingPage.settingPage_01.x - settingPage.settingPage_01.width;
						settingPage.movePageSetting = -1;
						settingPage.allowToPressSetting = false;
					}
				}
				break;
			}
			if (keyCode == -7) {
				idHalaman = 1;
			}
			repaint();
			break;
		case 4: // Log Pages Action -------------------------------------------------------------------------------------------------
			if (keyCode == -7) {
				idHalaman = 1;
			}
			repaint();
			break;
		case 5: // Help Pages Action ------------------------------------------------------------------------------------------------
			switch (helpPage.helpPage) {
			case 1:
				helpPage.helpPage_01.keyPressed(keyCode);
				helpPage.paintOffScreenHelp(helpPage.graphHelp);
				break;
			case 2:
				helpPage.helpPage_02.keyPressed(keyCode);
				helpPage.paintOffScreenHelp(helpPage.graphHelp);
				break;
			case 3:
				helpPage.helpPage_03.keyPressed(keyCode);
				helpPage.paintOffScreenHelp(helpPage.graphHelp);
				break;
			case 4:
				helpPage.helpPage_04.keyPressed(keyCode);
				helpPage.paintOffScreenHelp(helpPage.graphHelp);
				break;
			case 5:
				helpPage.helpPage_05.keyPressed(keyCode);
				helpPage.paintOffScreenHelp(helpPage.graphHelp);
				break;
			case 6:
				helpPage.helpPage_06.keyPressed(keyCode);
				helpPage.paintOffScreenHelp(helpPage.graphHelp);
				break;
			case 7:
				helpPage.helpPage_07.keyPressed(keyCode);
				helpPage.paintOffScreenHelp(helpPage.graphHelp);
				break;
			case 8:
				helpPage.helpPage_08.keyPressed(keyCode);
				helpPage.paintOffScreenHelp(helpPage.graphHelp);
				break;
			}
			switch (keyCode) {
			case -3:
				if (helpPage.helpPage > 1) {
					if (helpPage.allowToPressHelp == true) {
						helpPage.helpPage_01.currY = helpPage.helpPage_01.y;
						helpPage.helpPage_02.currY = helpPage.helpPage_02.y;
						helpPage.helpPage_03.currY = helpPage.helpPage_03.y;
						helpPage.helpPage_04.currY = helpPage.helpPage_04.y;
						helpPage.helpPage_05.currY = helpPage.helpPage_05.y;
						helpPage.helpPage_06.currY = helpPage.helpPage_06.y;
						helpPage.helpPage_07.currY = helpPage.helpPage_07.y;
						helpPage.helpPage_08.currY = helpPage.helpPage_08.y;
						helpPage.xBreakHelp = helpPage.helpPage_01.x + helpPage.helpPage_01.width;
						helpPage.movePageHelp = 1;
						helpPage.allowToPressHelp = false;
					}
				}
				break;
			case -4:
				if (helpPage.helpPage < 8) {
					if (helpPage.allowToPressHelp == true) {
						helpPage.helpPage_01.currY = helpPage.helpPage_01.y;
						helpPage.helpPage_02.currY = helpPage.helpPage_02.y;
						helpPage.helpPage_03.currY = helpPage.helpPage_03.y;
						helpPage.helpPage_04.currY = helpPage.helpPage_04.y;
						helpPage.helpPage_05.currY = helpPage.helpPage_05.y;
						helpPage.helpPage_06.currY = helpPage.helpPage_06.y;
						helpPage.helpPage_07.currY = helpPage.helpPage_07.y;
						helpPage.helpPage_08.currY = helpPage.helpPage_08.y;
						helpPage.xBreakHelp = helpPage.helpPage_01.x - helpPage.helpPage_01.width;
						helpPage.movePageHelp = -1;
						helpPage.allowToPressHelp = false;
					}
				}
				break;
			}
			if (keyCode == -7) {
				idHalaman = 1;
			}
			repaint();
			break;

		case 6: // About Pages Action ----------------------------------------------------------------------------------------------
			if (keyCode == -7) {
				idHalaman = 1;
			}
			repaint();
			break;
		case 7: // Control Menu Tap Pages Action ----------------------------------------------------------------------------------
			int gameCode = getGameAction(keyCode);
			menuManual.keyPressed(gameCode);
			if (keyCode == -7) {
				if (menuManual.hide == true) {
					idHalaman = 2;
				} else {
					menuManual.hide = true;
					menuManual.allowPressNavigator = false;
					menuManual.leftMenu = "Menu";
					menuManual.rightMenu = "Kembali";
					menuManual.centerMenu = "Geser";
					subMenu.indexSubMenu = 1;
				}
			}
			if (keyCode == -6) {
				if (menuManual.hide == true) {
					menuManual.allowPressNavigator = true;
					menuManual.hide = false;
					menuManual.centerMenu = "Pilih";
					menuManual.rightMenu = "Batal";
				} else {
					menuManual.hide = true;
					menuManual.leftMenu = "Menu";
					menuManual.rightMenu = "Kembali";
				}
			}
			repaint();
			break;
		}
	}
};
