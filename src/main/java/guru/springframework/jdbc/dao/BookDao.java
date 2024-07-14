package guru.springframework.jdbc.dao;


import guru.springframework.jdbc.domain.Book;

public interface BookDao {
    Book saveBook(Book book);

    Book getById(Long id);

    Book findBookByTitle(String title);

    Book updateBook(Book book);

    void deleteBookById(Long id);
}
