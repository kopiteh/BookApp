package tfip.ibf.BookApp.Service;

import static tfip.ibf.BookApp.Constants.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import tfip.ibf.BookApp.BookAppApplication;
import tfip.ibf.BookApp.Model.Book;

@Service
public class BookService {
    private final Logger logger = Logger.getLogger(BookService.class.getName());


    public List<Book> search(String searchTerm){ 
        
        final String url = UriComponentsBuilder
            .fromUriString(API_URL)
            .queryParam("title", searchTerm.replace(" ", "+"))
            .queryParam("limit",20)
            .toUriString();

            final RequestEntity<Void> req = RequestEntity.get(url).build();
            final RestTemplate template = new RestTemplate();
            final ResponseEntity<String> resp = template.exchange(req, String.class);
    
            if (resp.getStatusCode() != HttpStatus.OK)
                throw new IllegalArgumentException(
                    "Error: status code %s".formatted(resp.getStatusCode().toString())
                );
            final String body = resp.getBody();
    
            //logger.log(Level.INFO, "payload: %s".formatted(body));

            try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
                final JsonReader reader = Json.createReader(is);
                final JsonObject result = reader.readObject();
                final JsonArray readings = result.getJsonArray("docs");
                return readings.stream()       
                        .map(v -> (JsonObject)v)    
                        .map(Book::create)       
                        .collect(Collectors.toList());


            }   catch (Exception ex) { 
                ex.printStackTrace();
            }

        return Collections.emptyList(); 
        
    }   

    public Book showBookDetail(String works_id){ 
        
        final String url2 = BOOK_DETAIL_URL+works_id+".json";
        logger.log(Level.INFO, "URL2 >>> %s".formatted(url2));

        final RequestEntity<Void> req2 = RequestEntity.get(url2).build();
            final RestTemplate template2 = new RestTemplate();
            final ResponseEntity<String> resp2 = template2.exchange(req2, String.class);
    
            if (resp2.getStatusCode() != HttpStatus.OK)
                throw new IllegalArgumentException(
                    "Error: status code %s".formatted(resp2.getStatusCode().toString())
                );
            final String body2 = resp2.getBody();
    
            logger.log(Level.INFO, "payload: %s".formatted(body2));

            try (InputStream is = new ByteArrayInputStream(body2.getBytes())) {
                final JsonReader reader = Json.createReader(is);
                final JsonObject result = reader.readObject();
                
                final Book book_view = new Book();
                book_view.setTitle(null);
                book_view.setDescription(null);

                
                book_view.setTitle(result.getString("title"));
                logger.log(Level.INFO, "Title >>>  %s".formatted(book_view.getTitle()));
                book_view.setDescription(result.getString("description"));
                logger.log(Level.INFO, "Description >>>  %s".formatted(book_view.getDescription()));
                
                return book_view; 


            }   catch (Exception ex) { 
                ex.printStackTrace();
            }


        return null;
        
    }   
}