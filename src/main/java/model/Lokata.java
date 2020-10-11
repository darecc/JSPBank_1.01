package model;

import java.sql.Date;

public class Lokata {
    private int ID;
    private int IDKlienta;
    private double kwota;
    private int czasTrwania;

    public double getOprocentowanie() {
        return oprocentowanie;
    }

    private double oprocentowanie;

    public int getID() {
        return ID;
    }

    public int getIDKlienta() {
        return IDKlienta;
    }

    public double getKwota() {
        return kwota;
    }

    public int getCzasTrwania() {
        return czasTrwania;
    }

    public Date getDataPoczatkowa() {
        return dataPoczatkowa;
    }

    private Date dataPoczatkowa;

    public Lokata(int ID, int IDKlienta, double kwota, int czasTrwania, Date dataPoczatkowa, double procent) {
        this.ID = ID;
        this.IDKlienta = IDKlienta;
        this.kwota = kwota;
        this.czasTrwania = czasTrwania;
        this.dataPoczatkowa = dataPoczatkowa;
        this.oprocentowanie = procent;
    }
}
