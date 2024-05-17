package views;

import back.NoteOperationDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AjouterInterface extends JFrame {
    private JComboBox<String> studentComboBox;
    private JComboBox<String> subjectComboBox;
    private JTextField noteTextField;
    private JButton addButton;

    private NoteOperationDB operationDB;

    public AjouterInterface() {
        setTitle("Ajouter une note");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        operationDB = new NoteOperationDB();

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel studentLabel = new JLabel("Étudiant:");
        inputPanel.add(studentLabel);
        studentComboBox = new JComboBox<>();
        inputPanel.add(studentComboBox);

        JLabel subjectLabel = new JLabel("Matière:");
        inputPanel.add(subjectLabel);
        subjectComboBox = new JComboBox<>();
        inputPanel.add(subjectComboBox);

        JLabel noteLabel = new JLabel("Note:");
        inputPanel.add(noteLabel);
        noteTextField = new JTextField(10);
        inputPanel.add(noteTextField);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        addButton = new JButton("Ajouter");
        mainPanel.add(addButton, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);

        loadStudents();
        loadSubjects();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterNote();
            }
        });
    }

    private void loadStudents() {
        ArrayList<String> students = operationDB.recupererListeEtudiants();
        for (String student : students) {
            studentComboBox.addItem(student);
        }
    }

    private void loadSubjects() {
        String[] subjects = operationDB.recupererCodesMatieres();
        for (String subject : subjects) {
            subjectComboBox.addItem(subject);
        }
    }

    private void ajouterNote() {
        String student = (String) studentComboBox.getSelectedItem();
        String[] studentParts = student.split(" ");
        String matricule = studentParts[0];

        String codeMatiere = (String) subjectComboBox.getSelectedItem();
        String noteText = noteTextField.getText();

        try {
            double note = Double.parseDouble(noteText);
            if (note >= 0 && note <= 20) {
                operationDB.ajouterNote(matricule, codeMatiere, note);
                JOptionPane.showMessageDialog(this, "Note ajoutée avec succès !");
                noteTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une note valide (entre 0 et 20).", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une valeur numérique pour la note.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AjouterInterface().setVisible(true);
            }
        });
    }
}
