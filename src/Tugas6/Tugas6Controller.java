/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas6;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberInputStream;
import java.io.LineNumberReader;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author snape
 */
public class Tugas6Controller {

    private Tugas6 view;
    private List<Integer> list = new ArrayList<>();

    public Tugas6Controller(Tugas6 view) {
        this.view = view;

        this.view.getBtBaca().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process();
            }
        });

        this.view.getBtSimpan().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }

    private void process() {
        JFileChooser loadFile = view.getLoadFile();
        StyledDocument doc = view.getTxtPane().getStyledDocument();
        if (JFileChooser.APPROVE_OPTION == loadFile.showOpenDialog(view)) {
            PushbackReader reader = null;
            char[] words = new char[(int) loadFile.getSelectedFile().length()];
            try {
                int desimal;
                char ascii;
                String line = null;

                int wCount = 0;
                int cCount = 0;

                LineNumberReader numReader = new LineNumberReader(new FileReader(loadFile.getSelectedFile()));
                LineNumberInputStream inputStream = new LineNumberInputStream(new FileInputStream(loadFile.getSelectedFile()));

                reader = new PushbackReader(new InputStreamReader(new FileInputStream(loadFile.getSelectedFile())));

                reader.read(words);

                String data = null;
                doc.insertString(0, "", null);
                if ((data = new String(words)) != null) {
                    doc.insertString(doc.getLength(), data, null);
                }
                
                while ((desimal = inputStream.read()) != -1) {
                    ascii = (char) desimal;
                }

                while ((line = numReader.readLine()) != null) {
                    String[] wList = line.split("\\s");
                    wCount += wList.length;
                    cCount += line.length();
                }
                JOptionPane.showMessageDialog(view, "File berhasil dibaca!"
                        + "\nJumlah baris     : " + (inputStream.getLineNumber() + 1)
                        + "\nJumlah kata      : " + (wCount)
                        + "\nJumlah karakter  : " + (cCount), "Information", JOptionPane.INFORMATION_MESSAGE);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tugas6Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | BadLocationException ex) {
                Logger.getLogger(Tugas6Controller.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Tugas6Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private void save() {
        JFileChooser loadFile = view.getLoadFile();
        if (JFileChooser.APPROVE_OPTION == loadFile.showSaveDialog(view)) {
            BufferedWriter writer = null;
            try {
                String contents = view.getTxtPane().getText();
                if (contents != null && !contents.isEmpty()) {
                    writer = new BufferedWriter(new FileWriter(loadFile.getSelectedFile()));
                    writer.write(contents);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tugas6Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Tugas6Controller.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (writer != null) {
                    try {
                        writer.flush();
                        writer.close();
                        view.getTxtPane().setText("");
                        JOptionPane.showMessageDialog(null, "Sukses!", "Information", JOptionPane.INFORMATION_MESSAGE);

                    } catch (IOException ex) {
                        Logger.getLogger(Tugas6Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Teks Tidak Boleh Kosong Bro!", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
