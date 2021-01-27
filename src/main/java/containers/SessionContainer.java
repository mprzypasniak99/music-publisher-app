package containers;

import java.sql.Date;

public class SessionContainer {
    private final long id_autora;
    private final String autor;
    private final Date data_sesji;
    private final StudioContainer studio;

    public SessionContainer(long id_autora, String autor, Date data, long id_studia, String kraj, String miasto, String ulica) {
        this.id_autora = id_autora;
        this.autor = autor;
        data_sesji = data;
        studio = new StudioContainer(id_studia, kraj, miasto, ulica);
    }

    public long getId_autora() {return id_autora;}

    public String getAutor() {return autor;}

    public Date getData_sesji() {return data_sesji;}

    public long getStudioId() {return studio.getId();}

    public String toString() {return "Sesja z " + data_sesji + " dla " + autor;}


}
