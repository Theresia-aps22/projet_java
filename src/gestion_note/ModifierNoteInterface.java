package views;

import back.NoteOperationDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifierNoteInterface extends JFrame {
    private JTextField noteTextField;
    private JButton modifierButton;

    private NoteOperationDB operationDB;

    public ModifierNoteInterface(String matricule, String codeMatiere, double note) {
        setTitle("Modifier la note");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialisation de l'objet NoteOperationDB
        operationDB = new NoteOperationDB();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Nouvelle note: "), BorderLayout.WEST);
        noteTextField = new JTextField(10);
        noteTextField.setText(String.valueOf(note));
        inputPanel.add(noteTextField, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);

        modifierButton = new JButton("Modifier");
        panel.add(modifierButton, BorderLayout.SOUTH);

        add(panel);

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Récupération de la nouvelle valeur de la note depuis le champ de texte
                    double nouvelleNote = Double.parseDouble(noteTextField.getText());

                    // Vérification si la note est dans la plage autorisée
                    if (nouvelleNote >= 0 && nouvelleNote <= 20) {
                        // Appel de la méthode modifierNote de l'objet operationDB pour modifier la note dans la base de données
                        operationDB.modifierNote(matricule, codeMatiere, nouvelleNote);

                        // Fermeture de la fenêtre de modification
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(ModifierNoteInterface.this, "La note doit être comprise entre 0 et 20.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ModifierNoteInterface.this, "Veuillez saisir une note valide.");
                }
            }
        });
        }

        public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ModifierNoteInterface("Mat123", "Math", 15.5).setVisible(true);
            }
        });
    }
}
