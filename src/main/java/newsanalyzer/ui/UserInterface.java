package newsanalyzer.ui;

//key: 5aa68f2b27a44569822d37332719d59d

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import newsanalyzer.ctrl.Controller;
import newsapi.Downloader;
import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.SequentialDownloader;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;

public class UserInterface 
{

	private Controller ctrl = new Controller();

	SequentialDownloader myDownloader = new SequentialDownloader();

	public void getDataFromCtrl1(){

		System.out.println("Aktuellste Nachricht zu Covid (Österreich)");

		ctrl.process("Covid", Category.health); //die Nachrichten werden an den Controller geschickt für die Analyse etc.
	}

	public void getDataFromCtrl2(){

		System.out.println("Aktuellste Nachricht zu Fußball (Österreich)");

		ctrl.process("Fußball", Category.sports); //die Nachrichten werden an den Controller geschickt für die Analyse etc.

		}

	public void getDataFromCtrl3(){

		System.out.println("Aktuellste Nachricht zu Bitcoin (Österreich)");

		ctrl.process("Bitcoin", Category.technology); //die Nachrichten werden an den Controller geschickt für die Analyse etc.

	}
	
	public void getDataForCustomInput() {

		//Input vom User
			String keyword;
			Category thema = null; //wird später zu Category


		boolean wrongInput = false;
		Scanner myScanner = new Scanner(System.in);  // Create a Scanner object

		System.out.println("Aktuellsten Nachricht zum Thema deiner Wahl (Österreich)");

		/** Themen input (business, entertainment, health, science, sports, technology) **/
		System.out.println("\n>Wähle das Thema ([1]business, [2]entertainment, [3]health, [4]science, [5]sports, [6]technology) deiner Wahl ein: ");

		do {
			wrongInput = false;
			switch (myScanner.nextLine()) {
				case "1":
					thema = Category.business;
					break;
				case "2":
					thema = Category.entertainment;
					break;
				case "3":
					thema = Category.health;
					break;
				case "4":
					thema = Category.science;
					break;
				case "5":
					thema = Category.sports;
					break;
				case "6":
					thema = Category.technology;
					break;
				default:
					System.out.println("Ungültige Eingabe! Bitte erneut eingeben.");
					wrongInput = true;
					break;
			}//switch
		}while(wrongInput);

		/** Keyword input **/

		System.out.println("\n>Gib ein Keyword, passend zum Thema, deiner Wahl ein: ");

		keyword = myScanner.nextLine();  // Read user input

		ctrl.process(keyword, thema); //die Nachrichten werden an den Controller geschickt für die Analyse etc.
	}

	public void downloadLastSearch(){
		System.out.println("Heruntergeladen werden: ");
		Controller.newsURLs.forEach(System.out::println);
		myDownloader.process(Controller.newsURLs);

	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interfacx");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Aktuellste Nachricht zu Covid (Österreich)", this::getDataFromCtrl1);
		menu.insert("b", "Aktuellste Nachricht zu Fußball (Österreich)", this::getDataFromCtrl2);
		menu.insert("c", "Aktuellste Nachricht zu Bitcoin (Österreich)", this::getDataFromCtrl3);
		menu.insert("d", "Aktuelleste Nachricht zum Thema deiner Wahl",this::getDataForCustomInput);
		menu.insert("f", "Die letzte Suche herunterladen",this::downloadLastSearch);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		System.out.println("Program finished");
	}


    protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
        } catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 	{
		Double number = null;
        while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
            if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
                number = null;
            } else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
                number = null;
			}
		}
		return number;
	}
}
