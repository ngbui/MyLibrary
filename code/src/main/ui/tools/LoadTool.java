package ui.tools;

import ui.ReadingListGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// EFFECTS: creates a new LoadTool with the given ReadingListGUI and parent.
public class LoadTool extends Tool {
    public LoadTool(ReadingListGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // MODIFIES: this
    // EFFECTS: creates new load button, enable button, and adds to parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Load Reading List");
        addToParent(parent);
        button.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new LoadToolClickHandler());
    }

    private class LoadToolClickHandler implements ActionListener {

        // MODIFIES: gui(readingList)
        // EFFECTS: when button is pressed, play a sound and load the reading list from file into readingList
        @Override
        public void actionPerformed(ActionEvent e) {
            playSound("./data/correct.wav");
            gui.loadReadingList();
        }
    }
}
