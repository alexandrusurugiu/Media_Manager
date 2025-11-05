package service.implementation;

import exception.StatisticsSavingException;
import java.io.FileWriter;
import java.io.IOException;
import model.Statistic;
import service.StatisticService;

public class StatisticServiceImpl implements StatisticService {

    private final Statistic statistic;

    private final static String STATISTICS_FILE = "src//resource/statistics.txt";

    public StatisticServiceImpl() {
        this.statistic = new Statistic();
    }

    @Override
    public void incrementMediaFilesCreated() {
        statistic.getAggregateCounts()[0]++;
    }

    @Override
    public void incrementMediaFilesDeleted() {
        statistic.getAggregateCounts()[1]++;
    }

    @Override
    public void incrementDirectoriesCreated() {
        statistic.getAggregateCounts()[2]++;
    }

    @Override
    public void incrementDirectoriesDeleted() {
        statistic.getAggregateCounts()[3]++;
    }

    @Override
    public void setTotalMediaFiles(long totalMediaFiles) {
        statistic.setTotalMediaFiles(totalMediaFiles);
    }

    @Override
    public void setTotalDirectories(long totalDirectories) {
        statistic.setTotalDirectories(totalDirectories);
    }

    @Override
    public String getStatisticsReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Statistici pentru fisiere media si foldere ---\n\n");
        sb.append(String.format("Fisiere media create: %d\n", statistic.getMediaFilesCreatedCount()));
        sb.append(String.format("Fisiere media sterse: %d\n", statistic.getMediaFilesDeletedCount()));
        sb.append(String.format("Foldere create: %d\n", statistic.getDirectoriesCreatedCount()));
        sb.append(String.format("Foldere sterse: %d\n", statistic.getDirectoriesDeletedCount()));
        sb.append(String.format("Total fisiere media: %d\n", statistic.getTotalMediaFiles()));
        sb.append(String.format("Total foldere: %d\n", statistic.getTotalDirectories()));

        sb.append("\n--- Fisiere media dupa tip ---\n");
        boolean filesFound = false;
        String[] predefinedMediaTypes = statistic.getPredefinedMediaTypes();
        int[][] mediaFileTypeMatrix = statistic.getMediaFileTypeMatrix();

        for (int i = 0; i < predefinedMediaTypes.length; i++) {
            if (mediaFileTypeMatrix[i][0] > 0 || predefinedMediaTypes[i].equals("other")) {
                filesFound = true;
                sb.append(String.format("%s: %d\n", predefinedMediaTypes[i], mediaFileTypeMatrix[i][0]));
            }
        }
        if (!filesFound && statistic.getTotalMediaFiles() == 0) {
            sb.append("Nu au fost gasite fisiere media.\n");
        }

        return sb.toString();
    }

    @Override
    public void incrementMediaFileTypeCount(String fileType) {
        statistic.incrementMediaFileTypeCount(fileType);
    }

    @Override
    public void decrementMediaFileTypeCount(String fileType) {
        statistic.decrementMediaFileTypeCount(fileType);
    }

    @Override
    public Statistic getStatistic() {
        return statistic;
    }

    @Override
    public void saveStatisticsToTextFile() throws StatisticsSavingException {
        String report = this.getStatisticsReport();

        try (FileWriter fileWriter = new FileWriter(STATISTICS_FILE, false)) {
                fileWriter.write(report);
        } catch (IOException e) {
            throw new StatisticsSavingException("Nu s-au putut salva statisticile in fisierul text!");
        }

    }
}
