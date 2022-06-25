package at.ac.fhcampuswien.api;

import at.ac.fhcampuswien.enums.*;

public class BuildURL {
    private Endpoint endpoint;
    private String q;
    private String qInTitle;
    private Country sourceCountry;
    private Category sourceCategory;
    private String domains;
    private String excludeDomains;
    private String from;
    private String to;
    private Language language;
    private SortBy sortBy;
    private String pageSize;
    private String page;

    public BuildURL(Builder builder){
        this.endpoint = builder.endpoint;
        this.q = builder.q;
        this.qInTitle = builder.qInTitle;
        this.sourceCountry = builder.sourceCountry;
        this.sourceCategory = builder.sourceCategory;
        this.domains = builder.domains;
        this.excludeDomains = builder.excludeDomains;
        this.from = builder.from;
        this.to = builder.to;
        this.language = builder.language;
        this.sortBy = builder.sortBy;
        this.pageSize = builder.pageSize;
        this.page = builder.page;
    }

    public static class Builder{
        private Endpoint endpoint;
        private String q;
        private String qInTitle;
        private Country sourceCountry;
        private Category sourceCategory;
        private String domains;
        private String excludeDomains;
        private String from;
        private String to;
        private Language language;
        private SortBy sortBy;
        private String pageSize;
        private String page;
        private String scheme;
        private String host;
        private String version;

        public Builder(Endpoint endpoint, String q, String qInTitle, Country sourceCountry, Category sourceCategory, String domains, String excludeDomains, String from, String to,
                       Language language, SortBy sortBy, String pageSize,String page, String scheme, String host, String version ){
            this.endpoint = endpoint;
            this.q = q;
            this.qInTitle = qInTitle;
            this.sourceCountry = sourceCountry;
            this.sourceCategory = sourceCategory;
            this.domains = domains;
            this.excludeDomains = excludeDomains;
            this.from = from;
            this.to = to;
            this.language = language;
            this.sortBy = sortBy;
            this.pageSize = pageSize;
            this.page = page;
            this.scheme = scheme;
            this.host = host;
            this.version = version;
        }

        @Override
        public String toString(){
            StringBuilder buildUrl = new StringBuilder().append(scheme).append(host).append(version).append(endpoint);

            if(language != null){
                buildUrl.append(language).append("language=");
            }
            if(sourceCountry != null){
                buildUrl.append(sourceCountry).append("sourceCountry=");
            }
            if(sourceCategory != null){
                buildUrl.append(sourceCategory).append("sourceCategory=");
            }
            if(sortBy != null){
                buildUrl.append(sortBy).append("sortBy=");
            }
            if(q != null){
                buildUrl.append(q).append("q=");
            }

            return buildUrl.toString();
        }

        public Builder endpoint(Endpoint endpoint){
            this.endpoint = endpoint;
            return this;
        }

        public Builder q(String q){
            this.q = q;
            return this;
        }

        public Builder qInTitle(String qInTitle){
            this.qInTitle = qInTitle;
            return this;
        }

        public Builder sourceCountry(Country sourceCountry){
            this.sourceCountry=sourceCountry;
            return this;
        }

        public Builder sourceCategory(Category sourceCategory){
            this.sourceCategory = sourceCategory;
            return this;
        }

        public Builder domains(String domains){
            this.domains = domains;
            return this;
        }

        public Builder excludeDomains(String excludeDomains){
            this.excludeDomains = excludeDomains;
            return this;
        }

        public Builder from(String from){
            this.from = from;
            return this;
        }

        public Builder to(String to){
            this.to = to;
            return this;
        }

        public Builder language(Language language){
            this.language = language;
            return this;
        }

        public Builder sortBy(SortBy sortBy){
            this.sortBy = sortBy;
            return this;
        }

        public Builder pageSize(String pageSize){
            this.pageSize = pageSize;
            return this;
        }

        public Builder page(String page){
            this.page = page;
            return this;
        }

        public Builder scheme(String scheme){
            this.scheme = scheme;
            return this;
        }

        public Builder host(String host){
            this.host = host;
            return this;
        }

        public Builder version(String version){
            this.version = version;
            return this;
        }

        public BuildURL build(){
            BuildURL buildURL = new BuildURL(this);
            return  buildURL;
        }
    }
}
