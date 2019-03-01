package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Arrays;


@RestController
@RequestMapping("Hello")
public class HelloController {
    
    @GetMapping(value="/", produces="application/json")
    public List<Book> index() {
    	List<Book> bookList = Arrays.stream(Book.values()).collect(Collectors.toList());
    	return bookList;
    }
    
    @GetMapping(value="/{id}", produces="application/json")
    public Book getBook(@PathVariable int id) {
        return findBookById(id);
    }
    
    private Book findBookById(int id) {
    	List<Book> bookList = Arrays.stream(Book.values()).filter(b -> b.val==id).collect(Collectors.toList());
    	return bookList.get(0);
    }
     
    public static enum Book {
    	ONE(1),
    	TWO(2),
    	THREE(3);
    	
    	int val;
    	Book(int val) {
    		this.val = val;
    	}
    }
}
