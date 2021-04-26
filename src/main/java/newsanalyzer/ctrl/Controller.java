package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;
import newsapi.enums.SortBy;
import newsapi.NewsApiException;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {

	public static final String APIKEY= "5aa68f2b27a44569822d37332719d59d";

	public void process(String thema, Category segment) {
		try { // DIE EXCEPTION IN EINE NEUE PACKEN

			System.out.println("Start process");

			//TODO load the news based on the parameters --- NewsApi -> NewsResponse -> Article
			NewsApi nachrichten = new NewsApiBuilder()
					.setApiKey(APIKEY)
					.setQ(thema)
					.setEndPoint(Endpoint.TOP_HEADLINES)
					.setSourceCountry(Country.at)
					.setSourceCategory(segment)
					.createNewsApi();

			NewsReponse newsReponse = nachrichten.getNews();

			if(newsReponse.getTotalResults()==0) //Wenn keine Nachrichten gefunden wird, dann kommt eine Fehlermeldung
				throw new NewsApiException();

			if (newsReponse != null) {
				List<Article> articles = newsReponse.getArticles();
				Article nachricht = articles.get(0);
				System.out.println("\n***NACHRICHT***");
				System.out.println("\n***" + nachricht.getTitle() + "*** --- " + nachricht.getPublishedAt() + "\n\n" + nachricht.getContent() + "\n\nUm die vollständige Nachricht zu lesen, gehen Sie auf: " + nachricht.getUrl()+"\n");
			}

			//TODO implement methods for analysis
			System.out.println("---Analysis---");
			System.out.println("<>Anzahl der Artikel\n"+countArticle(newsReponse.getArticles())+"\n");
			System.out.println("<>Titel nach Länge sortieren");
			List<Article> sortierteListe =getTitlesSortedByLength(newsReponse.getArticles());
			sortierteListe.forEach(article -> System.out.println(" - "+article.getTitle()));
			System.out.println("\n<>Der Autor mit dem kürzesten Namen\n"+shortestName(newsReponse.getArticles()));
			System.out.println("\n<>Provider mit den meisten Nachrichten\n"+bestProvider(newsReponse.getArticles()));


		}catch(NewsApiException e){
				System.out.println("--------Ein Fehler ist aufgetreten.--------");
				System.out.println("Die aktuellsten Nachrichten zu Covid (Österreich) werden angezeigt.");
				NewsApi newsApi = new NewsApiBuilder()
						.setApiKey("5aa68f2b27a44569822d37332719d59d")
						.setQ("corona")
						.setEndPoint(Endpoint.TOP_HEADLINES)
						.setSourceCountry(Country.at)
						.setSourceCategory(Category.health)
						.createNewsApi();
				NewsReponse responseInException = newsApi.getNews();
			if (responseInException != null) {//Ausgabe
				List<Article> articles = responseInException.getArticles();
				Article nachricht = articles.get(0);
				System.out.println("\n***NACHRICHT***");
				System.out.println("\n***" + nachricht.getTitle() + "*** --- " + nachricht.getPublishedAt() + "\n\n" + nachricht.getContent() + "\n\nUm die vollständige Nachricht zu lesen, gehen Sie auf: " + nachricht.getUrl());
			}
		}catch (NullPointerException n){
			System.out.println("\n"+n.getMessage());

		}
	}



	public  List<Article> getTitlesSortedByLength(List<Article> data){
		return data
				.stream()
				.sorted(Comparator.comparingInt(Article -> Article.getTitle().length()))
				.collect(Collectors.toList());
	}

	public String shortestName(List<Article> data) {
		return data
				.stream()
				.sorted(Comparator.comparingInt(Article -> Article.getAuthor().length()))
				.findFirst()
				.get().getAuthor();
	}

	public int countArticle(List<Article> data){
		return (int)data.stream().count();
	}

	public String bestProvider(List<Article> data){
		Map<String, Long> providerMap = data.stream()
				.collect(Collectors.groupingBy((Article a) -> a.getSource().getName(), Collectors.counting()));

		return providerMap.keySet().stream().findFirst().get();
	}


	public Object getData() {
		return null;
	}
}
