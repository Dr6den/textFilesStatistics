package com.gmail.dr6den.words.statistic;

import com.gmail.dr6den.words.statistic.entity.Statistics;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author andrew
 */
public class StatisticsCalculator {
    public static List<Statistics> calculateStatistics(List<String> lines) {
        Stream<Statistics> statStream = lines.stream().map(Statistics::new);
        List<Statistics> statistics = statStream.map(StatisticsCalculator::setStatisticAttributes).collect(Collectors.toList());
        return statistics;
    }
    
    private static Statistics setStatisticAttributes(Statistics statistics) {
        statistics.setLength(statistics.getLine().length());
        String[] words = statistics.getLine().split(" ");
        if (words.length > 0) {
            final Comparator<String> stringLengthComparator = (String s1, String s2) -> Integer.compare(s1.length(), s2.length());
            statistics.setLongestWord((String) Stream.of(words).max(stringLengthComparator).get());
            statistics.setShortestWord((String) Stream.of(words).min(stringLengthComparator).get());
            statistics.setAverageWordLength(Stream.of(words).mapToInt((String s) -> s.length()).average().getAsDouble());
        } else {
            statistics.setLongestWord("");
            statistics.setShortestWord("");
            statistics.setAverageWordLength(0);
        }
        return statistics;
    }
    
    public static void storeResultsToMongoDatabase(List<Statistics> statistics) throws IOException {
        MongoClient mongoClient = null;
        try {
            Properties prop = TextFileReader.readPropertiesFile();
            mongoClient = new MongoClient(prop.getProperty("databaseHost"), Integer.parseInt(prop.getProperty("databasePort")));
            DB db = mongoClient.getDB(prop.getProperty("databaseName"));
            DBCollection table = db.getCollection("statistics");
            
            BasicDBObject document = new BasicDBObject();
            List<BasicDBObject> lines = new ArrayList<>();
            for (Statistics sta:statistics) {
                BasicDBObject lineDocument = new BasicDBObject();
                lineDocument.put("line", sta.getLine());
                lineDocument.put("lengs_of_line", sta.getLength());
                lineDocument.put("longest_word", sta.getLongestWord());
                lineDocument.put("shortest_word", sta.getShortestWord());
                lineDocument.put("average_word_length", sta.getAverageWordLength());
                lines.add(lineDocument);
            }
            document.put("lines", lines);
            table.insert(document);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(StatisticsCalculator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mongoClient.close();
        }
    }
}
