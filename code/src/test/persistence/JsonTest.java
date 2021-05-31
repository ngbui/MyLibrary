package persistence;

import model.Book;
import model.BookStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkBook(String title, String author, BookStatus status, Book book) {
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(status, book.getReadStatus());
    }
}
