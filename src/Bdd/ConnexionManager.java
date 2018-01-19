package Bdd;

import Model.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class ConnexionManager {
    private static String url = "jdbc:mysql://localhost/orenjisaek";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "root";
    private static String password = "";
    private static Connection con;
    private static String urlstring;

    public static Connection getConnection() {
        BufferedReader br = null;
        String line;

        try {
            br = new BufferedReader(new FileReader("./databaseinfo.txt"));
            while ((line = br.readLine()) != null) {
                password = line;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        }
        return con;
    }

    public User login(String login, String password)
    {
        User user = new User();

        String sql = "SELECT * FROM user WHERE login='"+login+"' AND motdepasse='"+password+"';";

        try {
            Connection con = ConnexionManager.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData resultMeta = rs.getMetaData();

            System.out.println("\n**********************************");

            //On affiche le nom des colonnes

//            for(int i = 1; i <= resultMeta.getColumnCount(); i++)
//                System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
//
//            System.out.println("\n**********************************");

            if (!rs.isBeforeFirst())
            {
                user.setId(-1);
               // System.out.println("No Data");
            }
            else
            {
                while(rs.next()){
                    for(int i = 1; i <= resultMeta.getColumnCount(); i++)
                    {
                       // System.out.print("\t" + rs.getObject(i).toString() + "\t |");
                        user.setId(Integer.parseInt(rs.getString("id")));
                        user.setLogin(rs.getString("login"));
                        user.setPassword(rs.getString("motdepasse"));
                        user.setAvatar(rs.getString("avatar"));
                        user.setColor(rs.getString("couleur"));
                    }
                  //  System.out.println("\n---------------------------------");
                }
            }

            rs.close();
            stmt.close();

            return user;

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

            user.setId(-1);

            return user;
        }

        /*try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/orenjisaek","root","");
            // Do something with the Connection
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM user WHERE login='"+login+"' AND motdepasse='"+password+"';");
            ResultSetMetaData resultMeta = result.getMetaData();
            System.out.println("\n**********************************");
            //On affiche le nom des colonnes
            for(int i = 1; i <= resultMeta.getColumnCount(); i++)
                System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
            System.out.println("\n**********************************");
            if (!result.isBeforeFirst())
            {
                user.setId(-1);
                System.out.println("No Data");
            }
            else
            {
                while(result.next()){
                    for(int i = 1; i <= resultMeta.getColumnCount(); i++)
                    {
                        System.out.print("\t" + result.getObject(i).toString() + "\t |");
                        user.setId(Integer.parseInt(result.getString("id")));
                        user.setLogin(result.getString("login"));
                        user.setPassword(result.getString("motdepasse"));
                        user.setAvatar(result.getString("avatar"));
                        user.setColor(result.getString("couleur"));
                    }
                    System.out.println("\n---------------------------------");
                }
            }
            result.close();
            state.close();*/



            /*return user;
        } catch (SQLException ex) {
            // handle any errors
        e.printStackTrace();
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
        user.setId(-1);
        return user;
        }*/
    }
}


/*
Exemples d'utilisation de la classe:
private Connection con = null;
private Statement stmt = null;
private ResultSet rs = null;
con = ConnectionManager.getConnection();
stmt = con.createStatement();
rs = stmt.executeQuery(sql);
 */