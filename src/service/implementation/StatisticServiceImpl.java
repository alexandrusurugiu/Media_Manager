package service.implementation;

import model.Statistic;
import service.StatisticService;

public class StatisticServiceImpl implements StatisticService {

    private final Statistic statistic;

    public StatisticServiceImpl() {
        this.statistic = new Statistic();
    }

    @Override
    public Statistic getStatistics() {
        return statistic;
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
}
