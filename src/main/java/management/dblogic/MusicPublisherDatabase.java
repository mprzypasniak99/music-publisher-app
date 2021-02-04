package management.dblogic;

import management.app.MusicPublisherApp;
import containers.*;
import management.gui.MessageWindow;
import oracle.sql.NUMBER;

import java.sql.*;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class MusicPublisherDatabase {
    private Connection conn = null; // connection to the database

    private String connectionString;    // address used to connect to the database, could be read from file
                                        // in the future
    private Properties connectionProps; // container for username and password

    public MusicPublisherDatabase() {
        connectionString = // could be changed to reading from config file file
                "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/"+
                        "dblab02_students.cs.put.poznan.pl";
        connectionProps = new Properties();
    }

    public void addLoginData(String user, String pass) { // add user data used to connect to database
        connectionProps.put("user", user);
        connectionProps.put("password", pass);
    }

    public boolean connect() { // try to connect to the database with given username and password
        try {
            conn = DriverManager.getConnection(connectionString,
                    connectionProps);
            conn.setAutoCommit(false);
            return authorisation(); // if succeeded in logging into the database, check if user is allowed to
            // access data related to this application
        } catch (SQLException ex) {
            Logger.getLogger(MusicPublisherApp.class.getName()).log(Level.SEVERE,
                    "Nie udało się połączyć z bazą danych", ex);
            return false; // if failed to connect, return false to inform about it
        }
    }

    private boolean authorisation() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM SESSION_ROLES")) {
            boolean check = false;
            while(rs.next()) {
                if(rs.getString("ROLE").equals("USER_ROLE")) { // look for specified role name
                    check = true;
                }
                // if user has been granted this role, he is authorized to access the program
            }
            if(!check) {
                disconnect(); // disconnect from the database if user is not authorized
            }
            return check;
        } catch (SQLException throwables) {
            ErrorHandler.handleError(throwables.getErrorCode());
            return false;
        }
    }

    public boolean disconnect() { // close connection with the database
        try {
            conn.close();
            return true;
        } catch (SQLException throwables) {
            ErrorHandler.handleError(throwables.getErrorCode());
            return false;
        }
    }

    public boolean rollback() {
        try{
            conn.rollback();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean commit() {
        try{
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public ArrayList<String> initializeConcerts() {
        ArrayList<String> ret = new ArrayList<String>();
        try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT data_koncertu, kraj, miasto FROM inf141302.Koncert WHERE data_koncertu >= sysdate ORDER BY data_koncertu ASC FETCH FIRST 3 ROW ONLY")) {
            while(rs.next()) {
                ret.add(rs.getDate(1).toString() + " w " + rs.getString(3) + ", " + rs.getString(2));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return ret;
    }

    public ArrayList<String> initializeRecordings() {
        ArrayList<String> ret = new ArrayList<String>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT data_sesji, kraj, miasto FROM inf141302.Sesja_nagraniowa JOIN inf141302.Studio USING(id_studia) WHERE data_sesji >= sysdate ORDER BY data_sesji ASC FETCH FIRST 3 ROW ONLY")) {
            while(rs.next()) {
                ret.add(rs.getDate(1).toString() + " w " + rs.getString(3) + ", " + rs.getString(2));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return ret;
    }

    public Vector<AuthorContainer> getAuthors(boolean band) {
        Vector<AuthorContainer> ac = new Vector<>();
        if(band) {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Zespol")) {
                while(rs.next()) {
                    ac.add(new AuthorContainer(rs.getLong(1), rs.getString(2)));
                }
            } catch (SQLException ex) {
                ErrorHandler.handleError(ex.getErrorCode());
            }
        }
        else {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Artysta")) {
                while(rs.next()) {
                    ac.add(new AuthorContainer(rs.getLong(1), rs.getString(4), rs.getString(2), rs.getString(3)));
                }
            } catch (SQLException ex) {
                ErrorHandler.handleError(ex.getErrorCode());
            }
        }
        return ac;
    }

    public Vector<AuthorContainer> getAuthors(boolean band, String fields) {

        Vector<AuthorContainer> ac = new Vector<>();
        if(band) {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Zespol " + (fields.equals("") ? "WHERE " + fields : ""))) {
                while(rs.next()) {
                    ac.add(new AuthorContainer(rs.getLong(1), rs.getString(2)));
                }
            } catch (SQLException ex) {
                ErrorHandler.handleError(ex.getErrorCode());
            }
        }
        else {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Artysta " + (fields.equals("") ? "WHERE " + fields : ""))) {
                while(rs.next()) {
                    ac.add(new AuthorContainer(rs.getLong(1), rs.getString(4), rs.getString(2), rs.getString(3)));
                }
            } catch (SQLException ex) {
                ErrorHandler.handleError(ex.getErrorCode());
            }
        }
        return ac;
    }

    public Vector<AuthorContainer> getBandMembers(long id_zesp){
        Vector<AuthorContainer> res = new Vector<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT id_autora, pseudonim, imie, nazwisko FROM inf141302.czlonek_zespolu JOIN inf141302.artysta ON id_artysty = id_autora WHERE id_zespolu = ?");) {
            stmt.setLong(1,id_zesp);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                res.add(new AuthorContainer(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            rs.close();
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public boolean addBandMember(long id_zesp, long id_art) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.czlonek_zespolu VALUES (?, ?)");) {
            stmt.setLong(1, id_art);
            stmt.setLong(2, id_zesp);
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
            return false;
        }
    }

    public boolean deleteBandMember(long id_zesp, long id_art) {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM inf141302.czlonek_zespolu WHERE id_artysty = ? AND id_zespolu = ?");) {
            stmt.setLong(1, id_art);
            stmt.setLong(2, id_zesp);
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean addAuthor(String imie, String nazwisko, String pseudonim) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Artysta(imie, nazwisko, pseudonim) VALUES (?, ?, ?)");) {
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            stmt.setString(3, pseudonim);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean addAuthor(String nazwa, Vector<AuthorContainer> czlonkowie) {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT id_autora FROM inf141302.Zespol WHERE nazwa_zespolu = ?");
             PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO inf141302.Zespol(nazwa_zespolu) VALUES (?)");
             PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO inf141302.czlonek_zespolu VALUES (?, ?)");) {
            stmt2.setString(1, nazwa);
            stmt.setString(1, nazwa);
            stmt2.executeUpdate();
            ResultSet rs = stmt.executeQuery();
            rs.next();
            long id = rs.getLong(1);
            for(AuthorContainer tmp : czlonkowie) {
                stmt3.setLong(1, tmp.getId());
                stmt3.setLong(2, id);
                stmt3.addBatch();
            }
            if(czlonkowie.size() > 0) stmt3.executeBatch();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean editAuthor(long id, String imie, String nazwisko, String pseudonim) {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Artysta SET imie = ?, nazwisko = ?, pseudonim = ? WHERE id_autora = ?");) {
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            stmt.setString(3, pseudonim);
            stmt.setLong(4, id);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean editAuthor(long id, String nazwa) {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Zespol SET nazwa_zespolu = ? WHERE id_autora = ?");) {
            stmt.setString(1, nazwa);
            stmt.setLong(2, id);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean addAlbum(long id, String nazwa, String gatunek, Vector<SongContainer> songs) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Album(id_autora, nazwa, gatunek_muzyczny) VALUES (?, ?, ?)");
             PreparedStatement stmt2 = conn.prepareStatement("SELECT id_albumu FROM inf141302.Album WHERE id_autora = ? AND nazwa = ? AND gatunek_muzyczny = ?");
             PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO inf141302.Utwor VALUES (?, ?, ?)")) {
            stmt.setLong(1, id);
            stmt.setString(2, nazwa);
            stmt.setString(3, gatunek);
            stmt.executeUpdate();
            stmt2.setLong(1, id);
            stmt2.setString(2, nazwa);
            stmt2.setString(3, gatunek);
            ResultSet rs = stmt2.executeQuery();
            rs.next();
            long id_a = rs.getLong(1);
            rs.close();
            for(SongContainer s : songs) {
                stmt3.setLong(1, id_a);
                stmt3.setString(2, s.getNazwa());
                stmt3.setInt(3, s.getDlugosc_m()*60+s.getDlugosc_s());
                stmt3.addBatch();
            }
            if(songs.size() > 0) {
                stmt3.executeBatch();
            }
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<AlbumContainer> getAlbums() {
        Vector<AlbumContainer> res = new Vector<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_albumu, id_autora, pseudonim, nazwa, gatunek_muzyczny FROM inf141302.Album JOIN inf141302.Artysta USING(id_autora)");
             Statement stmt2 = conn.createStatement();
             ResultSet rs2 = stmt2.executeQuery("SELECT id_albumu, id_autora, nazwa_zespolu, nazwa, gatunek_muzyczny FROM inf141302.Album JOIN inf141302.Zespol USING(id_autora)");) {
            while(rs.next()) {
                res.add(new AlbumContainer(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            while(rs2.next()) {
                res.add(new AlbumContainer(rs2.getLong(1), rs2.getLong(2), rs2.getString(3), rs2.getString(4), rs2.getString(5)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public Vector<AlbumContainer> getAlbums(boolean band, String fields) {
        String query = "SELECT id_albumu, id_autora, pseudonim, nazwa, gatunek_muzyczny FROM inf141302.Album JOIN";

        Vector<AlbumContainer> res = new Vector<>();
        if(!band) {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query + " inf141302.Artysta USING(id_autora) " + (!fields.equals("") ? "WHERE " + fields : ""));) {
                while (rs.next()) {
                    res.add(new AlbumContainer(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                }
            } catch (SQLException ex) {
                ErrorHandler.handleError(ex.getErrorCode());
            }
        } else {
            try (Statement stmt2 = conn.createStatement();
                 ResultSet rs2 = stmt2.executeQuery("SELECT id_albumu, id_autora, nazwa_zespolu, nazwa, gatunek_muzyczny FROM inf141302.Album JOIN inf141302.Zespol USING(id_autora) " + (!fields.equals("") ? "WHERE " + fields : ""));) {
                while (rs2.next()) {
                    res.add(new AlbumContainer(rs2.getLong(1), rs2.getLong(2), rs2.getString(3), rs2.getString(4), rs2.getString(5)));
                }
            } catch (SQLException ex) {
                ErrorHandler.handleError(ex.getErrorCode());
            }
        }
        return res;
    }

    public boolean deleteSong(long id_albumu, String nazwa) {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM inf141302.Utwor WHERE id_albumu = ? AND nazwa = ?");) {
            stmt.setLong(1, id_albumu);
            stmt.setString(2, nazwa);
            stmt.executeUpdate();

            return true;
        } catch(SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean addSong(long id_albumu, SongContainer song) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Utwor VALUES (?, ?, ?)");) {
            stmt.setLong(1, id_albumu);
            stmt.setString(2, song.getNazwa());
            stmt.setInt(3, song.getDlugosc_m()*60+song.getDlugosc_s());
            stmt.executeUpdate();

            return true;
        } catch(SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }
    public Vector<SongContainer> getSongs(long id_albumu) {
        Vector<SongContainer> ret = new Vector<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM inf141302.Utwor WHERE id_albumu = ?");) {
            stmt.setLong(1, id_albumu);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                ret.add(new SongContainer(rs.getString(2), rs.getInt(3)));
            }
            rs.close();
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return ret;
    }

    public boolean editAlbum(long id_albumu, String nazwa, String gatunek) {
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Album SET nazwa = ?, gatunek_muzyczny = ? WHERE id_albumu = ?");) {
            stmt.setString(1, nazwa);
            stmt.setString(2, gatunek);
            stmt.setLong(3, id_albumu);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean addEmployee(String imie, String nazwisko, String etat) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Pracownik(imie, nazwisko, etat) VALUES (?, ?, ?)");) {
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            stmt.setString(3, etat);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<EmployeeContainer> getEmployees() {
        Vector<EmployeeContainer> ret = new Vector<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Pracownik")) {
            while(rs.next()) {
                ret.add(new EmployeeContainer(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return ret;
    }

    public Vector<EmployeeContainer> getEmployees(String fields) {
        Vector<EmployeeContainer> ret = new Vector<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Pracownik " + (fields.equals("") ? "WHERE " + fields : ""))) {
            while(rs.next()) {
                ret.add(new EmployeeContainer(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return ret;
    }

    public boolean editEmployee(long id, String imie, String nazwisko, String etat, String old_etat){
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Pracownik SET imie = ?, nazwisko = ?, etat = ? WHERE id_prac = ?");
            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM inf141302.Menadzer WHERE id_prac = ?");
            PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM inf141302.Techniczny WHERE id_prac = ?");
            PreparedStatement stmt4 = conn.prepareStatement("INSERT INTO inf141302.Menadzer VALUES (?)");
            PreparedStatement stmt5 = conn.prepareStatement("INSERT INTO inf141302.Techniczny VALUES (?)");) {
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            stmt.setString(3, etat);
            stmt.setLong(4, id);
            stmt.executeUpdate();
            if (!etat.equals(old_etat)) {
                if (old_etat.equals("MENEDŻER")) {
                    stmt2.setLong(1, id);
                    stmt2.executeUpdate();
                } else if (old_etat.equals("TECHNICZNY")) {
                    stmt3.setLong(1, id);
                    stmt3.executeUpdate();
                }
                if (etat.equals("MENEDŻER")) {
                    stmt4.setLong(1, id);
                    stmt4.executeUpdate();
                } else if (etat.equals("TECHNICZNY")) {
                    stmt5.setLong(1, id);
                    stmt5.executeUpdate();
                }
            }
            conn.commit();
            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean addBill(long id_prac, Date data_rozp, Date data_zak, double kwota) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Rachunek(id_prac, data_rozp, data_zak, kwota) VALUES (?, ?, ?, ?)");) {
            stmt.setLong(1, id_prac);
            stmt.setDate(2, data_rozp);
            stmt.setDate(3, data_zak);
            stmt.setDouble(4, kwota);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<BillContainer> getBills() {
        Vector<BillContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_rachunku, id_prac, imie, nazwisko, etat, data_rozp, data_zak, kwota FROM inf141302.Rachunek JOIN inf141302.pracownik USING(id_prac)");) {
            while(rs.next()){
                EmployeeContainer tmp = new EmployeeContainer(rs.getLong(2), rs.getString(3), rs.getString(4), rs.getString(5));
                res.add(new BillContainer(rs.getLong(1), tmp.toString(), rs.getDate(6), rs.getDate(7), rs.getDouble(8)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public Vector<BillContainer> getBills(String fields) {
        Vector<BillContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_rachunku, id_prac, imie, nazwisko, etat, data_rozp, data_zak, kwota FROM inf141302.Rachunek JOIN inf141302.pracownik USING(id_prac) " + (fields.equals("") ? "WHERE " + fields : ""));) {
            while(rs.next()){
                EmployeeContainer tmp = new EmployeeContainer(rs.getLong(2), rs.getString(3), rs.getString(4), rs.getString(5));
                res.add(new BillContainer(rs.getLong(1), tmp.toString(), rs.getDate(6), rs.getDate(7), rs.getDouble(8)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public boolean editBill(long id_rachunku, Date data_rozp, Date data_zak, double kwota) {
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Rachunek SET data_rozp = ?, data_zak = ?, kwota = ? WHERE id_rachunku = ?");) {
            stmt.setLong(4, id_rachunku);
            stmt.setDate(1, data_rozp);
            stmt.setDate(2, data_zak);
            stmt.setDouble(3, kwota);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<EmployeeContainer> getManagers() {
        Vector<EmployeeContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Pracownik JOIN inf141302.Menadzer USING(id_prac)");) {
            while(rs.next()) {
                res.add(new EmployeeContainer(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public boolean addContract(long id_autora, long id_menedzera, Date data_rozp, Date data_zak, double kwota) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Kontrakt(id_autora, id_menadzera, data_zawarcia, data_wygasniecia, kwota) VALUES (?, ?, ?, ?, ?)");) {
            stmt.setLong(1, id_autora);
            stmt.setLong(2, id_menedzera);
            stmt.setDate(3, data_rozp);
            stmt.setDate(4, data_zak);
            stmt.setDouble(5, kwota);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch(SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<ContractContainer> getContracts() {
        Vector<ContractContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_kontraktu, ar.pseudonim, me.imie, me.nazwisko, data_zawarcia, data_wygasniecia, kwota FROM inf141302.Kontrakt JOIN inf141302.Artysta ar USING(id_autora) JOIN inf141302.Pracownik me ON me.id_prac = id_menadzera");
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT id_kontraktu, nazwa_zespolu, imie, nazwisko, data_zawarcia, data_wygasniecia, kwota FROM inf141302.Kontrakt JOIN inf141302.Zespol USING(id_autora) JOIN inf141302.Pracownik ON id_menadzera = id_prac");) {
            while(rs.next()) {
                res.add(new ContractContainer(rs.getLong(1), rs.getString(2), rs.getString(3) + " " + rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getDouble(7)));
            }
            while(rs2.next()) {
                res.add(new ContractContainer(rs2.getLong(1), rs2.getString(2), rs2.getString(3) + " " + rs2.getString(4), rs2.getDate(5), rs2.getDate(6), rs2.getDouble(7)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public Vector<ContractContainer> getContracts(String fields) {
        Vector<ContractContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_kontraktu, ar.pseudonim, me.imie, me.nazwisko, data_zawarcia, data_wygasniecia, kwota FROM inf141302.Kontrakt JOIN inf141302.Artysta ar USING(id_autora) JOIN inf141302.Pracownik me ON me.id_prac = id_menadzera " + (fields.equals("") ? "WHERE " + fields : ""));
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT id_kontraktu, nazwa_zespolu, imie, nazwisko, data_zawarcia, data_wygasniecia, kwota FROM inf141302.Kontrakt JOIN inf141302.Zespol USING(id_autora) JOIN inf141302.Pracownik ON id_menadzera = id_prac " + (fields.equals("") ? "WHERE " + fields : ""));) {
            while(rs.next()) {
                res.add(new ContractContainer(rs.getLong(1), rs.getString(2), rs.getString(3) + " " + rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getDouble(7)));
            }
            while(rs2.next()) {
                res.add(new ContractContainer(rs2.getLong(1), rs2.getString(2), rs2.getString(3) + " " + rs2.getString(4), rs2.getDate(5), rs2.getDate(6), rs2.getDouble(7)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public boolean editContract(long id_kontraktu, Date data_zawarcia, Date data_wygasniecia, double kwota) {
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Kontrakt SET data_zawarcia = ?, data_wygasniecia = ?, kwota = ? WHERE id_kontraktu = ?");) {
            stmt.setDate(1, data_zawarcia);
            stmt.setDate(2, data_wygasniecia);
            stmt.setDouble(3, kwota);
            stmt.setLong(4, id_kontraktu);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean addConcert(Date data, String kraj, String miasto, String ulica, double zysk) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Koncert(data_koncertu, kraj, miasto, ulica, zysk) VALUES (?, ?, ?, ?, ?)");) {
            stmt.setDate(1, data);
            stmt.setString(2, kraj);
            stmt.setString(3, miasto);
            stmt.setString(4, ulica);
            stmt.setDouble(5, zysk);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch(SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
            return false;
        }
    }

    public boolean addConcert(Date data, String kraj, String miasto, String ulica) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Koncert(data_koncertu, kraj, miasto, ulica, zysk) VALUES (?, ?, ?, ?, ?)");) {
            stmt.setDate(1, data);
            stmt.setString(2, kraj);
            stmt.setString(3, miasto);
            stmt.setString(4, ulica);
            stmt.setNull(5, Types.DOUBLE);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch(SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<ConcertContainer> getConcerts() {
        Vector<ConcertContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_koncertu, data_koncertu, kraj, miasto, ulica, zysk FROM inf141302.Koncert");) {
            while(rs.next()) {
                res.add(new ConcertContainer(rs.getLong(1), rs.getDate(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public Vector<ConcertContainer> getConcerts(String fields) {
        Vector<ConcertContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_koncertu, data_koncertu, kraj, miasto, ulica, zysk FROM inf141302.Koncert " + (fields.equals("") ? "WHERE " + fields : ""));) {
            while(rs.next()) {
                res.add(new ConcertContainer(rs.getLong(1), rs.getDate(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public boolean editConcert(long id, Date data, String kraj, String miasto, String ulica, double zysk) {
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Koncert SET data_koncertu = ?, kraj = ?, miasto = ?, ulica = ?, zysk = ? WHERE id_koncertu = ?");) {
            stmt.setDate(1, data);
            stmt.setString(2, kraj);
            stmt.setString(3, miasto);
            stmt.setString(4, ulica);
            stmt.setDouble(5, zysk);
            stmt.setLong(6, id);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex){
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<AuthorContainer> getConPerformers(long id_koncertu) {
        Vector<AuthorContainer> res = new Vector<>();
        try(PreparedStatement stmt = conn.prepareStatement("SELECT id_autora, pseudonim, imie, nazwisko FROM inf141302.Artysta JOIN inf141302.Wystep ON id_autora = id_artysty JOIN inf141302.Koncert USING(id_koncertu) WHERE id_koncertu = ?");
            PreparedStatement stmt2 = conn.prepareStatement("SELECT id_autora, nazwa_zespolu FROM inf141302.Zespol JOIN inf141302.Wystep ON id_autora = id_artysty JOIN inf141302.Koncert USING(id_koncertu) WHERE id_koncertu = ?");){
            stmt.setLong(1, id_koncertu);
            stmt2.setLong(1, id_koncertu);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                res.add(new AuthorContainer(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            rs.close();
            ResultSet rs2 = stmt2.executeQuery();
            while(rs2.next()){
                res.add(new AuthorContainer(rs2.getLong(1), rs2.getString(2)));
            }
            rs.close();
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public boolean addConPerformer(long id_artysty, long id_koncertu) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Wystep VALUES (?, ?)");) {
            stmt.setLong(1, id_artysty);
            stmt.setLong(2, id_koncertu);
            stmt.executeUpdate();

            return true;
        } catch(SQLException ex){
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean deleteConPerformer(long id_artysty, long id_koncertu) {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM inf141302.Wystep WHERE id_artysty = ? AND id_koncerrtu = ?");) {
            stmt.setLong(1, id_artysty);
            stmt.setLong(2, id_koncertu);
            stmt.executeUpdate();

            return true;
        } catch(SQLException ex){
            ErrorHandler.handleError(ex.getErrorCode());

            return true;
        }
    }

    public boolean addTour(String nazwa, Date data_rozp, Date data_zak, Vector<Long> vcc) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Trasa(nazwa_trasy, data_rozpoczecia, data_zakonczenia) VALUES (?, ?, ?)");
            PreparedStatement stmt2 = conn.prepareStatement("SELECT id_trasy FROM inf141302.Trasa WHERE nazwa_trasy = ? AND data_rozpoczecia = ? AND data_zakonczenia = ?");
            PreparedStatement stmt3 = conn.prepareStatement("UPDATE inf141302.Koncert SET id_trasy = ? WHERE id_koncertu = ?");) {
            stmt.setString(1, nazwa);
            stmt.setDate(2, data_rozp);
            stmt.setDate(3, data_zak);
            stmt.executeUpdate();
            if(vcc.size() > 0) {
                stmt2.setString(1, nazwa);
                stmt2.setDate(2, data_rozp);
                stmt2.setDate(3, data_zak);
                ResultSet rs = stmt2.executeQuery();
                rs.next();
                for(Long id : vcc) {
                    stmt3.setLong(1, rs.getLong(1));
                    stmt3.setLong(2, id);
                    stmt3.addBatch();
                }
                stmt3.executeBatch();
                rs.close();
            }
            conn.commit();

            return true;
        } catch(SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
            return false;
        }
    }

    public Vector<ConcertContainer> getConcertsNotInTour() {
        Vector<ConcertContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_koncertu, data_koncertu, kraj, miasto, ulica, zysk FROM inf141302.Koncert WHERE id_trasy IS NULL");) {
            while(rs.next()) {
                res.add(new ConcertContainer(rs.getLong(1), rs.getDate(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public Vector<TourContainer> getTours() {
        Vector<TourContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Trasa");) {
            while(rs.next()) {
                res.add(new TourContainer(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public Vector<TourContainer> getTours(String fields) {
        Vector<TourContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Trasa " + (fields.equals("") ? "WHERE " + fields : ""));) {
            while(rs.next()) {
                res.add(new TourContainer(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public Vector<ConcertContainer> getTourConcerts(long id) {
        Vector<ConcertContainer> res = new Vector<>();
        try(PreparedStatement stmt = conn.prepareStatement("SELECT id_koncertu, data_koncertu, kraj, miasto, ulica, zysk FROM inf141302.Koncert WHERE id_trasy = ?");) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                res.add(new ConcertContainer(rs.getLong(1), rs.getDate(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6)));
            }
            rs.close();
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public boolean addConToTour(long id_trasy, long id_koncertu) {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Koncert SET id_trasy = ? WHERE id_koncertu = ?");) {
            stmt.setLong(1, id_trasy);
            stmt.setLong(2, id_koncertu);
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean deleteConFromTour(long id_koncertu) {
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Koncert SET id_trasy = NULL WHERE id_koncertu = ?");) {
            stmt.setLong(1, id_koncertu);
            stmt.executeUpdate();

            return true;
        } catch(SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
            return false;
        }
    }

    public boolean editTour(long id_trasy, String nazwa, Date data_rozp, Date data_zak) {
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Trasa SET nazwa_trasy = ?, data_rozpoczecia = ?, data_zakonczenia = ? WHERE id_trasy = ?");) {
            stmt.setString(1, nazwa);
            stmt.setDate(2, data_rozp);
            stmt.setDate(3, data_zak);
            stmt.setLong(4, id_trasy);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch(SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean addStudio(String kraj, String miasto, String ulica) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Studio(kraj, miasto, ulica) VALUES (?, ?, ?)");) {
            stmt.setString(1, kraj);
            stmt.setString(2, miasto);
            stmt.setString(3, ulica);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch(SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<StudioContainer> getStudios() {
        Vector<StudioContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Studio");) {
            while(rs.next()) {
                res.add(new StudioContainer(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        }  catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public Vector<StudioContainer> getStudios(String fields) {
        Vector<StudioContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Studio " + (fields.equals("") ? "WHERE " + fields : ""));) {
            while(rs.next()) {
                res.add(new StudioContainer(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        }  catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public boolean editStudio(long id, String kraj, String miasto, String ulica) {
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Studio SET kraj = ?, miasto = ?, ulica = ? WHERE id_studia = ?");) {
            stmt.setString(1, kraj);
            stmt.setString(2, miasto);
            stmt.setString(3, ulica);
            stmt.setLong(4, id);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean addSession(long id_autora, Date data_sesji, long id_studia) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Sesja_nagraniowa VALUES (?, ?, ?)");) {
            stmt.setLong(1, id_autora);
            stmt.setDate(2, data_sesji);
            stmt.setLong(3, id_studia);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<SessionContainer> getSessions() {
        Vector<SessionContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_autora, pseudonim, data_sesji, id_studia, kraj, miasto, ulica FROM inf141302.Artysta JOIN inf141302.Sesja_nagraniowa USING(id_autora) JOIN inf141302.Studio USING(id_studia)");
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT id_autora, nazwa_zespolu, data_sesji, id_studia, kraj, miasto, ulica FROM inf141302.Zespol JOIN inf141302.Sesja_nagraniowa USING(id_autora) JOIN inf141302.Studio USING(id_studia)");) {
            while(rs.next()) {
                res.add(new SessionContainer(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getLong(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
            while(rs2.next()) {
                res.add(new SessionContainer(rs2.getLong(1), rs2.getString(2), rs2.getDate(3), rs2.getLong(4), rs2.getString(5), rs2.getString(6), rs2.getString(7)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public Vector<SessionContainer> getSessions(String fields) {
        Vector<SessionContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_autora, pseudonim, data_sesji, id_studia, kraj, miasto, ulica FROM inf141302.Artysta JOIN inf141302.Sesja_nagraniowa USING(id_autora) JOIN inf141302.Studio USING(id_studia) " + (fields.equals("") ? "WHERE " + fields : ""));
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT id_autora, nazwa_zespolu, data_sesji, id_studia, kraj, miasto, ulica FROM inf141302.Zespol JOIN inf141302.Sesja_nagraniowa USING(id_autora) JOIN inf141302.Studio USING(id_studia) " + (fields.equals("") ? "WHERE " + fields : ""));) {
            while(rs.next()) {
                res.add(new SessionContainer(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getLong(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
            while(rs2.next()) {
                res.add(new SessionContainer(rs2.getLong(1), rs2.getString(2), rs2.getDate(3), rs2.getLong(4), rs2.getString(5), rs2.getString(6), rs2.getString(7)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public boolean editSession(long new_id_autora, long old_id_autora, Date data_sesji, Date old_data_sesji, long new_id_studia) {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM inf141302.Obsluga_sesji WHERE data_sesji = ? AND id_autora = ?");
            PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO inf141302.Obsluga_sesji VALUES (?, ?, ?)");) {
            stmt.setDate(1, old_data_sesji);
            stmt.setLong(2, old_id_autora);
            stmt2.setLong(1, new_id_autora);
            stmt2.setDate(2, data_sesji);
            stmt2.setLong(3, new_id_studia);
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<EmployeeContainer> getTechEmployees() {
        Vector<EmployeeContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Pracownik JOIN inf141302.Techniczny USING(id_prac)");) {
            while(rs.next()) {
                res.add(new EmployeeContainer(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public boolean addConTech(long id_koncertu, long id_prac) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Obsluga_koncertu VALUES (?, ?)");) {
            stmt.setLong(1, id_prac);
            stmt.setLong(2, id_koncertu);
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean deleteConTech(long id_koncertu, long id_prac) {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM inf141302.Obsluga_koncertu WHERE id_prac = ? AND id_koncertu = ?");) {
            stmt.setLong(1, id_prac);
            stmt.setLong(2, id_koncertu);
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<EmployeeContainer> getConTech(long id_koncertu) {
        Vector<EmployeeContainer> res = new Vector<>();
        try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM inf141302.Pracownik JOIN inf141302.Obsluga_koncertu USING(id_prac) WHERE id_koncertu = ?");) {
            stmt.setLong(1, id_koncertu);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                res.add(new EmployeeContainer(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            rs.close();
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public boolean addSesTech(long id_prac, Date data_sesji, long id_autora) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Obsluga_sesji VALUES (?, ?, ?)");) {
            stmt.setLong(1, id_prac);
            stmt.setDate(2, data_sesji);
            stmt.setLong(3, id_autora);
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean deleteSesTech(long id_prac, Date data_sesji, long id_autora) {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM inf141302.Obsluga_sesji WHERE id_prac = ? AND AND data_sesji = ? AND id_autora = ?");) {
            stmt.setLong(1, id_prac);
            stmt.setDate(2, data_sesji);
            stmt.setLong(3, id_autora);
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<EmployeeContainer> getSesTech(Date data_sesji, long id_autora) {
        Vector<EmployeeContainer> res = new Vector<>();
        try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM inf141302.Pracownik JOIN inf141302.Obsluga_sesji USING(id_prac) WHERE data_sesji = ? AND id_autora = ?");) {
            stmt.setDate(1, data_sesji);
            stmt.setLong(2, id_autora);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                res.add(new EmployeeContainer(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            rs.close();
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public Vector<StorageMediumContainer> getStorageMedia() {
        Vector<StorageMediumContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inf141302.Nosnik");) {
            while(rs.next()) {
                res.add(new StorageMediumContainer(rs.getString(1), rs.getDouble(2)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public boolean addStorageMedium(String typ, double cena) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Nosnik VALUES (?, ?)");) {
            stmt.setString(1, typ);
            stmt.setDouble(2, cena);
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean editPrice(String typ, double cena) {
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Nosnik SET cena_nosnika = ? WHERE typ = ?");) {
            stmt.setString(2, typ);
            stmt.setDouble(1, cena);
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean addAlbumSell(long id_albumu, double cena, long ilosc, String typ_nosnika) {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO inf141302.Sprzedaz VALUES (?, ?, ?, ?)");) {
            stmt.setLong(1, id_albumu);
            stmt.setDouble(2, cena);
            stmt.setLong(3, ilosc);
            stmt.setString(4, typ_nosnika);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public boolean editAlbumSell(long id_albumu, double cena, long ilosc, String typ_nosnika) {
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE inf141302.Sprzedaz SET cena_za_sztuke = ?, ilosc_sprzedanych = ? WHERE id_albumu = ? AND typ = ?");) {
            stmt.setLong(3, id_albumu);
            stmt.setDouble(1, cena);
            stmt.setLong(2, ilosc);
            stmt.setString(4, typ_nosnika);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());

            return false;
        }
    }

    public Vector<SellContainer> getSells() {
        Vector<SellContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_albumu, nazwa, cena_za_sztuke, ilosc_sprzedanych, typ, cena_nosnika FROM inf141302.Album JOIN inf141302.Sprzedaz USING(id_albumu) JOIN inf141302.Nosnik USING(typ)");) {
            while(rs.next()) {
                res.add(new SellContainer(rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getLong(4), rs.getString(5), rs.getDouble(6)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public Vector<SellContainer> getSells(String fields) {
        Vector<SellContainer> res = new Vector<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_albumu, nazwa, cena_za_sztuke, ilosc_sprzedanych, typ, cena_nosnika FROM inf141302.Album JOIN inf141302.Sprzedaz USING(id_albumu) JOIN inf141302.Nosnik USING(typ) " + (fields.equals("") ? "WHERE " + fields : ""));) {
            while(rs.next()) {
                res.add(new SellContainer(rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getLong(4), rs.getString(5), rs.getDouble(6)));
            }
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }

    public double getSellReportForAlbum(long id) {
        double res = 0;
        try(CallableStatement cs = conn.prepareCall("{? = call inf141302.Album_zysk(?)}");) {
            cs.registerOutParameter(1, Types.DOUBLE);
            cs.setLong(2, id);
            cs.execute();
            res = cs.getDouble(1);
        } catch (SQLException ex) {
            ErrorHandler.handleError(ex.getErrorCode());
        }
        return res;
    }
}
