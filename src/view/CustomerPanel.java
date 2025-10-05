package view;

import model.Customer;
import service.CustomerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerPanel extends JPanel {
    private CustomerService service = new CustomerService();
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtId, txtName, txtEmail, txtPhone, txtAddress;

    public CustomerPanel() {
        setLayout(new BorderLayout());

        // ===== Bảng =====
        String[] columns = {"ID", "Tên KH", "Email", "SĐT", "Địa chỉ"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        loadData();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== Form nhập =====
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        txtId = new JTextField();
        txtId.setEnabled(false);
        txtName = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtAddress = new JTextField();

        formPanel.add(new JLabel("ID:")); formPanel.add(txtId);
        formPanel.add(new JLabel("Tên KH:")); formPanel.add(txtName);
        formPanel.add(new JLabel("Email:")); formPanel.add(txtEmail);
        formPanel.add(new JLabel("SĐT:")); formPanel.add(txtPhone);
        formPanel.add(new JLabel("Địa chỉ:")); formPanel.add(txtAddress);

        add(formPanel, BorderLayout.NORTH);

        // ===== Nút chức năng =====
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Làm mới");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.SOUTH);

        // ===== Sự kiện =====
        btnAdd.addActionListener(e -> addCustomer());
        btnUpdate.addActionListener(e -> updateCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnRefresh.addActionListener(e -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtEmail.setText(tableModel.getValueAt(row, 2).toString());
                txtPhone.setText(tableModel.getValueAt(row, 3).toString());
                txtAddress.setText(tableModel.getValueAt(row, 4).toString());
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Customer> list = service.getAllCustomers();
        for (Customer c : list) {
        tableModel.addRow(new Object[]{
                    c.getId(), c.getName(), c.getEmail(), c.getPhone(), c.getAddress()
            });
        }
    }

    private void addCustomer() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String address = txtAddress.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống!");
            return;
        }

        Customer c = new Customer(0, name, email, phone, address);
        if (service.addCustomer(c)) {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    }

    private void updateCustomer() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String address = txtAddress.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên không được để trống!");
                return;
            }

            Customer c = new Customer(id, name, email, phone, address);
            if (service.updateCustomer(c)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!");
        }
    }

    private void deleteCustomer() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng ID=" + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            if (service.deleteCustomer(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!");
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
    }
}    
        