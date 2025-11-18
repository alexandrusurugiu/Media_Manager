package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.Directory;
import model.MediaFile;
import service.DirectoryService;
import service.MediaFileService;
import service.SourceService;
import service.StatisticService;
import service.implementation.DirectoryServiceImpl;
import service.implementation.MediaFileServiceImpl;
import service.implementation.SourceServiceImpl;
import service.implementation.StatisticServiceImpl;

public class GUI {

    private final JFrame mainFrame;
    private final JFrame mediaFilesFrame;
    private final JFrame directoriesFrame;
    private final JFrame statsFrame;

    private final JButton addButton;
    private final JButton filesButton;
    private final JButton directoriesButton;
    private final JButton deleteButton;
    private final JButton statsButton;

    private final JTextArea pathsTextArea;
    private final JTextArea processingTextArea;
    private final JTextArea statsTextArea;

    private StatisticService statisticService = new StatisticServiceImpl();
    private final MediaFileService mediaFileService = new MediaFileServiceImpl();
    private final DirectoryService directoryService = new DirectoryServiceImpl(mediaFileService);
    private final SourceService textFileService = new SourceServiceImpl(directoryService, mediaFileService);

    private List<String> pathsList = new ArrayList<>();
    List<MediaFile> mediaFiles = mediaFileService.getAllMediaFiles();
    List<Directory> directories = directoryService.getAllDirectories();

    private int selectedLineIndex = -1;

    public GUI() {

        mainFrame = new JFrame("Media Manager");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 800);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());

        pathsTextArea = new JTextArea();
        pathsTextArea.setEditable(false);
        pathsTextArea.setLineWrap(true);

        processingTextArea = new JTextArea();
        processingTextArea.setEditable(true);
        processingTextArea.setLineWrap(true);

        JScrollPane leftScrollPane = new JScrollPane(pathsTextArea);
        JScrollPane rightScrollPane = new JScrollPane(processingTextArea);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(1000, 60));

        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filesButton = new JButton("Files");
        filesButton.setIcon(new ImageIcon("src/icon/filesIcon.png"));
        filesButton.setPreferredSize(new Dimension(100, 30));
        directoriesButton = new JButton("Folders");
        directoriesButton.setIcon(new ImageIcon("src/icon/directoriesIcon.png"));
        directoriesButton.setPreferredSize(new Dimension(100, 30));
        statsButton = new JButton("Stats");
        statsButton.setIcon(new ImageIcon("src/icon/statsIcon.png"));
        statsButton.setPreferredSize(new Dimension(100, 30));
        topButtons.add(filesButton);
        topButtons.add(directoriesButton);
        topButtons.add(statsButton);
        topPanel.add(topButtons, BorderLayout.WEST);
        topPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 10));
        mainFrame.add(topPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(leftScrollPane);
        splitPane.setRightComponent(rightScrollPane);
        splitPane.setEnabled(false);
        splitPane.setDividerLocation(0.5);
        splitPane.setResizeWeight(0.5);
        splitPane.setPreferredSize(new Dimension(980, 650));
        mainFrame.add(splitPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(1000, 60));

        JPanel bottomButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 30));
        deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(100, 30));
        bottomButtons.add(addButton);
        bottomButtons.add(deleteButton);
        bottomPanel.add(bottomButtons, BorderLayout.EAST);
        bottomPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 10));
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        mainFrame.pack();

        mediaFilesFrame = new JFrame("Media Files");
        mediaFilesFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mediaFilesFrame.setSize(600, 400);
        mediaFilesFrame.setLocationRelativeTo(null);
        mediaFilesFrame.setLayout(new BorderLayout());

        directoriesFrame = new JFrame("Folders");
        directoriesFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        directoriesFrame.setSize(600, 400);
        directoriesFrame.setLocationRelativeTo(null);
        directoriesFrame.setLayout(new BorderLayout());

        statsFrame = new JFrame("Statistics");
        statsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        statsFrame.setSize(400, 300);
        statsFrame.setLocationRelativeTo(null);
        statsFrame.setLayout(new BorderLayout());

        statsTextArea = new JTextArea();
        statsTextArea.setEditable(false);
        statsTextArea.setLineWrap(true);
        JScrollPane statsScrollPane = new JScrollPane(statsTextArea);
        statsFrame.add(statsScrollPane, BorderLayout.CENTER);

        importDataFromTextFile();
        sendFileToEditingTab();
        setupAutoCommitForProcessingTextArea();
        saveFilesToTextFileWhenExitingFrame();
        deleteFile();
        addFile();
        openMediaFilesFrame();
        openDirectoriesFrame();
        openStatsFrameListener();
        rebuildModelsFromPaths();
    }

    private void refreshMediaFilesFrameContent() {

        mediaFilesFrame.getContentPane().removeAll();
        mediaFilesFrame.revalidate();

        rebuildModelsFromPaths();

        JPanel mediaFilesPanel = new JPanel();
        mediaFilesPanel.setLayout(new BoxLayout(mediaFilesPanel, BoxLayout.Y_AXIS));

        if (mediaFiles != null) {
            for (MediaFile mediaFile : mediaFiles) {
                JLabel label = new JLabel(mediaFile.getName() + '.' + mediaFile.getFileType() + " (" + mediaFile.getPath() + ')', JLabel.CENTER);
                setLabelIcon(mediaFile.getFileType(), label);
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                label.setIconTextGap(10);
                mediaFilesPanel.add(label);
                mediaFilesPanel.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane mediaFilesScrollPane = new JScrollPane(mediaFilesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mediaFilesScrollPane.setPreferredSize(new Dimension(400, 400));
        mediaFilesFrame.add(mediaFilesScrollPane, BorderLayout.CENTER);

        mediaFilesFrame.pack();
    }

    private void refreshDirectoriesFrameContent() {

        directoriesFrame.getContentPane().removeAll();
        directoriesFrame.revalidate();

        rebuildModelsFromPaths();

        JPanel directoriesPanel = new JPanel();
        directoriesPanel.setLayout(new BoxLayout(directoriesPanel, BoxLayout.Y_AXIS));

        if (directories != null) {
            for (Directory directory : directories) {
                JLabel label = new JLabel(directory.getName() + " (" + directory.getPath() + ')', new ImageIcon("src/icon/folder.png"), JLabel.LEFT);
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                label.setIconTextGap(10);
                directoriesPanel.add(label);
                directoriesPanel.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane directoriesScrollPane = new JScrollPane(directoriesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        directoriesScrollPane.setPreferredSize(new Dimension(400, 400));
        directoriesFrame.add(directoriesScrollPane, BorderLayout.CENTER);

        directoriesFrame.pack();
    }


    private void setLabelIcon(String extension, JLabel label) {
        switch (extension) {
            case "mp3" -> label.setIcon(new ImageIcon("src/icon/mp3Icon.png"));
            case "wav" -> label.setIcon(new ImageIcon("src/icon/wavIcon.png"));
            case "jpg", "jpeg" -> label.setIcon(new ImageIcon("src/icon/jpgIcon.png"));
            case "png" -> label.setIcon(new ImageIcon("src/icon/pngIcon.png"));
            case "mp4" -> label.setIcon(new ImageIcon("src/icon/mp4Icon.png"));
            default -> label.setIcon(new ImageIcon("src/icon/filesIcon.png"));
        }
    }

    private void openDirectoriesFrame() {

        directoriesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshDirectoriesFrameContent();
                directoriesFrame.setVisible(true);
                directoriesFrame.toFront();
            }
        });
    }

    private void openMediaFilesFrame() {

        filesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshMediaFilesFrameContent();
                mediaFilesFrame.setVisible(true);
                mediaFilesFrame.toFront();
            }
        });
    }

    private void openStatsFrameListener() {
        statsButton.addActionListener(e -> {
            updateStatisticsDisplay();
            statsFrame.setVisible(true);
            statsFrame.toFront();
        });
    }

    private void updateStatisticsDisplay() {
        statsTextArea.setText(statisticService.getStatisticsReport());
    }

    private void addFile() {

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedLineIndex == -1) {
                    String pathToBeAdded = processingTextArea.getText().trim();
                    if (!pathToBeAdded.isEmpty()) {
                        pathsList.add(pathToBeAdded);
                        analyzePathToBeAdded(pathToBeAdded);
                        refreshPathsTextArea();
                        rebuildModelsFromPaths();
                    }
                }
                else {
                    updateSelectedPath();
                }
            }
        });
    }

    private void analyzePathToBeAdded(String path) {

        if (path == null || path.trim().isEmpty()) {
            return;
        }

        String[] components = path.split("/");
        StringBuilder sbPath = new StringBuilder();
        StringBuilder sbFile = new StringBuilder();

        for (int i = 0; i < components.length - 1; i++) {
            sbFile.append(components[i]).append('/');
        }


        for (String component : components) {
            if (component.isEmpty()) {
                continue;
            }

            sbPath.append(component);

            if (mediaFileService.isMediaFile(component)) {
                String fileName = mediaFileService.getFileNameWithoutExtension(component);
                String fileType = mediaFileService.getFileExtension(component);
                boolean found = false;

                for (MediaFile mediaFile : mediaFiles) {
                    if (mediaFile.getName().equals(fileName) && mediaFile.getPath().contentEquals(sbFile)) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    statisticService.incrementMediaFilesCreated();
                    statisticService.incrementMediaFileTypeCount(fileType);
                }
            } else {
                sbPath.append("/");
                boolean found = false;

                for (Directory directory : directories) {
                    if (directory.getName().equals(component) && directory.getPath().contentEquals(sbPath)) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    statisticService.incrementDirectoriesCreated();
                }
            }
        }
    }

    private void deleteFile() {

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedLineIndex >= 0 && selectedLineIndex < pathsList.size()) {
                    analyzePathToBeDeleted(pathsList.get(selectedLineIndex));
                    pathsList.remove(selectedLineIndex);
                    selectedLineIndex = -1;
                    processingTextArea.setText("");
                    refreshPathsTextArea();
                    rebuildModelsFromPaths();
                }
            }
        });
    }

    private void analyzePathToBeDeleted(String path) {

        if (path.trim().isEmpty()) {
            return;
        }

        String[] components = path.split("/");
        StringBuilder sbPath = new StringBuilder();
        StringBuilder sbFile = new StringBuilder();

        for (int i = 0; i < components.length - 1; i++) {
            sbFile.append(components[i]).append('/');
        }


        for (String component : components) {
            if (component.isEmpty()) {
                continue;
            }

            sbPath.append(component);

            if (mediaFileService.isMediaFile(component)) {
                String fileName = mediaFileService.getFileNameWithoutExtension(component);
                String fileType = mediaFileService.getFileExtension(component);
                boolean found = false;

                for (MediaFile mediaFile : mediaFiles) {
                    if (mediaFile.getName().equals(fileName) && mediaFile.getPath().contentEquals(sbFile)) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    statisticService.incrementMediaFilesDeleted();
                    statisticService.decrementMediaFileTypeCount(fileType);
                }
            } else {
                sbPath.append("/");
                boolean found = false;

                for (Directory directory : directories) {
                    if (directory.getName().equals(component) && directory.getPath().contentEquals(sbPath)) {
                        if (directory.getName().length() <= 2) {
                            continue;
                        }

                        found = true;
                        break;
                    }
                }

                if (found) {
                    statisticService.incrementDirectoriesDeleted();
                }
            }
        }
    }

    private void updateSelectedPath() {

        if (selectedLineIndex >= 0 && selectedLineIndex < pathsList.size()) {
            String updatedText = processingTextArea.getText().trim();
            String oldText = pathsList.get(selectedLineIndex);
            if (!updatedText.equals(oldText)) {
                pathsList.set(selectedLineIndex, updatedText);
                refreshPathsTextArea();
                rebuildModelsFromPaths();
            }
        }
    }

    private void refreshPathsTextArea() {

        SwingUtilities.invokeLater(() -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pathsList.size(); i++) {
                sb.append(pathsList.get(i));
                if (i < pathsList.size() - 1) {
                    sb.append(System.lineSeparator());
                }
            }
            pathsTextArea.setText(sb.toString());

            mediaFiles.forEach(System.out::println);
        });
    }


    private void saveFilesToTextFileWhenExitingFrame() {

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    textFileService.saveFilesToTextFile(pathsList);
                    statisticService.saveStatisticsToTextFile();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mainFrame,"Eroare la salvarea datelor: " + ex.getMessage(),"Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void sendFileToEditingTab() {

        pathsTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextArea textArea = (JTextArea) e.getSource();
                int offset = textArea.viewToModel2D(e.getPoint());
                try {
                    int totalLength = textArea.getText().length();
                    if (offset >= totalLength) {
                        selectedLineIndex = -1;
                        processingTextArea.setText("");
                        return;
                    }

                    int line = textArea.getLineOfOffset(Math.max(0, Math.min(offset, Math.max(totalLength - 1, 0))));
                    if (line < 0 || line >= pathsList.size()) {
                        selectedLineIndex = -1;
                        processingTextArea.setText("");
                        return;
                    }

                    int startOffset = textArea.getLineStartOffset(line);
                    int endOffset = textArea.getLineEndOffset(line);
                    String lineText = textArea.getText(startOffset, endOffset - startOffset).trim();
                    processingTextArea.setText(lineText);
                    selectedLineIndex = line;
                } catch (Exception ex) {
                    selectedLineIndex = -1;
                    JOptionPane.showMessageDialog(mainFrame, "Eroare la selectarea liniei: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setupAutoCommitForProcessingTextArea() {

        final int debounceMs = 400;
        final Timer[] timerHolder = new Timer[1];

        processingTextArea.getDocument().addDocumentListener(new DocumentListener() {
            private void scheduleCommit() {
                if (timerHolder[0] != null && timerHolder[0].isRunning()) {
                    timerHolder[0].restart();
                    return;
                }
                timerHolder[0] = new Timer(debounceMs, e -> {
                    if (selectedLineIndex >= 0 && selectedLineIndex < pathsList.size()) {
                        String updatedText = processingTextArea.getText().trim();
                        if (!updatedText.equals(pathsList.get(selectedLineIndex))) {
                            pathsList.set(selectedLineIndex, updatedText);
                            refreshPathsTextArea();
                            rebuildModelsFromPaths();
                        }
                    }
                    timerHolder[0].stop();
                });
                timerHolder[0].setRepeats(false);
                timerHolder[0].start();
            }

            @Override public void insertUpdate(DocumentEvent e) {
                scheduleCommit();
            }

            @Override public void removeUpdate(DocumentEvent e) {
                scheduleCommit();
            }

            @Override public void changedUpdate(DocumentEvent e) {
                scheduleCommit();
            }
        });

        processingTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt) {
                if (selectedLineIndex >= 0 && selectedLineIndex < pathsList.size()) {
                    String updatedText = processingTextArea.getText().trim();
                    if (!updatedText.equals(pathsList.get(selectedLineIndex))) {
                        pathsList.set(selectedLineIndex, updatedText);
                        refreshPathsTextArea();
                        rebuildModelsFromPaths();
                    }
                }
            }
        });
    }

    private void importDataFromTextFile() {

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    List<String> importedPaths = textFileService.loadFilesFromTextFile();

                    if (importedPaths != null && !importedPaths.isEmpty()) {
                        pathsList.addAll(importedPaths);
                        refreshPathsTextArea();
                        rebuildModelsFromPaths();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mainFrame,"Eroare la incarcarea datelor: " + ex.getMessage(),"Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void show() {

        SwingUtilities.invokeLater(() -> mainFrame.setVisible(true));
    }

    private void rebuildModelsFromPaths() {

        if ((mediaFiles != null) && (directories != null)) {
            new ArrayList<>(mediaFiles).forEach(mediaFileService::deleteMediaFile);
            new ArrayList<>(directories).forEach(directoryService::deleteDirectory);
        }

        statisticService.getStatistic().resetMediaFileTypeCounts();
        List<String> pathsToRemove = new ArrayList<>();

        for (String path : pathsList) {
            if (path == null || path.trim().isEmpty()) {
                continue;
            }
            try {
                directoryService.createDirectory(path);
                mediaFileService.createMediaFile(path);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Eroare la crearea directorului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
                pathsToRemove.add(path);
                continue;
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Eroare la crearea fisierului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
                pathsToRemove.add(path);
                continue;
            }

            if (mediaFileService.isMediaFile(path)) {
                String fileType = mediaFileService.getFileExtension(path);
                statisticService.incrementMediaFileTypeCount(fileType);
            }
        }

        if (!pathsToRemove.isEmpty()) {
            pathsList.removeLast();
            refreshPathsTextArea();
        }

        mediaFiles = mediaFileService.getAllMediaFiles();
        directories = directoryService.getAllDirectories();
        statisticService.setTotalMediaFiles(mediaFiles.size());
        statisticService.setTotalDirectories(directories.size());
    }
}