package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private double price;
    private int stock;
    private String description;

    public Book() {}

    public Book(int id, String title, String author, double price, int stock, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public Book(String title, String author, double price, int stock, String description) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}