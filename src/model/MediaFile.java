package model;

import java.util.Date;

public class MediaFile {

    private String name;
    private String path;
    private String fileType;
    private Date lastModified;

    public MediaFile() {
    }

    public MediaFile(String name, String path, String type, Date lastModified) {
        this.name = name;
        this.path = path;
        this.fileType = type;
        this.lastModified = lastModified;
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "MediaFile{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", fileType='" + fileType + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}
