package containers;

public class SongContainer {
    private final String nazwa;
    private final int dlugosc_m;
    private final int dlugosc_s;

    public SongContainer(String nazwa, int dlugosc_m, int dlugosc_s) {
        this.nazwa = nazwa;
        this.dlugosc_m = dlugosc_m;
        this.dlugosc_s = dlugosc_s;
    }

    public SongContainer(String nazwa, int dlugosc) {
        this.nazwa = nazwa;
        this.dlugosc_m = dlugosc / 60;
        this.dlugosc_s = dlugosc % 60;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getDlugosc_m() { return dlugosc_m;}

    public int getDlugosc_s() { return dlugosc_s;}

    public String toString() {
        if(dlugosc_s > 10) {
            return nazwa + ", " + dlugosc_m + ":" + dlugosc_s;
        } else {
            return nazwa + ", " + dlugosc_m + ":0" + dlugosc_s;
        }
    }
}
