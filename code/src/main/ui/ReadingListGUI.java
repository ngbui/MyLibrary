package ui;

import model.Book;
import model.BookStatus;
import model.ReadingList;
import model.exception.NoSuchBookException;
import persistence.ReadingListJsonReader;
import persistence.ReadingListJsonWriter;
import ui.tools.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represent the GUI for a ReadingList Application
public class ReadingListGUI extends JFrame {
    public Font regularFont = new Font("Arial",Font.PLAIN,14);
    private static final String JSON_STORE = "./data/myList.txt";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    public ReadingList readingList;
    private JTextArea textArea;
    private List<Tool> tools;
    private ReadingListJsonWriter readingListJsonWriter;
    private ReadingListJsonReader readingListJsonReader;

    // EFFECTS: creates the window and init for the reading list application
    public ReadingListGUI() {
        super("Welcome to the BookList");
        initializeFields();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: initialize reading list, tools and reader/writer
    public void initializeFields() {
        readingList = new ReadingList("User");
        tools = new ArrayList<>();
        readingListJsonWriter = new ReadingListJsonWriter(JSON_STORE);
        readingListJsonReader = new ReadingListJsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: create the JFrame window for the reading list, and makes the tools that will work on the reading list
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        createTextPanel();
        createTools();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the scrollable area where all the books and messages to the user can be displayed
    private void createTextPanel() {
        JPanel textPanel = new JPanel();
        textPanel.setPreferredSize(new Dimension(WIDTH * 4 / 5,HEIGHT - 40));
        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(WIDTH * 4 / 5,HEIGHT - 40));
        textArea.setFont(regularFont);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(WIDTH * 4 / 5,HEIGHT - 45));

        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);

        textPanel.add(scroll);
        add(textPanel, BorderLayout.WEST);
    }


    // MODIFIES: this
    // EFFECTS: declares and instantiates all the tools and display them on the right side of the window
    private void createTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0,1));
        toolArea.setSize(new Dimension(WIDTH / 5, HEIGHT));
        add(toolArea, BorderLayout.EAST);

        AddTool addTool = new AddTool(this, toolArea);
        tools.add(addTool);

        MarkAsReadTool markAsReadTool = new MarkAsReadTool(this, toolArea);
        tools.add(markAsReadTool);

        SaveTool saveTool = new SaveTool(this, toolArea);
        tools.add(saveTool);

        LoadTool loadTool = new LoadTool(this, toolArea);
        tools.add(loadTool);

        ViewToReadTool viewToReadTool = new ViewToReadTool(this, toolArea);
        tools.add(viewToReadTool);

        ViewAllTool viewAllTool = new ViewAllTool(this, toolArea);
        tools.add(viewAllTool);
    }

    // MODIFIES: this
    // EFFECTS: construct a new book with user's input and add it to reading list
    public void addBook(String title, String author) {
        if (readingList.isBookInList(title,author)) {
            setText("Looks like you've already got the exact book on the list. Add something else!");
        } else if (title.equals("") || author.equals("")) {
            setText("Invalid input! Please try again.");
        } else {
            Book newBook = new Book(title, author);
            readingList.addBook(newBook);
            setText("You added a book called: " + title + " by " + author);
        }
    }

    // EFFECTS: sets the text in the text area to say that the reading list is empty if
    // the reading list is empty; if not do nothing.
    public void emptyReadingList() {
        if (readingList.getBooksList().isEmpty()) {
            setText("Looks like your reading list is still empty. Please add a book!");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the text in the text area to the given text
    public void setText(String txt) {
        textArea.setText(txt);
    }

    // MODIFIES: this
    // EFFECTS: display the to-read books in reading list on textArea
    public void viewToRead() {
        String toRead = "Books left to read: ";
        for (Book b: readingList.getBooksList()) {
            if (b.getReadStatus().equals(BookStatus.FALSE)) {
                toRead += "\n" + b.getTitle() + " by " + b.getAuthor();
            }
        }
        setText(toRead);
    }

    // MODIFIES: this
    // EFFECTS: display all the books in reading list on textArea
    public void viewAllBook() {
        String toRead = "You currently have these books on your reading list: ";
        for (Book b: readingList.getBooksList()) {
            toRead += "\n" + b.getTitle() + " by " + b.getAuthor();
        }
        setText(toRead);
    }

    // MODIFIES: this
    // EFFECTS: loads reading list from file
    public void loadReadingList() {
        try {
            readingList = readingListJsonReader.read();
            setText("Loaded your reading list from " + JSON_STORE);
        } catch (IOException e) {
            setText("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the reading list to file
    public void saveReadingList() {
        try {
            readingListJsonWriter.open();
            readingListJsonWriter.write(readingList);
            readingListJsonWriter.close();
            setText("Saved your reading list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            setText("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: marks the book with the given name and author
    public void markBookAsRead(String title, String author) {
        try {
            readingList.getBook(title,author).markAsRead();
            setText("You marked the book " + title + " by " + author + " as read.");
        } catch (NoSuchBookException bookNotFound) {
            setText("Book could not be found. Please try again.");
        }
    }

    public static void main(String[] args) {
        new ReadingListGUI();
    }
}
