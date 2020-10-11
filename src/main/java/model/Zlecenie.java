package model;

public class Zlecenie {
    private int ID;
    private int IDKlienta;
    private int dzien;
    private double kwota;

    public int getID() {
        return ID;
    }

    public int getIDKlienta() {
        return IDKlienta;
    }

    public int getDzien() {
        return dzien;
    }

    public double getKwota() {
        return kwota;
    }

    public String getOdbiorca() {
        return odbiorca;
    }

    private String odbiorca;

    public Zlecenie(int ID, int IDKlienta, int dzien, double kwota, String odbiorca) {
        this.ID = ID;
        this.IDKlienta = IDKlienta;
        this.dzien = dzien;
        this.kwota = kwota;
        this.odbiorca = odbiorca;
    }
}
