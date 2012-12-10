package screen;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * @author Neo-Nevz
 * 
 */

public class LoadingAnimation extends Canvas implements Runnable {

	// Loading Animation
	public boolean allowToPress = true;
	public boolean allowToPressSave = true;
	public boolean showLoadingArrow = false;
	public boolean showLoadingSave = false;
	public long endLoading;
	public long endLoadingSave;
	public long intervalLoading = 20000;
	public long intervalLoadingSave = 3000;
	public int action = 1;
	public int frameIndex = 0;
	public int jmlFrame;
	public int frameWidth;
	public int x;
	public int xClip;
	public Image imageTest;
	public int frameHeight;
	public int y;
	public Sprite sprite;
	public Image transparant;
	public Image layoutOut;
	public Image fieldLoad;
	public Image hpOne;
	public Image hpTwo;
	public boolean loadingProgress = true;
	public boolean loadingProgressSave = true;
	public String loadingText;
	public long endLoadingtext = 15000;

	public LoadingAnimation() {

		// Images
		try {
			layoutOut = Image.createImage("/Layout/loadingOut.png");
			transparant = Image.createImage("/Layout/Transparan.png");
			imageTest = Image.createImage("/Loading/test_03.png");
			hpOne = Image.createImage("/ControlMenu/hp_01.png");
			hpTwo = Image.createImage("/ControlMenu/hp_02.png");
			fieldLoad = Image.createImage("/Loading/fieldLoad.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Arrow Loading Animation
		jmlFrame = 8;
		frameHeight = imageTest.getHeight();
		frameWidth = imageTest.getWidth() / jmlFrame;
		x = (getWidth() / 2) - (frameWidth / 2);
		y = (getHeight() / 2) - (frameHeight / 2);
		xClip = x;
		new Thread();
		sprite = new Sprite(imageTest, frameWidth, frameHeight);
		sprite.setRefPixelPosition(x, y);
	}

	// Show Loading Arrow
	public void loadingArrow(Graphics g, int width, int height, int anchor) {
		// Transparan Background
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 16; j++) {
				g.drawImage(transparant, i * 20, j * 20, anchor);
			}
		}

		// Loading Layout
		g.drawImage(layoutOut, width, height, anchor);
		// Icon HP
		g.drawImage(hpOne, width - 110, height, anchor);
		g.drawImage(hpTwo, width + 110, height, anchor);
		// Content
		sprite.setFrame(frameIndex);
		sprite.paint(g);
		Sender.display.callSerially(this);
		g.setColor(0x000000);
		g.setFont(FontLibs.plainfonts);
		g.drawString(loadingText, getWidth() / 2, 127, Graphics.TOP | Graphics.HCENTER);
	}

	// Loading Save
	public void loadingSave(Graphics g, String notify, int widthField, int heightField, int anchorField, int widthString, int heightString, int anchorString) {
		g.drawImage(fieldLoad, widthField, heightField, anchorField);
		g.setColor(0xffffff);
		g.setFont(FontLibs.largeBoldfonts);
		g.drawString(notify, widthString, heightString, anchorString);
	}

	// Show Loading Arrow field
	public void loadingSaveField(Graphics g, String notify, int widthField, int heightField, int anchorField, int widthString, int heightString, int anchorString) {
		if (allowToPressSave == false) {
			if (showLoadingSave == true) {
				loadingSave(g, notify, widthField, heightField, anchorField, widthString, heightString, anchorString);
				Sender.display.callSerially(this);
			}
		}
	}

	public void run() {
		if (action == 1) {
			if (allowToPress == false) {
				if (showLoadingArrow == true) {
					if (loadingProgress == false && System.currentTimeMillis() < endLoading) {
						if (System.currentTimeMillis() < endLoadingtext) {

						}
						if (frameIndex < jmlFrame - 1) {
							x = x - frameWidth;
							frameIndex++;
							try {
								Thread.sleep(3);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} else {
							x = xClip;
							frameIndex = 0;
						}
					} else {
						allowToPress = true;
						loadingProgress = false;
						showLoadingArrow = false;
					}
				}
			}
			repaint();
		}

		if (action == 1) {
			if (allowToPressSave == false) {
				if (showLoadingSave == true) {
					if (loadingProgressSave == false && System.currentTimeMillis() < endLoadingSave) {
						// Testing
						System.out.println("->>" + endLoadingSave + " < " + System.currentTimeMillis());
					} else {
						allowToPressSave = true;
						loadingProgressSave = false;
						showLoadingSave = false;
					}
				}
			}
		}
		repaint();
	}

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
	}

}
