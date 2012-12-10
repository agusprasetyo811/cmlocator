package screen;

import java.util.Hashtable;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import data.manager.RMS;

import screen.input.form.CustomComponent;
import screen.input.form.CustomForm;
import screen.input.form.CustomPage;
import screen.input.form.CustomScrollBar;
import screen.input.form.RadioButton;

public class SettingPage extends Canvas implements Runnable {

	public int xBreakSetting = 0; // Posisi berhenti tiap kali keyPresed
	public int xTransWindowSetting = 0; // Posisi x sebagai acuan pergeseran page Setting
	public int transSpeedSetting = 40;
	public Image imgSetting; // menyimpan tampilan off screen
	private Image sender;
	private Image receiver;
	public Image panahKiri;
	public Image panahKanan;
	public Image header;
	public Image footer;
	public Graphics graphSetting; // Menggambar tampilan off screen
	public int movePageSetting = 0; // Penanda pergerakan, 1 ke kanan, -1 ke kiri
	public boolean allowToPressSetting = true; // memperbolehkan aksi keyPress
	public int settingPage = 1;
	public CustomPage settingPage_01;
	public CustomPage settingPage_02;
	public CustomPage settingPage_03;
	public CustomScrollBar settingScroll_01;
	public CustomScrollBar settingScroll_02;
	public CustomScrollBar settingScroll_03;
	public CustomForm settingForm_01;
	public CustomForm settingForm_02;
	public CustomForm settingForm_03;
	public CustomComponent images_01;
	public CustomComponent images_02;
	public CustomComponent images_03;
	public CustomComponent field_01;
	public CustomComponent field_02;
	public CustomComponent field_03;
	public String value_01;
	public String value_02;
	public String value_03;
	public RadioButton radioButton_01;
	public RadioButton radioButton_02;
	public int indexRadio;
	public boolean showScreen;
	public boolean hiddenScreen;
	public Image screenReceiver;

	public SettingPage() {
		try {
			imgSetting = Image.createImage(getWidth(), getHeight());
			sender = Image.createImage("/Form/content-setting-sender.png");
			receiver = Image.createImage("/Form/content-setting-receiver.png");
			footer = Image.createImage("/Layout/footer.png");
			header = Image.createImage("/Layout/header.png");
			panahKiri = Image.createImage("/Arrow/panah-kiri.png");
			panahKanan = Image.createImage("/Arrow/panah-kanan.png");
			screenReceiver = Image.createImage("/Form/content-setting-screen.png");
		} catch (Exception e) {
		}

		// Load Data Sender
		Hashtable loadDataSender = RMS.load("senderData");
		if (loadDataSender != null) {
			value_01 = loadDataSender.get("noSender").toString();
		} else {
			value_01 = "";
		}

		// Load Data Receiver
		Hashtable loadDataReceiver = RMS.load("receiverData");
		if (loadDataReceiver != null) {
			value_02 = loadDataReceiver.get("noReceiver").toString();
		} else {
			value_02 = "";
		}

		// Load Data Screen Receiver
		Hashtable loadScreenReceiver = RMS.load("screenReceiver");
		if (loadScreenReceiver != null) {
			value_03 = loadScreenReceiver.get("screen").toString();
			if (value_03.equals("Show")) {
				indexRadio = 3;
				showScreen = true;
				hiddenScreen = false;
				System.out.println("Screen ->" + showScreen);
			} else if (value_03.equals("Hidden")) {
				indexRadio = 2;
				showScreen = false;
				hiddenScreen = true;
				System.out.println("Screen ->" + showScreen);
			}
			System.out.println("Screen Condition : " + value_03);
		} else {
			value_03 = "";
		}

		settingPage_01 = new CustomPage("", 0, 120, getWidth() - 1, 2);
		settingPage_01.setPadding(8, 0, 10, 0);
		settingScroll_01 = new CustomScrollBar(40, settingPage_01.windowHeight, 5);
		settingForm_01 = new CustomForm("Setting Sender");
		images_01 = new CustomComponent(null, 0, 0, true, null, sender, CustomComponent.TYPE_IMAGES);
		field_01 = new CustomComponent("No HP Target", 0x222222, 0xFFFFFF, true, value_01, null, CustomComponent.TYPE_INPUT_TEXT);
		field_01.setIndex(2);
		settingForm_01.add(images_01);
		settingForm_01.add(field_01);
		settingPage_01.add(settingForm_01);
		settingPage_01.add(settingScroll_01);

		settingPage_02 = new CustomPage("", 0, 120, getWidth() - 1, 2);
		settingPage_02.setPadding(8, 0, 10, 0);
		settingScroll_02 = new CustomScrollBar(40, settingPage_02.windowHeight, 5);
		images_02 = new CustomComponent(null, 0, 0, true, null, receiver, CustomComponent.TYPE_IMAGES);
		field_02 = new CustomComponent("No HP Terima Informasi", 0x222222, 0xFFFFFF, true, value_02, null, CustomComponent.TYPE_INPUT_TEXT);
		field_02.setIndex(2);
		settingForm_02 = new CustomForm("Setting Receiver");
		settingForm_02.add(images_02);
		settingForm_02.add(field_02);
		settingPage_02.add(settingForm_02);
		settingPage_02.add(settingScroll_02);
		graphSetting = imgSetting.getGraphics();

		settingPage_03 = new CustomPage("", 0, 120, getWidth() - 1, 2);
		settingPage_03.setPadding(8, 0, 10, 0);
		settingScroll_03 = new CustomScrollBar(40, settingPage_03.windowHeight, 5);
		images_03 = new CustomComponent(null, 0, 0, true, null, screenReceiver, CustomComponent.TYPE_IMAGES);
		field_03 = new CustomComponent("Mode Layar Receiver ", 0x222222, 0xFFFFFF, true, null, null, CustomComponent.TYPE_RADIO);
		field_03.setIndex(indexRadio);
		radioButton_01 = new RadioButton("Hidden", hiddenScreen, 0x0000FF, 0xFFFFFF, true);
		radioButton_02 = new RadioButton("Show", showScreen, 0x0000FF, 0xFFFFFF, true);
		settingForm_03 = new CustomForm("Setting Receiver Screen");
		field_03.add(radioButton_01);
		field_03.add(radioButton_02);
		settingForm_03.add(images_03);
		settingForm_03.add(field_03);
		settingPage_03.add(settingForm_03);
		settingPage_03.add(settingScroll_03);
		graphSetting = imgSetting.getGraphics();
	}

	// Footer
	public void footer(Graphics g, int width, int height) {
		g.drawImage(footer, width, height, Graphics.BOTTOM | Graphics.HCENTER);
	}

	// Command Footer Label Menu
	public void CommandMenuLabelSetting(Graphics g, String leftmenu, String centermenu, String rightmenu, int widthLeft, int heightLeft, int widthCenter, int heightCenter, int widthRight, int heightRight) {
		g.setColor(0x000000);
		g.setFont(FontLibs.plainfonts);
		if (leftmenu != null) {
			g.drawString(leftmenu, widthLeft, heightLeft, Graphics.BOTTOM | Graphics.LEFT);
		}
		if (rightmenu != null) {
			g.drawString(rightmenu, widthCenter, heightCenter, Graphics.BOTTOM | Graphics.RIGHT);
		}
		g.setFont(FontLibs.boldfonts);
		if (centermenu != null) {
			g.drawString(centermenu, widthRight, heightRight, Graphics.BOTTOM | Graphics.HCENTER);
		}
	}

	// Arrow Slider Footer
	public void arrowSlider(Graphics g, int widthLeft, int heightLeft, int widthRight, int HeighRight, int anchor) {
		g.drawImage(panahKiri, widthLeft, heightLeft, anchor);
		g.drawImage(panahKanan, widthRight, HeighRight, anchor);
	}

	public void paintOffScreenSetting(Graphics g, int width, int height, int widthLeft, int heightLeft, int widthCenter, int heightCenter, int widthRight, int heightRight, int arrowWidthLeft, int arrowHeightLeft, int arrowWidthRight, int arrowHeightRight, int anchor) {
		switch (settingPage) {
		case 1:
			settingPage_01.paint(g);
			footer(g, width, height);
			CommandMenuLabelSetting(g, "Simpan", "Pilih", "Kembali", widthLeft, heightLeft, widthCenter, heightCenter, widthRight, heightRight);
			arrowSlider(g, arrowWidthLeft, arrowHeightLeft, arrowWidthRight, arrowHeightRight, anchor);
			break;
		case 2:
			settingPage_02.paint(g);
			footer(g, width, height);
			CommandMenuLabelSetting(g, "Simpan", "Pilih", "Kembali", widthLeft, heightLeft, widthCenter, heightCenter, widthRight, heightRight);
			arrowSlider(g, arrowWidthLeft, arrowHeightLeft, arrowWidthRight, arrowHeightRight, anchor);
			break;
		case 3:
			settingPage_03.paint(g);
			footer(g, width, height);
			CommandMenuLabelSetting(g, "Simpan", "Pilih", "Kembali", widthLeft, heightLeft, widthCenter, heightCenter, widthRight, heightRight);
			arrowSlider(g, arrowWidthLeft, arrowHeightLeft, arrowWidthRight, arrowHeightRight, anchor);
			break;
		}
	}

	// Setting Pages Position
	public void setSettingPagePosition() {
		settingPage_01.setProperties(xTransWindowSetting, settingPage_01.y, settingPage_01.width);
		settingPage_02.setProperties(xTransWindowSetting + settingPage_01.width, settingPage_02.y, settingPage_02.width);
		settingPage_03.setProperties(xTransWindowSetting + settingPage_01.width + settingPage_02.width, settingPage_03.y, settingPage_03.width);
	}

	// Command Action
	public void commandAction(Command c, Displayable d) {
		settingPage_01.commandAction(c, d);
		settingPage_02.commandAction(c, d);
		settingPage_03.commandAction(c, d);
	}

	// Run
	public void run() {
		if (allowToPressSetting == false) {
			if (movePageSetting == 1) {
				if (xBreakSetting > xTransWindowSetting) {
					xTransWindowSetting += transSpeedSetting;
				} else {
					xTransWindowSetting = xBreakSetting;
					allowToPressSetting = true;
					settingPage--;
				}
				settingPage_01.setProperties(xTransWindowSetting, settingPage_01.y, settingPage_01.width);
				settingPage_02.setProperties(xTransWindowSetting + settingPage_01.width, settingPage_02.y, settingPage_02.width);
				settingPage_03.setProperties(xTransWindowSetting + settingPage_01.width + settingPage_02.width, settingPage_03.y, settingPage_03.width);
			} else if (movePageSetting == -1) {
				if (xTransWindowSetting > xBreakSetting) {
					xTransWindowSetting -= transSpeedSetting;
				} else {
					xTransWindowSetting = xBreakSetting;
					allowToPressSetting = true;
					settingPage++;
				}
				settingPage_01.setProperties(xTransWindowSetting, settingPage_01.y, settingPage_01.width);
				settingPage_02.setProperties(xTransWindowSetting + settingPage_01.width, settingPage_02.y, settingPage_02.width);
				settingPage_03.setProperties(xTransWindowSetting + settingPage_01.width + settingPage_02.width, settingPage_03.y, settingPage_03.width);
			}
			paintOffScreenSetting(graphSetting, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			repaint();
		}
	}

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
	}
}
