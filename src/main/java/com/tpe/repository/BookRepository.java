package com.tpe.repository;

import com.tpe.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//optional
public interface BookRepository extends JpaRepository<Book, Long> {


    List<Book> findByTitle(String title);// select * from t_book where title=?


}
