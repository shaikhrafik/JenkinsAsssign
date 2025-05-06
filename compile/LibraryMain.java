package assignment1;

import java.util.List;
import java.util.Scanner;

public class LibraryMain {
    private static Library library;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        try {
            // Load library data from file
            library = Library.loadFromFile();
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
            library = new Library(); 
        }
        
        boolean exit = false;
        while (!exit) {
            try {
                printMainMenu();
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        bookManagement();
                        break;
                    case 2:
                        memberManagement();
                        break;
                    case 3:
                        borrowReturnManagement();
                        break;
                    case 4:
                        try {
                            library.saveToFile();
                        } catch (LibraryException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        System.out.println("Thank you for using the Library Management System!");
    }
    
    private static void printMainMenu() {
        System.out.println("\n---- LIBRARY MANAGEMENT SYSTEM ----");
        System.out.println("1. Book Management");
        System.out.println("2. Member Management");
        System.out.println("3. Borrow/Return Books");
        System.out.println("4. Save and Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static void bookManagement() {
        boolean back = false;
        while (!back) {
            System.out.println("\n---- BOOK MANAGEMENT ----");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search Book by ISBN");
            System.out.println("4. Search Books by Title");
            System.out.println("5. List All Books");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        removeBook();
                        break;
                    case 3:
                        searchBookByIsbn();
                        break;
                    case 4:
                        searchBooksByTitle();
                        break;
                    case 5:
                        listAllBooks();
                        break;
                    case 6:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static void addBook() throws LibraryException {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        
        Book book = new Book(title, author, isbn);
        library.addBook(book);
        System.out.println("Book added successfully.");
    }
    
    private static void removeBook() throws LibraryException {
        System.out.print("Enter ISBN of book to remove: ");
        String isbn = scanner.nextLine();
        
        Book book = library.removeBook(isbn);
        System.out.println("Book removed successfully: " + book.getTitle());
    }
    
    private static void searchBookByIsbn() throws LibraryException {
        System.out.print("Enter ISBN to search: ");
        String isbn = scanner.nextLine();
        
        Book book = library.findBookByIsbn(isbn);
        System.out.println("Book found: " + book);
    }
    
    private static void searchBooksByTitle() {
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine();
        
        List<Book> books = library.searchBooksByTitle(title);
        if (books.isEmpty()) {
            System.out.println("No books found with that title.");
        } else {
            System.out.println("Books found:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }
    
    private static void listAllBooks() {
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            System.out.println("All books in the library:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }
    
    private static void memberManagement() {
        boolean back = false;
        while (!back) {
            System.out.println("\n---- MEMBER MANAGEMENT ----");
            System.out.println("1. Add Member");
            System.out.println("2. Remove Member");
            System.out.println("3. Find Member by ID");
            System.out.println("4. List All Members");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        addMember();
                        break;
                    case 2:
                        removeMember();
                        break;
                    case 3:
                        findMemberById();
                        break;
                    case 4:
                        listAllMembers();
                        break;
                    case 5:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static void addMember() throws LibraryException {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();
        
        Member member = new Member(name, memberId);
        library.addMember(member);
        System.out.println("Member added successfully.");
    }
    
    private static void removeMember() throws LibraryException {
        System.out.print("Enter ID of member to remove: ");
        String memberId = scanner.nextLine();
        
        Member member = library.removeMember(memberId);
        System.out.println("Member removed successfully: " + member.getName());
    }
    
    private static void findMemberById() throws LibraryException {
        System.out.print("Enter member ID to search: ");
        String memberId = scanner.nextLine();
        
        Member member = library.findMemberById(memberId);
        System.out.println("Member found: " + member);
        
        // Display borrowed books
        List<Book> borrowedBooks = member.getBorrowedBooks();
        if (!borrowedBooks.isEmpty()) {
            System.out.println("Borrowed books:");
            for (Book book : borrowedBooks) {
                System.out.println(book);
            }
        } else {
            System.out.println("No books borrowed.");
        }
    }
    
    private static void listAllMembers() {
        List<Member> members = library.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members in the library.");
        } else {
            System.out.println("All members in the library:");
            for (Member member : members) {
                System.out.println(member);
            }
        }
    }
    
    private static void borrowReturnManagement() {
        boolean back = false;
        while (!back) {
            System.out.println("\n---- BORROW/RETURN MANAGEMENT ----");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        borrowBook();
                        break;
                    case 2:
                        returnBook();
                        break;
                    case 3:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static void borrowBook() throws LibraryException {
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();
        
        System.out.print("Enter ISBN of book to borrow: ");
        String isbn = scanner.nextLine();
        
        library.borrowBook(memberId, isbn);
        System.out.println("Book borrowed successfully.");
    }
    
    private static void returnBook() throws LibraryException {
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();
        
        System.out.print("Enter ISBN of book to return: ");
        String isbn = scanner.nextLine();
        
        library.returnBook(memberId, isbn);
        System.out.println("Book returned successfully.");
    }
}

