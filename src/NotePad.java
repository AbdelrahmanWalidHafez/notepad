import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.Scanner;
public class NotePad extends JFrame implements ActionListener, WindowListener {
    JTextArea textArea = new JTextArea();
    File fNameContainer;
    public NotePad() {
        Container container = getContentPane();
        JMenuBar menuBar = new JMenuBar();
        JMenu jMenuFile = new JMenu("File");
        JMenu jMenuEdit = new JMenu("Edit");
        JMenu jMenuHelp = new JMenu("Help");
        container.setLayout(new BorderLayout());
        JScrollPane scrollPaneText = new JScrollPane(textArea);
        scrollPaneText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneText.setVisible(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 15));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        container.add(scrollPaneText);
        createMenuItem(jMenuFile, "New");
        createMenuItem(jMenuFile, "Open");
        createMenuItem(jMenuFile, "Save");
        jMenuFile.addSeparator();
        createMenuItem(jMenuFile, "Exit");
        createMenuItem(jMenuEdit, "Cut");
        createMenuItem(jMenuEdit, "Copy");
        createMenuItem(jMenuEdit, "Paste");
        createMenuItem(jMenuHelp, "Help");
        createMenuItem(jMenuHelp, "About NotePad");
        menuBar.add(jMenuFile);
        menuBar.add(jMenuEdit);
        menuBar.add(jMenuHelp);
        setJMenuBar(menuBar);
        setIconImage(Toolkit.getDefaultToolkit().getImage("notepad.gif"));
        addWindowListener(this);
        setSize(500, 500);
        setTitle("Untitled.txt");
        setVisible(true);
    }
    private void createMenuItem(JMenu jMenu, String txt) {
        JMenuItem jMenuItem = new JMenuItem(txt);
        jMenuItem.addActionListener(this);
        jMenu.add(jMenuItem);
    }
    private void openFile(String fName) throws IOException {
        Scanner scanner = new Scanner(new File(fName));
        textArea.setText("");
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        while (scanner.hasNext()) {
            textArea.append(scanner.nextLine() + "\r\n");
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        scanner.close();
    }
    private void saveFile(String fName) throws IOException {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fName));
        outputStream.writeBytes(textArea.getText());
        outputStream.close();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (e.getActionCommand().equalsIgnoreCase("New")) {
            this.setTitle("Untitled.txt - NotePad");
            textArea.setText("");
            fNameContainer = null;
        } else if (e.getActionCommand().equalsIgnoreCase("Open")) {
            int open = fileChooser.showDialog(null, "Open");
            if (open == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    openFile(file.getAbsolutePath());
                    this.setTitle(file.getName() + " - NotePad");
                    fNameContainer = file;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getActionCommand().equalsIgnoreCase("Save")) {
            if (fNameContainer != null) {
                fileChooser.setCurrentDirectory(fNameContainer);
                fileChooser.setSelectedFile(fNameContainer);
            } else {
                fileChooser.setSelectedFile(new File("Untitled.txt"));
            }
            int save = fileChooser.showSaveDialog(null);
            if (save == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    saveFile(file.getAbsolutePath());
                    this.setTitle(file.getName() + " - NotePad");
                    fNameContainer = file;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getActionCommand().equalsIgnoreCase("Exit")) {
            System.exit(0);
        } else if (e.getActionCommand().equalsIgnoreCase("Copy")) {
            textArea.copy();
        } else if (e.getActionCommand().equalsIgnoreCase("Paste")) {
            textArea.paste();
        } else if (e.getActionCommand().equalsIgnoreCase("Cut")) {
            textArea.cut();
        } else if (e.getActionCommand().equalsIgnoreCase("About NotePad")) {
            JOptionPane.showMessageDialog(this, "Created by: Abdelrahman Walid", "NotePad", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    @Override
    public void windowDeactivated(WindowEvent e) {}
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
}
