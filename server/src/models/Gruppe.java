package models;

import java.util.ArrayList;


public class Gruppe implements DBinterface{
    private String gruppeNavn;
    private ArrayList<Ansatt> ansattListe;

    @Override
    public void save() {

    }
    public void delete() {

    }
    @Override
    public void initialize() {

    }

    @Override
    public void refresh() {

    }

    // Get-ers and set-ers
    public ArrayList<Ansatt> getAnsattListe() {
        return ansattListe;
    }

    public void setAnsattListe(ArrayList<Ansatt> ansattListe) {
        this.ansattListe = ansattListe;
    }

    public String getGruppeNavn() {
        return gruppeNavn;
    }

    public void setGruppeNavn(String gruppeNavn) {
        this.gruppeNavn = gruppeNavn;
    }
}
