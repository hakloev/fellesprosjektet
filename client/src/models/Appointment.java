package models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Appointment implements NetInterface {


	private ParticipantListModel participantList;
	private PropertyChangeSupport pcs;

	private int year;
	private int month;
	private int day;
	private int starthour;
	private int startmin;
	private int endhour;
	private int endmin;
	private int duration;
	private boolean startset;
	private boolean endset;
	private boolean durationset;



	public Appointment() {
		pcs = new PropertyChangeSupport(this);
		participantList = new ParticipantListModel();
	}
	
	public void setDate(String date){
		day = Integer.parseInt(date.substring(0,2)); // DD.MM.YYYY
		month = Integer.parseInt(date.substring(3,5)); // 01.34.6789
		year = Integer.parseInt(date.substring(6,10));
	}
	public void setStart(String start){
		starthour = Integer.parseInt(start.substring(0,2));
		startmin = Integer.parseInt(start.substring(3,5));
		startset = true;

		if(endset){
			int newduration = (endhour -starthour)*60 + endmin - startmin;
			pcs.firePropertyChange("Duration", duration, newduration);
			duration = newduration;
			durationset = true;
		}
		else if(durationset){
			String oldEnd = getEnd();
			endmin = startmin + duration;
			endhour = starthour;
			while (endmin > 59){
				endhour += 1;
				endmin -= 60;
			}
			endset = true;
			pcs.firePropertyChange("End", oldEnd, getEnd());
		}

	}
	public void setEnd(String end){
		endhour = Integer.parseInt(end.substring(0,2));
		endmin = Integer.parseInt(end.substring(3,5));
		endset = true;

		if(startset){
			int oldDuration = getDuration();
			duration = (endhour -starthour)*60 + endmin - startmin;
			durationset = true;
			pcs.firePropertyChange("Duration", oldDuration, getDuration());
		}
		
	}
	public void setDuration(int duration){
		this.duration = duration;
		durationset = true;
		if (startset){
			String oldEnd = getEnd();
			int reminder = startmin + duration;
			endhour = starthour;
			while (reminder > 59){
				endhour += 1;
				reminder -= 60;

			}
			endmin = reminder;
			endset = true;
			durationset = true;
			pcs.firePropertyChange("End", oldEnd, getEnd());
		}
		
		
	}
	public String getDate(){
		String ret = "";
		ret += day/10;
		ret += day % 10;
		ret += ".";
		ret += month/10;
		ret += month % 10;
		ret += ".";
		ret += year;
		return ret;
	}
	public String getStart(){
		String ret = "";
		ret += starthour/10;
		ret += starthour % 10;
		ret += ":";
		ret += startmin/10;
		ret += startmin%10;
		return ret;

	}
	public String getEnd(){
		String ret = "";
		ret += endhour/10;
		ret += endhour % 10;
		ret += ":";
		ret += endmin/10;
		ret += endmin%10;
		return ret;
	}
	public int getDuration(){
		return duration;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}

	public ParticipantListModel getParticipantList() {
		return participantList;
	}


	public void setParticipantList(ParticipantListModel participantList) {
		if (participantList != null){
			pcs.firePropertyChange("participantList", this.participantList, participantList);
			this.participantList = participantList;
		}
	}



	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}
}
