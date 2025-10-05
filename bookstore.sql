-- full_create_bookstore.sql
DROP DATABASE IF EXISTS BookStore;
CREATE DATABASE BookStore;
USE BookStore;

-- Users (password hashed with MD5 for simplicity)
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(60) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  fullname VARCHAR(120),
  login_count INT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Books
CREATE TABLE books (
  id INT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(60) UNIQUE,
  title VARCHAR(255) NOT NULL,
  author VARCHAR(200),
  category VARCHAR(100),
  price DECIMAL(12,2) NOT NULL,
  stock INT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Customers
CREATE TABLE customers (
  id INT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(60) UNIQUE,
  name VARCHAR(200),
  email VARCHAR(200),
  phone VARCHAR(50),
  address VARCHAR(300),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Orders (header)
CREATE TABLE orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  order_code VARCHAR(80) UNIQUE,
  customer_id INT NULL,
  status VARCHAR(40) DEFAULT 'PENDING',
  order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE SET NULL
);

-- Order details (items)
CREATE TABLE order_details (
  id INT AUTO_INCREMENT PRIMARY KEY,
  order_id INT,
  book_id INT,
  book_title VARCHAR(255),
  quantity INT,
  price DECIMAL(12,2),
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
  FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE SET NULL
);

-- Invoices
CREATE TABLE invoices (
  id INT AUTO_INCREMENT PRIMARY KEY,
  invoice_code VARCHAR(80) UNIQUE,
  order_id INT,
  total DECIMAL(12,2),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE SET NULL
);

-- Preorders (for books not in stock)
CREATE TABLE preorders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  customer_name VARCHAR(200),
  phone VARCHAR(50),
  email VARCHAR(200),
  book_title VARCHAR(255),
  quantity INT,
  status VARCHAR(30) DEFAULT 'PENDING',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Seed admin and sample books & customers
INSERT INTO users (username, password_hash, fullname) VALUES ('admin', MD5('123'), 'Administrator');

INSERT INTO books (code,title,author,category,price,stock) VALUES
('B001','Lập trình Java cơ bản','Nguyễn Văn A','CNTT',120000,10),
('B002','Cấu trúc dữ liệu','Lê Thị B','CNTT',150000,5);

INSERT INTO customers (code,name,email,phone,address) VALUES
('C001','Nguyễn Văn Khách','van.khach@example.com','0912345678','Hà Nội'),
('C002','Trần Thị Khách','thi.khach@example.com','0987654321','Hồ Chí Minh');
