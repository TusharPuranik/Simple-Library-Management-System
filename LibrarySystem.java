import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private String isbn;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }
}

class Library {
    private String name;
    private String address;
    private List<Book> books;
    private List<Member> members;

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            System.out.println("Available Books:");
            for (Book book : books) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("--------------------");
            }
        }
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public List<Member> getMembers() {
        return members;
    }

    public void displayMembers() {
        if (members.isEmpty()) {
            System.out.println("No members in the library.");
        } else {
            System.out.println("Library Members:");
            for (Member member : members) {
                System.out.println("Name: " + member.getName());
                System.out.println("Address: " + member.getAddress());
                System.out.println("--------------------");
            }
        }
    }
}

class Member {
    private String name;
    private String address;
    private List<Book> borrowedBooks;

    public Member(String name, String address) {
        this.name = name;
        this.address = address;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void borrowBook(Library library, Book book) {
        if (library.getBooks().contains(book)) {
            borrowedBooks.add(book);
            library.getBooks().remove(book);
            System.out.println(name + " borrowed the book: " + book.getTitle());
        } else {
            System.out.println("The requested book is not available in the library.");
        }
    }

    public void returnBook(Library library, Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            library.addBook(book);
            System.out.println(name + " returned the book: " + book.getTitle());
        } else {
            System.out.println(name + " did not borrow this book.");
        }
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        // Create a library
        Library library = new Library("Public Library", "123 Main Street");

        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Library System menu
        while (true) {
            System.out.println("------- Library System Menu -------");
            System.out.println("1. View available books");
            System.out.println("2. Add a book");
            System.out.println("3. Borrow a book");
            System.out.println("4. Return a book");
            System.out.println("5. View all members");
            System.out.println("6. Add a member");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    library.displayBooks();
                    break;
                case 2:
                    System.out.print("Enter book title: ");
                    String bookTitleToAdd = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String bookAuthorToAdd = scanner.nextLine();
                    System.out.print("Enter book ISBN: ");
                    String bookIsbnToAdd = scanner.nextLine();
                    Book bookToAdd = new Book(bookTitleToAdd, bookAuthorToAdd, bookIsbnToAdd);
                    library.addBook(bookToAdd);
                    System.out.println("Book added successfully.");
                    break;
                case 3:
                    System.out.print("Enter member name: ");
                    String memberName = scanner.nextLine();
                    System.out.print("Enter member address: ");
                    String memberAddress = scanner.nextLine();
                    Member member = new Member(memberName, memberAddress);

                    if (library.getBooks().isEmpty()) {
                        System.out.println("No books available in the library.");
                        break;
                    }

                    System.out.print("Enter book title: ");
                    String bookTitle = scanner.nextLine();

                    boolean isBookFound = false;
                    for (Book book : library.getBooks()) {
                        if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                            member.borrowBook(library, book);
                            isBookFound = true;
                            break;
                        }
                    }
                    if (!isBookFound) {
                        System.out.println("Book not found in the library.");
                    }
                    break;
                case 4:
                    System.out.print("Enter member name: ");
                    String returningMemberName = scanner.nextLine();
                    System.out.print("Enter member address: ");
                    String returningMemberAddress = scanner.nextLine();
                    Member returningMember = new Member(returningMemberName, returningMemberAddress);

                    if (returningMember.getBorrowedBooks().isEmpty()) {
                        System.out.println("No books borrowed by this member.");
                        break;
                    }

                    System.out.print("Enter book title: ");
                    String returningBookTitle = scanner.nextLine();

                    boolean isReturned = false;
                    for (Book book : returningMember.getBorrowedBooks()) {
                        if (book.getTitle().equalsIgnoreCase(returningBookTitle)) {
                            returningMember.returnBook(library, book);
                            isReturned = true;
                            break;
                        }
                    }
                    if (!isReturned) {
                        System.out.println("Book not found in the member's borrowed list.");
                    }
                    break;
                case 5:
                    library.displayMembers();
                    break;
                case 6:
                    System.out.print("Enter member name: ");
                    String newMemberName = scanner.nextLine();
                    System.out.print("Enter member address: ");
                    String newMemberAddress = scanner.nextLine();
                    Member newMember = new Member(newMemberName, newMemberAddress);
                    library.addMember(newMember);
                    System.out.println("New member added successfully.");
                    break;
                case 7:
                    System.out.println("Exiting Library System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
