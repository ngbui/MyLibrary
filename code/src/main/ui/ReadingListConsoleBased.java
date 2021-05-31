package ui;

import model.Book;
import model.ReadingList;
import model.exception.NoSuchBookException;
import persistence.NameList;
import persistence.ReadingListJsonReader;
import persistence.ReadingListJsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Represent a console-based interface for ReadingList application
public class Application {
    private static String JSON_STORE;
    private static final String JSON_NAME_LIST = "./data/users.txt";
    private ArrayList<String> stringList;
    private Scanner scanner = new Scanner(System.in);
    private ReadingList myList;
    private String name;
    private String command;
    private String title;
    private String author;
    private Book toModify;
    private ReadingListJsonWriter readingListJsonWriter;
    private ReadingListJsonReader readingListJsonReader;
    private NameList nameList;

    // EFFECTS: run the reading list application
    public Application() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: initialize reading list, name list and reader/writer
    private void initialize() {
        myList = new ReadingList(name + "'s reading list");
        JSON_STORE = "./data/" + name + ".txt";
        readingListJsonWriter = new ReadingListJsonWriter(JSON_STORE);
        readingListJsonReader = new ReadingListJsonReader(JSON_STORE);
        nameList = new NameList(JSON_NAME_LIST);
        toModify = null;
        rememberName();
    }

    // MODIFIES: this
    // EFFECTS: check if users have already saved their reading list before and if users did save their list,
    //          prompt them to load their reading list
    private void rememberName() {
        try {
            stringList = nameList.readFileIntoList();
        } catch (IOException e) {
            return;
        }
        if (stringList.contains(name)) {
            System.out.println("Welcome back! Looks like you have been here before.");
            promptLoadPastData();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompt users to load their past data. If user select yes, load the data. If not, do nothing
    ///         if user's choice is invalid, ask again.
    private void promptLoadPastData() {
        System.out.println("Please be reminded that if you do not load your data now and add new books to the");
        System.out.println("existing file, you are at risk of OVERWRITING your data. This is your last warning");
        System.out.println("Do you want to load your data now?");
        System.out.println("Press Y for YES");
        System.out.println("Press N for NO");
        command = scanner.nextLine();
        switch (command) {
            case "Y":
                loadReadingList();
                break;
            case "N":
                break;
            default:
                System.out.println("That is an invalid choice. Please try again.");
                promptLoadPastData();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        start();
        initialize();

        while (true) {
            printOptions();
            command = scanner.nextLine();

            if (command.equals("0")) {
                break;
            } else {
                makeChoices(command);
            }
        }
        System.out.println("Bye! See you later");
    }

    // EFFECTS: print out greetings at the start of the application
    private void start() {
        System.out.println("Hi there!");
        System.out.println("Welcome to The Book List");
        askName();
    }

    // MODIFIES: this
    // EFFECTS: ask and set name to user's input. if name is invalid, ask user for name again.
    private void askName() {
        System.out.println("What's your name?");
        name = scanner.nextLine();
        if (name.equals("") || name.equals(" ")) {
            System.out.println("Invalid name. Please try again.");
            askName();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void makeChoices(String command) {
        switch (command) {
            case "1":
                createBook();
                break;
            case "2":
                chooseModifyOptions();
                break;
            case "3":
                printList();
                break;
            case "4":
                reportProgress();
                break;
            case "5":
                saveReadingList();
                break;
            case "6":
                loadReadingList();
                break;
            default:
                System.out.println("You picked an invalid number. Please choose again.");
                break;
        }
    }

    // EFFECTS: print out the number of already read books and to-read books on reading list along with short phrases
    //          on screen
    private void reportProgress() {
        System.out.println("You have already read " + myList.countHasRead() + " books! Well done!");
        System.out.println("You still have " + myList.countToRead() + " books left to read.");
    }

    // EFFECTS: print on screen the titles and authors of the books on the reading list by the format: title by author
    private void printList() {
        if (myList.getBooksList().size() == 0) {
            System.out.println("Your reading list is currently empty. Please add a book!");
        } else {
            System.out.println("Your reading list contains these following books: ");
            for (Book b : myList.getBooksList()) {
                title = b.getTitle();
                author = b.getAuthor();
                System.out.println(title + " by " + author);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: mark book specified by user as already read if book is in reading list
    private void markBookInList() {
        getBookInList();
        toModify.markAsRead();
        System.out.println("You marked a book called: " + title + " by " + author + " as already read");
    }

    // MODIFIES: this
    // EFFECTS: delete book specified by user from reading list if book is in reading list
    private void deleteBookFromList() {
        getBookInList();
        myList.deleteBook(toModify);
        System.out.println("You deleted a book called: " + title + " by " + author);
    }

    // MODIFIES: this
    // EFFECTS: return true if book specified by user is in reading list and get the book specified by user
    //          return false if book is not in reading list
    private void getBookInList() {
        askBookDetails();
        try {

            toModify = myList.getBook(title,author);
        } catch (NoSuchBookException e) {
            System.out.println("Sorry that book is not on your reading list. Please try again.");
            getBookInList();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user's input and processes user's command
    private void chooseModifyOptions() {
        System.out.println("To delete a book on your reading list, press D");
        System.out.println("To mark a book as read on your reading list, press M");
        command = scanner.nextLine();
        switch (command) {
            case "D":
                deleteBookFromList();
                break;
            case "M":
                markBookInList();
                break;
            default:
                System.out.println("That is an invalid choice. Please try again.");
                chooseModifyOptions();
        }
    }

    // MODIFIES: this
    // EFFECTS: set title and author to the user's inputs
    private void askBookDetails() {
        System.out.println("What is the title of the book?");
        title = scanner.nextLine();
        System.out.println("Who wrote the book?");
        author = scanner.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: construct a new book with user's input and add it to reading list
    private void createBook() {
        askBookDetails();
        if (myList.isBookInList(title,author)) {
            System.out.println("Looks like you've already got the exact book on the list. Add something else!");
        } else {
            Book newBook = new Book(title, author);
            myList.addBook(newBook);
            System.out.println("You added a book called: " + title + " by " + author);
        }
    }

    // EFFECTS: print menu of options to user on screen
    private void printOptions() {
        System.out.println("To add a new book to your reading list, press 1");
        System.out.println("To modify your reading list, press 2");
        System.out.println("To view the books on your reading list, press 3");
        System.out.println("To view the number of to-read and number of already read books on your list, press 4");
        System.out.println("To save reading list to file, press 5");
        System.out.println("To load reading list from file, press 6");
        System.out.println("To quit, press 0");
    }

    // EFFECTS: saves the reading list to file
    private void saveReadingList() {
        saveName();
        try {
            readingListJsonWriter.open();
            readingListJsonWriter.write(myList);
            readingListJsonWriter.close();
            System.out.println("Saved " + myList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: check if the name of user is already saved, if not saved, save name of users into file
    private void saveName() {
        if (!stringList.contains(name)) {
            stringList.add(name);
        }
        try {
            nameList.saveListIntoFile(stringList);
        } catch (IOException e) {
            return;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads reading list from file
    private void loadReadingList() {
        try {
            myList = readingListJsonReader.read();
            System.out.println("Loaded " + myList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            System.out.println("Seems like you haven't saved a reading list. Please remember to do it next time!");
        }
    }
}
