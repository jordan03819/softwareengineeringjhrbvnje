import java.net.*;
import java.util.*;

public class Guest extends User {
	private String sessionID;
	private String userType;

	public Guest(String notimportant) {
		super(generateSessionID(), "guest");
		this.sessionID = generateSessionID();
		this.userType = "guest";
	}

	public static String generateSessionID() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			NetworkInterface networkInterface = networkInterfaces.nextElement();
			byte[] mac = networkInterface.getHardwareAddress();
			if (mac != null) {
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
				   stringBuilder.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
				}
			return stringBuilder.toString();
	   		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return UUID.randomUUID().toString();
	}

	
	public void likeComment(Article article) {
		article.addLike(sessionID);
	}

	public String getSessionID() {
		return sessionID;
	}

	public String getUserType() {
		return userType;
	}

	@Override
	public void viewMenu() {
		System.out.println("Guest doesn't have a menu");
	}
}
