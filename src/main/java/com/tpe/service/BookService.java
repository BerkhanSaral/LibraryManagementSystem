package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.domain.Owner;
import com.tpe.dto.BookDTO;
import com.tpe.exceptions.BookNotFoundException;
import com.tpe.exceptions.ConflictException;
import com.tpe.exceptions.isBookAlreadyExist;
import com.tpe.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OwnerService ownerService;

    //1
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    //2
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    //3
    public Book getBookById(Long id) {
        Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException("kitap bulunamadı, ID : "+id));
        return book;
    }

    public void deleteBookById(Long id) {
        // Book book=getBookById(id);
        // bookRepository.delete(book);
        bookRepository.deleteById(id);
    }

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public void updateBookById(Long id, BookDTO bookDTO) {
        Book existingBook=getBookById(id);
        //güncelleme
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setPublicationDate(bookDTO.getPublicationDate());
        bookRepository.save(existingBook);
    }

    public Page<Book> getAllBookWithPage(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public List<Book>getBooksByAuthor(String author){
        List<Book>bookList=bookRepository.findByAuthorWithJPQL(author);
        if (bookList.isEmpty()){
            throw new BookNotFoundException("Yazar ait kitap bulunamadi!");
        }


        return bookList;
    }

    public void addBookToOwner(Long bookId, Long ownerId) {
        Book foundBook= getBookById(bookId);
        Owner foundOwner=ownerService.getOwnerById(ownerId);

        //belirtilen kitap daha once bu owner'a verilmis mi?
        boolean isBookExists=foundOwner.getBooks().contains(foundBook);
        if (isBookExists){
            throw new isBookAlreadyExist("Bu kitaba zaten sahipsin!!!!");
        }else if(foundBook.getOwner()!=(null)){
            throw new ConflictException("Bu kitap başka birine ait!!!");
        }
        else {
            foundBook.setOwner(foundOwner);
        }
        bookRepository.save(foundBook);
    }
}