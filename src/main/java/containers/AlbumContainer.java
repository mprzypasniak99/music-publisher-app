package containers;

public class AlbumContainer {

    private final long id_albumu;
    private final long id_autora;
    private final String autor;
    private final String nazwa;
    private final String gatunek;

    public AlbumContainer(long id_albumu, long id_autora, String autor, String nazwa, String gatunek) {
        this.id_albumu = id_albumu;
        this.id_autora = id_autora;
        this.autor = autor;
        this.nazwa = nazwa;
        this.gatunek = gatunek;
    }

    public long getId_albumu() {
        return id_albumu;
    }

    public long getId_autora() { return id_autora; }

    public String getAutor() { return autor; }

    public String getNazwa() {
        return nazwa;
    }

    public String getGatunek() {
        return gatunek;
    }

    public String toString() {return autor + ", " + nazwa;};
}
