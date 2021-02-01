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
        else if(errorCode == 2290) {
            content = "Wprowadzono niepoprawne dane, sprawdź ich zgodność";
        }
        else if(errorCode == 1400) {
            content = "Wypełnij wszystkie wymagane pola";
        }
        else if(errorCode == 20004) {
            content = "Data koncertu, który chcesz dodać do trasy, nie mieści się w ramach czasowych tej trasy";
        }
        else if(errorCode == 20003) {
            content = "Pracownik jest przydzielony do innego koncertu tego dnia";
        }
        else if(errorCode == 20001) {
            content = "Pracownik jest przydzielony do innej sesji tego dnia";
        }
        else if(errorCode == 20000) {
            content = "Artysta ma już zaplanowaną sesję w tym czasie";
        }
        else if(errorCode == 20002) {
            content = "Artysta ma już zaplanowany koncert w tym czasie";
        }
        else {
            content = "Nieznany błąd. Kod błędu: " +  errorCode;
        }

        new MessageWindow(title, content);
    }
}
