package tfip.ibf.BookApp.Controller;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tfip.ibf.BookApp.BookAppApplication;
import tfip.ibf.BookApp.Model.Book;
import tfip.ibf.BookApp.Service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/book")
public class SearchController {

    @Autowired
    private BookService bookSvc; 
    
    private final Logger logger = Logger.getLogger(SearchController.class.getName());

    @GetMapping
    public String searchTitle(@RequestParam String title, Model model){
        //@PathVariable String worksID, 
        logger.log(Level.INFO, "Title to search >>> %s".formatted(title));
        
        
        List<Book> bookList = Collections.emptyList();
        
        bookList = bookSvc.search(title); 
        

        logger.log(Level.INFO, "bookList contains >> \n\n %s".formatted(bookList));
       
        
        model.addAttribute("searchedtitle", title);
        model.addAttribute("bookList", bookList);

        return "result"; 
    }

    
}
