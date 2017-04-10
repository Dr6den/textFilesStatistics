package com.gmail.dr6den.words.statistic;

import com.gmail.dr6den.words.statistic.entity.Statistics;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author andrew
 */
public class StatisticsCalculatorTest {
    
    public StatisticsCalculatorTest() {
    }
    
    @Test
    public void statisticsCalculateTest() throws IOException {
        Properties prop = TextFileReader.readPropertiesFile();        
        List<String> lines = TextFileReader.readTextFile(prop.getProperty("defaultPath"));
        List<Statistics> statistics = StatisticsCalculator.calculateStatistics(lines);
        assertNotNull(statistics);
        assertEquals(statistics.size(), 10);
        assertEquals(statistics.stream().mapToInt((Statistics stat) -> stat.getLength()).sum(),
                lines.stream().mapToInt((String s) -> s.length()).sum());
        
        Statistics sta0 = statistics.get(0);
        assertEquals(Double.compare(sta0.getAverageWordLength(), 5.5), 0);
        assertEquals(sta0.getLongestWord(), "\"intellectual\"");
        assertEquals(sta0.getShortestWord(), "to");
        
        Statistics sta8 = statistics.get(8);
        assertEquals(Double.compare(sta8.getAverageWordLength(), 4.277777777777778), 0);
        assertEquals(sta8.getLongestWord(), "representative");
        assertEquals(sta8.getShortestWord(), "a");
        
        Statistics sta9 = statistics.get(9);
        assertEquals(Double.compare(sta9.getAverageWordLength(), 0), 0);
        assertEquals(sta9.getLongestWord(), "");
        assertEquals(sta9.getShortestWord(), "");
    }
}
