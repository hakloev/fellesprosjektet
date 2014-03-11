package models;


public class Moterom implements DBinterface {
    private String romKode;

    public Moterom(String romKode) {
        this.romKode = romKode;
    }

    public void delete() {

    }

    public void initialize() {

    }

    public void save() {

    }

    @Override
    public void refresh() {

    }

    public String getRomKode() {
        return romKode;
    }

    public void setRomKode(String romKode) {
        this.romKode = romKode;
    }


}
