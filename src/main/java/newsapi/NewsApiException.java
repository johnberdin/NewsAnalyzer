package newsapi;

public class NewsApiException extends Exception{

    public NewsApiException(){
        super("Ein Fehler in der NewsApi ist aufgetreten. Die aktuellsten Nachrichten zu Covid (Österreich) werden angezeigt.");
    }

    public NewsApiException(String message){
        super(message);
    }
}
