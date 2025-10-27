package gui;

import java.awt.BorderLayout;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import model.Directory;
import model.MediaFile;
import service.DirectoryService;
import service.MediaFileService;
import service.TextFileService;
import service.implementation.DirectoryServiceImpl;
import service.implementation.MediaFileServiceImpl;
import service.implementation.TextFileServiceImpl;

public class GUI {

    private final JFrame frame;

    private final JButton loadButton;

    private final JButton saveButton;

    private final JButton addButton;

    private final JButton deleteButton;

    private final JTextArea pathsTextArea;

    private final JTextArea processingTextArea;

    private MediaFileService mediaFileService = new MediaFileServiceImpl();

    private DirectoryService directoryService = new DirectoryServiceImpl(mediaFileService);

    private TextFileService textFileService = new TextFileServiceImpl(directoryService, mediaFileService);

    private List<String> pathsList = new ArrayList<>();

    List<MediaFile> mediaFiles = mediaFileService.getAllMediaFiles();

    List<Directory> directories = directoryService.getAllDirectories();

    private int selectedLineIndex = -1;

    public GUI() {

        frame = new JFrame("Media Manager");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setPreferredSize(new Dimension(1000, 60));

        loadButton = new JButton("Load");
        loadButton.setPreferredSize(new Dimension(100, 35));
        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 35));

        topPanel.add(loadButton);
        topPanel.add(saveButton);
        frame.add(topPanel, BorderLayout.NORTH);

        pathsTextArea = new JTextArea();
        pathsTextArea.setEditable(false);
        pathsTextArea.setLineWrap(true);

        processingTextArea = new JTextArea();
        processingTextArea.setEditable(true);
        processingTextArea.setLineWrap(true);

        JScrollPane leftScrollPane = new JScrollPane(pathsTextArea);
        JScrollPane rightScrollPane = new JScrollPane(processingTextArea);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(leftScrollPane);
        splitPane.setRightComponent(rightScrollPane);
        splitPane.setEnabled(false);
        splitPane.setDividerLocation(0.5);
        splitPane.setResizeWeight(0.5);

        splitPane.setPreferredSize(new Dimension(980, 650));

        frame.add(splitPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(1000, 60));

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 35));
        deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(100, 35));

        rightButtons.add(addButton);
        rightButtons.add(deleteButton);
        bottomPanel.add(rightButtons, BorderLayout.EAST);
        bottomPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 10));

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();

        importDataFromTextFile();
        sendFileToEditingTab();
        setupRealTimeUpdatesForPathsTextArea();
        saveFilesToTextFileWhenExitingFrame();
        deleteFile();
        addFile();
    }

    private void addFile() {
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedLineIndex == -1) {
                    String pathToBeAdded = processingTextArea.getText().trim();
                    pathsList.add(pathToBeAdded);
                    refreshPathsTextArea();
                }
                else {
                    updateSelectedPath();
                }
            }
        });
    }

    private void deleteFile() {

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pathsList.remove(selectedLineIndex);
                refreshPathsTextArea();
            }
        });
    }

    private void setupRealTimeUpdatesForPathsTextArea() {

        processingTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt) {
                updateSelectedPath();
            }
        });
    }

    private void updateSelectedPath() {

        if (selectedLineIndex >= 0 && selectedLineIndex < pathsList.size()) {
            String updatedText = processingTextArea.getText().trim();
            if (!updatedText.equals(pathsList.get(selectedLineIndex))) {
                pathsList.set(selectedLineIndex, updatedText);
                refreshPathsTextArea();
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
        });
    }


    private void saveFilesToTextFileWhenExitingFrame() {

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    textFileService.saveFilesToTextFile(pathsList);
                } catch (IOException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
        });
    }

    private void sendFileToEditingTab() {

        pathsTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextArea textArea = (JTextArea) e.getSource();
                int offset = textArea.viewToModel(e.getPoint());
                try {
                    int line = textArea.getLineOfOffset(offset);
                    int startOffset = textArea.getLineStartOffset(line);
                    int endOffset = textArea.getLineEndOffset(line);

                    int totalLength = textArea.getText().length();
                    if (offset >= totalLength) {
                        selectedLineIndex = -1;
                        processingTextArea.setText("");
                        return;
                    }

                    String lineText = textArea.getText(startOffset, endOffset - startOffset).trim();
                    processingTextArea.setText(lineText);
                    selectedLineIndex = line;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    selectedLineIndex = -1;
                }
            }
        });
    }

    private void importDataFromTextFile() {

        loadButton.addActionListener(click -> {
            try {
                List<String> paths = textFileService.loadFilesFromTextFile();
                setPaths(paths);
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        });
    }

    public void show() {

        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public void setPaths(List<String> paths) {

        SwingUtilities.invokeLater(() -> {
            pathsList.clear();
            if (paths != null && !paths.isEmpty()) {
                pathsList.addAll(paths);
            }

            refreshPathsTextArea();
        });
    }
}