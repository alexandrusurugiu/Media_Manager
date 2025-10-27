package service.implementation;

import exception.FilesLoadingException;
import exception.FilesSavingException;
import model.Directory;
import model.MediaFile;
import service.DirectoryService;
import service.MediaFileService;
import service.TextFileService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextFileServiceImpl implements TextFileService {

    private static final String PATHS_FILE = "src//resource/paths.txt";

    private List<Directory> directories;

    private MediaFileService mediaFileService;

    private DirectoryService directoryService;

    public TextFileServiceImpl(DirectoryService directoryService, MediaFileService mediaFileService) {
        this.directoryService = directoryService;
        this.mediaFileService = mediaFileService;
        this.directories = directoryService.getAllDirectories();
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

    public List<String> loadFilesFromTextFile() throws FilesLoadingException {

        List<String> pathLinesToBeDisplayed = new ArrayList<>();
        Map<String, Directory> directoryMap = new HashMap<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATHS_FILE))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                pathLinesToBeDisplayed.add(line);

                Directory parentDirectory = directoryService.createDirectory(line);

                String[] pathComponents = line.split("/");
                StringBuilder currentPath = new StringBuilder();
                for (int i = 0; i < pathComponents.length - 1; i++) {
                    String component = pathComponents[i];
                    if (component == null || component.isEmpty()) {
                        continue;
                    }

                    currentPath.append(component).append("/");
                    String dirPath = currentPath.toString();

                    Directory directory = directoryService.getDirectoryByPath(dirPath);
                    if (directory != null) {
                        directoryMap.putIfAbsent(dirPath, directory);
                    }
                }

                MediaFile mediaFile = mediaFileService.createMediaFile(line);
                if (mediaFile != null && mediaFile.getName() != null && parentDirectory != null) {
                    parentDirectory.addMediaFile(mediaFile);
                }
            }
        } catch (IOException ex) {
            throw new FilesLoadingException("Nu s-au putut incarca path-urile din fisierul text!");
        }

        return pathLinesToBeDisplayed;
    }

    @Override
    public void saveFilesToTextFile(List<String> pathsToBeSaved) throws FilesSavingException {
        try (FileWriter fileWriter = new FileWriter(PATHS_FILE, false)) {
            for (String path : pathsToBeSaved) {
                fileWriter.write(path + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new FilesSavingException("Nu s-au putut salva noile path-uri in fisierul text!");
        }
    }
}