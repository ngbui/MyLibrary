package model;

import model.exception.NoSuchBookException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReadingListTest {
    private ReadingList testReadingList;
    private Book bookexample;
    private Book bookexample1;
    private Book bookexample2;
    private List<String> titles;
    private List<Book> books;


    @BeforeEach
    void runBefore() {
        testReadingList = new ReadingList("test");
        bookexample = new Book("The Hobbit", "J. R. R. Tolkien");
        bookexample1 = new Book("The Name of the Wind", "Patrick Rothfuss");
        bookexample2 = new Book("Twilight","Stephenie Meyer");
        books = testReadingList.getBooksList();
    }

    @Test
    void testConstructor() {
        books = testReadingList.getBooksList();
        assertEquals(0,books.size());
    }

    @Test
    void testAddBookOnce() {
        // test addBook() with one book added
        assertEquals(0,books.size());
        testReadingList.addBook(bookexample);
        List<Book> booksafter = testReadingList.getBooksList();
        assertTrue(booksafter.contains(bookexample));
        assertEquals(1,booksafter.size());
    }

    @Test
    void testAddBookMultipleTimes() {
        // test addBook() with the same book added many times
        assertEquals(0,books.size());

        // adding a book once
        testReadingList.addBook(bookexample);
        List<Book> booksafter = testReadingList.getBooksList();
        assertTrue(booksafter.contains(bookexample));
        assertEquals(1,booksafter.size());

        // adding the same book twice
        testReadingList.addBook(bookexample);
        List<Book> booksaftertwice = testReadingList.getBooksList();
        assertTrue(booksaftertwice.contains(bookexample));
        assertEquals(1,booksaftertwice.size());
    }

    @Test
    void testDeleteBook() {
        // delete a book
        testReadingList.addBook(bookexample);
        testReadingList.deleteBook(bookexample);
        List<Book> booksafter = testReadingList.getBooksList();
        assertFalse(booksafter.contains(bookexample));
        assertEquals(0,booksafter.size());
    }

    @Test
    void testCountToReadEmptyList() {
        // test countToRead on empty reading list
        assertEquals(0,testReadingList.countToRead());
    }

    @Test
    void testCountToReadNoBookToRead() {
        // test countToRead on reading list with no book left to read
        testReadingList.addBook(bookexample);
        try {
            testReadingList.getBook(bookexample.getTitle(),bookexample.getAuthor()).markAsRead();
        } catch (NoSuchBookException e) {
            fail("Exception not expected");
        }
        assertEquals(0,testReadingList.countToRead());
    }

    @Test
    void testCountToReadOne() {
        // reading list has multiple books but only one unread book
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample1);
        try {
            testReadingList.getBook(bookexample.getTitle(),bookexample.getAuthor()).markAsRead();
        } catch (NoSuchBookException e) {
            fail("Exception not expected");
        }
        assertEquals(1,testReadingList.countToRead());
    }

    @Test
    void testCountToReadMultiple() {
        // reading list has multiple unread books and one already read book
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample1);
        testReadingList.addBook(bookexample2);
        try {
            testReadingList.getBook(bookexample2.getTitle(),bookexample2.getAuthor()).markAsRead();
        } catch (NoSuchBookException e) {
            fail("Exception not expected");
        }
        assertEquals(2,testReadingList.countToRead());
    }

    @Test
    void testCountToReadAll() {
        // reading list has multiple books - all books have not been read
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample1);
        testReadingList.addBook(bookexample2);
        assertEquals(3,testReadingList.countToRead());
    }

    @Test
    void testCountHasReadEmptyList() {
        // test countHasRead on empty reading list
        assertEquals(0,testReadingList.countHasRead());
    }

    @Test
    void testCountHasReadNoAlreadyReadBook() {
        // test countToRead on reading list with no book left to read
        testReadingList.addBook(bookexample);
        assertEquals(0,testReadingList.countHasRead());
    }

    @Test
    void testCountHasReadOne() {
        // reading list has multiple books but only one already read book
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample1);
        try {
            testReadingList.getBook(bookexample.getTitle(),bookexample.getAuthor()).markAsRead();
        } catch (NoSuchBookException e) {
            fail("Exception not expected");
        }
        assertEquals(1,testReadingList.countHasRead());
    }

    @Test
    void testCountHasReadMultiple() {
        // reading list has multiple already books and one unread book
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample1);
        testReadingList.addBook(bookexample2);
        try {
            testReadingList.getBook(bookexample.getTitle(),bookexample.getAuthor()).markAsRead();
        } catch (NoSuchBookException e) {
            fail("Exception not expected");
        }
        try {
            testReadingList.getBook(bookexample2.getTitle(),bookexample2.getAuthor()).markAsRead();
        } catch (NoSuchBookException e) {
            fail("Exception not expected");
        }
        assertEquals(2,testReadingList.countHasRead());
    }

    @Test
    void testCountHasReadAll() {
        // reading list has multiple books - all books have been read
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample1);
        testReadingList.addBook(bookexample2);
        try {
            testReadingList.getBook(bookexample.getTitle(),bookexample.getAuthor()).markAsRead();
        } catch (NoSuchBookException e) {
            fail("Exception not expected");
        }
        try {
            testReadingList.getBook(bookexample1.getTitle(),bookexample1.getAuthor()).markAsRead();
        } catch (NoSuchBookException e) {
            fail("Exception not expected");
        }
        try {
            testReadingList.getBook(bookexample2.getTitle(),bookexample2.getAuthor()).markAsRead();
        } catch (NoSuchBookException e) {
            fail("Exception not expected");
        }
        assertEquals(3,testReadingList.countHasRead());
    }

    @Test
    void testGetBookSameTitleDifferentAuthor() {
        // a book we must find has the same title but different author from the one on the reading list
        testReadingList.addBook(bookexample1);
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample2);
        try {
            testReadingList.getBook(bookexample.getTitle(),"Someone");
            fail("Exception expected");
        } catch (NoSuchBookException e) {
            // pass the test
        }
    }

    @Test
    void testGetBookDifferentTitleSameAuthor() {
        // a book we must find has the different title but same author from the one on the reading list
        testReadingList.addBook(bookexample1);
        testReadingList.addBook(bookexample2);
        testReadingList.addBook(bookexample);
        try {
            testReadingList.getBook("Some title",bookexample.getAuthor());
            fail("Exception expected");
        } catch (NoSuchBookException e) {
            // pass the test
        }
    }

    @Test
    void testGetBookFailBecauseBookIsNotInList() {
        // a book we must find has the same title but different author from the one on the reading list
        testReadingList.addBook(bookexample1);
        testReadingList.addBook(bookexample2);
        testReadingList.addBook(bookexample);
        try {
            testReadingList.getBook("Some title","Someone");
            fail("Exception expected");
        } catch (NoSuchBookException e) {
            // pass the test
        }
    }

    @Test
    void testGetBookFailBecauseMismatchedTitleAndAuthor() {
        // a book we must find has the same title but different author from the one on the reading list
        testReadingList.addBook(bookexample1);
        testReadingList.addBook(bookexample2);
        testReadingList.addBook(bookexample);
        try {
            testReadingList.getBook(bookexample.getTitle(),bookexample2.getAuthor());
            fail("Exception expected");
        } catch (NoSuchBookException e) {
            // pass the test
        }
    }

    @Test
    void testGetBookSingleBook() {
        // find a book by title and author in a reading list with only one book
        testReadingList.addBook(bookexample);
        try {
            assertEquals(bookexample,testReadingList.getBook("The Hobbit","J. R. R. Tolkien"));
        } catch (NoSuchBookException e) {
            fail("Exception not expected");
        }
    }

    @Test
    void testGetBookManyBooks() {
        // find a book by title and author in a reading list with many books
        testReadingList.addBook(bookexample2);
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample1);
        try {
            assertEquals(bookexample,testReadingList.getBook("The Hobbit","J. R. R. Tolkien"));
        } catch (NoSuchBookException e) {
            fail("Exception not expected");
        }
    }

    @Test
    void testGetBookEmpty() {
        // find a book by title and author in an empty reading list
        try {
            assertEquals(bookexample,testReadingList.getBook("The Hobbit","J. R. R. Tolkien"));
            fail("Exception expected");
        } catch (NoSuchBookException e) {
            // pass the test
        }
    }

    @Test
    void testGetBooksNameEmpty() {
        // get list of books' title in an empty reading list
        titles = testReadingList.getBooksName();
        assertEquals(titles.size(),0);
        assertTrue(titles.isEmpty());
    }

    @Test
    void testGetBooksNameNotEmpty() {
        // get list of books' title in a non-empty reading list
        testReadingList.addBook(bookexample2);
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample1);
        titles = testReadingList.getBooksName();
        assertEquals(titles.size(),3);
        assertTrue(titles.contains(bookexample2.getTitle()));
        assertTrue(titles.contains(bookexample.getTitle()));
        assertTrue(titles.contains(bookexample1.getTitle()));
    }

    @Test
    void testIsBookInListNoBecauseOfBothTitleAndAuthor() {
        // book with title is not in the reading list
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample1);
        assertFalse(testReadingList.isBookInList(bookexample2.getTitle(),bookexample2.getAuthor()));
    }

    @Test
    void testIsBookInListYes() {
        // book with title is already in the reading list
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample1);
        assertFalse(testReadingList.isBookInList(bookexample2.getTitle(),bookexample2.getAuthor()));

        testReadingList.addBook(bookexample2);
        assertTrue(testReadingList.isBookInList(bookexample2.getTitle(),bookexample2.getAuthor()));
    }

    @Test
    void testIsBookInListNoBecauseOfTitle() {
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample1);
        assertFalse(testReadingList.isBookInList(bookexample2.getTitle(),bookexample1.getAuthor()));
    }

    @Test
    void testIsBookInListNoBecauseOfAuthor() {
        testReadingList.addBook(bookexample);
        testReadingList.addBook(bookexample1);
        assertFalse(testReadingList.isBookInList(bookexample1.getTitle(),bookexample2.getAuthor()));
    }
}
