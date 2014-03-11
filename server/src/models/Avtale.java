package models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Stasj on 07.03.14.
 */
public class Avtale implements DBinterface{
    private int avtaleID;
    private Ansatt avtaleAnsvarlig;
    private Date startTid;
    private Date sluttTid;
    private String beskrivelse;
    private String sted;
    private Moterom moterom;
    private ArrayList<Ansatt> deltagerListe;

    //Constructor med beskriverlse og sted
    public Avtale(int avtaleID, Ansatt avtaleAnsvarlig,Date startTid, Date sluttTid, String beskrivelse, String sted, ArrayList<Ansatt> deltagerListe){
        this.avtaleAnsvarlig = avtaleAnsvarlig;
        this.avtaleID = avtaleID;
        this.beskrivelse = beskrivelse;
        this.deltagerListe = deltagerListe;
        this.sted = sted;
        this.startTid = startTid;
        this.sluttTid = sluttTid;

    }
    //Constructor med beskrivelse og møterom
    public Avtale(int avtaleID, Ansatt avtaleAnsvarlig, Date startTid, Date sluttTid, String beskrivelse, Moterom moterom, ArrayList<Ansatt> deltagerListe){
        this.avtaleAnsvarlig = avtaleAnsvarlig;
        this.avtaleID = avtaleID;
        this.beskrivelse = beskrivelse;
        this.deltagerListe = deltagerListe;
        this.moterom = moterom;
        this.startTid = startTid;
        this.sluttTid = sluttTid;
    }
    //Constructor uten beskrivelse og med sted.
    public Avtale(int avtaleID, Ansatt avtaleAnsvarlig, Date startTid, Date sluttTid, ArrayList<Ansatt> deltagerListe, String sted) {
        this.avtaleID = avtaleID;
        this.avtaleAnsvarlig = avtaleAnsvarlig;
        this.startTid = startTid;
        this.sluttTid = sluttTid;
        this.deltagerListe = deltagerListe;
        this.sted = sted;
    }

    //Constructor uten beskrivelse og med møterom
    public Avtale(int avtaleID, Ansatt avtaleAnsvarlig, Date startTid, Date sluttTid, ArrayList<Ansatt> deltagerListe, Moterom moterom) {
        this.avtaleID = avtaleID;
        this.avtaleAnsvarlig = avtaleAnsvarlig;
        this.startTid = startTid;
        this.sluttTid = sluttTid;
        this.deltagerListe = deltagerListe;
        this.moterom = moterom;
    }

	@Override
    public void delete(){}

	@Override
    public void initialize(){}

    @Override
    public void save() {

   }

    @Override
    public void refresh() {

    }

    public int getAvtaleID() {
        return avtaleID;
    }
    public void setAvtaleID(int avtaleID) {
        this.avtaleID = avtaleID;
    }

    public Ansatt getAvtaleAnsvarlig() {
        return avtaleAnsvarlig;
    }

    public void setAvtaleAnsvarlig(Ansatt avtaleAnsvarlig) {
        this.avtaleAnsvarlig = avtaleAnsvarlig;
    }

    public Date getStartTid() {
        return startTid;
    }

    public void setStartTid(Date startTid) {
        this.startTid = startTid;
    }

    public Date getSluttTid() {
        return sluttTid;
    }

    public void setSluttTid(Date sluttTid) {
        this.sluttTid = sluttTid;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getSted() {
        return sted;
    }

    public void setSted(String sted) {
        this.sted = sted;
    }

    public Moterom getMoterom() {
        return moterom;
    }

    public void setMoterom(Moterom moterom) {
        this.moterom = moterom;
    }

    public ArrayList<Ansatt> getDeltagerListe() {
        return deltagerListe;
    }

    public void setDeltagerListe(ArrayList<Ansatt> deltagerListe) {
        this.deltagerListe = deltagerListe;
    }
}
