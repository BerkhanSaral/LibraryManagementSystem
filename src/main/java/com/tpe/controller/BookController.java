package com.tpe.controller;

import com.tpe.domain.Book;
import com.tpe.dto.BookDTO;
import com.tpe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController//body
//@Controller //model,view
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    //Created
    //1- save a book & return : Message
    //http://localhost:8080/books + post + json format body
    @PostMapping
    public ResponseEntity<String> saveBook(@Valid @RequestBody Book book) {
        bookService.saveBook(book);
        //return ResponseEntity.created()
        return new ResponseEntity<>("Kitap başarıyla kaydedildi.", HttpStatus.CREATED);//201 202
    }

    //READ
    //2- Get All Books,return : List<Book>
    //http://localhost:8080/books + get
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> bookList = bookService.getAll();
        return ResponseEntity.ok(bookList);//200-201
    }

    //3-Get a book by Id,return : Book
    //http://localhost:8080/books/2
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book foundBook = bookService.getBookById(id);
        return new ResponseEntity<>(foundBook, HttpStatus.OK);
    }

    //4- delete a book by ID,return:Message
    //http://localhost:8080/books/2
    @DeleteMapping("/{no}")
    public ResponseEntity<String> deleteBook(@PathVariable("no") Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok("Kitap başarıyla silindi.");
    }

    //5- Get a Book by ID with RequestParam , Return:Book
    //http://localhost:8080/books/q?id=2
    @GetMapping("/q")
    public ResponseEntity<Book> getBookByIdWithQuery(@RequestParam("id") Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    //6- Get a Book by Title with RequestParam , Return:Book
    //http://localhost:8080/books/search?title=sefiller + get
    @GetMapping("/search")
    public ResponseEntity<List<Book>> getBookByTitleWithQuery(@RequestParam("title") String title) {
        List<Book> books = bookService.getBookByTitle(title);
        return ResponseEntity.ok(books);
    }

    //7-Update a Book With Using DTO , return:String
    //http://localhost:8080/books/update/2
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") Long id, @Valid @RequestBody BookDTO bookDTO) {
        bookService.updateBookById(id, bookDTO);
        return ResponseEntity.ok("Kitap başarıyla güncellendi . ID : " + id);
    }
    //8- Get Book with page
    //http://localhost:8080/books/part?page=1?size=2?sort=publicationDate?direction=ASC + Get

    @GetMapping("/part")
    public ResponseEntity<Page<Book>> getAllBooksWithPage(@RequestParam("page") int page,
                                                          @RequestParam("size") int size,
                                                          @RequestParam("sort") String sort,
                                                          @RequestParam("direction") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<Book> bookWithPage = bookService.getAllBookWithPage(pageable);
        return ResponseEntity.ok(bookWithPage);
    }

    //9- Get a Book By Its' author using Jpql

    //http://localhost:8080/books/jpql?author=alican
    @GetMapping("/jpql")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author") String author) {
        List<Book> bookList = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(bookList);
    }


    //10- Add a book To an owner
    //http://localhost:8080/books/add?book=3&owner=1
    //return : mesaj
    @PatchMapping("/add")
    public ResponseEntity<String> addBookToOwner(@RequestParam("book") Long bookId,
                                                 @RequestParam("owner") Long ownerId) {

        bookService.addBookToOwner(bookId, ownerId);
        return ResponseEntity.ok("Kitap Üyeye Eklendi!!!");
    }

}

