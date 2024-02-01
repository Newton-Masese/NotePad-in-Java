import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Gui extends JFrame {

    private final JFileChooser fileChooser;

    protected JTextArea textArea;
    private File currentFile;

    private final UndoManager undoManager;
    public Gui(){
        super("NotePad");
        setSize(400,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // file chooser
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("src/assets"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        undoManager = new UndoManager();
        addGuiComponents();
    }

    private void addGuiComponents(){
        addToolBar();

        // text area
        textArea = new JTextArea();
        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                // adds each edit done in the text area
                undoManager.addEdit(e.getEdit());
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addToolBar(){
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        //menu bar
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        // add menus
        menuBar.add(addFileMenu());
        menuBar.add(addEditMenu());
        menuBar.add(addFormatMenu());

        add(toolBar, BorderLayout.NORTH);
    }
    private JMenu addFileMenu(){
        JMenu fileMenu = new JMenu("File");

        // New - resets everything
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setTitle("NotePad");
                textArea.setText("");
                currentFile = null;
            }
        });
        fileMenu.add(newMenuItem);

        // Open - open a text file
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                // opens file explorer
                int result = fileChooser.showOpenDialog(Gui.this);
                if(result != JFileChooser.APPROVE_OPTION) return;

                try{
                    // reset notepad
                    newMenuItem.doClick();

                    File selectedFile = fileChooser.getSelectedFile();
                    currentFile = selectedFile;
                    setTitle(selectedFile.getName());

                    FileReader fileReader = new FileReader(selectedFile);
                    BufferedReader br = new BufferedReader(fileReader);

                    // store text
                    StringBuilder fileText = new StringBuilder();
                    String readText;
                    while ((readText = br.readLine()) != null){
                        fileText.append(readText).append("\n");
                    }

                    textArea.setText(fileText.toString());

                    br.close();
                    fileReader.close();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        fileMenu.add(openMenuItem);

        // Save As - creates file and saves text to the file
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        saveAsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                int result = fileChooser.showSaveDialog(Gui.this);

                if(result != JFileChooser.APPROVE_OPTION) return;
                try{
                    File selectedFile = fileChooser.getSelectedFile();

                    // appends .txt if no extension on the file
                    String fileName = selectedFile.getName();
                    if (!fileName.substring(fileName.length() - 4).equalsIgnoreCase(".txt")) {
                        selectedFile = new File(selectedFile.getAbsoluteFile() + ".txt");
                    }

                    selectedFile.createNewFile();

                    // write user's text to file
                    FileWriter fileWriter = new FileWriter(selectedFile);
                    BufferedWriter bw = new BufferedWriter(fileWriter);

                    bw.write(textArea.getText());

                    bw.close();
                    fileWriter.close();

                    // change gui's title to filename
                    setTitle(fileName);
                    currentFile = selectedFile;

                    JOptionPane.showMessageDialog(Gui.this, "Saved File");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        fileMenu.add(saveAsMenuItem);

        // Save - save text to current file
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // if file empty - perform save as function
                if(currentFile == null){
                    saveAsMenuItem.doClick();
                }
                if(currentFile == null) return;

                try{
                    FileWriter fileWriter = new FileWriter(currentFile);
                    BufferedWriter bw = new BufferedWriter(fileWriter);
                    bw.write(textArea.getText());
                    bw.close();
                    fileWriter.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        fileMenu.add(saveMenuItem);

        // exit - close program
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.this.dispose();
            }
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }
    private JMenu addEditMenu(){
        JMenu editMenu = new JMenu("Edit");

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(undoManager.canUndo()){
                    undoManager.undo();
                }
            }
        });
        editMenu.add(undoMenuItem);

        JMenuItem redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(undoManager.canRedo()){
                    undoManager.redo();
                }
            }
        });
        editMenu.add(redoMenuItem);

        return editMenu;
    }
    private JMenu addFormatMenu(){
        JMenu formatMenu = new JMenu("Format");

        // wrap words
        JCheckBoxMenuItem wordWrapMenuItem = new JCheckBoxMenuItem("Word Wrap");
        wordWrapMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isChecked = wordWrapMenuItem.getState();
                if(isChecked){
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                }else{
                    textArea.setLineWrap(false);
                    textArea.setWrapStyleWord(false);
                }
            }
        });
        formatMenu.add(wordWrapMenuItem);

        //align text
        JMenu alignMenuItem = new JMenu("Align");

        JMenuItem alignLeft = new JMenuItem("Left");
        alignLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            }
        });
        alignMenuItem.add(alignLeft);

        JMenuItem alignRight = new JMenuItem("Right");
        alignRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            }
        });
        alignMenuItem.add(alignRight);


        formatMenu.add(alignMenuItem);

        // fonts
        JMenuItem fontMenuItem = new JMenuItem("Font");
        fontMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //launch font menu
                new FontMenu(Gui.this).setVisible(true);
            }
        });
        formatMenu.add(fontMenuItem);
        return formatMenu;
    }
}
