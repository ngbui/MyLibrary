package persistence;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class NameListTest {
    public NameList testNameList;
    public ArrayList<String> listofnames;

    @Test
    void testSaveListIntoInvalidFile() {
        try {
            listofnames = new ArrayList<>();
            testNameList = new NameList("./data/my\0illegal:fileName.txt");
            testNameList.saveListIntoFile(listofnames);
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testSaveListIntoFileEmpty(){
        listofnames = new ArrayList<>();
        assertTrue(listofnames.isEmpty());
        testNameList = new NameList("./data/testSaveNameListEmpty.txt");
        try {
            testNameList.saveListIntoFile(listofnames);
            ArrayList<String> test = testNameList.readFileIntoList();
            assertTrue(test.isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

    }

    @Test
    public void testSaveListIntoFileNotEmpty() {
        listofnames = new ArrayList<>();
        assertTrue(listofnames.isEmpty());
        listofnames.add("Alvin");
        listofnames.add("Kevin");
        listofnames.add("Crystal");
        listofnames.add("Sunny");
        testNameList = new NameList("./data/testSaveNameListGeneral.txt");
        try {
            testNameList.saveListIntoFile(listofnames);
            ArrayList<String> test = testNameList.readFileIntoList();
            assertFalse(test.isEmpty());
            assertEquals(test.size(),4);
            assertEquals(test.get(0),"Alvin");
            assertEquals(test.get(1),"Kevin");
            assertEquals(test.get(2),"Crystal");
            assertEquals(test.get(3),"Sunny");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testReadFileIntoListNonExistentFile() {
        testNameList = new NameList("./data/noSuchFile.txt");
        try {
            listofnames = testNameList.readFileIntoList();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReadFileIntoListEmpty()  {
        testNameList = new NameList("./data/testReadNameListEmpty.txt");
        try {
            listofnames = testNameList.readFileIntoList();
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
        assertTrue(listofnames.isEmpty());
    }

    @Test
    public void testReadFileIntoListNotEmpty() {
        testNameList = new NameList("./data/testReadNameListGeneral.txt");
        try {
            listofnames = testNameList.readFileIntoList();
            assertFalse(listofnames.isEmpty());
            assertEquals(listofnames.size(),5);
            assertEquals(listofnames.get(0),"Sammy");
            assertEquals(listofnames.get(1),"Kristen");
            assertEquals(listofnames.get(2),"John");
            assertEquals(listofnames.get(3),"Alex");
            assertEquals(listofnames.get(4),"Corina");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
