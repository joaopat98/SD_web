package Server;

import java.sql.*;

class QueryExecutor {
    static String connection;
    static String user;
    static String password;
    Connection c;

    public static void Init(String connection, String user, String password){
        QueryExecutor.connection = connection;
        QueryExecutor.user = user;
        QueryExecutor.password = password;
    }

    private QueryExecutor(Connection c) {
        this.c = c;
    }

    public static QueryExecutor newConnection() throws SQLException {
        return new QueryExecutor(DriverManager.getConnection(connection, user, password));
    }


    public ResultSet executeQuery(String sql) throws SQLException {
        if (sql.toUpperCase().startsWith("SELECT"))
            return executeSelect(sql);
        else
            return executeUpdate(sql);
    }

    public void close(){
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet executeSelect(String sql) throws SQLException {
        Statement stmt = c.createStatement();
        return stmt.executeQuery(sql);
    }

    private ResultSet executeUpdate(String sql) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.execute(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stmt.getGeneratedKeys();
        return rs;
    }
}