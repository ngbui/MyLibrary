package ui.tools;

import ui.ReadingListGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarkAsReadTool extends Tool {
    public MarkAsReadTool(ReadingListGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // MODIFIES: this
    // EFFECTS: creates new button and adds to parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Mark As Read");
        addToParent(parent);
        button.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new MarkAsReadClickHandler());
    }

    private class MarkAsReadClickHandler implements ActionListener {

        // MODIFIES: gui(readingList)
        // EFFECTS: when button is pressed, create pop-up window to ask for user's input,
        // get data from the fields from pop-up window to
        // mark the book with title and author as read

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gui.readingList.getBooksList().isEmpty()) {
                gui.emptyReadingList();
            } else {
                display();
            }
        }

        // MODIFIES: gui(readingList)
        // EFFECTS: create pop-up window to ask for user's input,
        // get data from the fields from pop-up window to
        // mark the book with title and author as read

        private void display() {
            JTextField title = new JTextField();
            JTextField author = new JTextField();
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Title:"));
            panel.add(title);
            panel.add(new JLabel("Author:"));
            panel.add(author);
            int result = JOptionPane.showConfirmDialog(null, panel, "Mark A Book",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                gui.markBookAsRead(title.getText(), author.getText());
            } else {
                System.out.println("Cancelled");
            }
        }
    }
}
