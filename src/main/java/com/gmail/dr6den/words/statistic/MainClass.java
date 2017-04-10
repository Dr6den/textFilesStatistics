package com.gmail.dr6den.words.statistic;

import com.gmail.dr6den.words.statistic.entity.Statistics;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrew
 */
public class MainClass {
    public static void main(String[] args) {
        try {
            String path = null;
            if (args.length == 0) {
                Properties prop = TextFileReader.readPropertiesFile();
                path = prop.getProperty("defaultPath");
            } else {
                path = args[0];
            }
            
            List<String> lines = TextFileReader.readTextFile(path);
            List<Statistics> statistics = StatisticsCalculator.calculateStatistics(lines);
            StatisticsCalculator.storeResultsToMongoDatabase(statistics);
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
