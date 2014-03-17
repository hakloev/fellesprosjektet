package models;

import javax.swing.ImageIcon;

public enum ParticipantStatus {
	
	
	participating("Deltar", "resources/participating-14.png"),
	notParticipating("Deltar ikke", "resources/notParticipating-14.png");
	
	
	private String status;
	public static final ImageIcon noStatusIcon = new ImageIcon("resources/noStatus-14.png");
	public static final ImageIcon leaderIcon = new ImageIcon("resources/leader-14.png");
	private final ImageIcon statusIcon;
	
	
	private ParticipantStatus(String status, String iconPath) {
		this.status = status;
		this.statusIcon = new ImageIcon(iconPath);
	}
	
	
	public static ParticipantStatus[] getStatusList() {
		ParticipantStatus[] statusList = {ParticipantStatus.participating, ParticipantStatus.notParticipating};
		return statusList;
	}
	
	
	public String toString() {
		return status;
	}
	
	
	public ImageIcon getStatusIcon() {
		return statusIcon;
	}
	
}
