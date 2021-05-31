package model;

import model.exception.NoSuchBookException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represent a reading list having a name of owner and a list of books
public class ReadingList implements Writable {
    private String name;
    private List<Book> books;

    /*
     * EFFECTS: create an empty reading list with owner's name set to name
     */
    public ReadingList(String name) {
        this.name = name;
        books = new ArrayList<>();
    }

    /*
     * EFFECTS: return the reading list owner's name
     */
    public String getName() {
        return name;
    }

    /*
     * MODIFIES: this
     * EFFECTS: if the book is not already in the reading list, add it to the reading list,
     *          else do nothing
     */

    public void addBook(Book book) {
        if (!isBookInList(book.getTitle(),book.getAuthor())) {
            books.add(book);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes the book from the reading list if book is in the list, if not, do nothing
     */

    public void deleteBook(Book book) {
        books.remove(book);
    }

    /*
     * EFFECTS: return the number of books that have not been read
     */
    public int countToRead() {
        int count = 0;
        for (Book b: books) {
            if (b.getReadStatus() == BookStatus.FALSE) {
                count++;
            }
        }
        return count;
    }

    /*
     * EFFECTS: return the number of books that have already been read
     */
    public int countHasRead() {
        int count = 0;
        for (Book b: books) {
            if (b.getReadStatus() == BookStatus.TRUE) {
                count++;
            }
        }
        return count;
    }

    // getters
    /*
     * EFFECTS: return a list of books in the reading list
     */
    public List<Book> getBooksList() {
        return books;
    }

    /*
     * EFFECTS: find a book that has the same title and author as the parameters and return it, throws
     * NoSuchBookException when book is not found in reading list
     */
    public Book getBook(String title, String author) throws NoSuchBookException {
        if (!isBookInList(title,author)) {
            throw new NoSuchBookException();
        }
        for (Book b : books) {
            if (b.getTitle().equals(title) && b.getAuthor().equals(author)) {
                return b;
            }
        }
        throw new NoSuchBookException();
    }

    /*
     * EFFECTS: return a list of all the books' titles in the reading list
     */
    public List<String> getBooksName() {
        List<String> titles = new ArrayList<>();
        for (Book b: books) {
            titles.add(b.getTitle());
        }
        return titles;
    }

    /*
     * EFFECTS: return a list of all the books' authors' names in the reading list
     */
    public List<String> getBooksAuthor() {
        List<String> authors = new ArrayList<>();
        for (Book b: books) {
            authors.add(b.getAuthor());
        }
        return authors;
    }


    // EFFECTS: return true if book specified by user is in reading list
    //          return false if book is not in reading list
    public boolean isBookInList(String title, String author) {
        return (getBooksName().contains(title) && getBooksAuthor().contains(author));
    }

    @Override
    // EFFECTS: return the reading list as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("books", booksToJson());
        return json;
    }

    // EFFECTS: returns list of books in this reading list as a JSON array
    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book b : books) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }
}
