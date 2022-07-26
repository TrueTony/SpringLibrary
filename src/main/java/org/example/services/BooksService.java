package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear) {
            return booksRepository.findAll(Sort.by("year"));
        } else {
            return booksRepository.findAll();
        }
    }

    public List<Book> findAllWithPage(int page, int booksPerPage, boolean sortByYear) {
        if (sortByYear) {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } else {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book updatedBook, int id) {
        updatedBook.setId(id);
        updatedBook.setOwner(updatedBook.getOwner());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Optional<Person> findOwnerById(int id) {
        return booksRepository.findById(id).map(Book::getOwner);
    }

    @Transactional
    public void setOwner(int id, Person owner) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()) {
            book.get().setTook(new Timestamp(new Date().getTime()));
            book.get().setOwner(owner);
        }
    }

    @Transactional
    public void deleteOwner(int id) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()) {
            book.get().setTook(null);
            book.get().setOwner(null);
        }
    }

    public List<Book> findByName(String title) {
        return booksRepository.findByTitleStartingWith(title);
    }
}
