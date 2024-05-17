package back;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EtudiantOperationDB {
    private Connection connection;

    public EtudiantOperationDB(Connection connection) {
        this.connection = connection;
    }

    public void ajouterEtudiant(String nom, String prenom, String matricule) {
        try {
            String query = "INSERT INTO etudiants (nom, prenom, matricule) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, matricule);
            preparedStatement.executeUpdate();
            System.out.println("Etudiant ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void afficherEtudiant(DefaultTableModel model) {
        try {
            String requete = "SELECT nom, idEtudiant, prenom, matricule FROM etudiants";
            Statement statement = connection.createStatement();
            ResultSet resultat = statement.executeQuery(requete);

            while (resultat.next()) {
                String nom = resultat.getString("nom");
                int id = resultat.getInt("idEtudiant");
                String prenom = resultat.getString("prenom");
                double matricule = resultat.getDouble("matricule");

                model.addRow(new Object[]{id, nom, prenom, matricule});
            }

            resultat.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierEtudiant(int id, String nom, String prenom, String matricule) {
        try {
            String query = "UPDATE etudiants SET nom = ?, prenom = ?, matricule = ? WHERE idEtudiant = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, matricule);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            System.out.println("Etudiant mise à jour avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerEtudiant(int id) {
        try {
            String query = "DELETE FROM etudiants WHERE idEtudiant = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Etudiant supprimée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
