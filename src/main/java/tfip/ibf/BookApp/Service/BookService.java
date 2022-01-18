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
}