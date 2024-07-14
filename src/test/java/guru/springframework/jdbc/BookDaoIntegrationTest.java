package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.BookDaoImpl;
import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@Import(BookDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;

    @Test
    void updateBook() {
        Book book = new Book();
        book.setTitle("M Book");
        book.setIsbn("132");

        Author author = new Author();
        author.setId(1L);

        book.setAuthor(author);
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

        Author author = new Author();
        author.setId(1L);

        book.setAuthor(author);
        book.setPublisher("I am");
        Book saved = bookDao.saveBook(book);
        assertThat(saved).isNotNull();
    }

    @Test
    void getById() {
        Book book = bookDao.getById(1L);

        assertThat(book).isNotNull();
    }

    @Test
    void findBookByName() {
        Book book = bookDao.findBookByTitle("M Book");

        assertThat(book).isNotNull();
    }

    @Test
    void deleteBookById() {
        Book book = new Book();
        book.setTitle("M Book");
        book.setIsbn("132");
        book.setPublisher("I am");
        Book saved = bookDao.saveBook(book);

        bookDao.deleteBookById(saved.getId());

        Book deleted = bookDao.getById(saved.getId());

        assertThat(deleted).isNull();
    }
}