package back;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Auth{
    private Connection connection;

    // Méthode pour vérifier les informations d'identification dans la base de données
    public boolean Auth(String username, String password) {

        try {
            // Connexion à la base de données en utilisant la classe ConnexionDB
            connection  = new ConnexionDB().getConnection();

            // Requête SQL pour récupérer l'utilisateur avec le nom d'utilisateur et le mot de passe donnés
            String query = "SELECT * FROM auth WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement  = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Si un résultat est retourné, cela signifie que l'utilisateur existe dans la base de données
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Si aucun résultat n'est trouvé ou s'il y a une exception, retourne false
        return false;
    }


    public static void main(String[] args) {
        // Point d'entrée pour tester la méthode
    }
}
