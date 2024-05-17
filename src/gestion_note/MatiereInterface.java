package gestion_note;

import back.ConnexionDB;
import back.MatiereOperationDB;
import gestion_note.HomeInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class MatiereInterface extends JFrame {
    private Connection connection;
    private MatiereOperationDB matiereOperationDB;

    private JTextField nomField;
    private JTextField codeField;
    private JTextField coefficientField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton refreshButton;
    private JTable matiereTable;

    public MatiereInterface() {
        // Appel du constructeur de la classe parente JFrame
        super("Gestion des Matières");

        // Connexion à la base de données en utilisant la classe ConnexionDB
        connection = new ConnexionDB().getConnection();

        // Initialisation de l'opération sur les matières en passant la connexion
        matiereOperationDB = new MatiereOperationDB(connection);

        // Création et configuration de la fenêtre Swing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);

        // Initialisation des composants
        initComponents();

        // Configuration de la fenêtre
        setVisible(true);
    }

    private void initComponents() {
        // Panel pour les champs de saisie
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Nom de la Matière:"));
        nomField = new JTextField();
        inputPanel.add(nomField);
        inputPanel.add(new JLabel("Code de la Matière:"));
        codeField = new JTextField();
        inputPanel.add(codeField);
        inputPanel.add(new JLabel("Coefficient:"));
        coefficientField = new JTextField();
        inputPanel.add(coefficientField);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Ajouter");
        deleteButton = new JButton("Supprimer");
        updateButton = new JButton("Modifier");
        refreshButton = new JButton("Rafraîchir");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(refreshButton);

        // Panel pour le tableau
        JPanel tablePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Matières");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tablePanel.add(titleLabel, BorderLayout.NORTH);

        // Initialisation du tableau
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nom Matière");
        model.addColumn("Code Matière");
        model.addColumn("Coefficient");
        matiereTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(matiereTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Ajout des composants à la fenêtre
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(tablePanel, BorderLayout.CENTER);

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

        // Ajout des écouteurs d'événements pour les boutons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String code = codeField.getText();
                float coefficient = Float.parseFloat(coefficientField.getText());
                matiereOperationDB.ajouterMatiere(nom, code, coefficient);
                refreshTable();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = matiereTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) matiereTable.getValueAt(selectedRow, 0);
                    matiereOperationDB.supprimerMatiere(id);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(MatiereInterface.this, "Veuillez sélectionner une matière à supprimer", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = matiereTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) matiereTable.getValueAt(selectedRow, 0);
                    String nom = (String) matiereTable.getValueAt(selectedRow, 1);
                    String code = (String) matiereTable.getValueAt(selectedRow, 2);
                    float coefficient = (float) matiereTable.getValueAt(selectedRow, 3);
                    modifierMatiereDialog(id, nom, code, coefficient);
                } else {
                    JOptionPane.showMessageDialog(MatiereInterface.this, "Veuillez sélectionner une matière à modifier", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });

        // Ajouter un écouteur d'événements de clic au tableau
        matiereTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Vérifier si un clic avec le bouton gauche de la souris est effectué
                if (e.getButton() == MouseEvent.BUTTON1) {
                    // Récupérer l'index de la ligne sélectionnée
                    int selectedRow = matiereTable.getSelectedRow();
                    // Vérifier si une ligne est sélectionnée
                    if (selectedRow != -1) {
                        // Récupérer les données de la ligne sélectionnée
                        int id = (int) matiereTable.getValueAt(selectedRow, 0);
                        String nom = (String) matiereTable.getValueAt(selectedRow, 1);
                        String code = (String) matiereTable.getValueAt(selectedRow, 2);
                        double coefficient = (double) matiereTable.getValueAt(selectedRow, 3);

                        // Afficher une boîte de dialogue modale pour modifier les données
                        modifierMatiereDialog(id, nom, code, coefficient);
                    }
                }
            }
        });

        // Rafraîchir le tableau initialement
        refreshTable();
    }

    private void modifierMatiereDialog(int id, String nom, String code, double coefficient) {
        // Créer une boîte de dialogue modale
        JDialog dialog = new JDialog(this, "Modifier Matière", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Ajouter des champs de texte avec les données de la ligne sélectionnée
        JLabel nomLabel = new JLabel("Nom de la Matière:");
        panel.add(nomLabel, gbc);

        gbc.gridx = 1;
        JTextField nomField = new JTextField(nom);
        nomField.setPreferredSize(new Dimension(200, 25));
        panel.add(nomField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel codeLabel = new JLabel("Code de la Matière:");
        panel.add(codeLabel, gbc);

        gbc.gridx = 1;
        JTextField codeField = new JTextField(code);
        codeField.setPreferredSize(new Dimension(200, 25));
        panel.add(codeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel coefficientLabel = new JLabel("Coefficient:");
        panel.add(coefficientLabel, gbc);

        gbc.gridx = 1;
        JTextField coefficientField = new JTextField(Double.toString(coefficient));
        coefficientField.setPreferredSize(new Dimension(200, 25));
        panel.add(coefficientField, gbc);

        // Ajouter un bouton de confirmation
        JButton confirmButton = new JButton("Confirmer");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les données modifiées des champs de texte
                String newNom = nomField.getText();
                String newCode = codeField.getText();
                double newCoefficient = Double.parseDouble(coefficientField.getText());

                // Appeler la méthode pour modifier les données dans la base de données
                matiereOperationDB.modifierMatiere(id, newNom, newCode, newCoefficient);

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

    // Méthode pour rafraîchir le tableau
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) matiereTable.getModel();
        model.setRowCount(0);
        matiereOperationDB.afficherMatiere(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MatiereInterface();
            }
        });
    }
}
