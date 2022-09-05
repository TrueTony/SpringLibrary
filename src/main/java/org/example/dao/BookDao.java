package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("select * from book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book profile(int id) {
        return jdbcTemplate.query("select * from book where id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book(name, author, year) values(?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("update book set name=?, author=?, year=? where id=?",
                book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from book where id=?", id);
    }

    public Optional<Person> owner(int id) {
        return jdbcTemplate.query("select * from person where id = (select person_id from book where id=?)",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void deleteOwner(int id) {
        jdbcTemplate.update("update book set person_id = null where id = ?", id);
    }

    public void setOwner(int id, Person person) {
        jdbcTemplate.update("update book set person_id = ? where id = ?",
                person.getId(), id);
    }
}
