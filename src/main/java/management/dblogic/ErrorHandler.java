package management.dblogic;

import management.gui.MessageWindow;

public class ErrorHandler {
    public static void handleError(int errorCode) {
        String title = "Błąd!";
        String content;

        if(errorCode == 2291) {
            content = "Zasób jest niedostępny, lub został usunięty. Odśwież okno edytora";
        }
        else if(errorCode == 1) {
            content = "Nie można dodać ponownie tego samego elementu";
        }
        else {
            content = "Nieznany błąd. Kod błędu: " +  errorCode;
        }

        new MessageWindow(title, content);
    }
}
