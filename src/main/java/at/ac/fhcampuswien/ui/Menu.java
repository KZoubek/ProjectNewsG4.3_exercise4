package at.ac.fhcampuswien.ui;

import at.ac.fhcampuswien.controllers.AppController;
import at.ac.fhcampuswien.controllers.NewsAPIException;
import at.ac.fhcampuswien.downloader.ParallelDownloader;
import at.ac.fhcampuswien.downloader.SequentialDownloader;
import at.ac.fhcampuswien.models.Article;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final String INVALID_INPUT_MESSAGE = "No valid input. Try again";
    private static final String EXIT_MESSAGE = "Bye bye!";

    //changed to static because singelton pattern must be static
    private static AppController controller;

    public void start(){
        String input;
        // from App Controller to get Singleton Pattern
        //4. Replaced direct calls with calls to statice creation method in our program
        controller = AppController.getInstance();

        do{
            printMenu();
            input = readLine();
            handleInput(input);
        } while(!input.equals("q"));
    }

    private void handleInput(String input){
        switch (input) {
            case "a" -> getTopHeadlinesAustria(controller);
            case "b" -> getAllNewsBitcoin(controller);
            case "y" -> getArticleCount(controller);
            case "q" -> printExitMessage();
            case "c" -> getProviderWithMostArticles();
            case "d" -> getLongestNameOfAuthors();
            case "e" -> getCountArticlesNYTimes();
            case "f" -> getArticlesShorterThan();
            case "g" -> sortArticlesByContentLength();
            case "h" -> downloadURLsMenu();
            default -> printInvalidInputMessage();
        }
    }

    // Method is needed for exercise 4 - ignore for exercise 3 solution
    private void downloadURLsMenu(){
        try {
            // TODO print time in ms it took to download URLs sequentially
            long start1 = System.currentTimeMillis();
            int resultSequential = controller.downloadURLs(new SequentialDownloader());
            long end1 = System.currentTimeMillis();

            // TODO print time in ms it took to download URLs parallel
            System.out.println(resultSequential+" files downloaded in "+ ( end1 - start1 ) + " ms.");

            // TODO implement the process() function in ParallelDownloader class
            long start = System.currentTimeMillis();
            int resultParallel = controller.downloadURLs(new ParallelDownloader());
            long end = System.currentTimeMillis();

            // TODO print time in ms it took to download URLs parallel
            System.out.println(resultParallel+" files downloaded with Parallel downloader in "+ (end - start) + " ms.");


        } catch (NewsAPIException e){
            System.out.println(e.getMessage());
        }
    }

    private void getProviderWithMostArticles() {
        try {
            System.out.println(controller.getProviderWithMostArticles());
        } catch (NewsAPIException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getLongestNameOfAuthors() {
        try {
            System.out.println(controller.getLongestNameOfAuthors());
        } catch (NewsAPIException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getCountArticlesNYTimes() {
        try {
            System.out.println(controller.getCountArticlesNYTimes());
        } catch (NewsAPIException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getArticlesShorterThan() {
        try {
            List<Article> articles = controller.getArticlesShorterThan(15);
            if(articles.size() > 0){
                articles.forEach(System.out::println);
            } else {
                System.out.println("No articles.");
            }
        } catch (NewsAPIException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sortArticlesByContentLength() {
        try {
            List<Article> sortedArticles = controller.sortArticlesByContentLength();
            sortedArticles.forEach(System.out::println);
        } catch (NewsAPIException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getArticleCount(AppController controller) {
        System.out.println("Number of articles: " + controller.getArticleCount());
    }

    private void getTopHeadlinesAustria(AppController controller) {
        List<Article> articleList = controller.getTopHeadlinesAustria();

        for( Article a : articleList) {
            System.out.println(a);
        }
    }

    private void getAllNewsBitcoin(AppController controller) {
        System.out.println(controller.getAllNewsBitcoin());
    }

    /*private void generateMockList(AppController controller) {
        List<Article> articleList = controller.generateMockList();

        for( Article a : articleList) {
            System.out.println(a);
    }*/

    public static void printExitMessage(){
        System.out.println(EXIT_MESSAGE);
    }

    public static void printInvalidInputMessage(){
        System.out.println(INVALID_INPUT_MESSAGE);
    }

    private static void printMenu(){
        String text = """
                *****************************
                *   Welcome to NewsApp   *
                *****************************
                Enter what you wanna do:
                a: Get top headlines austria
                b: Get all news about bitcoin
                y: Count articles
                q: Quit program
                c: Get provider with most articles
                d: Get longest author name
                e: Count articles from NY Times
                f: Get articles with short title
                g: Sort articles by content length
                h: Download URLs
                """;

        System.out.println(text);
    }

    private static String readLine() {
        String value;
        Scanner scanner = new Scanner(System.in);
        value = scanner.nextLine();
        return value.trim();
    }

}
