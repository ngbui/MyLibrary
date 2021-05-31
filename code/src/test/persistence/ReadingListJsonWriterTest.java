package persistence;

import model.Book;
import model.BookStatus;
import model.ReadingList;
import model.exception.NoSuchBookException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReadingListJsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ReadingList rl = new ReadingList("My reading list");
            ReadingListJsonWriter writer = new ReadingListJsonWriter("./data/my\0illegal:fileName.txt");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyReadingList() {
        try {
            ReadingList rl = new ReadingList("My reading list");
            ReadingListJsonWriter writer = new ReadingListJsonWriter("./data/testWriterEmptyReadingList.txt");
            writer.open();
            writer.write(rl);
            writer.close();

            ReadingListJsonReader reader = new ReadingListJsonReader("./data/testWriterEmptyReadingList.txt");
            rl = reader.read();
            assertEquals("My reading list", rl.getName());
            assertEquals(0, rl.getBooksList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralReadingList() {
        try {
            ReadingList rl = new ReadingList("My reading list");
            rl.addBook(new Book("The Hobbit", "J. R. R. Tolkien"));
            rl.addBook(new Book("The Name of the Wind", "Patrick Rothfuss"));
            rl.getBook("The Name of the Wind","Patrick Rothfuss").markAsRead();
            ReadingListJsonWriter writer = new ReadingListJsonWriter("./data/testWriterGeneralReadingList.txt");
            writer.open();
            writer.write(rl);
            writer.close();

            ReadingListJsonReader reader = new ReadingListJsonReader("./data/testWriterGeneralReadingList.txt");
            rl = reader.read();
            assertEquals("My reading list", rl.getName());
            List<Book> books = rl.getBooksList();
            assertEquals(2, books.size());
            checkBook("The Hobbit", "J. R. R. Tolkien", BookStatus.FALSE, books.get(0));
            checkBook("The Name of the Wind", "Patrick Rothfuss", BookStatus.TRUE, books.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (NoSuchBookException e) {
            fail("Exception should not have been thrown");
        }
    }
}
