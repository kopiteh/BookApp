package tfip.ibf.BookApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import tfip.ibf.BookApp.Model.Book;
import tfip.ibf.BookApp.Service.BookService;

import java.util.logging.Level;
import java.util.logging.Logger;
@Controller
@RequestMapping(path="/book")
public class BookController {
    
    private final Logger logger = Logger.getLogger(BookController.class.getName());

    @Autowired
    private BookService bookSvc; 

    @GetMapping(path="{works_id}")
    public String bookDetails(@PathVariable String works_id, Model model){

        logger.log(Level.INFO, "works_id to query => %s".formatted(works_id));

        Book book_view = bookSvc.showBookDetail(works_id);  

        model.addAttribute("book_view", book_view);
        
        return "bookview";
    }
}
