package service;

import exception.StatisticsSavingException;
import model.Statistic;

public interface StatisticService {

    /**
     * Increments the count of media files created by one.
     */
    void incrementMediaFilesCreated();

    /**
     * Increments the count of media files deleted by one.
     */
    void incrementMediaFilesDeleted();

    /**
     * Increments the count of directories created by one.
     */
    void incrementDirectoriesCreated();

    /**
     * Increments the count of directories deleted by one.
     */
    void incrementDirectoriesDeleted();

    /**
     * Sets the total number of media files currently stored.
     */
    void setTotalMediaFiles(long totalMediaFiles);

    /**
     * Sets the total number of directories currently stored.
     */
    void setTotalDirectories(long totalDirectories);

    /**
     * Generates a formatted string of all current statistics for display.
     *
     * @return A string containing all statistics.
     */
    String getStatisticsReport();

    /**
     * Increments the count of media files for a specific file type.
     *
     * @param fileType The type of media file to increment the count for.
     */
    void incrementMediaFileTypeCount(String fileType);

    /**
     * Decrements the count of media files for a specific file type.
     *
     * @param fileType The type of media file to decrement the count for.
     */
    void decrementMediaFileTypeCount(String fileType);

    /**
     * Resets all media file type counts to zero.
     *
     * @return The Statistic object containing all current statistics.
     */
    Statistic getStatistic();

    void saveStatisticsToTextFile() throws StatisticsSavingException;
}
