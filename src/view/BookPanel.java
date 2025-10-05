package view;

import model.Book;
import service.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookPanel extends JPanel {
    private BookService service = new BookService();
    private JTable table;
    private DefaultTableModel tableModel;

    // Ô nhập liệu
    private JTextField txtId, txtTitle, txtAuthor, txtPrice, txtStock, txtDesc;

    public BookPanel() {
        setLayout(new BorderLayout());

        // ===== Bảng =====
        String[] columns = {"ID", "Tên sách", "Tác giả", "Giá", "Số lượng", "Mô tả"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        loadData();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== Form nhập liệu =====
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        txtId = new JTextField(); txtId.setEnabled(false); // id tự tăng
        txtTitle = new JTextField();
        txtAuthor = new JTextField();
        txtPrice = new JTextField();
        txtStock = new JTextField();
        txtDesc = new JTextField();

        formPanel.add(new JLabel("ID:")); formPanel.add(txtId);
        formPanel.add(new JLabel("Tên sách:")); formPanel.add(txtTitle);
        formPanel.add(new JLabel("Tác giả:")); formPanel.add(txtAuthor);
        formPanel.add(new JLabel("Giá:")); formPanel.add(txtPrice);
        formPanel.add(new JLabel("Số lượng:")); formPanel.add(txtStock);
        formPanel.add(new JLabel("Mô tả:")); formPanel.add(txtDesc);

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
        btnAdd.addActionListener(e -> addBook());
        btnUpdate.addActionListener(e -> updateBook());
        btnDelete.addActionListener(e -> deleteBook());
        btnRefresh.addActionListener(e -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtTitle.setText(tableModel.getValueAt(row, 1).toString());
                txtAuthor.setText(tableModel.getValueAt(row, 2).toString());
                txtPrice.setText(tableModel.getValueAt(row, 3).toString());
                txtStock.setText(tableModel.getValueAt(row, 4).toString());
                txtDesc.setText(tableModel.getValueAt(row, 5).toString());
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Book> list = service.getAllBooks();
        for (Book b : list) {
            tableModel.addRow(new Object[]{
                    b.getId(), b.getTitle(), b.getAuthor(), b.getPrice(), b.getStock(), b.getDescription()
            });
        }
    }

    private void addBook() {
        try {
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());
            String desc = txtDesc.getText();

            Book b = new Book(0, title, author, price, stock, desc);
            if (service.addBook(b)) {
                JOptionPane.showMessageDialog(this, "Thêm sách thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm sách thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu!");
        }
    }

    private void updateBook() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());
            String desc = txtDesc.getText();

            Book b = new Book(id, title, author, price, stock, desc);
            if (service.updateBook(b)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sách để sửa!");
        }
    }

    private void deleteBook() {
        try {
            int id = Integer.parseInt(txtId.getText());
            if (service.deleteBook(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sách để xóa!");
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        txtDesc.setText("");
    }
}