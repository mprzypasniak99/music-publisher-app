package containers;

public class SellContainer {
    private final long id_albumu;
    private final String nazwa;
    private final double cena_szt;
    private final long ilosc;
    private final String typ;
    private final double cena_no;

    public SellContainer(long id_albumu, String nazwa, double cena_szt, long ilosc, String typ, double cena_no) {
        this.id_albumu = id_albumu;
        this.nazwa = nazwa;
        this.cena_szt = cena_szt;
        this.ilosc = ilosc;
        this.typ = typ;
        this.cena_no = cena_no;
    }

    public long getId_albumu() {
        return id_albumu;
    }

    public String getNazwa() {
        return nazwa;
    }

    public double getCena_szt() {
        return cena_szt;
    }

    public long getIlosc() {
        return ilosc;
    }

    public String getTyp() {
        return typ;
    }

    public double getCena_no() {
        return cena_no;
    }

    public String toString(){
        return nazwa + " " + typ;
    }
}
