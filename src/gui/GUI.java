package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class GUI {

    private final JFrame frame;

    private final JButton loadButton;

    private final JButton saveButton;

    private final JButton addButton;

    private final JButton deleteButton;

    private final JTextArea pathsTextArea;

    private final JTextArea processingTextArea;

    public GUI() {
        frame = new JFrame("Media Manager");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(8, 8));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        loadButton = new JButton("Load");
        saveButton = new JButton("Save");
        topPanel.add(loadButton);
        topPanel.add(saveButton);
        frame.add(topPanel, BorderLayout.NORTH);

        pathsTextArea = new JTextArea();
        pathsTextArea.setEditable(false);
        JScrollPane pathsScroll = new JScrollPane(pathsTextArea);
        pathsScroll.setBorder(BorderFactory.createTitledBorder("Loaded Paths"));

        processingTextArea = new JTextArea();
        JScrollPane processingScroll = new JScrollPane(processingTextArea);
        processingScroll.setBorder(BorderFactory.createTitledBorder("Processing / Editing"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pathsScroll, processingScroll);
        splitPane.setResizeWeight(0.4);
        frame.add(splitPane, BorderLayout.CENTER);

        JPanel bottomPAnel = new JPanel(new BorderLayout());
        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        rightButtons.add(addButton);
        rightButtons.add(deleteButton);
        bottomPAnel.add(rightButtons, BorderLayout.SOUTH);
        frame.add(bottomPAnel, BorderLayout.SOUTH);
    }

    public void show() {

        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public void setPaths(List<String> paths) {

        SwingUtilities.invokeLater(() -> {
            pathsTextArea.setText("");

            if (paths != null) {
                for (String path : paths) {
                    pathsTextArea.append(path);
                    pathsTextArea.append(System.lineSeparator());
                }
            }
        });
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JTextArea getPathsTextArea() {
        return pathsTextArea;
    }

    public JTextArea getProcessingTextArea() {
        return processingTextArea;
    }
}