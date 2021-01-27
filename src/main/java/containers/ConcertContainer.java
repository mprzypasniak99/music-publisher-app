package containers;

import java.sql.Date;

public class ConcertContainer {
    private final long id;
    private long id_trasy;
    private final Date data;
    private final String kraj;
    private final String miasto;
    private final String ulica;
    private double zysk;

    public ConcertContainer(long id, Date data, String kraj, String miasto, String ulica) {
        this.id = id;
        this.data = data;
        this.kraj = kraj;
        this.miasto = miasto;
        this.ulica = ulica;
    }

    public ConcertContainer(long id, Date data, String kraj, String miasto, String ulica, double zysk) {
        this.id = id;
        this.data = data;
        this.kraj = kraj;
        this.miasto = miasto;
        this.ulica = ulica;
        this.zysk = zysk;
    }

    public ConcertContainer(long id, long id_trasy, Date data, String kraj, String miasto, String ulica, double zysk) {
        this.id = id;
        this.id_trasy = id_trasy;
        this.data = data;
        this.kraj = kraj;
        this.miasto = miasto;
        this.ulica = ulica;
        this.zysk = zysk;
    }

    public long getId() {return id;};

    public long getId_trasy() {return id_trasy;}

    public Date getData() {return data;}

    public String getKraj() {return kraj;}

    public String getMiasto() {return miasto;}

    public String getUlica() {return ulica;}

    public double getZysk() {return zysk;}

    public String toString() {return "Koncert z " + data + ", w "+ kraj + ", " + miasto + ", " + ulica;}
}
