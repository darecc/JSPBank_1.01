package model;

public class Klient {
    private int ID;
    private String nazwisko;
    private String imie;
    private double stanKonta;

    public int getID() {
        return ID;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public double getStanKonta() {
        return stanKonta;
    }

    public String getNumerKonta() {
        return numerKonta;
    }

    private String numerKonta;
    public Klient(int ID, String nazwisko,String imie, String numerKonta, double stanKonta) {
        this.ID = ID;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.numerKonta = numerKonta;
        this.stanKonta = stanKonta;
    }
}
