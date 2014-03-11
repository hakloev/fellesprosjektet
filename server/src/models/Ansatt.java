package models;


public class Ansatt implements DBinterface {
    private String brukernavn;
    private String password;
    private String navn;

    public Ansatt(String brukernavn, String password, String navn) {
        this.brukernavn = brukernavn;
        this.password = password;
        this.navn = navn;
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

    public String getBrukernavn() {
        return brukernavn;
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }
}
