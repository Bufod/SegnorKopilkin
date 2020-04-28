package sample;

import java.sql.*;

enum ConnectionInfo{
    HOST("localhost"),
    PORT("3306"),
    USER("root"),
    PASS("1234"),
    DB_NAME("segnorkopilkin");

    private String value;

    ConnectionInfo(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}

public class DBServer {

    Connection database;

    public DBServer() {
        try {
            database = getDbConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectStr = "jdbc:mysql://" + ConnectionInfo.HOST + ":" +
                ConnectionInfo.PORT + "/" + ConnectionInfo.DB_NAME +
                "?useUnicode=true&serverTimezone=UTC&useSSL=false";

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(connectStr, ConnectionInfo.USER.toString(),
                ConnectionInfo.PASS.toString());
    }

    public class Users{
        private final static String TABLE_NAME = "users";

        private final static String ID = "id";
        private final static String FIRSTNAME = "firstname";
        private final static String LASTNAME = "lastname";
        private final static String LOGIN = "login";
        private final static String PASSWORD = "password";

        private final static int NUM_COLUMN_ID = 1;
        private final static int NUM_COLUMN_FIRSTNAME = 2;
        private final static int NUM_COLUMN_LASTNAME = 3;
        private final static int NUM_COLUMN_LOGIN = 4;
        private final static int NUM_COLUMN_PASSWORD = 5;

        public boolean insert(int id, String firstname, String lastname,
                              String login, String password){
            String query = "INSERT INTO " + TABLE_NAME + "(" +
                    ID + "," + FIRSTNAME + "," + LASTNAME + "," + LOGIN + "," + PASSWORD
                    + ") values (?, ?, ?, ?, ?);";

            PreparedStatement prstmt = null;
            boolean ok = false;
            try {
                prstmt = database.prepareStatement(query);
                prstmt.setInt(1, id);
                prstmt.setString(2, firstname);
                prstmt.setString(3, lastname);
                prstmt.setString(4, login);
                prstmt.setString(5, password);
                ok = prstmt.execute();
                prstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ok;
        }

        public boolean insert(String firstname, String lastname,
                              String login, String password){
            String query = "INSERT INTO " + TABLE_NAME + "(" +
                    FIRSTNAME + "," + LASTNAME + "," + LOGIN + "," + PASSWORD
                    + ") values (?, ?, ?, ?);";

            PreparedStatement prstmt = null;
            boolean ok = false;
            try {
                prstmt = database.prepareStatement(query);
                prstmt.setString(1, firstname);
                prstmt.setString(2, lastname);
                prstmt.setString(3, login);
                prstmt.setString(4, password);
                ok = prstmt.execute();
                prstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ok;
        }

        public boolean checkFreeLogin(String login){
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                    LOGIN + "=?";
            ResultSet resultSet = null;
            boolean ok = false;
            try {
                PreparedStatement prstmt = database.prepareStatement(query,
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                prstmt.setString(1, login);
                resultSet = prstmt.executeQuery();
                ok = !resultSet.first();
                resultSet.close();
                prstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ok;
        }

        public User select(String login, String password){
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                    LOGIN + "=? AND " + PASSWORD + " =?";
            ResultSet resultSet = null;
            User user = null;
            try {
                PreparedStatement prstmt = database.prepareStatement(query,
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                prstmt.setString(1, login);
                prstmt.setString(2, password);
                resultSet = prstmt.executeQuery();
                if (resultSet.first()){
                    int id = resultSet.getInt(NUM_COLUMN_ID);
                    String firstname = resultSet.getString(NUM_COLUMN_FIRSTNAME),
                            lastname = resultSet.getString(NUM_COLUMN_LASTNAME);
                    user = new User(id, firstname, lastname, login, password);
                }
                resultSet.close();
                prstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return user;
        }
    }
}
