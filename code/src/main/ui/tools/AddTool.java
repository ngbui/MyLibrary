package ui.tools;

import ui.ReadingListGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// EFFECTS: creates a new AddTool with the given ReadingListGUI and parent.
public class AddTool extends Tool {
    public AddTool(ReadingListGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // MODIFIES: this
    // EFFECTS: creates new add button, enable button, and adds to parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add A New Book");
        addToParent(parent);
        button.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new AddToolClickHandler());
    }

    private class AddToolClickHandler implements ActionListener {

        // MODIFIES: gui(readingList)
        // EFFECTS: when button is pressed, create pop-up window,
        // get data from the fields from pop-up window and construct
        //          a book based on the data to add to reading list
        @Override
        public void actionPerformed(ActionEvent e) {
            display();
        }

        // MODIFIES: gui(readingList)
        // EFFECTS: create pop-up window to ask for user's input,
        // get data from the fields from pop-up window to
        // add the book with title and author into readingList

        private void display() {
            JTextField title = new JTextField();
            JTextField author = new JTextField();
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Title:"));
            panel.add(title);
            panel.add(new JLabel("Author:"));
            panel.add(author);
            int result = JOptionPane.showConfirmDialog(null, panel, "Add A Book",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                gui.addBook(title.getText(), author.getText());
            } else {
                System.out.println("Cancelled");
            }
        }
    }
}
