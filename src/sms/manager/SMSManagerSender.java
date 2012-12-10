package sms.manager;

import java.io.IOException;
import java.util.Hashtable;

import javax.microedition.io.Connector;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

import data.manager.RMS;

/**
 * @author Neo-Nevz
 * 
 */
public class SMSManagerSender {

	static String timeMsg;

	// Sender Message
	public static void sender(String nomor, String message, String portSender) {

		String nomorTelp = "sms://" + nomor + portSender;
		try {
			MessageConnection mc = (MessageConnection) Connector.open(nomorTelp);
			TextMessage tm = (TextMessage) mc.newMessage(MessageConnection.TEXT_MESSAGE);
			tm.setPayloadText(message);
			mc.send(tm);
			mc.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Receiver Message
	public static void receiver(SMSManagerListenerSender listener, String portReceiver) {
		try {
			MessageConnection mcr = (MessageConnection) Connector.open("sms://" + portReceiver);
			while (true) {
				Message msg = mcr.receive();
				if (msg != null) {
					// Get Time Messagge
					timeMsg = msg.getTimestamp().toString();
					// Save Time Message
					Hashtable timeMsg = new Hashtable();
					timeMsg.put("recentDate", SMSManagerSender.timeMsg);
					RMS.save("dataTimeSender", timeMsg);
					if (msg instanceof TextMessage) {
						TextMessage tmsg = (TextMessage) msg;
						String msgOut = tmsg.getPayloadText();
						listener.SMSManagerListenerSenderEvent(msgOut);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
};
