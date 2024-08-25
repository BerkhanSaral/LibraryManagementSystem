package com.tpe.controller;

import com.tpe.DTO.BookDTO;
import com.tpe.domain.Book;
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

//@Controller model, view
@RestController //body
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    //created
    //1- save a book & return : Message
    //http://localhost:8080/books + post + json format body
    @PostMapping
    public ResponseEntity<String> saveBook(@Valid @RequestBody Book book) {
        bookService.saveBook(book);
        return new ResponseEntity<>("Kitap basariyla kaydedildi", HttpStatus.CREATED);//201,202
    }

    //READ
    //2-Get All Books return: List<Book>
    //http://localhost:8080/books + get
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> bookList = bookService.getAll();
        return ResponseEntity.ok(bookList);
    }

    //3- Get a book by id,return : Book
    //http://localhost:8080/books/2
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book foundBook = bookService.getBookById(id);
        ;
        return new ResponseEntity<>(foundBook, HttpStatus.OK);
    }

    //4- delete a book by id, return:Message
    //http://localhost:8080/books/2
    @DeleteMapping("/{no}")
    public ResponseEntity<String> deleteBook(@PathVariable("no") Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok("Book is successfully deleted");
    }

    //5-Get a book by id with RequestParam, return:book
    //http://localhost:8080/books/q?id=2
    @GetMapping("/q")
    public ResponseEntity<Book> getBookByIdWithQuery(@RequestParam() Long id) {
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

    //7- Update a Book with using DTO
    //http://localhost:8080/books/update/2
    @GetMapping("/update/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") Long id, @Valid @RequestBody BookDTO bookDTO) {
        bookService.updateBookId(id, bookDTO);
        return ResponseEntity.ok("Kitap is successfully updated. id : " + id);
    }

    //8-Get book with page
    //http://localhost:8080/books/part?page=1?size=2?sort=publicationDate?direction=ASC    @GetMapping("/part")
    @GetMapping("/part")
    public ResponseEntity<Page<Book>> getAllBooksWithPage(@RequestParam("page") int page,
                                                          @RequestParam("size") int size,
                                                          @RequestParam("sort") String sort,
                                                          @RequestParam("direction") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<Book> bookWithPage = bookService.getAllBookWithPage(pageable);
        return ResponseEntity.ok(bookWithPage);
    }

    //9-Get a book by its author using Jpql

    @GetMapping("/jpql")
    public ResponseEntity<List<Book>> getBookByAuthor(@RequestParam("author") String author) {
        List<Book> bookList = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(bookList);
    }
}
