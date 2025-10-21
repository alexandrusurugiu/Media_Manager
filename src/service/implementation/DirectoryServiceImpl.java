package service.implementation;

import exceptions.AlreadyExistingDirectory;
import model.Directory;
import service.DirectoryService;
import service.MediaFileService;

import java.util.ArrayList;
import java.util.List;

public class DirectoryServiceImpl implements DirectoryService {

    private final List<Directory> directories = new ArrayList<>();

    private final MediaFileService mediaFileService;

    public DirectoryServiceImpl(MediaFileService mediaFileService) {
        this.mediaFileService = mediaFileService;
    }

    @Override
    public Directory createDirectory(String path) throws AlreadyExistingDirectory {

        if (path ==  null || path.trim().isEmpty()) {
            return null;
        }

        String[] components = path.split("/");
        StringBuilder currentPath = new StringBuilder();
        Directory lastDirectory = null;
        Directory parent = null;

        for (int i = 0; i < components.length; i++) {
            String component = components[i];
            if (component == null || component.isEmpty()) {
                continue;
            }

            if (mediaFileService.isMediaFile(component)) {
                break;
            }

            currentPath.append(component).append("/");
            String dirPath = currentPath.toString();

            Directory existing = getDirectoryByPath(dirPath);
            if (existing != null) {
                if (parent != null && !parent.getSubdirectories().contains(existing)) {
                    parent.addSubdirectory(existing);
                }

                lastDirectory = existing;
                parent = existing;
            } else {
                Directory newDirectory = new Directory();
                newDirectory.setName(component);
                newDirectory.setPath(dirPath);
                saveDirectory(newDirectory);

                if (parent != null) {
                    parent.addSubdirectory(newDirectory);
                }

                lastDirectory = newDirectory;
                parent = newDirectory;

            }
        }

        return lastDirectory;
    }

    public Directory getDirectoryByPath(String path) {

        if (path == null) {
            return null;
        }

        for (Directory directory : directories) {
            if (directory.getPath().equals(path)) {
                return directory;
            }
        }

        return null;
    }

    @Override
    public void saveDirectory(Directory directory) throws AlreadyExistingDirectory {

        for (Directory currentDirectory : directories) {
            if ((currentDirectory.getPath().equals(directory.getPath())) && currentDirectory.getName().equals(directory.getName())) {
                throw new AlreadyExistingDirectory("Directorul " + directory.getName() + " exista deja in path-ul " + directory.getPath());
            }
        }

        directories.add(directory);
    }

    @Override
    public void deleteDirectory(Directory directory) {

        directories.remove(directory);
    }

    @Override
    public Directory getMediaFile(String directoryName) {

        for (Directory directory : directories) {
            if (directory.getName().equals(directoryName)) {
                return directory;
            }
        }

        return null;
    }

    @Override
    public List<Directory> getAllDirectories() {

        return directories;
    }
}