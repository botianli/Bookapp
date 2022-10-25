package com.stackroute.searchservice.service;

import com.stackroute.searchservice.controller.SearchController;
import com.stackroute.searchservice.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@CacheConfig(cacheNames = "search")
@Service
public class SearchServiceImpl implements SearchService{

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    private static String baseUrl = "http://openlibrary.org/search.json";

    @Cacheable(value = "searchCache", key="#searchInput")
    @Override
    public List<Book> searchBooks(String searchOption, String searchInput) throws RuntimeException {
        //required variables
        String url = "";
        String content="";
        List<String> titles = new ArrayList<>();
        List<String> isbns = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        List<String> subjects = new ArrayList<>();

        List<Book> books = new ArrayList<>();
        String search="";

        if(searchInput.split(" ").length>1){
            String words[]=searchInput.split(" ");
            for(String word:words){
                search=search+word+"+";
            }
        }
        else{
            search=searchInput+"+";
        }
        if(searchOption.equalsIgnoreCase("title")){
            logger.info("Searching books by Title");
            url= url+baseUrl+"?title="+search.substring(0,search.length()-1)+"&limit=20";
        }
        else if(searchOption.equalsIgnoreCase("author")){
            logger.info("Searching books by Author");
            url=url+baseUrl+"?author="+search.substring(0,search.length()-1)+"&limit=20";
        }
        else{
            logger.error("Invalid URL");
            throw new RuntimeException();
        }
        try{
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            logger.info("Called Open Library url is {}",url);
            int responseCode = connection.getResponseCode();
            if(responseCode!=200){
                logger.error("Invalid Response code {}",responseCode);
                throw new RuntimeException();
            }
            else{
                Scanner sc = new Scanner(requestUrl.openStream());
                while(sc.hasNext()){
                    content=content+sc.next()+" ";
                }
                sc.close();
            }
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(content);
            JSONArray docsArray = (JSONArray) jsonObject.get("docs");
            for(int i=0;i<docsArray.size();i++){
                JSONObject indexedJsonObject = (JSONObject) docsArray.get(i);

                titles.add((String) indexedJsonObject.get("title"));
                isbns.add((String)((JSONArray) indexedJsonObject.get("isbn")).get(0));
                authors.add((String)((JSONArray) indexedJsonObject.get("author_name")).get(0));
                subjects.add((String)((JSONArray) indexedJsonObject.get("subject")).get(0));
                Book book = new Book(titles.get(i),authors.get(i),subjects.get(i),isbns.get(i));
                books.add(book);
            }
        }catch(Exception e){
            // e.printStackTrace();
        }
        return books;
    }

    @Cacheable(value = "searchCache", key="#authorName")
    @Override
    public List<Book> getRecommendations(String authorName) {
        List<Book> recommendedBooks = searchBooks("author", authorName);
        return recommendedBooks;
    }

}
