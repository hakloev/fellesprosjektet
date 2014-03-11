package models;


public class Varsel implements DBinterface {
    private int varselID;
    private boolean erSett;
    private type_varsel typeVarsel;
    private Ansatt ansatt;
    private Avtale avtale;

    public Varsel(int varselID, boolean erSett, type_varsel typeVarsel, Ansatt ansatt, Avtale avtale) {
        this.varselID = varselID;
        this.erSett = erSett;
        this.typeVarsel = typeVarsel;
        this.ansatt = ansatt;
        this.avtale = avtale;
    }

    @Override
    public void save() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void refresh() {
    }

    public int getVarselID() {
        return varselID;
    }

    public void setVarselID(int varselID) {
        this.varselID = varselID;
    }

    public boolean isErSett() {
        return erSett;
    }

    public void setErSett(boolean erSett) {
        this.erSett = erSett;
    }

    public type_varsel getTypeVarsel() {
        return typeVarsel;
    }

    public void setTypeVarsel(type_varsel typeVarsel) {
        this.typeVarsel = typeVarsel;
    }

    public Ansatt getAnsatt() {
        return ansatt;
    }

    public void setAnsatt(Ansatt ansatt) {
        this.ansatt = ansatt;
    }

    public Avtale getAvtale() {
        return avtale;
    }

    public void setAvtale(Avtale avtale) {
        this.avtale = avtale;
    }
}
