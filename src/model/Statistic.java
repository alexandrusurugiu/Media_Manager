package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Statistic implements Serializable {

    private static final long serialVersionUID = 1L;

    private long[] aggregateCounts;

    private int[][] mediaFileTypeMatrix;

    private Map<String, Integer> mediaTypeToIndexMap;
    private String[] predefinedMediaTypes;

    public Statistic() {
        this.aggregateCounts = new long[6];
        this.predefinedMediaTypes = new String[]{"mp3", "wav", "jpg", "jpeg", "png", "mp4", "other"};
        this.mediaTypeToIndexMap = new HashMap<>();
        initializeMediaTypeMap();
        this.mediaFileTypeMatrix = new int[predefinedMediaTypes.length][1];
    }

    private void initializeMediaTypeMap() {
        mediaTypeToIndexMap.put("mp3", 0);
        mediaTypeToIndexMap.put("wav", 1);
        mediaTypeToIndexMap.put("jpg", 2);
        mediaTypeToIndexMap.put("jpeg", 3);
        mediaTypeToIndexMap.put("png", 4);
        mediaTypeToIndexMap.put("mp4", 5);
        mediaTypeToIndexMap.put("other", 6);
    }

    public long[] getAggregateCounts() {
        return aggregateCounts;
    }

    public void setAggregateCounts(long[] aggregateCounts) {
        this.aggregateCounts = aggregateCounts;
    }

    public int[][] getMediaFileTypeMatrix() {
        return mediaFileTypeMatrix;
    }

    public void setMediaFileTypeMatrix(int[][] mediaFileTypeMatrix) {
        this.mediaFileTypeMatrix = mediaFileTypeMatrix;
    }

    public Map<String, Integer> getMediaTypeToIndexMap() {
        return mediaTypeToIndexMap;
    }

    public void setMediaTypeToIndexMap(Map<String, Integer> mediaTypeToIndexMap) {
        this.mediaTypeToIndexMap = mediaTypeToIndexMap;
    }

    public String[] getPredefinedMediaTypes() {
        return predefinedMediaTypes;
    }

    public void setPredefinedMediaTypes(String[] predefinedMediaTypes) {
        this.predefinedMediaTypes = predefinedMediaTypes;
        initializeMediaTypeMap();
    }

    public long getMediaFilesCreatedCount() {
        return aggregateCounts[0];
    }

    public long getMediaFilesDeletedCount() {
        return aggregateCounts[1];
    }

    public long getDirectoriesCreatedCount() {
        return aggregateCounts[2];
    }

    public long getDirectoriesDeletedCount() {
        return aggregateCounts[3];
    }

    public long getTotalMediaFiles() {
        return aggregateCounts[4];
    }

    public long getTotalDirectories() {
        return aggregateCounts[5];
    }

    public void setTotalMediaFiles(long totalMediaFiles) {
        this.aggregateCounts[4] = totalMediaFiles;
    }

    public void setTotalDirectories(long totalDirectories) {
        this.aggregateCounts[5] = totalDirectories;
    }

    public int getMediaFileTypeCount(String type) {
        Integer index = mediaTypeToIndexMap.get(type.toLowerCase());
        if (index != null && index < predefinedMediaTypes.length) {
            return mediaFileTypeMatrix[index][0];
        }
        return 0;
    }
}
