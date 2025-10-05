package service;

import dao.BookDAO;
import model.Book;

import java.util.List;

public class BookService {
    private BookDAO dao = new BookDAO();

    public boolean addBook(Book b) {
        return dao.insert(b);
    }

    public List<Book> getAllBooks() {
        return dao.findAll();
    }

    public boolean updateBook(Book b) {
        return dao.update(b);
    }

    public boolean deleteBook(int id) {
        return dao.delete(id);
    }
}