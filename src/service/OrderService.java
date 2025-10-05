package service;

import dao.OrderDAO;
import model.Order;

import java.util.List;

public class OrderService {
    private OrderDAO dao = new OrderDAO();
    private CustomerService customerService = new CustomerService();

    // thêm order + đồng thời thêm/cập nhật customer trong bảng customers
    public boolean addOrder(Order o) {
        boolean ok = dao.addOrder(o);
        if (ok) {
            // add or update customer record based on email
            try {
                // tạo customer từ order
                model.Customer c = new model.Customer(0, o.getCustomerName(), o.getEmail(), o.getPhone(), "");
                customerService.addOrUpdateCustomer(c);
            } catch (Exception ignored) {}
        }
        return ok;
    }

    public List<Order> getAllOrders() { return dao.getAllOrders(); }

    public boolean updateOrder(Order o) { return dao.updateOrder(o); }

    public boolean deleteOrder(int id) { return dao.deleteOrder(id); }

    public Order getOrderById(int id) { return dao.findById(id); }
}