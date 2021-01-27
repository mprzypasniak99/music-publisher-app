package containers;

public class AuthorContainer {
    private final long id;
    private final String pseudonim;
    private String imie = null;
    private String nazwisko = null;
    public AuthorContainer(long id, String nazwa) {
        this.id = id;
        this.pseudonim = nazwa;
    }
    public AuthorContainer(long id, String pseudonim, String imie, String nazwisko) {
        this.id = id;
        this.pseudonim = pseudonim;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }
    public String toString() {
        if(imie == null && nazwisko == null) {
            return pseudonim;
        }
        else {
            return imie + " \"" + pseudonim + "\" " + nazwisko;
        }
    }

    // =========== GETTERS ==============
    public long getId() {
        return id;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getPseudonim() {
        return pseudonim;
    }
}
