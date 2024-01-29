import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Gui extends JFrame {

    private final JFileChooser fileChooser;

    private JTextArea textArea;
    private File currentFile;
    public Gui(){
        super("NotePad");
        setSize(400,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // file chooser
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("src/assets"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        addGuiComponents();
    }

    private void addGuiComponents(){
        addToolBar();

        // text area
        textArea = new JTextArea();
        add(textArea, BorderLayout.CENTER);
    }

    private void addToolBar(){
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        //menu bar
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        // add menus
        menuBar.add(addFileMenu());

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
}
