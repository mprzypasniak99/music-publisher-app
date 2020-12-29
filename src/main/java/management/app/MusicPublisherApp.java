package management.app;

import management.dblogic.MusicPublisherDatabase;
import management.gui.LoginWindow;

// Main class for running the app logic and managing various windows in the future

public class MusicPublisherApp {
    public static void main(String[] args) {

        MusicPublisherDatabase db = new MusicPublisherDatabase();
        // class wrapping up communication with database

        LoginWindow log = new LoginWindow(db);
        // window used for logging into the database and the system
    }

}
