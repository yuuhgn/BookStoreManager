package dao;

import model.Order;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public boolean addOrder(Order o) {
        String sql = "INSERT INTO orders(customer_name,email,phone,book_name,unit_price,quantity,total,order_date,status) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, o.getCustomerName());
            ps.setString(2, o.getEmail());
            ps.setString(3, o.getPhone());
            ps.setString(4, o.getBookName());
            ps.setDouble(5, o.getUnitPrice());
            ps.setInt(6, o.getQuantity());
            ps.setDouble(7, o.getTotal());
            // nếu Order đã có orderDate thì dùng, nếu không dùng thời gian hiện tại
            Timestamp ts = o.getOrderDate() != null ? o.getOrderDate() : new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(8, ts);
            ps.setString(9, o.getStatus());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) o.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT id, customer_name, email, phone, book_name, unit_price, quantity, total, order_date, status FROM orders ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order o = new Order(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("book_name"),
                        rs.getDouble("unit_price"),
                        rs.getInt("quantity"),
                        rs.getDouble("total"),
                        rs.getTimestamp("order_date"),
                        rs.getString("status")
                );
                list.add(o);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public boolean updateOrder(Order o) {
        String sql = "UPDATE orders SET customer_name=?, email=?, phone=?, book_name=?, unit_price=?, quantity=?, total=?, order_date=?, status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, o.getCustomerName());
            ps.setString(2, o.getEmail());
            ps.setString(3, o.getPhone());
            ps.setString(4, o.getBookName());
            ps.setDouble(5, o.getUnitPrice());
            ps.setInt(6, o.getQuantity());
            ps.setDouble(7, o.getTotal());
            ps.setTimestamp(8, o.getOrderDate() != null ? o.getOrderDate() : new Timestamp(System.currentTimeMillis()));
            ps.setString(9, o.getStatus());
            ps.setInt(10, o.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Order findById(int id) {
        String sql = "SELECT id, customer_name, email, phone, book_name, unit_price, quantity, total, order_date, status FROM orders WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                            rs.getInt("id"),
                            rs.getString("customer_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("book_name"),
                            rs.getDouble("unit_price"),
                            rs.getInt("quantity"),
                            rs.getDouble("total"),
                            rs.getTimestamp("order_date"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
        