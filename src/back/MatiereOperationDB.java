package back;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MatiereOperationDB {
    private Connection connection;

    public MatiereOperationDB(Connection connection) {
        this.connection = connection;
    }

    public void ajouterMatiere(String nomMatiere, String codeMatiere, float coefficient) {
        try {
            String query = "INSERT INTO matieres (nom_matiere, code_matiere, coefficient) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nomMatiere);
            preparedStatement.setString(2, codeMatiere);
            preparedStatement.setFloat(3, coefficient);
            preparedStatement.executeUpdate();
            System.out.println("Matière ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void afficherMatiere(DefaultTableModel model) {
        try {
            String requete = "SELECT nom_matiere, idMatiere, code_matiere, coefficient FROM matieres";
            Statement statement = connection.createStatement();
            ResultSet resultat = statement.executeQuery(requete);

            while (resultat.next()) {
                String nomMatiere = resultat.getString("nom_matiere");
                int idMatiere = resultat.getInt("idMatiere");
                String codeMatiere = resultat.getString("code_matiere");
                double coefficient = resultat.getDouble("coefficient");

                model.addRow(new Object[]{idMatiere, nomMatiere, codeMatiere, coefficient});
            }

            resultat.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierMatiere(int id, String nomMatiere, String codeMatiere, double coefficient) {
        try {
            String query = "UPDATE matieres SET nom_matiere = ?, code_matiere = ?, coefficient = ? WHERE idMatiere = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nomMatiere);
            preparedStatement.setString(2, codeMatiere);
            preparedStatement.setDouble(3, coefficient);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            System.out.println("Matière mise à jour avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerMatiere(int id) {
        try {
            String query = "DELETE FROM matieres WHERE idMatiere = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Matière supprimée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
