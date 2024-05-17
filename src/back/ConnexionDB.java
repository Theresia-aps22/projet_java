package back;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {
    // Informations sur la connexion à la base de données
    static final String URL = "jdbc:mysql://localhost:3306/projet_java";
    static final String USER = "root";
    static final String PASSWORD = "";

    private static Connection connection;

    public ConnexionDB() {
        try {
            // Établir une connexion à la base de données
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Vérifier si la connexion est réussie
            if (connection != null) {
                System.out.println("Connexion établie avec succès !");
            } else {
                System.out.println("Échec de la connexion !");
            }
        } catch (SQLException e) {
            // Gérer les exceptions liées à la connexion à la base de données
            e.printStackTrace();
        }
    }

    // Méthode pour obtenir la connexion à la base de données
    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        // Crée une instance de Connexion dans la méthode main
        new ConnexionDB();
    }
}
