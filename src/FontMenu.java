import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FontMenu extends JDialog {

    private final Gui gui;
    private JTextField currentFontField, currentFontStyleField, currentFontSizeTextField;
    private JPanel colourBox;
    public FontMenu(Gui gui){
        this.gui = gui;
        setTitle("Fonts");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(425,350);
        setLocationRelativeTo(gui);
        setModal(true);

        setLayout(null);

        addComponents();
    }

    private void addComponents(){
        addFontChooser();
        addFontStyleChooser();
        addFontSizeChooser();
        addColourChooser();

        // apply button
        JButton applyButton = new JButton("Apply");
        applyButton.setBounds(230, 265, 75, 25);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // gets current font
                String fontType = currentFontField.getText();

                // get font style
                int fontStyle = switch (currentFontStyleField.getText()) {
                    case "Plain" -> Font.PLAIN;
                    case "Bold" -> Font.BOLD;
                    case "Italic" -> Font.ITALIC;
                    default -> Font.BOLD | Font.ITALIC;
                };

                int fontSize = Integer.parseInt(currentFontSizeTextField.getText());
                Color fontColor = colourBox.getBackground();

                Font newFont = new Font(fontType, fontStyle, fontSize);

                gui.textArea.setFont(newFont);
                gui.textArea.setForeground(fontColor);

                FontMenu.this.dispose();
            }
        });
        add(applyButton);

        // cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(315, 265, 75, 25);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // dispose menu
                FontMenu.this.dispose();
            }
        });
        add(cancelButton);
    }
    
    private void addFontChooser(){
        JLabel fontLabel = new JLabel("Font");
        fontLabel.setBounds(10,5,125,10);
        add(fontLabel);

        JPanel fontPanel = new JPanel();
        fontPanel.setBounds(10,15,125,160);

        // displays current font
        currentFontField = new JTextField(gui.textArea.getFont().getFontName());
        currentFontField.setPreferredSize(new Dimension(125,25));
        currentFontField.setEditable(false);
        fontPanel.add(currentFontField);

        // displays available fonts
        JPanel listOfFontPanel = new JPanel();
        listOfFontPanel.setLayout(new BoxLayout(listOfFontPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listOfFontPanel);
        scrollPane.setPreferredSize(new Dimension(125,125));

        //retrieve fonts
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();

        for(String fontName : fontNames){
            JLabel fontNameLabel = new JLabel(fontName);

            fontNameLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    currentFontField.setText(fontName);
                }
                @Override
                public void mouseEntered(MouseEvent event){
                    fontNameLabel.setOpaque(true);
                    fontNameLabel.setBackground(Color.BLUE);
                    fontNameLabel.setForeground(Color.WHITE);
                }
                @Override
                public void mouseExited(MouseEvent event){
                    fontNameLabel.setBackground(null);
                    fontNameLabel.setForeground(null);
                }
            });

            // add fonts to panel
            listOfFontPanel.add(fontNameLabel);
        }
        fontPanel.add(scrollPane);


        add(fontPanel);
    }
    private void addFontStyleChooser() {
        JLabel fontStyleLabel = new JLabel("Font Style");
        fontStyleLabel.setBounds(145, 5, 125, 15);
        add(fontStyleLabel);

        // display font style and available font style
        JPanel fontStylePanel = new JPanel();
        fontStylePanel.setBounds(145, 15, 125, 160);

        // current style
        int currentFontStyle = gui.textArea.getFont().getStyle();
        String currentFontStyleText = switch (currentFontStyle) {
            case Font.PLAIN -> "Plain";
            case Font.BOLD -> "Bold";
            case Font.ITALIC -> "Italic";
            default -> "Bold Italic";
        };

        currentFontStyleField = new JTextField(currentFontStyleText);
        currentFontStyleField.setPreferredSize(new Dimension(125, 25));
        currentFontStyleField.setEditable(false);
        fontStylePanel.add(currentFontStyleField);


        // displays list of all font style
        JPanel listOfFontStylePanel = new JPanel();
        listOfFontStylePanel.setLayout(new BoxLayout(listOfFontStylePanel, BoxLayout.Y_AXIS));

        JLabel plainStyle = new JLabel("Plain");
        plainStyle.setFont(new Font("Dialog", Font.PLAIN, 12));
        plainStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFontStyleField.setText(plainStyle.getText());
            }
            @Override
            public void mouseEntered(MouseEvent event){
                plainStyle.setOpaque(true);
                plainStyle.setBackground(Color.BLUE);
                plainStyle.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent event){
                plainStyle.setBackground(null);
                plainStyle.setForeground(null);
            }
        });
        listOfFontStylePanel.add(plainStyle);

        JLabel boldStyle = new JLabel("Bold");
        boldStyle.setFont(new Font("Dialog", Font.BOLD, 12));
        boldStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFontStyleField.setText(boldStyle.getText());
            }
            @Override
            public void mouseEntered(MouseEvent event){
                boldStyle.setOpaque(true);
                boldStyle.setBackground(Color.BLUE);
                boldStyle.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent event){
                boldStyle.setBackground(null);
                boldStyle.setForeground(null);
            }
        });
        listOfFontStylePanel.add(boldStyle);

        JLabel italicStyle = new JLabel("Italic");
        italicStyle.setFont(new Font("Dialog", Font.ITALIC, 12));
        italicStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFontStyleField.setText(italicStyle.getText());
            }
            @Override
            public void mouseEntered(MouseEvent event){
                italicStyle.setOpaque(true);
                italicStyle.setBackground(Color.BLUE);
                italicStyle.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent event){
                italicStyle.setBackground(null);
                italicStyle.setForeground(null);
            }
        });
        listOfFontStylePanel.add(italicStyle);

        JLabel boldItalicStyle = new JLabel("Bold Italic");
        boldItalicStyle.setFont(new Font("Dialog", Font.PLAIN | Font.ITALIC, 12));
        boldItalicStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFontStyleField.setText(boldItalicStyle.getText());
            }
            @Override
            public void mouseEntered(MouseEvent event){
                boldItalicStyle.setOpaque(true);
                boldItalicStyle.setBackground(Color.BLUE);
                boldItalicStyle.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent event){
                boldItalicStyle.setBackground(null);
                boldItalicStyle.setForeground(null);
            }
        });
        listOfFontStylePanel.add(boldItalicStyle);

        JScrollPane scrollPane = new JScrollPane(listOfFontStylePanel);
        scrollPane.setPreferredSize(new Dimension(125, 125));
        fontStylePanel.add(scrollPane);

        add(fontStylePanel);

    }
    private void addFontSizeChooser(){
        JLabel fontSizeLabel = new JLabel("Font Size: ");
        fontSizeLabel.setBounds(275, 5, 125, 15);
        add(fontSizeLabel);

        // displays current font size and list of sizes
        JPanel fontSizePanel = new JPanel();
        fontSizePanel.setBounds(275, 15, 125, 160);

        currentFontSizeTextField = new JTextField(Integer.toString(gui.textArea.getFont().getSize()));
        currentFontSizeTextField.setPreferredSize(new Dimension(125, 25));
        currentFontSizeTextField.setEditable(false);
        fontSizePanel.add(currentFontSizeTextField);

        // lists of font size to choose
        JPanel listOfSizesPanel = new JPanel();
        listOfSizesPanel.setLayout(new BoxLayout(listOfSizesPanel, BoxLayout.Y_AXIS));

        // size 8 to 72
        for(int i = 8; i <= 72; i+=2){
            JLabel fontSizeValueLabel = new JLabel(Integer.toString(i));
            fontSizeValueLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    currentFontSizeTextField.setText(fontSizeValueLabel.getText());
                }
                @Override
                public void mouseEntered(MouseEvent event){
                    fontSizeValueLabel.setOpaque(true);
                    fontSizeValueLabel.setBackground(Color.BLUE);
                    fontSizeValueLabel.setForeground(Color.WHITE);
                }
                @Override
                public void mouseExited(MouseEvent event){
                    fontSizeValueLabel.setBackground(null);
                    fontSizeValueLabel.setForeground(null);
                }
            });
            listOfSizesPanel.add(fontSizeValueLabel);
        }

        JScrollPane scrollPane = new JScrollPane(listOfSizesPanel);
        scrollPane.setPreferredSize(new Dimension(125, 125));
        fontSizePanel.add(scrollPane);

        add(fontSizePanel);
    }
    private void addColourChooser(){
        colourBox = new JPanel();
        colourBox.setBounds(175, 200, 23, 23);
        colourBox.setBackground(gui.textArea.getForeground());
        colourBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(colourBox);

        JButton colourButton = new JButton("Choose Color");
        colourButton.setBounds(10, 200, 150, 25);
        colourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Select Colour", Color.BLACK);
                colourBox.setBackground(c);
            }
        });
        add(colourButton);
    }
}
