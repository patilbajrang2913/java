import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class Book {
    private String id;
    private String title;
    private String author;
    private boolean isAvailable;
    private Date dueDate;
    private String borrowerId;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.dueDate = null;
        this.borrowerId = null;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    public Date getDueDate() { return dueDate; }
    public String getBorrowerId() { return borrowerId; }

    public void borrowBook(String borrowerId, Date dueDate) {
        this.isAvailable = false;
        this.borrowerId = borrowerId;
        this.dueDate = dueDate;
    }

    public void returnBook() {
        this.isAvailable = true;
        this.borrowerId = null;
        this.dueDate = null;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String status = isAvailable ? "Available" : 
                       "Checked out to " + borrowerId + " (Due: " + sdf.format(dueDate) + ")";
        return "ID: " + id + " | Title: " + title + " | Author: " + author + " | Status: " + status;
    }
}

class Borrower {
    private String id;
    private String name;
    private List<String> borrowedBooks;

    public Borrower(String id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public List<String> getBorrowedBooks() { return borrowedBooks; }

    public void borrowBook(String bookId) {
        borrowedBooks.add(bookId);
    }

    public void returnBook(String bookId) {
        borrowedBooks.remove(bookId);
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Books Borrowed: " + borrowedBooks.size();
    }
}

public class LibraryManagementSystem {
    private Map<String, Book> books;
    private Map<String, Borrower> borrowers;
    private Scanner scanner;
    private SimpleDateFormat dateFormat;

    public LibraryManagementSystem() {
        books = new HashMap<>();
        borrowers = new HashMap<>();
        scanner = new Scanner(System.in);
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        
        // Initialize with some sample data
        initializeSampleData();
    }

    private void initializeSampleData() {
        books.put("B001", new Book("B001", "Java Programming", "John Doe"));
        books.put("B002", new Book("B002", "Python Basics", "Jane Smith"));
        books.put("B003", new Book("B003", "Data Structures", "Mike Johnson"));
        
        borrowers.put("M001", new Borrower("M001", "Alice Brown"));
        borrowers.put("M002", new Borrower("M002", "Bob Wilson"));
    }

    public void run() {
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add Borrower");
            System.out.println("3. Check Out Book");
            System.out.println("4. Return Book");
            System.out.println("5. View All Books");
            System.out.println("6. View All Borrowers");
            System.out.println("7. View Overdue Books");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    addBorrower();
                    break;
                case 3:
                    checkOutBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    viewAllBooks();
                    break;
                case 6:
                    viewAllBorrowers();
                    break;
                case 7:
                    viewOverdueBooks();
                    break;
                case 8:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addBook() {
        System.out.print("Enter book ID: ");
        String id = scanner.nextLine();
        
        if (books.containsKey(id)) {
            System.out.println("Book ID already exists!");
            return;
        }
        
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        
        books.put(id, new Book(id, title, author));
        System.out.println("Book added successfully!");
    }

    private void addBorrower() {
        System.out.print("Enter borrower ID: ");
        String id = scanner.nextLine();
        
        if (borrowers.containsKey(id)) {
            System.out.println("Borrower ID already exists!");
            return;
        }
        
        System.out.print("Enter borrower name: ");
        String name = scanner.nextLine();
        
        borrowers.put(id, new Borrower(id, name));
        System.out.println("Borrower added successfully!");
    }

    private void checkOutBook() {
        System.out.print("Enter book ID to check out: ");
        String bookId = scanner.nextLine();
        
        Book book = books.get(bookId);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        
        if (!book.isAvailable()) {
            System.out.println("Book is already checked out!");
            return;
        }
        
        System.out.print("Enter borrower ID: ");
        String borrowerId = scanner.nextLine();
        
        Borrower borrower = borrowers.get(borrowerId);
        if (borrower == null) {
            System.out.println("Borrower not found!");
            return;
        }
        
        try {
            System.out.print("Enter due date (MM/dd/yyyy): ");
            Date dueDate = dateFormat.parse(scanner.nextLine());
            
            book.borrowBook(borrowerId, dueDate);
            borrower.borrowBook(bookId);
            System.out.println("Book checked out successfully!");
        } catch (Exception e) {
            System.out.println("Invalid date format!");
        }
    }

    private void returnBook() {
        System.out.print("Enter book ID to return: ");
        String bookId = scanner.nextLine();
        
        Book book = books.get(bookId);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        
        if (book.isAvailable()) {
            System.out.println("Book is not checked out!");
            return;
        }
        
        Borrower borrower = borrowers.get(book.getBorrowerId());
        if (borrower != null) {
            borrower.returnBook(bookId);
        }
        
        book.returnBook();
        System.out.println("Book returned successfully!");
    }

    private void viewAllBooks() {
        System.out.println("\nAll Books:");
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            for (Book book : books.values()) {
                System.out.println(book);
            }
        }
    }

    private void viewAllBorrowers() {
        System.out.println("\nAll Borrowers:");
        if (borrowers.isEmpty()) {
            System.out.println("No borrowers registered.");
        } else {
            for (Borrower borrower : borrowers.values()) {
                System.out.println(borrower);
            }
        }
    }

    private void viewOverdueBooks() {
        System.out.println("\nOverdue Books:");
        Date today = new Date();
        boolean found = false;
        
        for (Book book : books.values()) {
            if (!book.isAvailable() && book.getDueDate() != null && 
                book.getDueDate().before(today)) {
                System.out.println(book);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No overdue books found.");
        }
    }

    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.run();
    }
}