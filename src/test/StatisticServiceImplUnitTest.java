package test;

import model.Statistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.implementation.StatisticServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class StatisticServiceImplUnitTest {

    private StatisticServiceImpl statisticService;

    @BeforeEach
    void setUp() {
        statisticService = new StatisticServiceImpl();
    }

    @Test
    void testIncrementCounters() {
        statisticService.incrementDirectoriesCreated();
        statisticService.incrementDirectoriesCreated();
        statisticService.incrementMediaFilesCreated();

        Statistic stats = statisticService.getStatistic();

        assertEquals(2, stats.getDirectoriesCreatedCount());
        assertEquals(1, stats.getMediaFilesCreatedCount());
    }

    @Test
    void testMediaFileTypeCounting() {
        statisticService.incrementMediaFileTypeCount("mp3");
        statisticService.incrementMediaFileTypeCount("mp3");
        statisticService.incrementMediaFileTypeCount("jpg");

        String report = statisticService.getStatisticsReport();

        assertTrue(report.contains("mp3: 2"));
        assertTrue(report.contains("jpg: 1"));
    }

    @Test
    void testReportGenerationStructure() {
        statisticService.setTotalMediaFiles(10);
        statisticService.setTotalDirectories(5);

        String report = statisticService.getStatisticsReport();

        assertNotNull(report);
        assertTrue(report.contains("Total fisiere media: 10"));
        assertTrue(report.contains("Total foldere: 5"));
    }
}