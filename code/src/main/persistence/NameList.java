package persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

// Represent a list of past users' names
public class NameList {
    private String source;
    private ArrayList<String> names;

    /*
     * EFFECTS: create new NameList object, with source of the name list set to source
     *
     */
    public NameList(String source) {
        this.source = source;
    }

    // MODIFIES: this
    // EFFECTS: writes text representation of a name list to file
    // throws IOException if an error occurs writing data to file
    public void saveListIntoFile(ArrayList<String> arr) throws IOException {
        PrintWriter writer = new PrintWriter(source);
        for (String str : arr) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
    }

    // EFFECTS: reads name list from file and returns an array list with a list of names on the name list;
    // throws IOException if an error occurs reading data from file
    public ArrayList<String> readFileIntoList() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(source));
        names = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (line != null) {
            names.add(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return names;
    }
}

