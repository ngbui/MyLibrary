package persistence;

import model.Book;
import model.BookStatus;
import model.ReadingList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads reading list from JSON data stored in file
public class ReadingListJsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public ReadingListJsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads reading list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ReadingList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseReadingList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses reading list from JSON object and returns it
    private ReadingList parseReadingList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ReadingList rl = new ReadingList(name);
        addBooksFromJson(rl, jsonObject);
        return rl;
    }

    // MODIFIES: rl
    // EFFECTS: parses books from JSON object and adds them to reading list
    private void addBooksFromJson(ReadingList rl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("books");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBookToJson(rl, nextBook);
        }
    }

    // MODIFIES: rl
    // EFFECTS: parses book from JSON object and adds it to reading list
    private void addBookToJson(ReadingList rl, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        BookStatus status = BookStatus.valueOf(jsonObject.getString("status"));
        Book book = new Book(title, author, status);
        rl.addBook(book);
    }
}
