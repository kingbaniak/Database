import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.*;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class
import java.io.FileReader;


public class Database {
    private JTextField addTitle;
    private JTextField addArtist;
    private JButton ADDButton;
    private JComboBox comboBox;
    private JLabel Title;
    private JTextPane ShowInfo;
    private JPanel DataWindow;
    private JButton SaveButton;
    private JButton ShowSavedButton;
    private JTextArea SavedText;
    private JLabel ErrorInfo;
    private JLabel fnLabel;
    private JTextField fileName;
    private JButton ClsButton;
    JFrame window = new JFrame();
    ArrayList<Ksiazka> biblioteka = new ArrayList<>();


    public void WriteToFile()
    {
        String text = ShowInfo.getText();
        if (text == null || text.trim().isEmpty()) {
            ErrorInfo.setText("Nothing to save");
            return;
        }

        File libFile = new File(LibName());
        FileWriter saveLib = null;
        try {
            saveLib = new FileWriter(LibName());
            saveLib.write(ShowInfo.getText());
            saveLib.close();
            ErrorInfo.setText("Saved Successfully");
        } catch (IOException e) {
            ErrorInfo.setText("An Error Occured.");
        }
    }

    public void ReadFromFile() throws IOException {
        FileReader libRead = new FileReader("C:/Users/kinga/Desktop/studia/java/baza_danych/"+LibName());
        String fileContent = "";
        int i;
        while ((i = libRead.read()) != -1) {
            fileContent += (char) i;
        }
        SavedText.setText(fileContent);
    }


    public String LibName()
    {
        return(fileName.getText()+".txt");
    }

    public Database() {

        window.setMinimumSize(new Dimension(1000,1000));
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.add(DataWindow);

        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ksiazka book = new Ksiazka();
                book.setTitle(addTitle.getText());
                book.setArtist(addArtist.getText());
                biblioteka.add(book);
                UpdateInfo();
            }
        });

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < comboBox.getItemCount(); i++) {
                    String selectedTitle = (String) comboBox.getSelectedItem();
                    for (Ksiazka book: biblioteka) {
                        if (book.getTitle().equals(selectedTitle)) {
                            ShowInfo.setText(book.storeInfo());
                        }
                    }
                }
            }
        });

        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WriteToFile();
            }
        });
        ShowSavedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ReadFromFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        ClsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowInfo.setText(null);
            }
        });
    }

    public void UpdateInfo(){
        StringBuilder spisKsiazek = new StringBuilder();
       for (Ksiazka book: biblioteka){
        spisKsiazek.append(book.storeInfo());
       spisKsiazek.append("\n\n");
           boolean isDuplicate = false;
           for (int i = 0; i < comboBox.getItemCount(); i++) {
               if (book.getTitle().equals(comboBox.getItemAt(i))) {
                   isDuplicate = true;
                   break;
               }
           }
           if (!isDuplicate) {
               comboBox.addItem(book.getTitle());
           }
       }
        ShowInfo.setText(spisKsiazek.toString());
    }


    public static void main(String[] args) {
        new Database();
    }
}
