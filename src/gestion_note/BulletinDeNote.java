package views;

import back.NoteOperationDB;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BulletinDeNote extends JFrame {
    private NoteOperationDB operationDB;
    private JComboBox<String> comboBoxEtudiants;
    private JTable tableNotes;
    private DefaultTableModel tableModel;
    private JLabel labelMoyenneGenerale;
    private JLabel labelRang;
    private JButton buttonImprimer;
    private String matricule;
    private String nom;
    private String prenom;
    private double moyenneGenerale;
    private int rang;

    public BulletinDeNote(NoteOperationDB operationDB) {
        this.operationDB = operationDB;
        initUI();
    }

    private void initUI() {
        setTitle("Bulletin de l'étudiant");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        comboBoxEtudiants = new JComboBox<>();
        tableNotes = new JTable();

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nom Matière", "Note", "Coefficient", "Note avec coefficient"}
        );
        tableNotes.setModel(tableModel);

        JPanel panelTop = new JPanel();
        panelTop.add(new JLabel("Sélectionnez un étudiant:"));
        panelTop.add(comboBoxEtudiants);

        buttonImprimer = new JButton("Imprimer");
        panelTop.add(buttonImprimer);

        JPanel panelBottom = new JPanel();
        labelMoyenneGenerale = new JLabel("Moyenne générale: ");
        labelRang = new JLabel("Rang: ");
        panelBottom.add(labelMoyenneGenerale);
        panelBottom.add(labelRang);

        getContentPane().add(panelTop, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(tableNotes), BorderLayout.CENTER);
        getContentPane().add(panelBottom, BorderLayout.SOUTH);

        populateComboBoxEtudiants();

        comboBoxEtudiants.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherNotesEtudiant();
            }
        });

        buttonImprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    imprimerBulletin();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(BulletinDeNote.this, "Erreur lors de la création du PDF : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void populateComboBoxEtudiants() {
        // Récupérer la liste des étudiants depuis la base de données
        ArrayList<String> etudiants = operationDB.recupererListeEtudiants();
        comboBoxEtudiants.setModel(new DefaultComboBoxModel<>(etudiants.toArray(new String[0])));
    }

    private void afficherNotesEtudiant() {
        String etudiantSelectionne = (String) comboBoxEtudiants.getSelectedItem();
        if (etudiantSelectionne != null) {
            // Séparez le matricule du reste du texte
            String[] parts = etudiantSelectionne.split(" ");
            matricule = parts[0];
            nom = parts[1];
            prenom = parts[2];

            // Récupérer les notes de l'étudiant sélectionné
            ArrayList<Object[]> notes = operationDB.afficherNotesEtudiant(matricule);

            // Mettre à jour le modèle de tableau avec les notes récupérées
            tableModel.setRowCount(0);
            for (Object[] note : notes) {
                tableModel.addRow(note);
            }

            // Calculer et afficher la moyenne générale et le rang
            moyenneGenerale = operationDB.calculerMoyenneGenerale(matricule);
            rang = operationDB.obtenirRang(matricule);

            labelMoyenneGenerale.setText("Moyenne générale: " + moyenneGenerale);
            labelRang.setText("Rang: " + rang);

            // Mettre à jour le titre du bouton d'impression
            buttonImprimer.setText("Imprimer : " + matricule + " " + nom + " " + prenom);
        }
    }

    private void imprimerBulletin() throws FileNotFoundException {
        // Choisir l'emplacement où enregistrer le fichier PDF
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer le bulletin de notes");
        fileChooser.setSelectedFile(new File("bulletin_" + matricule + "_" + nom + "_" + prenom + ".pdf"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String dest = fileToSave.getAbsolutePath();

            // Créer le document PDF
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Ajouter le nom, la moyenne générale et le rang de l'étudiant
            document.add(new Paragraph("Bulletin de Notes"));
            document.add(new Paragraph("Etudiant : " + nom + " " + prenom + " (Matricule : " + matricule + ")"));
            document.add(new Paragraph("Moyenne générale : " + moyenneGenerale));
            document.add(new Paragraph("Rang : " + rang));

            // Ajouter les notes dans une table
            Table table = new Table(tableModel.getColumnCount());
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                table.addHeaderCell(tableModel.getColumnName(i));
            }
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    table.addCell(tableModel.getValueAt(i, j).toString());
                }
            }

            document.add(table);
            document.close();

            JOptionPane.showMessageDialog(this, "PDF créé avec succès : " + dest, "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NoteOperationDB operationDB = new NoteOperationDB();
                BulletinDeNote ex = new BulletinDeNote(operationDB);
                ex.setVisible(true);
            }
        });
    }
}
