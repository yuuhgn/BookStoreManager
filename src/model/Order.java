package model;

import java.sql.Timestamp;

public class Order {
    private int id;
    private String customerName;
    private String email;
    private String phone;
    private String bookName;
    private double unitPrice;
    private int quantity;
    private double total;
    private Timestamp orderDate;
    private String status;

    public Order() {}

    // dùng khi load từ DB (có id và orderDate)
    public Order(int id, String customerName, String email, String phone,
                 String bookName, double unitPrice, int quantity, double total,
                 Timestamp orderDate, String status) {
        this.id = id;
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
        this.bookName = bookName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = total;
        this.orderDate = orderDate;
        this.status = status;
    }

    // dùng khi thêm mới (chưa có id, orderDate sẽ set ở DAO)
    public Order(String customerName, String email, String phone,
                 String bookName, double unitPrice, int quantity, double total, String status) {
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
        this.bookName = bookName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = total;
        this.status = status;
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Order{id=" + id + ", customer=" + customerName + ", book=" + bookName + ", qty=" + quantity + ", total=" + total + "}";
    }
}