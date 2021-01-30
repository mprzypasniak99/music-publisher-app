package containers;

public class StorageMediumContainer {
    private final String typ;
    private final double cena;

    public StorageMediumContainer(String typ, double cena) {
        this.typ = typ;
        this.cena = cena;
    }

    public String getTyp() {return typ;}

    public double getCena() {return cena;}

    public String toString() {return typ;}
}
