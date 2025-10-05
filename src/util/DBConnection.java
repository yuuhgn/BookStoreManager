package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // sửa USER/PASSWORD nếu khác
    private static final String URL = "jdbc:mysql://localhost:3306/bookstore?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "nghuy14th@1";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // đăng ký driver
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver không tìm thấy. Thêm mysql-connector vào classpath.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}