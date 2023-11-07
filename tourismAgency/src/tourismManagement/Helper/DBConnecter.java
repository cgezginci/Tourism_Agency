package tourismManagement.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnecter {//Bağlantıyı kolaylaştırmak adına işlemler

    private Connection connect = null;

    public Connection connectDB(){
        try {
            this.connect = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return this.connect;
    }

    public static Connection getInstance(){
        DBConnecter db = new DBConnecter();
        return db.connectDB();
    }
}
