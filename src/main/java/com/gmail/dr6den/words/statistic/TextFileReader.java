package com.gmail.dr6den.words.statistic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author andrew
 */
public class TextFileReader {
    public static String propPath = "src/main/resources/customization.properties";
    
    public static List<String> readTextFile(String fullPathToTextFile) throws IOException {
        Path textFile = Paths.get(fullPathToTextFile);
        return Files.readAllLines(textFile);
    }
    
    public static Properties readPropertiesFile() throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream(propPath);
        prop.load(input);
        return prop;	
    }
}
