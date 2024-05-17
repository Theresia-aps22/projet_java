package gestion_note;

import gestion_note.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeInterface extends JFrame {

    public HomeInterface() {
        // Définir les propriétés de la fenêtre
        setTitle("Gestion de Notes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer l'application lorsque la fenêtre est fermée
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran

        // Panel principal avec gestionnaire de disposition BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);

        // Panel pour le titre
        JPanel titlePanel = new JPanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Créer un libellé pour le titre
        JLabel titleLabel = new JLabel("Gestion de Notes");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titlePanel.add(titleLabel);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20)); // 1 ligne, 3 colonnes, avec un espacement de 20 pixels entre les composants
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Ajouter une marge autour du panneau
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Créer des panneaux personnalisés pour simuler des boutons
        ClickablePanel matierePanel = createStyledPanel("C:\\Users\\Theresia\\IdeaProjects\\Gestion Note\\src\\gestion_note\\images\\matière-removebg-preview(1).png", "Matière");
        ClickablePanel etudiantPanel = createStyledPanel("C:\\Users\\Theresia\\IdeaProjects\\Gestion Note\\src\\gestion_note\\images\\student(1).png", "Étudiant");
        ClickablePanel notePanel = createStyledPanel("C:\\Users\\Theresia\\IdeaProjects\\Gestion Note\\src\\gestion_note\\images\\note(1).png", "Note");

        // Ajouter les panneaux personnalisés au panel des boutons
        buttonPanel.add(matierePanel);
        buttonPanel.add(etudiantPanel);
        buttonPanel.add(notePanel);

        // Panel pour le bouton de déconnexion
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton logoutButton = new JButton("Déconnexion");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à effectuer lors de la déconnexion
                JOptionPane.showMessageDialog(null, "Déconnexion...");
                // Ajoutez ici le code pour effectuer la déconnexion

                // Redirection vers la page de connexion
                Login loginPage = new Login();
                loginPage.setVisible(true);
                dispose(); // Fermer la fenêtre actuelle
            }
        });
        logoutPanel.add(logoutButton);
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);
        // Action pour le bouton "Matière"
        matierePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MatiereInterface matiereInterface = new MatiereInterface();
                matiereInterface.setVisible(true);
            }
        });

        // Action pour le bouton "Étudiant"
        etudiantPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EtudiantInterface etudiantInterface = new EtudiantInterface();
                etudiantInterface.setVisible(true);
            }
        });

        // Action pour le bouton "Note"
        notePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NoteInterface noteInterface = new NoteInterface();
                noteInterface.setVisible(true);
            }
        });
    }

    // Classe pour les panneaux personnalisés simulant des boutons
    private class ClickablePanel extends JPanel {
        private String buttonText;
        private Image image;

        public ClickablePanel(String imagePath, String buttonText) {
            this.buttonText = buttonText;
            ImageIcon icon = new ImageIcon(imagePath);
            image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Redimensionner l'image
            setPreferredSize(new Dimension(200, 200)); // Définir la taille du panneau
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Changer le curseur au survol
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Dessiner le fond coloré
            g.setColor(new Color(52, 152, 219));
            g.fillRect(0, 0, getWidth(), getHeight());
            // Dessiner l'image de fond
            int imageSize = Math.min(getWidth(), getHeight()) - 40; // Calculer la taille maximale de l'image
            int x = (getWidth() - imageSize) / 2;
            int y = (getHeight() - imageSize) / 2;
            g.drawImage(image, x, y, imageSize, imageSize, this);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            // Centrer le texte dans le panneau en bas
            FontMetrics metrics = g.getFontMetrics();
            int textWidth = metrics.stringWidth(buttonText);
            int textX = (getWidth() - textWidth) / 2;
            int textY = getHeight() - metrics.getHeight() / 4; // Positionner le texte au bas du panneau
            g.drawString(buttonText, textX, textY);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200); // Définir la taille fixe du panneau
        }
    }

    // Méthode utilitaire pour créer des panneaux personnalisés simulant des boutons
    private ClickablePanel createStyledPanel(String imagePath, String buttonText) {
        return new ClickablePanel(imagePath, buttonText);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeInterface homeInterface = new HomeInterface();
            homeInterface.setVisible(true); // Rendre la fenêtre visible
        });
    }
}
