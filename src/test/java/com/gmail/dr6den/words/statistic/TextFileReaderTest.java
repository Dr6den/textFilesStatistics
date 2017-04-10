
package com.gmail.dr6den.words.statistic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author andrew
 */
public class TextFileReaderTest {
    String text = "The narrator, Sal Paradise, starts to tell the story: he, with his \"intellectual\" friends,\n"
            + "was a young writer in New York City in the winter of 1947, depressed and bored, when Dean Moriarty\n"
            + "arrived in New York City. Dean has just gotten out of reform school, just married a pretty young blonde,\n"
            + "Marylou, and they have come to New York City for the first time, from Denver. Sal heard of Dean before \n"
            + "from Chad King and was intrigued--Dean used to write Chad from jail, asking questions about Nietzsche.\n"
            + "Sal and his friends go to see Dean and Marylou in a dumpy flat in Spanish Harlem. Dean comes to the door\n"
            + "in his shorts; he is occupied with Marylou, and he has to make explanations to her. Dean is frenetic, hyper,\n"
            + "and full of ideas. He speaks formally, in long, rambling sentences. Sal's first impression of Dean is that he\n"
            + "is like a young Gene Autry, a real representative of the West. They drink and talk until dawn.\n"
            + " ";
    
    public TextFileReaderTest() {
    }
    
    @Before
    public void setUp() {
        String[] splittedText = text.split("\n");
        List<String> textList = Arrays.asList(splittedText);
        try {
            Properties prop = TextFileReader.readPropertiesFile();
            Path textFile = Paths.get(prop.getProperty("defaultPath"));
            Files.deleteIfExists(textFile);
            Files.createFile(textFile);
            Files.write(textFile, textList);
        } catch (IOException ex) {
            Logger.getLogger(TextFileReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void readFileTest() throws IOException {
        Properties prop = TextFileReader.readPropertiesFile();        
        List<String> someText = TextFileReader.readTextFile(prop.getProperty("defaultPath"));
        assertEquals(someText.size(), 10);
    }
    
    @Test
    public void readPropertiesFileTest() throws IOException {
        Properties prop = TextFileReader.readPropertiesFile();
        assertEquals(prop.getProperty("defaultPath"),"src/main/resources/input.txt");
    }
}
