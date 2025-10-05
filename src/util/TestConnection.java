package util;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection c = DBConnection.getConnection()) {
            if (c != null && !c.isClosed()) {
                System.out.println("KẾT NỐI MySQL THÀNH CÔNG ✅");
            } else {
                System.out.println("Kết nối trả về null hoặc đã đóng.");
            }
        } catch (Exception e) {
            System.err.println("KẾT NỐI THẤT BẠI ❌");
            e.printStackTrace();
        }
    }
}