package view;

import model.Order;
import service.OrderService;
import service.BookService;
import model.Book;
import service.CustomerService;
import model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class OrderPanel extends JPanel {
    private OrderService orderService = new OrderService();
    private BookService bookService = new BookService(); // để lấy sách trên kệ
    private CustomerService customerService = new CustomerService();

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtCustomer, txtEmail, txtPhone, txtPrice, txtQuantity;
    private JComboBox<String> cbBook; // show "id - title - price"

    public OrderPanel() {
        setLayout(new BorderLayout());

        // Table
        String[] cols = {"ID", "Tên KH", "Email", "SĐT", "Sách", "Giá 1 quyển", "Số lượng", "Tổng tiền", "Ngày"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        loadData();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form
        JPanel form = new JPanel(new GridLayout(6, 2, 6, 6));
        txtCustomer = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        cbBook = new JComboBox<>();
        txtPrice = new JTextField();
        txtQuantity = new JTextField();

        form.add(new JLabel("Tên khách hàng:")); form.add(txtCustomer);
        form.add(new JLabel("Email:")); form.add(txtEmail);
        form.add(new JLabel("Số điện thoại:")); form.add(txtPhone);
        form.add(new JLabel("Sách (chọn):")); form.add(cbBook);
        form.add(new JLabel("Giá 1 quyển:")); form.add(txtPrice);
        form.add(new JLabel("Số lượng:")); form.add(txtQuantity);

        add(form, BorderLayout.NORTH);

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm đơn");
        JButton btnRefresh = new JButton("Làm mới");
        btnPanel.add(btnAdd); btnPanel.add(btnRefresh);
        add(btnPanel, BorderLayout.SOUTH);

        // populate books into combo
        loadBooksIntoCombo();

        // when select book -> fill price
        cbBook.addActionListener(e -> {
            String sel = (String) cbBook.getSelectedItem();
            if (sel != null && !sel.isEmpty()) {
                // format: id - title - price
                String[] parts = sel.split(" - ");
                if (parts.length >= 3) {
                    txtPrice.setText(parts[2]);
                }
            }
        });

        btnAdd.addActionListener(e -> {
            try {
                String name = txtCustomer.getText().trim();
                String email = txtEmail.getText().trim();
                String phone = txtPhone.getText().trim();
                String bookSel = (String) cbBook.getSelectedItem();
                if (bookSel == null) { JOptionPane.showMessageDialog(this, "Chọn sách."); return; }
                String[] parts = bookSel.split(" - ");
                String bookName = parts[1];
                double price = Double.parseDouble(txtPrice.getText().trim());
                int qty = Integer.parseInt(txtQuantity.getText().trim());
                double total = price * qty;
                String status = "Đang xử lý";
                // create order
                Order o = new Order(name, email, phone, bookName, price, qty, total, status);
                boolean ok = orderService.addOrder(o);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Đã thêm vào Customers");
                    // also ensure customer saved/updated (orderService already tries)
                    // refresh tables
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm đơn hàng thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá và số lượng phải là số hợp lệ.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });

        btnRefresh.addActionListener(e -> {
            loadData();
            loadBooksIntoCombo();
        });
    }

    private void loadBooksIntoCombo() {
        cbBook.removeAllItems();
        List<Book> books = bookService.getAllBooks();
        for (Book b : books) {
            // item: id - title - price
            cbBook.addItem(b.getId() + " - " + b.getTitle() + " - " + b.getPrice());
        }
        if (cbBook.getItemCount() > 0) cbBook.setSelectedIndex(0);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Order> list = orderService.getAllOrders();
        for (Order o : list) {
            String date = o.getOrderDate() == null ? "" : o.getOrderDate().toString();
            tableModel.addRow(new Object[]{
                    o.getId(), o.getCustomerName(), o.getEmail(), o.getPhone(),
                    o.getBookName(), o.getUnitPrice(), o.getQuantity(), o.getTotal(), date
            });
        }
    }
}