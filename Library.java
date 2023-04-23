import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Library {
    private HashMap<String, Book> bookCatalog;
    private ArrayList<Book> rentedBooks;

    public Library() {
        bookCatalog = new HashMap<>();
        rentedBooks = new ArrayList<>();
    }

    public void addBook(Book book) {
        bookCatalog.put(book.getIsbn(), book);
        System.out.println("Book added successfully.");
    }

    public void deleteBook(String isbn) {
        Book book = bookCatalog.get(isbn);
        if (book != null) {
            bookCatalog.remove(isbn);
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    public void searchBook(String isbn) {
        Book book = bookCatalog.get(isbn);
        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("Book not found.");
        }
    }

    public void updateBook(String isbn, String title, String author, int year) {
        Book book = bookCatalog.get(isbn);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setYear(year);
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    public void rentBook(String isbn) {
        Book book = bookCatalog.get(isbn);
        if (book != null) {
            if (!book.isRented()) {
                book.setRented(true);
                rentedBooks.add(book);
                System.out.println("Book rented successfully.");
            } else {
                System.out.println("Book is already rented.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    public void returnBook(String isbn, int daysLate) {
        Book book = bookCatalog.get(isbn);
        if (book != null && book.isRented()) {
            double fine = daysLate * 0.5; // charge $0.50 per day late
            book.setRented(false);
            rentedBooks.remove(book);
            System.out.println("Book returned successfully. Fine charged: $" + fine);
        } else {
            System.out.println("Book not found or not rented.");
        }
    }

    public void displayAvailableBooks() {
        int count = 0;
        for (Book book : bookCatalog.values()) {
            if (!book.isRented()) {
                System.out.println(book);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No books available for rent.");
        }
    }

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter option:");
            System.out.println("1. Add book");
            System.out.println("2. Delete book");
            System.out.println("3. Search book");
            System.out.println("4. Update book");
            System.out.println("5. Rent book");
            System.out.println("6. Return book");
            System.out.println("7. Display available books");
            System.out.println("0. Exit");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline character

            switch (option) {
                case 1:
                    System.out.println("Enter ISBN:");
                    String isbn = scanner.nextLine();
                    System.out.println("Enter title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter author:");
                    String author = scanner.nextLine();
                    System.out.println("Enter year:");
                    int year = scanner.nextInt();
                    Book book = new Book(isbn, title, author, year);
                    library.addBook(book);
                    break;
                case 2:
                    System.out.println("Enter ISBN:");
                    isbn = scanner.nextLine();
                    library.deleteBook(isbn);
                    break;
                case 3:
                    System.out.println("Enter ISBN:");
                    isbn = scanner.nextLine();
                    library.searchBook(isbn);
                    break;
                case 4:
                    System.out.println("Enter ISBN:");
                    isbn = scanner.nextLine();
                    System.out.println("Enter new title (press enter to keep current title):");
                    title = scanner.nextLine();
                    System.out.println("Enter new author (press enter to keep current author):");
                    author = scanner.nextLine();
                    System.out.println("Enter new year (press 0 to keep current year):");
                    year = scanner.nextInt();
                    library.updateBook(isbn, title, author, year);
                    break;
                case 5:
                    System.out.println("Enter ISBN:");
                    isbn = scanner.nextLine();
                    library.rentBook(isbn);
                    break;
                case 6:
                    System.out.println("Enter ISBN:");
                    isbn = scanner.nextLine();
                    System.out.println("Enter number of days late:");
                    int daysLate = scanner.nextInt();
                    library.returnBook(isbn, daysLate);
                    break;
                case 7:
                    library.displayAvailableBooks();
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

class Book {
    private String isbn;
    private String title;
    private String author;
    private int year;
    private boolean rented;

    public Book(String isbn, String title, String author, int year) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.rented = false;
    }

    /* public class Book {
    private String title;
    private String author;
    private String type;
    private double rentalFee;
    private double damageFee;
    
    public Book(String title, String author, String type, double rentalFee, double damageFee) {
        this.title = title;
        this.author = author;
        this.type = type;
        this.rentalFee = rentalFee;
        this.damageFee = damageFee;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getType() {
        return type;
    }
    
    public double getRentalFee() {
        return rentalFee;
    }
    
    public double getDamageFee() {
        return damageFee;
    }
}

public class Rental {
    private Book book;
    private Date startDate;
    private Date endDate;
    private boolean isDamaged;
    
    public Rental(Book book, Date startDate, Date endDate) {
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDamaged = false;
    }
    
    public void reportDamage() {
        isDamaged = true;
    }
    
    public double calculateFee() {
        long daysRented = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
        double fee = daysRented * book.getRentalFee();
        if (isDamaged) {
            fee += book.getDamageFee();
        }
        return fee;
    }
}

// Example usage
Book book = new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "Science Fiction", 1.50, 5.00);
Rental rental = new Rental(book, new Date(), new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000))); // Rent for 7 days
double fee = rental.calculateFee();
System.out.println("Rental fee: $" + fee);
rental.reportDamage();
fee = rental.calculateFee();
System.out.println("Rental fee (damaged): $" + fee);
 */

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    @Override
    public String toString() {
        return "Book{ISBN=" + isbn + ", title=" + title + ", author=" + author + ", year=" + year + "}";
    }
}
