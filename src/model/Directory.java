package model;

import java.util.ArrayList;
import java.util.List;

public class Directory {

    private String name;
    private String path;
    private List<MediaFile> mediaFiles = new ArrayList<>();
    private List<Directory> subdirectories = new ArrayList<>();

    public Directory() {
    }

    public Directory(String name, String path, List<MediaFile> mediaFiles, List<Directory> subdirectories) {
        this.name = name;
        this.path = path;
        this.mediaFiles = mediaFiles;
        this.subdirectories = subdirectories;
    }

    public Directory(String name, String path, List<MediaFile> mediaFiles) {
        this.name = name;
        this.path = path;
        this.mediaFiles = mediaFiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void addMediaFile(MediaFile mediaFile) {
        this.mediaFiles.add(mediaFile);
    }

    public List<Directory> getSubdirectories() {
        return subdirectories;
    }

    public void addSubdirectory(Directory subdirectory) {
        this.subdirectories.add(subdirectory);
    }

    @Override
    public String toString() {
        return "Directory{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", mediaFiles=" + mediaFiles +
                ", subdirectories=" + subdirectories +
                '}';
    }
}