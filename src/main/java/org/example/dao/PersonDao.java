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
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person profile(int id) {
        return jdbcTemplate.query("select * from person where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> profile(String fio) {
         return jdbcTemplate.query("select * from person where fio=?", new Object[]{fio},
                 new BeanPropertyRowMapper<>(Person.class))
                 .stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(fio, yearborn) values(?, ?)",
                person.getFio(), person.getYearBorn());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("update person set fio=?, yearborn=? where id=?",
                person.getFio(), person.getYearBorn(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where id=?", id);
    }

    public List<Book> personBooks(int id) {
        return jdbcTemplate.query("select book.* from book left join person p on book.person_id = p.id where p.id=?;",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}
