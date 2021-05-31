package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represent a book having a title, an author, and a read status (either already read or unread)
public class Book implements Writable {
    private BookStatus status; // the read status of the book (FALSE = unread, TRUE = already read)
    private String title;      // the title of the book
    private String author;     // the author of the book

    /* Constructor
     * EFFECTS: title of book is set to title; author of the book is set to author;
     *          the status of the book is set to false (unread) by default.
     */
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        status = BookStatus.FALSE;
    }

    /* Constructor
     * EFFECTS: title of book is set to title; author of the book is set to author;
     *          the status of the book is set to status.
     */
    public Book(String title, String author, BookStatus status) {
        this.title = title;
        this.author = author;
        this.status = status;
    }

    // getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BookStatus getReadStatus() {
        return status;
    }

    /*
     * MODIFIES: this
     * EFFECTS: change the status of the book to already read
     */
    public void markAsRead() {
        status = BookStatus.TRUE;
    }

    @Override
    // EFFECTS: return this book as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("status",status);
        return json;
    }
}