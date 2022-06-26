package at.ac.fhcampuswien.controllers;

import at.ac.fhcampuswien.App;
import at.ac.fhcampuswien.api.NewsApi;
import at.ac.fhcampuswien.downloader.Downloader;
import at.ac.fhcampuswien.enums.Country;
import at.ac.fhcampuswien.enums.Endpoint;
import at.ac.fhcampuswien.models.Article;
import at.ac.fhcampuswien.models.NewsResponse;
import at.ac.fhcampuswien.models.Source;

import java.util.*;
import java.util.stream.Collectors;

public class AppController {
    private List<Article> articles;
    //1. Add a private static field for storing Singleton instance
    //Applies here as the app controller will only create one object
    private static AppController instance;

    //2. Make constructor of class private
    private AppController() {}

    // makes sense to make a singleton pattern here as only one object will be created
    //3. Declare a public static creation method for getting the instance
    public static AppController getInstance() {
        if (instance == null){
            instance = new AppController();
        }
        return instance;
    }

    public void setArticles(List<Article> articles){
        this.articles = articles;
    }


    public List<Article> getArticles(){
        return articles;
    }

    // Method is needed for exercise 4 - ignore for exercise 3 solution
    // returns number of downloaded article urls
    public int downloadURLs(Downloader downloader) throws NewsAPIException{
        if( articles == null)
            throw new NewsAPIException();
        // TODO extract urls from articles with java stream
        List<String> urls = articles.stream()
                .map(Article::getUrl)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return downloader.process(urls);
    }

    /**
     * gets the size of last fetched articles
     * @return size of article list
     */
    public int getArticleCount(){
        if(articles != null) {
            return articles.size();
        }
        return 0;
    }

    /**
     * get the top headlines from austria via newsapi
     * @return article list
     */
    public List<Article> getTopHeadlinesAustria() {
        NewsApi api = new NewsApi.Builder()
                .endpoint(Endpoint.TOP_HEADLINES)
                .q("")
                .sourceCountry(Country.at)
                .build();

        articles = new ArrayList<>();
        try {
            NewsResponse response = api.requestData();
            articles = response.getArticles();
        } catch (NewsAPIException e){
            System.out.println("An error occurred while fetching articles: " + e.getMessage());
        }

        return articles;
    }

    /**
     * returns all articles that do contain "bitcoin"
     * in their title from newsapi
     * @return filtered list
     */
    public List<Article> getAllNewsBitcoin() {
        NewsApi api = new NewsApi.Builder()
                .endpoint(Endpoint.EVERYTHING)
                .q("bitcoin")
                .build();

        articles = new ArrayList<>();
        try {
            NewsResponse response = api.requestData();
            articles = response.getArticles();
        } catch (NewsAPIException e){
            System.out.println("An error occurred while fetching articles: " + e.getMessage());
        }

        return articles;
    }

    public String getProviderWithMostArticles() throws NewsAPIException {
        if(articles == null)
            throw new NewsAPIException("Load data first");

        return articles.stream()
                .map(Article::getSource)
                .collect(Collectors.groupingBy(Source::getName))
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(o -> o.getValue().size()))
                .map(stringListEntry -> stringListEntry.getKey() + " " + stringListEntry.getValue().size())
                .orElseThrow(() -> new NewsAPIException("problem with analysing data in getProviderWithMostArticles"));
    }

    public String getLongestNameOfAuthors() throws NewsAPIException {
        if(articles == null)
            throw new NewsAPIException("Load data first");

        return articles.stream()
                //added stream to filter urls
                .map(Article::getAuthor)
                .filter(Objects::nonNull)
                .max(Comparator.comparing(String::length))
                .orElseThrow(() -> new NewsAPIException("problem with analysing data in getLongestNameOfAuthors"));
    }

    public long getCountArticlesNYTimes() throws NewsAPIException{
        if(articles == null)
            throw new NewsAPIException("Load data first");

       return articles.stream()
               .filter(e -> e.getSource().getName().equals("New York Times"))
               .count();
    }

    public List<Article> getArticlesShorterThan(int length) throws NewsAPIException{
        if(articles == null)
            throw new NewsAPIException("Load data first");

        return articles.stream()
                .filter(article -> article.getTitle().length() <= length)
                .collect(Collectors.toList());
    }

    public List<Article> sortArticlesByContentLength() throws NewsAPIException{
        if(articles == null)
            throw new NewsAPIException("Load data first");

        return articles.stream()
                .sorted(Comparator.comparing(Article::getDescription))
                .sorted(Comparator.comparingInt(a -> a.getDescription().length()))
                .collect(Collectors.toList());

        /* long form
        return articles.stream()
                .sorted( (a1, a2) -> {
                    if(a1.getDescription().length() == a2.getDescription().length()){
                        return a1.getDescription().compareTo(a2.getDescription());
                    } else if (a1.getDescription().length() > a2.getDescription().length()) {
                        return 1;
                    } else {
                        return -1;
                    }
                })
                .collect(Collectors.toList());
        */
    }

    /**
     * filters a given article list based on a query
     * @param query to filter by
     * @param articles  list to filter
     * @return filtered list
     */
    private static List<Article> filterList(String query, List<Article> articles){
        List<Article> filtered = new ArrayList<>();
        for(Article i : articles){
            if(i.getTitle().toLowerCase().contains(query)){
                filtered.add(i);
            }
        }
        return filtered;
    }
    // how to use Builder Pattern from Article Class
    private static List<Article> generateMockList() {
        Article one = new Article.Builder()
                .author("Margarete Schramboeck")
                .title("SWIFT geht in Austria auch mit Erlagschein")
                .build();

        Article two = new Article.Builder()
                .author("Simon Neuch")
                .title("Angermanagement and Mandalas")
                .build();

        Article three = new Article.Builder()
                .author("Bloomberg")
                .title("How to: Bitcoin in echtes Geld umwandeln")
                .build();

        List<Article> mock = new ArrayList<>();
        mock.add(one);
        mock.add(two);
        mock.add(three);
        return mock;
    }
}
