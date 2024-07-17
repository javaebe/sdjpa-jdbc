package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.AuthorDaoImpl;
import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.dao.BookDaoImpl;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
//@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@Import({AuthorDaoImpl.class, BookDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DaoIntegrationTest {
    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

    @Test
    void updateAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        Author saved = authorDao.saveAuthor(author);

        saved.setLastName("D");
        Author updated = authorDao.updateAuthor(saved);
        assertThat(updated.getLastName()).isEqualTo("D");
    }

    @Test
    void saveAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        Author saved = authorDao.saveAuthor(author);
        assertThat(saved).isNotNull();
    }

    @Test
    void getAuthorById() {
        Author author = authorDao.getById(1L);

        assertThat(author).isNotNull();
    }

    @Test
    void findAuthorByName() {
        Author author = authorDao.findAuthorByName("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void deleteAuthorById() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        Author saved = authorDao.saveAuthor(author);

        Long id = saved.getId();
        authorDao.deleteAuthorById(id);

        assertThrows(TransientDataAccessResourceException.class, () -> {
            authorDao.getById(id);
        });
    }

    @Test
    void updateBook() {
        Book book = new Book();
        book.setTitle("M Book");
        book.setIsbn("132");
        book.setAuthorId(1L);
        book.setPublisher("I am");
        Book saved = bookDao.saveBook(book);

        saved.setIsbn("321");
        Book updated = bookDao.updateBook(saved);
        assertThat(updated.getIsbn()).isEqualTo("321");
    }

    @Test
    void saveBook() {
        Book book = new Book();
        book.setTitle("M Book");
        book.setIsbn("132");
        book.setAuthorId(1L);
        book.setPublisher("I am");
        Book saved = bookDao.saveBook(book);
        assertThat(saved).isNotNull();
    }

    @Test
    void getBookById() {
        Book book = bookDao.getById(1L);

        assertThat(book).isNotNull();
    }

    @Test
    void findBookByName() {
        Book book = bookDao.findBookByTitle("Spring in Action, 5th Edition");

        assertThat(book).isNotNull();
    }

    @Test
    void deleteBookById() {
        Book book = new Book();
        book.setTitle("M Book");
        book.setIsbn("132");
        book.setPublisher("I am");
        Book saved = bookDao.saveBook(book);

        Long id = saved.getId();
        bookDao.deleteBookById(id);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            bookDao.getById(id);
        });
    }
}