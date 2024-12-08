package com.bookrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookrental.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{

}
