/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestion_note;

import back.NoteOperationDB;
import gestion_note.Login;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;






/**
 *
 * @author eudoxie
 */
public class NoteInterface extends javax.swing.JFrame {
    // Tableau de tableaux pour stocker les données des différentes matières
    ArrayList<Object[][]> data = new ArrayList<>();

    private final NoteOperationDB operationDB;
    private JButton jButton3;



    /**
     * Creates new form tableau
     */
    public NoteInterface() {
        initComponents();
        operationDB = new NoteOperationDB();
        populateComboBox();
        afficherDonneesEtudiants();
        ArrayList<Object[]> dataList = operationDB.afficherNote();

    // Créer un nouveau modèle de table par défaut
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{}, // Les données commencent vides
                new String[]{"Nom", "Prénom(s)", "Matricule" , "Note", "Coefficient", "Note avec coefficient"} // Définir les noms de colonnes
        );

    // Parcourir les données récupérées et les ajouter au modèle de table
        for (Object[] rowData : dataList) {
            model.addRow(rowData);
        }

    // Définir le modèle de table pour jTable2
        jTable2.setModel(model);

        // Ajouter un bouton pour afficher les bulletins de notes
        btnAfficherBulletin = new JButton("Afficher Bulletin");
        btnAfficherBulletin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnAfficherBulletinActionPerformed(evt);
            }
        });
        jPanel3.add(btnAfficherBulletin); // Ajouter le bouton au panneau



    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
            // Initialisation des composants et configuration de la disposition de la fenêtre

        studentComboBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel("Étudiant:");




        // Création d'un JTabbedPane pour la barre de navigation
            JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);

            // Ajout des onglets à la barre de navigation
            JPanel accueilPanel = new JPanel();
            JPanel ajouterPanel = new JPanel();
            JPanel imprimerPanel = new JPanel();

            tabbedPane.addTab("Accueil", accueilPanel);
            tabbedPane.addTab("Ajouter", ajouterPanel);
            tabbedPane.addTab("Imprimer", imprimerPanel);

            // Définition de la barre de navigation pour cette fenêtre
            getContentPane().add(tabbedPane, java.awt.BorderLayout.CENTER);


        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextFieldNote = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelNote = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        btnModifier = new javax.swing.JButton();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(400, 350));

        jTable2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 51, 255)));




        jScrollPane2.setViewportView(jTable2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));


        jButton1.setBackground(new java.awt.Color(0, 255, 0));
        jButton1.setText("Ajouter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 0, 0));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Supprimer");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(573, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Info-312", "Info-320", "Info-321", "Info-373" }));
        jComboBox1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String selectedCodeMatiere = (String) jComboBox1.getSelectedItem();
                int idMatiere = operationDB.recupererIdMatiere(selectedCodeMatiere);
                refreshTableData(idMatiere);
            }
        });


        jLabel5.setText("Matière :");

        jButton2.setBackground(new java.awt.Color(0, 0, 204));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Déconnexion");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });



        btnModifier = new JButton("Modifier");
        btnModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int selectedRow = jTable2.getSelectedRow();

                // Vérifier si une ligne est sélectionnée
                if (selectedRow != -1) {
                    String codeMatiere = (String) jComboBox1.getSelectedItem();
                    String matricule = (String) jTable2.getValueAt(selectedRow, 2);
                    Object noteObj = jTable2.getValueAt(selectedRow, 3);

                    // Vérifier si la note est valide
                    if (noteObj != null && noteObj instanceof Number) {
                        double note = ((Number) noteObj).doubleValue();

                        // Récupérer l'ID de la matière
                        int idMatiere = operationDB.recupererIdMatiere(codeMatiere);

                        // Ouvrir l'interface de modification avec les informations nécessaires
                        views.ModifierNoteInterface modifierInterface = new views.ModifierNoteInterface(matricule, codeMatiere, note);
                        modifierInterface.setVisible(true);

                        // Rafraîchir le tableau après avoir modifié la note
                        refreshTableData(idMatiere);
                    } else {
                        JOptionPane.showMessageDialog(NoteInterface.this, "La note sélectionnée est invalide.");
                    }
                } else {
                    JOptionPane.showMessageDialog(NoteInterface.this, "Veuillez sélectionner une ligne du tableau.");
                }
            }
    });


        btnImprimer = new JButton("Imprimer");
        btnImprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnImprimerActionPerformed(evt);
            }
        });

        jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.X_AXIS));
        Component rigidArea = Box.createHorizontalGlue();

        // Ajouter d'abord la zone rigide pour pousser les boutons à gauche
        jPanel3.add(rigidArea);




        jPanel3.add(btnModifier);
        jPanel3.add(btnImprimer);

        jButton3 = new JButton("Retour");
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Créez une nouvelle instance de HomeInterface et affichez-la
                HomeInterface homeInterface = new HomeInterface();
                homeInterface.setVisible(true);

                // Fermez l'interface actuelle
                dispose();
            }
        });

        jPanel1.add(jButton3); // Ajoutez le bouton de retour au panneau


        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(219, 219, 219)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(182, 182, 182)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 929, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );


        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean isNumeric(String str) {
        // Vérifie si la chaîne est un entier en utilisant une expression régulière
        return str.matches("\\d+");
    }

    // Méthode pour mettre à jour les données en fonction de la sélection du JComboBox
    // Méthode pour mettre à jour les données en fonction de la sélection du JComboBox
    private void updateTableData(int selectedIndex) {
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            Object[][] selectedData = data.get(selectedIndex);

            // Mettre à jour le modèle de tableau jTable2 avec les données correspondantes
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0); // Effacer toutes les lignes existantes
            for (Object[] row : selectedData) {
                model.addRow(row);
            }
        }
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        // Prendre le modèle de tableau
        DefaultTableModel tblModel = (DefaultTableModel) jTable2.getModel();

        // Effacer la ligne sélectionnée
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow != -1) {
            // Si une ligne est sélectionnée, récupérer l'ID de la note dans cette ligne
            String matricule = (String) jTable2.getValueAt(selectedRow, 2);
            String codeMatiere = (String) jComboBox1.getSelectedItem();

            // Appeler la méthode supprimerNote de la classe OperationDB pour supprimer la note correspondante
            operationDB.supprimerNote(matricule, codeMatiere);

            // Mettre à jour le modèle du tableau après la suppression
            tblModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à supprimer.");
        }
    }


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        views.AjouterInterface addNoteWindow = new views.AjouterInterface();
        addNoteWindow.setVisible(true);
    }


    // Méthode pour rafraîchir les données dans jTable2
    private void refreshTableData(int idMatiere) {
        // Appel de la méthode afficherNoteParMatiere() pour récupérer les données depuis la base de données
        ArrayList<Object[]> dataList = operationDB.afficherNoteParMatiere(idMatiere);

        // Récupérer le modèle de tableau
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

        // Effacer toutes les lignes actuellement dans le modèle de tableau
        model.setRowCount(0);

        // Parcourir les données récupérées et les ajouter au modèle de tableau
        for (Object[] rowData : dataList) {
            model.addRow(rowData);
        }
    }


    public void populateComboBox() {
        // Récupérer les codes de matière depuis la base de données
        String[] codesMatieres = operationDB.recupererCodesMatieres();

        // Mettre les codes de matière dans le JComboBox
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(codesMatieres));

        //
    }

    public void afficherDonneesEtudiants() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0); // Effacer toutes les lignes existantes

        // Vérifier si operationDB est null
        if(operationDB == null) {
            System.out.println("operationDB est null. Assurez-vous qu'il est correctement initialisé.");
            return;
        }

        // Utiliser la méthode afficherNote() de operationDB pour récupérer les données
        ArrayList<Object[]> data = operationDB.afficherNote();

        // Vérifier si data est null
        if(data == null) {
            System.out.println("Les données récupérées sont null. Assurez-vous qu'il y a des données à afficher.");
            return;
        }

        // Ajouter les données récupérées au modèle de jTable2
        for (Object[] row : data) {
            model.addRow(row);
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Cacher la forme actuelle
    this.setVisible(false);

    // Créer une nouvelle instance de la classe
    Login loginForm = new Login();

    // Afficher la nouvelle forme
    loginForm.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed


    private void btnImprimerActionPerformed(ActionEvent evt) {
        try {
            // Récupérer les codes de matière depuis la base de données
            String code_matiere = (String) jComboBox1.getSelectedItem();

            // Définir le titre du fichier imprimé avec le code de matière
            MessageFormat header = new MessageFormat("Matière: " + code_matiere);

            // Définir le pied de page avec le numéro de page
            MessageFormat footer = new MessageFormat("Page {0}");

            // Imprimer le contenu de la jTable2 avec le mode FIT_WIDTH et les en-têtes et pieds de page spécifiés
            boolean complete = jTable2.print(JTable.PrintMode.FIT_WIDTH, header, footer);

            // Vérifier si l'impression est complète
            if (complete) {
                JOptionPane.showMessageDialog(this, "Impression réussie.");
            } else {
                JOptionPane.showMessageDialog(this, "Impression annulée.");
            }
        } catch (PrinterException pe) {
            // Gérer les erreurs d'impression
            JOptionPane.showMessageDialog(this, "Échec de l'impression: " + pe.getMessage());
        }
    }

    private void btnAfficherBulletinActionPerformed(ActionEvent evt) {
        // Créer une nouvelle instance de la fenêtre de bulletin et l'afficher
        views.BulletinDeNote fenetreBulletin = new views.BulletinDeNote(operationDB);
        fenetreBulletin.setVisible(true);
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NoteInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NoteInterface tableau = new NoteInterface();
                tableau.setVisible(true);

                // Récupérer les codes de matière depuis la base de données et les mettre dans le JComboBox
                tableau.populateComboBox();
            }
        });

    }



    private JComboBox<String> etudiantsComboBox;
    private javax.swing.JComboBox<String> studentComboBox;
    private javax.swing.JButton btnDelete;
    private JButton btnModifier;
    private JButton btnImprimer;

    private JButton btnAfficherBulletin;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;

    private javax.swing.JLabel jLabel6;

    private javax.swing.JLabel jLabelNote;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;


    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;

    private javax.swing.JTextField jTextFieldNote;



    // End of variables declaration//GEN-END:variables
}
