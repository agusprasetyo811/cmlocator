package screen;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import screen.input.form.CustomComponent;
import screen.input.form.CustomForm;
import screen.input.form.CustomPage;
import screen.input.form.CustomScrollBar;

public class HelpPage extends Canvas implements Runnable {

	// Help menu
	public int xBreakHelp = 0; // Posisi berhenti tiap kali keyPresed
	public int xTransWindowHelp = 0; // Posisi x sebagai acuan pergeseran page
	public int transSpeedHelp = 40;
	public Image imgHelp;
	public Graphics graphHelp;
	public int movePageHelp = 0;
	public boolean allowToPressHelp = true;
	public int helpPage = 1;
	public CustomPage helpPage_01;
	public CustomPage helpPage_02;
	public CustomPage helpPage_03;
	public CustomPage helpPage_04;
	public CustomPage helpPage_05;
	public CustomPage helpPage_06;
	public CustomPage helpPage_07;
	public CustomPage helpPage_08;
	public CustomScrollBar helpScroll_01;
	public CustomScrollBar helpScroll_02;
	public CustomScrollBar helpScroll_03;
	public CustomScrollBar helpScroll_04;
	public CustomScrollBar helpScroll_05;
	public CustomScrollBar helpScroll_06;
	public CustomScrollBar helpScroll_07;
	public CustomScrollBar helpScroll_08;
	public CustomForm helpForm_01;
	public CustomForm helpForm_02;
	public CustomForm helpForm_03;
	public CustomForm helpForm_04;
	public CustomForm helpForm_05;
	public CustomForm helpForm_06;
	public CustomForm helpForm_07;
	public CustomForm helpForm_08;
	public CustomComponent helpContent_01;
	public CustomComponent helpContent_02;
	public CustomComponent helpContent_03;
	public CustomComponent helpContent_04;
	public CustomComponent helpContent_05;
	public CustomComponent helpContent_06;
	public CustomComponent helpContent_07;
	public CustomComponent helpContent_08;
	public CustomComponent helpValue_01;
	public CustomComponent helpValue_02;
	public CustomComponent helpValue_03;
	public CustomComponent helpValue_04;
	public CustomComponent helpValue_05;
	public CustomComponent helpValue_06;
	public CustomComponent helpValue_07;
	public CustomComponent helpValue_08;
	public Image ContentHelpValue_01;
	public Image ContentHelpValue_02;
	public Image ContentHelpValue_03;
	public Image ContentHelpValue_04;
	public Image ContentHelpValue_05;
	public Image ContentHelpValue_06;
	public Image ContentHelpValue_07;
	public Image ContentHelpValue_08;
	public Image content;

	public HelpPage() {
		try {
			ContentHelpValue_01 = Image.createImage("/Content/help/cover.png");
			ContentHelpValue_02 = Image.createImage("/Content/help/control.png");
			ContentHelpValue_03 = Image.createImage("/Content/help/setting.png");
			ContentHelpValue_04 = Image.createImage("/Content/help/log-help.png");
			ContentHelpValue_05 = Image.createImage("/Content/help/about-exit.png");
			ContentHelpValue_06 = Image.createImage("/Content/help/lokasi.png");
			ContentHelpValue_07 = Image.createImage("/Content/help/lokasi-1.png");
			ContentHelpValue_08 = Image.createImage("/Content/help/navigasi.png");
			imgHelp = Image.createImage(getWidth(), getHeight());
			content = Image.createImage("/Content/content.png");
		} catch (Exception e) {
			// TODO: handle exception
		}

		helpPage_01 = new CustomPage("Help 1", 0, 120, getWidth() - 1, 1);
		helpPage_01.setPadding(9, 0, 10, 0);
		helpScroll_01 = new CustomScrollBar(40, helpPage_01.windowHeight, 5);
		helpContent_01 = new CustomComponent(null, 0, 0, true, null, content, CustomComponent.TYPE_IMAGES);
		helpValue_01 = new CustomComponent(null, 0, 0, true, null, ContentHelpValue_01, CustomComponent.TYPE_IMAGES);
		helpForm_01 = new CustomForm("Help 1");
		helpForm_01.add(helpContent_01);
		helpForm_01.add(helpValue_01);
		helpPage_01.add(helpForm_01);
		helpPage_01.add(helpScroll_01);

		helpPage_02 = new CustomPage("Help 2", 0, 120, getWidth() - 1, 1);
		helpPage_02.setPadding(9, 0, 10, 0);
		helpScroll_02 = new CustomScrollBar(40, helpPage_02.windowHeight, 5);
		helpContent_02 = new CustomComponent(null, 0, 0, true, null, content, CustomComponent.TYPE_IMAGES);
		helpValue_02 = new CustomComponent(null, 0, 0, true, null, ContentHelpValue_02, CustomComponent.TYPE_IMAGES);
		helpForm_02 = new CustomForm("Help_2");
		helpForm_02.add(helpContent_02);
		helpForm_02.add(helpValue_02);
		helpPage_02.add(helpForm_02);
		helpPage_02.add(helpScroll_02);

		helpPage_03 = new CustomPage("Help 3", 0, 120, getWidth() - 1, 1);
		helpPage_03.setPadding(9, 0, 10, 0);
		helpScroll_03 = new CustomScrollBar(40, helpPage_03.windowHeight, 5);
		helpContent_03 = new CustomComponent(null, 0, 0, true, null, content, CustomComponent.TYPE_IMAGES);
		helpValue_03 = new CustomComponent(null, 0, 0, true, null, ContentHelpValue_03, CustomComponent.TYPE_IMAGES);
		helpForm_03 = new CustomForm("Help_3");
		helpForm_03.add(helpContent_03);
		helpForm_03.add(helpValue_03);
		helpPage_03.add(helpForm_03);
		helpPage_03.add(helpScroll_03);

		helpPage_04 = new CustomPage("Help 4", 0, 120, getWidth() - 1, 1);
		helpPage_04.setPadding(9, 0, 10, 0);
		helpScroll_04 = new CustomScrollBar(40, helpPage_04.windowHeight, 5);
		helpContent_04 = new CustomComponent(null, 0, 0, true, null, content, CustomComponent.TYPE_IMAGES);
		helpValue_04 = new CustomComponent(null, 0, 0, true, null, ContentHelpValue_04, CustomComponent.TYPE_IMAGES);
		helpForm_04 = new CustomForm("Help_4");
		helpForm_04.add(helpContent_04);
		helpForm_04.add(helpValue_04);
		helpPage_04.add(helpForm_04);
		helpPage_04.add(helpScroll_04);

		helpPage_05 = new CustomPage("Help 5", 0, 120, getWidth() - 1, 1);
		helpPage_05.setPadding(9, 0, 10, 0);
		helpScroll_05 = new CustomScrollBar(40, helpPage_05.windowHeight, 5);
		helpContent_05 = new CustomComponent(null, 0, 0, true, null, content, CustomComponent.TYPE_IMAGES);
		helpValue_05 = new CustomComponent(null, 0, 0, true, null, ContentHelpValue_05, CustomComponent.TYPE_IMAGES);
		helpForm_05 = new CustomForm("Help_5");
		helpForm_05.add(helpContent_05);
		helpForm_05.add(helpValue_05);
		helpPage_05.add(helpForm_05);
		helpPage_05.add(helpScroll_05);

		helpPage_06 = new CustomPage("Help 6", 0, 120, getWidth() - 1, 1);
		helpPage_06.setPadding(9, 0, 10, 0);
		helpScroll_06 = new CustomScrollBar(40, helpPage_06.windowHeight, 5);
		helpContent_06 = new CustomComponent(null, 0, 0, true, null, content, CustomComponent.TYPE_IMAGES);
		helpValue_06 = new CustomComponent(null, 0, 0, true, null, ContentHelpValue_06, CustomComponent.TYPE_IMAGES);
		helpForm_06 = new CustomForm("Help_6");
		helpForm_06.add(helpContent_06);
		helpForm_06.add(helpValue_06);
		helpPage_06.add(helpForm_06);
		helpPage_06.add(helpScroll_06);

		helpPage_07 = new CustomPage("Help 7", 0, 120, getWidth() - 1, 1);
		helpPage_07.setPadding(9, 0, 10, 0);
		helpScroll_07 = new CustomScrollBar(40, helpPage_07.windowHeight, 5);
		helpContent_07 = new CustomComponent(null, 0, 0, true, null, content, CustomComponent.TYPE_IMAGES);
		helpValue_07 = new CustomComponent(null, 0, 0, true, null, ContentHelpValue_07, CustomComponent.TYPE_IMAGES);
		helpForm_07 = new CustomForm("Help_7");
		helpForm_07.add(helpContent_07);
		helpForm_07.add(helpValue_07);
		helpPage_07.add(helpForm_07);
		helpPage_07.add(helpScroll_07);

		helpPage_08 = new CustomPage("Help 8", 0, 120, getWidth() - 1, 1);
		helpPage_08.setPadding(9, 0, 10, 0);
		helpScroll_08 = new CustomScrollBar(40, helpPage_08.windowHeight, 5);
		helpContent_08 = new CustomComponent(null, 0, 0, true, null, content, CustomComponent.TYPE_IMAGES);
		helpValue_08 = new CustomComponent(null, 0, 0, true, null, ContentHelpValue_08, CustomComponent.TYPE_IMAGES);
		helpForm_08 = new CustomForm("Help_8");
		helpForm_08.add(helpContent_08);

		helpForm_08.add(helpValue_08);
		helpPage_08.add(helpForm_08);
		helpPage_08.add(helpScroll_08);
		graphHelp = imgHelp.getGraphics(); // Menyimpan tampilan off screen

	}

	public void paintOffScreenHelp(Graphics g) {
		switch (helpPage) {
		case 1:
			helpPage_01.paint(g);
			break;
		case 2:
			helpPage_02.paint(g);
			break;
		case 3:
			helpPage_03.paint(g);
			break;
		case 4:
			helpPage_04.paint(g);
			break;
		case 5:
			helpPage_05.paint(g);
			break;
		case 6:
			helpPage_06.paint(g);
			break;
		case 7:
			helpPage_07.paint(g);
			break;
		case 8:
			helpPage_08.paint(g);
			break;
		}
	}

	public void setHelpPagePosition() {
		helpPage_01.setProperties(xTransWindowHelp, helpPage_01.y, helpPage_01.width);
		helpPage_02.setProperties(xTransWindowHelp + helpPage_01.width, helpPage_02.y, helpPage_02.width);
		helpPage_03.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width, helpPage_03.y, helpPage_03.width);
		helpPage_04.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width, helpPage_04.y, helpPage_04.width);
		helpPage_05.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width + helpPage_04.width, helpPage_05.y, helpPage_05.width);
		helpPage_06.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width + helpPage_04.width + helpPage_05.width, helpPage_06.y, helpPage_06.width);
		helpPage_07.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width + helpPage_04.width + helpPage_05.width + helpPage_06.width, helpPage_07.y, helpPage_07.width);
		helpPage_08.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width + helpPage_04.width + helpPage_05.width + helpPage_06.width + helpPage_07.width, helpPage_08.y, helpPage_08.width);
	}

	public void commandAction(Command c, Displayable d) {
		helpPage_01.commandAction(c, d);
		helpPage_02.commandAction(c, d);
		helpPage_03.commandAction(c, d);
		helpPage_04.commandAction(c, d);
		helpPage_05.commandAction(c, d);
		helpPage_06.commandAction(c, d);
		helpPage_07.commandAction(c, d);
		helpPage_08.commandAction(c, d);
	}

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub

	}

	public void run() {
		if (allowToPressHelp == false) {
			if (movePageHelp == 1) {
				if (xBreakHelp > xTransWindowHelp) {
					xTransWindowHelp += transSpeedHelp;
				} else {
					xTransWindowHelp = xBreakHelp;
					allowToPressHelp = true;
					helpPage--;
				}
				helpPage_01.setProperties(xTransWindowHelp, helpPage_01.y, helpPage_01.width);
				helpPage_02.setProperties(xTransWindowHelp + helpPage_01.width, helpPage_02.y, helpPage_02.width);
				helpPage_03.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width, helpPage_03.y, helpPage_03.width);
				helpPage_04.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width, helpPage_04.y, helpPage_04.width);
				helpPage_05.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width + helpPage_04.width, helpPage_05.y, helpPage_05.width);
				helpPage_06.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width + helpPage_04.width + helpPage_05.width, helpPage_06.y, helpPage_06.width);
				helpPage_07.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width + helpPage_04.width + helpPage_05.width + helpPage_06.width, helpPage_07.y, helpPage_07.width);
				helpPage_08.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width + helpPage_04.width + helpPage_05.width + helpPage_06.width + helpPage_07.width, helpPage_08.y, helpPage_08.width);
			} else if (movePageHelp == -1) {
				if (xTransWindowHelp > xBreakHelp) {
					xTransWindowHelp -= transSpeedHelp;
				} else {
					xTransWindowHelp = xBreakHelp;
					allowToPressHelp = true;
					helpPage++;
				}
				helpPage_01.setProperties(xTransWindowHelp, helpPage_01.y, helpPage_01.width);
				helpPage_02.setProperties(xTransWindowHelp + helpPage_01.width, helpPage_02.y, helpPage_02.width);
				helpPage_03.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width, helpPage_03.y, helpPage_03.width);
				helpPage_04.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width, helpPage_04.y, helpPage_04.width);
				helpPage_05.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width + helpPage_04.width, helpPage_05.y, helpPage_05.width);
				helpPage_06.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width + helpPage_04.width + helpPage_05.width, helpPage_06.y, helpPage_06.width);
				helpPage_07.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width + helpPage_04.width + helpPage_05.width + helpPage_06.width, helpPage_07.y, helpPage_07.width);
				helpPage_08.setProperties(xTransWindowHelp + helpPage_01.width + helpPage_02.width + helpPage_03.width + helpPage_04.width + helpPage_05.width + helpPage_06.width + helpPage_07.width, helpPage_08.y, helpPage_08.width);
			}
			paintOffScreenHelp(graphHelp);
			repaint();
		}
	}
}
