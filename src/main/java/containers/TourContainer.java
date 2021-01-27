package containers;

import java.sql.Date;
import java.util.Calendar;

public class TourContainer {
    private final long id_trasy;
    private final String nazwa;
    private final Date data_rozp;
    private final Date data_zak;
    private boolean started;

    public TourContainer(long id_trasy, String nazwa, Date data_rozp, Date data_zak) {
        this.id_trasy = id_trasy;
        this.nazwa = nazwa;
        this.data_rozp = data_rozp;
        this.data_zak = data_zak;
        this.started = data_rozp.getTime() < Calendar.getInstance().getTimeInMillis();
    }

    public long getId_trasy() {return id_trasy;}

    public String getNazwa() {return nazwa;}

    public Date getData_rozp(){return data_rozp;}

    public Date getData_zak(){return data_zak;}

    public boolean getStarted() {return started;}

    public String toString(){return nazwa;};
}
