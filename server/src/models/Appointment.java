package models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.*;

//@JsonIgnoreProperties({"participantList", "getParticipants", "size"})
public class Appointment implements DBInterface {


	@JsonProperty("participantList")
	private ParticipantListModel participantList;

	@JsonProperty("year")
	private int year;
	@JsonProperty("month")
	private int month;
	@JsonProperty("day")
	private int day;
	@JsonProperty("starthour")
	private int starthour;
	@JsonProperty("startmin")
	private int startmin;
	@JsonProperty("endhour")
	private int endhour;
	@JsonProperty("endmin")
	private int endmin;

	private int duration;
	private boolean startset;
	private boolean endset;
	private boolean durationset;



	public Appointment() {

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
		}
		else if(durationset){
			String oldStart = getStart();
			startmin = endmin - duration;
			starthour = endhour;
			while (startmin < 0){
				starthour -= 1;
				startmin += 60;
			}
			startset = true;
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
		}
		else if (!startset && endset){
			String oldStart = getStart();
			int reminder = endmin - duration;
			starthour = endhour;
			while (reminder < 0){
				starthour -= 1;
				reminder += 60;

			}
			startmin = reminder;
			startset = true;
			durationset = true;
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


	public ParticipantListModel getParticipantList() {
		return participantList;
	}


	public void setParticipantList(ParticipantListModel participantList) {
		if (participantList != null){
			this.participantList = participantList;
		}
	}


	@Override
	public String toString() {
		return "Appointment{" +
				"participantList=" + participantList +
				", year=" + year +
				", month=" + month +
				", day=" + day +
				", starthour=" + starthour +
				", startmin=" + startmin +
				", endhour=" + endhour +
				", endmin=" + endmin +
				", duration=" + duration +
				", startset=" + startset +
				", endset=" + endset +
				", durationset=" + durationset +
				'}';
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

	}

	@Override
	public void delete() {

	}
}
