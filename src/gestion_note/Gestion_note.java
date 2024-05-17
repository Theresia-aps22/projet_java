package views;

import gestion_note.Login;

/**
 * La classe principale de votre application.
 */
public class Gestion_note {

    /**
     * La méthode principale de votre application.
     */
    public static void main(String[] args) {
        // Créer une instance de la fenêtre de connexion (gestion_note.Login)
        Login loginForm = new Login();
        
        // Afficher la fenêtre de connexion
        loginForm.setVisible(true);
    }
    
}
