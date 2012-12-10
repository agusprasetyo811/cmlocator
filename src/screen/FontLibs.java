package screen;

import javax.microedition.lcdui.Font;

public class FontLibs {

	public static Font plainfonts;
	public static Font boldfonts;
	public static Font plainfontsitalic;
	public static Font largeBoldfonts;

	public FontLibs() {
		// Fonts
		plainfonts = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		boldfonts = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
		plainfontsitalic = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_ITALIC, Font.SIZE_SMALL);
		largeBoldfonts = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE);
	}
}
