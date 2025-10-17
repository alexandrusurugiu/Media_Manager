import exceptions.FilesLoadingException;
import exceptions.FilesSavingException;
import model.MediaFile;
import service.implementations.MediaFileServiceImpl;
import service.implementations.TextFileServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) throws FilesLoadingException, FilesSavingException {

        MediaFileServiceImpl mediaFileServiceImpl = new MediaFileServiceImpl();
        TextFileServiceImpl textFileServiceImpl = new TextFileServiceImpl(mediaFileServiceImpl);
        List<String> paths = textFileServiceImpl.loadFilesFromTextFile();

        //Testez ca pathurile sunt preluate bine din fisiere
        System.out.println("------Paths importate din fisier------");
        for (String path : paths) {
            System.out.println(path);
        }

        //Testez fisierele media create
        System.out.println("\n------Lista de fisiere media------");
        List<MediaFile> mediaFiles = mediaFileServiceImpl.getAllMediaFiles();
        mediaFiles.forEach(System.out::println);

        //Testez cautarea fisierului audio
        System.out.println("\n------Fisier media cautat------");
        MediaFile searchedMediaFile = mediaFileServiceImpl.getMediaFile("audio");
        System.out.println(searchedMediaFile);

        //Testez stergerea fisierului peisaj
        System.out.println("\n------Lista de fisiere actualizata------");
        MediaFile toBeDeleted = mediaFileServiceImpl.getMediaFile("trailer");
        mediaFileServiceImpl.deleteMediaFile(toBeDeleted);
        List<MediaFile> mediaFilesUpdated = mediaFileServiceImpl.getAllMediaFiles();
        mediaFilesUpdated.forEach(System.out::println);

        //Testez salvarea noilor pathuri in fisierul text
        MediaFile newMediaFile = mediaFileServiceImpl.createMediaFile("D:/ProgramFiles/RockstarGames/trailer.mp4");
        MediaFile newMediaFile2 = mediaFileServiceImpl.createMediaFile("D:/ProgramFilesX86/CommonFiles/minimDoi.mp3");
        MediaFile newMediaFile3 = mediaFileServiceImpl.createMediaFile("C:/Root/Documents/Projects/Java/src");
        MediaFile newMediaFile4 = mediaFileServiceImpl.createMediaFile("C:/User/Desktop/Temp/Backup");
        MediaFile newMediaFile5 = mediaFileServiceImpl.createMediaFile("C:/Users/Public/Pictures/photo.jpg");
        MediaFile newMediaFile6 = mediaFileServiceImpl.createMediaFile("D:/Media/Music");
        MediaFile newMediaFile7 = mediaFileServiceImpl.createMediaFile("D:/Media/Music/playlist.m3u");
        MediaFile newMediaFile8 = mediaFileServiceImpl.createMediaFile("D:/Media/Music/song.wav");
        MediaFile newMediaFile9 = mediaFileServiceImpl.createMediaFile("C:/Root/Music/Rock/Album/song.mp3");
        textFileServiceImpl.saveFilesToTextFile(mediaFileServiceImpl.getAllMediaFiles());
    }
}
