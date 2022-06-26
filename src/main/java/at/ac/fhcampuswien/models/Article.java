package at.ac.fhcampuswien.models;

public class Article {
    private String author;
    private String title;
    private Source source;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    //new Builder constructor
    private Article(Builder builder){
        this.author = builder.author;
        this.title = builder.title;
        this.source = builder.source;
        this.description = builder.description;
        this.url = builder.url;
        this.urlToImage = builder.urlToImage;
        this.publishedAt = builder.publishedAt;
        this.content = builder.content;
    }

    //OLD CODE
    //All getter, and NO setter to provide immutability
    // (the state of not changing, or being unable to be changed)
    public Source getSource() {
        return source;
    }
    public String getDescription() {
        return description;
    }
    public String getUrl() {
        return url;
    }
    public String getUrlToImage() {
        return urlToImage;
    }
    public String getPublishedAt() {
        return publishedAt;
    }
    public String getContent() {
        return content;
    }
    public String getAuthor() {
        return author;
    }
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return
                "Title: " + this.title + "\n" +
                        "Author: " + this.author + "\n"
                        + "Source: " + this.source.getName() + "\n"
                        + "Desc (length): " + this.description.length()
                        + "\n Desc: " + this.description + "\n"
                        + "Url: " + this.url + "\n"
                        + "Published at: " + this.publishedAt + "\n"
                        + "\n";
    }

    //nested builder class inside Article
    public static class Builder {
        private String author;
        private String title;
        private Source source;
        private String description;
        private String url;
        private String urlToImage;
        private String publishedAt;
        private String content;

        //Constructor
        public Builder(){
        }

        //Constructor for the optional variables
        // each functions needs to return the builder itself= return this;

        public Builder author (String author){
            this.author = author;
            return this;
        }

        public Builder title (String title){
            this.title = title;
            return this;
        }

        public Builder source (Source source) {
            this.source = source;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder urlToImage(String urlToImage){
            this.urlToImage = urlToImage;
            return this;
        }

        public Builder publishedAt(String publishedAt){
            this.publishedAt = publishedAt;
            return this;
        }

        public Builder content(String content){
            this.content = content;
            return this;
        }

        // build() returns a new Article
        //Return the finally constructed Article object
        //method used in Appcontroller with Mocklist to show usability
        public Article build(){
            return new Article(this);
        }
    }
}




