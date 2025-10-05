package service;

import dao.CustomerDAO;
import model.Customer;

import java.util.List;

public class CustomerService {
    private CustomerDAO dao = new CustomerDAO();

    // nếu email tồn tại -> cập nhật thông tin, ngược lại insert
    public boolean addOrUpdateCustomer(Customer c) {
        if (c == null) return false;
        if (c.getEmail() == null || c.getEmail().trim().isEmpty()) {
            // nếu không có email, thử insert với tên (không chuẩn nhưng chấp nhận)
            return dao.insert(c);
        }
        Customer exist = dao.findByEmail(c.getEmail());
        if (exist == null) {
            return dao.insert(c);
        } else {
            // cập nhật tên/phone/address nếu có thay đổi
            c.setId(exist.getId());
            return dao.update(c);
        }
    }

    public boolean addCustomer(Customer c) {
        // dùng addOrUpdate để tránh duplicate email
        return addOrUpdateCustomer(c);
    }

    public boolean updateCustomer(Customer c) { return dao.update(c); }

    public boolean deleteCustomer(int id) { return dao.delete(id); }

    public List<Customer> getAllCustomers() { return dao.findAll(); }

    public Customer getCustomerByEmail(String email) { return dao.findByEmail(email); }

    public Customer getCustomerById(int id) { return dao.findById(id); }
}