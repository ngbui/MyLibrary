package ui.tools;

import ui.ReadingListGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// EFFECTS: creates a new SaveTool with the given ReadingListGUI and parent.
public class SaveTool extends Tool {
    public SaveTool(ReadingListGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // MODIFIES: this
    // EFFECTS: creates new save button, enable button, and adds to parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Save Reading List");
        addToParent(parent);
        button.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new SaveToolClickHandler());
    }

    private class SaveToolClickHandler implements ActionListener {

        // EFFECTS: when button is pressed, play a sound and save the reading list to file
        @Override
        public void actionPerformed(ActionEvent e) {
            playSound("./data/win.wav");
            gui.saveReadingList();
        }
    }
}
