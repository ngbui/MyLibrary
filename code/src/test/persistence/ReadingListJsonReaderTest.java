package persistence;

import model.Book;
import model.BookStatus;
import model.ReadingList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReadingListJsonReaderTest extends JsonTest {
    public ReadingListJsonReader reader;
    @Test
    void testReaderNonExistentFile() {
        reader = new ReadingListJsonReader("./data/noSuchFile.txt");
        try {
            ReadingList rl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyReadingList() {
        reader = new ReadingListJsonReader("./data/testReaderEmptyReadingList.txt");
        try {
            ReadingList rl = reader.read();
            assertEquals("My reading list", rl.getName());
            assertEquals(0, rl.getBooksList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralReadingList() {
        reader = new ReadingListJsonReader("./data/testReaderGeneralReadingList.txt");
        try {
            ReadingList rl = reader.read();
            assertEquals("My reading list", rl.getName());
            List<Book> books = rl.getBooksList();
            assertEquals(2, books.size());
            checkBook("The Hobbit", "J. R. R. Tolkien", BookStatus.FALSE, books.get(0));
            checkBook("The Name of the Wind", "Patrick Rothfuss", BookStatus.TRUE, books.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

