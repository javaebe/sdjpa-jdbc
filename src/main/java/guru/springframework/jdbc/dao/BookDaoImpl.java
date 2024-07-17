package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book saveBook(Book book) {
        jdbcTemplate.update("INSERT INTO book (title,isbn,publisher,author_id) VALUES(?,?,?,?)",
                book.getTitle(),
                book.getIsbn(),
                book.getPublisher(),
                book.getAuthorId());
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        if (id != null) {
            return getById(id);
        }
        return null;
    }

    @Override
    public Book getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?", getRowMapper(), id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE title = ?", getRowMapper(), title);
    }

    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update("UPDATE book SET title=?,isbn=?,publisher=?,author_id=? WHERE id=?",
                book.getTitle(),
                book.getIsbn(),
                book.getPublisher(),
                book.getAuthorId(),
                book.getId());
        return getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    private RowMapper<Book> getRowMapper() {
        return new BookMapper();
    }
}
