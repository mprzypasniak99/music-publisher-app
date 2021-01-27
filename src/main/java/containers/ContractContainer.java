package containers;

import java.sql.Date;
import java.util.Calendar;

public class ContractContainer {
    private final long id_kontraktu;
    private final String autor;
    private final String menedzer;
    private final Date data_rozp;
    private final Date data_zak;
    private final double kwota;
    private final boolean started;

    public ContractContainer(long id_kontraktu, String autor, String menedzer, Date data_rozp, Date data_zak, double kwota) {
        this.id_kontraktu = id_kontraktu;
        this.autor = autor;
        this.menedzer = menedzer;
        this.data_rozp = data_rozp;
        this.data_zak = data_zak;
        this.kwota = kwota;
        started = data_rozp.getTime() < Calendar.getInstance().getTimeInMillis();
    }

    public long getId_kontraktu() {return id_kontraktu;}

    public String getAutor() {return autor;}

    public String getMenedzer() {return menedzer;}

    public Date getData_rozp() {return data_rozp;}

    public Date getData_zak() {return data_zak;}

    public double getKwota() {return kwota;}

    public boolean getStarted() {return started;}

    public String toString() {return id_kontraktu + ", " + autor + ", " + menedzer;}
}