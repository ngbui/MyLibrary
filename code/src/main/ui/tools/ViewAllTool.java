package ui.tools;

import ui.ReadingListGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// EFFECTS: creates a new ViewAllTool with the given ReadingListGUI and parent.
public class ViewAllTool extends Tool {
    public ViewAllTool(ReadingListGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // MODIFIES: this
    // EFFECTS: creates new view all books button, enable button, and adds to parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("View all books");
        addToParent(parent);
        button.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new ViewAllTool.ViewAllToolClickHandler());
    }

    private class ViewAllToolClickHandler implements ActionListener {
        // MODIFIES: gui(textArea)
        // EFFECTS: when button pressed, play a sound and display all the books on the reading list
        @Override
        public void actionPerformed(ActionEvent e) {
            playSound("./data/jazzy-piano.wav");
            if (gui.readingList.getBooksList().isEmpty()) {
                gui.emptyReadingList();
            } else {
                gui.viewAllBook();
            }
        }
    }
}
