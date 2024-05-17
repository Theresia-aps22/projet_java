package gestion_note;

import back.ConnexionDB;
import back.EtudiantOperationDB;
import gestion_note.HomeInterface;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class EtudiantInterface extends JFrame {
    private Connection connection;
    private EtudiantOperationDB etudiantOperationDB;

    private JTextField nomField;
    private JTextField prenomField;
    private JTextField matriculeField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton refreshButton;
    private JTable etudiantTable;

    public EtudiantInterface() {
        super("Gestion des Étudiants");

        // Connexion à la base de données
        connection = new ConnexionDB().getConnection();

        // Initialisation de l'opération sur les étudiants en passant la connexion
        etudiantOperationDB = new EtudiantOperationDB(connection);

        // Création et configuration de la fenêtre Swing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);

        // Panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Nom de l'Étudiant:"));
        nomField = new JTextField();
        inputPanel.add(nomField);
        inputPanel.add(new JLabel("Prénom de l'Étudiant:"));
        prenomField = new JTextField();
        inputPanel.add(prenomField);
        inputPanel.add(new JLabel("Matricule de l'Étudiant:"));
        matriculeField = new JTextField();
        inputPanel.add(matriculeField);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Ajouter");
        deleteButton = new JButton("Supprimer");
        updateButton = new JButton("Modifier");
        refreshButton = new JButton("Rafraîchir");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(refreshButton);

        // Panel for table
        JPanel tablePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Étudiants");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tablePanel.add(titleLabel, BorderLayout.NORTH);

        // Table initialization
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nom");
        model.addColumn("Prénom");
        model.addColumn("Matricule");
        etudiantTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(etudiantTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add components to frame
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(tablePanel, BorderLayout.CENTER);

        // Set up frame
        setVisible(true);

        // Bouton de retour
        JButton backButton = new JButton("Retour");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermer la fenêtre actuelle
                dispose();
                // Ouvrir la fenêtre HomeInterface
                new HomeInterface().setVisible(true);
            }
        });

        // Ajouter le bouton de retour au panel
        buttonPanel.add(backButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);



    // ActionListeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String matricule = matriculeField.getText();
                etudiantOperationDB.ajouterEtudiant(nom, prenom, matricule);
                refreshTable();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = etudiantTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) etudiantTable.getValueAt(selectedRow, 0);
                    etudiantOperationDB.supprimerEtudiant(id);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(EtudiantInterface.this, "Veuillez sélectionner un étudiant à supprimer", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Ajouter un écouteur d'événements de clic au tableau
        etudiantTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Vérifier si un clic avec le bouton gauche de la souris est effectué
                if (e.getButton() == MouseEvent.BUTTON1) {
                    // Récupérer l'index de la ligne sélectionnée
                    int selectedRow = etudiantTable.getSelectedRow();
                    // Vérifier si une ligne est sélectionnée
                    if (selectedRow != -1) {
                        // Récupérer les données de la ligne sélectionnée
                        int id = (int) etudiantTable.getValueAt(selectedRow, 0);
                        String nom = (String) etudiantTable.getValueAt(selectedRow, 1);
                        String prenom = (String) etudiantTable.getValueAt(selectedRow, 2);
                        Object value = etudiantTable.getValueAt(selectedRow, 3);
                        String matricule = "";
                        if (value instanceof Double) {
                            matricule = Double.toString((Double) value);
                        } else if (value instanceof String) {
                            matricule = (String) value;
                        } else {
                            // Gérer d'autres types si nécessaire
                        }



                        // Afficher une boîte de dialogue modale pour modifier les données
                        modifierMatiereDialog(id, nom, prenom, matricule);
                    }
                }
            }
        });

        // Rafraîchir le tableau initialement
        refreshTable();
    }
        private void modifierMatiereDialog(int id, String nom, String prenom, String matricule) {
            // Créer une boîte de dialogue modale
            JDialog dialog = new JDialog(this, "Modifier Etudiant", true);
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 5, 5, 5);

            // Ajouter des champs de texte avec les données de la ligne sélectionnée
            JLabel nomLabel = new JLabel("Nom:");
            panel.add(nomLabel, gbc);

            gbc.gridx = 1;
            JTextField nomField = new JTextField(nom);
            nomField.setPreferredSize(new Dimension(200, 25));
            panel.add(nomField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            JLabel prenomLabel = new JLabel("Prenom:");
            panel.add(prenomLabel, gbc);

            gbc.gridx = 1;
            JTextField prenomField = new JTextField(prenom);
            prenomField.setPreferredSize(new Dimension(200, 25));
            panel.add(prenomField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            JLabel matriculeLabel = new JLabel("Matricule:");
            panel.add(matriculeLabel, gbc);

            gbc.gridx = 1;
            JTextField matriculeField = new JTextField(matricule);
            matriculeField.setPreferredSize(new Dimension(200, 25));
            panel.add(matriculeField, gbc);

            // Ajouter un bouton de confirmation
            JButton confirmButton = new JButton("Confirmer");
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Récupérer les données modifiées des champs de texte
                    String newNom = nomField.getText();
                    String newPrenom = prenomField.getText();
                    String newMatricule = matriculeField.getText();

                    // Appeler la méthode pour modifier les données dans la base de données
                    etudiantOperationDB.modifierEtudiant(id, newNom, newPrenom, newMatricule);

                    // Rafraîchir la table après modification
                    refreshTable();

                    // Fermer la boîte de dialogue
                    dialog.dispose();
                }
            });

            gbc.gridx = 1;
            gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(confirmButton, gbc);

            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }

        private void refreshTable() {
            DefaultTableModel model = (DefaultTableModel) etudiantTable.getModel();
            model.setRowCount(0);
            etudiantOperationDB.afficherEtudiant(model);

        }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EtudiantInterface(); // Remplacez MatiereInterface par EtudiantInterface
            }
        });
    }

}
