package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.AuthorDaoImpl;
import guru.springframework.jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("local")
@DataJpaTest
//@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

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
    void getById() {
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

        authorDao.deleteAuthorById(saved.getId());

        Author deleted = authorDao.getById(saved.getId());

        assertThat(deleted).isNull();
    }
}