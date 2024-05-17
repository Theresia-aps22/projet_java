package back;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NoteOperationDB {
    private Connection connection;

    public NoteOperationDB(){
        // Créer une connexion à la base de données en utilisant ConnexionDB
        connection = new ConnexionDB().getConnection();
        if (connection != null) {
            System.out.println("Connexion établie avec succès !");
        } else {
            System.out.println("Échec de la connexion !");
        }
    }
    // Fonction pour afficher les données
    public ArrayList<Object[]> afficherNote(){
        ArrayList<Object[]> data = new ArrayList<>();

        // Requête SQL pour afficher les notes
        String query = "SELECT e.nom AS nom_etudiant, e.prenom AS prenom_etudiant, e.matricule AS matricule_etudiant, n.note, m.coefficient " +
                "FROM note n " + // Utilisation de la table "notes" pour récupérer les notes
                "JOIN etudiants e ON n.idEtudiant = e.idEtudiant " +
                "JOIN matieres m ON m.idMatiere = n.idMatiere";

        try {
            // Préparer la requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Parcourir les résultats de la requête
            while (resultSet.next()) {
                String nom = resultSet.getString("nom_etudiant");
                String prenom = resultSet.getString("prenom_etudiant");
                String matricule = resultSet.getString("matricule_etudiant");
                double note = resultSet.getDouble("note");
                double coefficient = resultSet.getDouble("coefficient");
                float noteAvecCoeffiscient = (float) (note * coefficient);

                // Ajouter les données à la liste
                Object[] rowData = {nom, prenom, matricule, note, coefficient, noteAvecCoeffiscient};
                data.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Cette ligne imprime la trace de la pile de l'exception
            System.out.println("Erreur lors de l'affichage des étudiants : " + e.getMessage()); // Cette ligne imprime le message d'erreur spécifique
        }
        return data;
    }
    public ArrayList<Object[]> afficherNoteParMatiere(int idMatiere) {
        ArrayList<Object[]> data = new ArrayList<>();

        // Requête SQL pour afficher les notes selon la matière
        String query = "SELECT e.nom AS nom_etudiant, e.prenom AS prenom_etudiant, e.matricule AS matricule_etudiant, n.note, m.coefficient " +
                "FROM note n " +
                "JOIN etudiants e ON n.idEtudiant = e.idEtudiant " +
                "JOIN matieres m ON m.idMatiere = n.idMatiere " +
                "WHERE n.idMatiere = ?";

        try {
            // Préparer la requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idMatiere);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Parcourir les résultats de la requête
            while (resultSet.next()) {
                String nom = resultSet.getString("nom_etudiant");
                String prenom = resultSet.getString("prenom_etudiant");
                String matricule = resultSet.getString("matricule_etudiant");
                double note = resultSet.getDouble("note");
                double coefficient = resultSet.getDouble("coefficient");
                float noteAvecCoeffiscient = (float) (note * coefficient);

                // Ajouter les données à la liste
                Object[] rowData = {nom, prenom, matricule, note, coefficient, noteAvecCoeffiscient};
                data.add(rowData);
                // Trier les données en fonction des notes, du plus grand au plus petit
                Collections.sort(data, new Comparator<Object[]>() {
                    @Override
                    public int compare(Object[] o1, Object[] o2) {
                        return Double.compare((double) o2[3], (double) o1[3]); // Comparaison inverse pour un tri décroissant
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'affichage des étudiants.");
        }
        return data;
    }


    // Méthode pour récupérer les codes de matière depuis la base de données
    public String[] recupererCodesMatieres() {
        List<String> codesMatieres = new ArrayList<>();
        String query = "SELECT code_matiere FROM matieres";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String codeMatiere = resultSet.getString("code_matiere");
                codesMatieres.add(codeMatiere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des codes de matières depuis la base de données.");
        }
        return codesMatieres.toArray(new String[0]);
    }

    // Fonction pour récupérer l'id étudiants via le matricule
    public int recupererIdEtudiant(String matricule){
        int idEtudiant = -1; // Valeur par défaut si l'étudiant n'est pas trouvé
        String query = "SELECT idEtudiant FROM etudiants WHERE matricule=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idEtudiant = resultSet.getInt("idEtudiant");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération de l'ID de l'étudiant par matricule.");
        }
        return idEtudiant;
    }
    // Fonction pour récupérer l'id étudiants via le matricule
    public int recupererIdMatiere(String codeMatiere){
        int idMatiere = -1; // Valeur par défaut si la matière n'est pas trouvée
        String query = "SELECT idMatiere FROM matieres WHERE code_matiere=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, codeMatiere);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idMatiere = resultSet.getInt("idMatiere");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération de l'ID de la matière par nom.");
        }
        return idMatiere;
    }

    // Fonction pour ajouter les Notes
    public void ajouterNote(String matricule, String codeMatiere, double note) {
        int idEtudiant = recupererIdEtudiant(matricule);
        int idMatiere = recupererIdMatiere(codeMatiere);
        String query = "INSERT INTO note (note, idEtudiant, idMatiere) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, note);
            preparedStatement.setInt(2, idEtudiant);
            preparedStatement.setInt(3, idMatiere);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Note ajoutée avec succès !");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la note !");
        }
    }



    // Fonction pour modifier une note
    public void modifierNote(String matricule, String codeMatiere, double nouvelleNote) {
        int idEtudiant = recupererIdEtudiant(matricule);
        int idMatiere = recupererIdMatiere(codeMatiere);

        if (idEtudiant != -1 && idMatiere != -1) {
            String query = "UPDATE note SET note=? WHERE idMatiere = ? AND idEtudiant=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDouble(1, nouvelleNote);
                preparedStatement.setInt(2, idMatiere);
                preparedStatement.setInt(3, idEtudiant);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Note modifiée avec succès !");
                } else {
                    System.out.println("Aucune note modifiée. Vérifiez les paramètres.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la modification de la note : " + e.getMessage());
            }
        } else {
            System.out.println("Étudiant ou matière spécifié non trouvé.");
        }
    }



    // Fonction pour supprimer une note
    public void supprimerNote(String matricule, String codeMatiere) {
        int idEtudiant = recupererIdEtudiant(matricule);
        int idMatiere = recupererIdMatiere(codeMatiere);
        if (idEtudiant != -1 && idMatiere != -1) {
            String query = "DELETE FROM note WHERE idEtudiant=? AND idMatiere=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idEtudiant);
                preparedStatement.setInt(2, idMatiere);
                preparedStatement.executeUpdate();
                System.out.println("Note supprimée avec succès !");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la suppression de la note.");

            }
        } else {
            System.out.println("Étudiant ou matière spécifié non trouvé.");
        }
    }

    //récupérer la liste des étudiants
    public ArrayList<String> recupererListeEtudiants() {
        String query = "SELECT matricule, nom, prenom FROM etudiants";
        ArrayList<String> data = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String matricule = resultSet.getString("matricule");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                data.add(matricule + " " + nom + " " + prenom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'affichage des étudiants.");
        }
        return data;
    }

    public ArrayList<Object[]> afficherNotesEtudiant(String matricule) {
        String query = "SELECT m.nom_matiere, n.note, m.coefficient, " +
                "(n.note * m.coefficient) AS note_avec_coefficient " +
                "FROM note n " +
                "JOIN etudiants e ON n.idEtudiant = e.idEtudiant " +
                "JOIN matieres m ON m.idMatiere = n.idMatiere " +
                "WHERE e.matricule = ?";
        ArrayList<Object[]> data = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nomMatiere = resultSet.getString("nom_matiere");
                double note = resultSet.getDouble("note");
                int coefficient = resultSet.getInt("coefficient");
                double noteAvecCoefficient = resultSet.getDouble("note_avec_coefficient");
                data.add(new Object[]{nomMatiere, note, coefficient, noteAvecCoefficient});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'affichage des notes de l'étudiant.");
        }
        return data;
    }

    public double calculerMoyenneGenerale(String matricule) {
        String query = "SELECT AVG(n.note * m.coefficient) / AVG(m.coefficient) AS moyenne_generale " +
                "FROM note n " +
                "JOIN etudiants e ON n.idEtudiant = e.idEtudiant " +
                "JOIN matieres m ON n.idMatiere = m.idMatiere " +
                "WHERE e.matricule = ?";
        double moyenneGenerale = 0.0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                moyenneGenerale = resultSet.getDouble("moyenne_generale");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du calcul de la moyenne générale.");
        }
        return moyenneGenerale;
    }

    public int obtenirRang(String matricule) {
        // La fonction RANK() attribue un rang à chaque ligne de la sous-requête basé sur l'ordre décroissant des moyennes générales calculées.
        String query = "SELECT matricule, RANK() OVER (ORDER BY moyenne_generale DESC) AS rang " +
                "FROM ( " +
                "    SELECT e.matricule, " +
                "           SUM(n.note * m.coefficient) / SUM(m.coefficient) AS moyenne_generale " +
                "    FROM note n " +
                "    JOIN etudiants e ON n.idEtudiant = e.idEtudiant " +
                "    JOIN matieres m ON n.idMatiere = m.idMatiere " +
                "    GROUP BY e.matricule " +
                ") AS sous_requete " +
                "WHERE matricule = ?";
        int rang = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rang = resultSet.getInt("rang");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'obtention du rang.");
        }
        return rang;
    }


    public static void main(String[] args) {


    }


}
