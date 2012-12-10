package sms.manager;

import java.util.Hashtable;

import data.manager.RMS;

/**
 * @author Neo-Nevz
 * 
 */
public class ActionMessageSender {

	private ActionMessageSender() {

	}

	// Mengambil nilai phone number dari RMS
	private static String phoneNumber() {
		String phoneNumber = null;
		Hashtable dataSender = RMS.load("senderData");
		if (dataSender != null) {
			phoneNumber = dataSender.get("noSender").toString();
		} else {
			phoneNumber = "+6285728103256";
		}
		return phoneNumber;
	}

	// Sent GPS Control
	public static void gps() {
		new Thread() {
			public void run() {
				SMSManagerSender.sender(phoneNumber(), "#GP5", ":11108");
			};
		}.start();
	}

	// Sent Locate Control
	public static void alarm() {
		new Thread() {
			public void run() {
				SMSManagerSender.sender(phoneNumber(), "#4L4RM", ":11108");
			};
		}.start();
	}

	// Sent info Control
	public static void vibrate() {
		new Thread() {
			public void run() {
				SMSManagerSender.sender(phoneNumber(), "#V113R4TE", ":11108");
			};
		}.start();
	}

	// Setting number Receiver
	public static void setting(final String message) {
		new Thread() {
			public void run() {
				SMSManagerSender.sender(phoneNumber(), "#" + message, ":11108");
			};
		}.start();
	}

	// Setting Screen Receiver
	public static void settingScreen(final String message) {
		new Thread() {
			public void run() {
				SMSManagerSender.sender(phoneNumber(), "$" + message, ":11108");
			};
		}.start();
	}
}