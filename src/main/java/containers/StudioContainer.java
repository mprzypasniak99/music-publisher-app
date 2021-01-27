package containers;

public class StudioContainer {
    private final long id;
    private final String kraj;
    private final String miasto;
    private final String ulica;

    public StudioContainer(long id, String kraj, String miasto, String ulica) {
        this.id = id;
        this.kraj = kraj;
        this.miasto = miasto;
        this.ulica = ulica;
    }

    public long getId() {return id;}

    public String getKraj() {return kraj;}

    public String getMiasto() {return miasto;}

    public String getUlica() {return ulica;}

    public String toString() {return id + ", " + kraj + ", " + miasto + ", " + ulica;}
}
