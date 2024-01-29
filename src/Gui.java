import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    public Gui(){
        super("NotePad");
        setSize(400,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addGuiComponents();
    }

    private void addGuiComponents(){
        addToolBar();
    }

    private void addToolBar(){
        JToolBar toolBar = new JToolBar();

        //menu bar
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        // add menus
        menuBar.add(addMenuBar());

        add(toolBar, BorderLayout.NORTH);
    }
    private JMenu addMenuBar(){
        JMenu fileMenu = new JMenu("File");

        // New - resets everything
        JMenuItem newMenuItem = new JMenuItem("New");
        fileMenu.add(newMenuItem);

        // Open - open a text file
        JMenuItem openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);

        // Save - save text to current file
        JMenuItem saveMenuItem = new JMenuItem("Save");
        fileMenu.add(saveMenuItem);

        // Save As - creates file and saves text to the file
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        fileMenu.add(saveAsMenuItem);

        // exit - close program
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }
}
