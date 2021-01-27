package containers;

import java.sql.Date;
import java.util.Calendar;

public class BillContainer {
    private final long id_rachunku;
    private final String pracownik;
    private final Date data_rozp;
    private final Date data_zak;
    private final double kwota;
    private final boolean started;

    public BillContainer(long id_rachunku, String pracownik, Date data_rozp, Date data_zak, double kwota) {
        this.id_rachunku = id_rachunku;
        this.pracownik = pracownik;
        this.data_rozp = data_rozp;
        this.data_zak = data_zak;
        this.kwota = kwota;
        started = data_rozp.getTime() < Calendar.getInstance().getTimeInMillis();
    }

    public long getId_rachunku() {return id_rachunku;}

    public String getPracownik() {return pracownik;}

    public Date getData_rozp() {return data_rozp;}

    public Date getData_zak() {return data_zak;}

    public double getKwota() {return kwota;}

    public boolean getStarted() {return started;}

    public String toString() {return id_rachunku + ", " + pracownik;}
}
