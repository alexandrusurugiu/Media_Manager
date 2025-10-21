import gui.GUI;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

//        MediaFileService mediaFileService = new MediaFileServiceImpl();
//        DirectoryService directoryService = new DirectoryServiceImpl(mediaFileService);
//        TextFileService textFileService = new TextFileServiceImpl(directoryService, mediaFileService);
//        List<String> paths = textFileService.loadFilesFromTextFile();
//
//        //Testez ca pathurile sunt preluate bine din fisiere
//        System.out.println("------Paths importate din fisier------");
//        for (String path : paths) {
//            System.out.println(path);
//        }
//
//        //Testez fisierele media create
//        System.out.println("\n------Lista de fisiere media------");
//        List<MediaFile> mediaFiles = mediaFileService.getAllMediaFiles();
//        mediaFiles.forEach(System.out::println);
//
//        //Testez directoarele create
//        System.out.println("\n------Lista de directoare------");
//        List<Directory> directories = directoryService.getAllDirectories();
//        directories.forEach(System.out::println);
//
//        //Testez cautarea fisierului audio
//        System.out.println("\n------Fisier media cautat------");
//        MediaFile searchedMediaFile = mediaFileService.getMediaFile("minimDoi");
//        System.out.println(searchedMediaFile);
//
//        //Testez stergerea fisierului peisaj
//        System.out.println("\n------Lista de fisiere actualizata------");
//        MediaFile toBeDeleted = mediaFileService.getMediaFile("song.mp3");
//        mediaFileService.deleteMediaFile(toBeDeleted);
//        List<MediaFile> mediaFilesUpdated = mediaFileService.getAllMediaFiles();
//        mediaFilesUpdated.forEach(System.out::println);
//
//        //Testez salvarea noilor pathuri in fisierul text
//        mediaFileService.createMediaFile("D:/ProgramFiles/RockstarGames/trailer.mp4");
//        mediaFileService.createMediaFile("D:/ProgramFilesX86/CommonFiles/minimDoi.mp3");
//        mediaFileService.createMediaFile("C:/Root/Documents/Projects/Java/src");
//        mediaFileService.createMediaFile("C:/User/Desktop/Temp/Backup");
//        mediaFileService.createMediaFile("C:/Users/Public/Pictures/photo.jpg");
//        mediaFileService.createMediaFile("D:/Media/Music");
//        mediaFileService.createMediaFile("D:/Media/Music/playlist.m3u");
//        mediaFileService.createMediaFile("D:/Media/Music/song.wav");
//        mediaFileService.createMediaFile("C:/Root/Music/Rock/Album/song.mp3");
//        textFileService.saveFilesToTextFile(mediaFileService.getAllMediaFiles());

        GUI gui = new GUI();
        gui.show();
    }
}