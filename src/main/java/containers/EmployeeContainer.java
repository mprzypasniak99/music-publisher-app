package containers;

public class EmployeeContainer {
    private final long id;
    private final String imie;
    private final String nazwisko;
    private final String etat;

    public EmployeeContainer(long id, String imie, String nazwisko, String etat) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.etat = etat;
    }

    public long getId() {
        return id;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getEtat() {
        return etat;
    }

    public String toString() {
        return imie + " " + nazwisko + ", " + etat;
    }
}