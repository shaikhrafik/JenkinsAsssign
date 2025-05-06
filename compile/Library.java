package assignment1;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library implements Serializable {
    private Map<String, Book> books; // ISBN -> Book
    private Map<String, Member> members; // memberID -> Member
    private static final String DATA_FILE = "library_data.ser";
    
    // Constructor
    public Library() {
        this.books = new HashMap<>();
        this.members = new HashMap<>();
    }
    
    // Book management methods
    public void addBook(Book book) throws LibraryException {
        if (book == null) {
            throw new LibraryException("Book cannot be null");
        }
        if (books.containsKey(book.getIsbn())) {
            throw new LibraryException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        books.put(book.getIsbn(), book);
    }
    
    public Book removeBook(String isbn) throws LibraryException {
        Book book = findBookByIsbn(isbn);
        if (!book.isAvailable()) {
            throw new LibraryException("Book is currently borrowed and cannot be removed");
        }
        books.remove(isbn);
        return book;
    }
    
    public Book findBookByIsbn(String isbn) throws LibraryException {
        Book book = books.get(isbn);
        if (book == null) {
            throw new LibraryException("Book with ISBN " + isbn + " not found");
        }
        return book;
    }
    
    public List<Book> searchBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
    
    // Member management methods
    public void addMember(Member member) throws LibraryException {
        if (member == null) {
            throw new LibraryException("Member cannot be null");
        }
        if (members.containsKey(member.getMemberId())) {
            throw new LibraryException("Member with ID " + member.getMemberId() + " already exists");
        }
        members.put(member.getMemberId(), member);
    }
    
    public Member removeMember(String memberId) throws LibraryException {
        Member member = findMemberById(memberId);
        if (!member.getBorrowedBooks().isEmpty()) {
            throw new LibraryException("Member has borrowed books and cannot be removed");
        }
        members.remove(memberId);
        return member;
    }
    
    public Member findMemberById(String memberId) throws LibraryException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new LibraryException("Member with ID " + memberId + " not found");
        }
        return member;
    }
    
    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }
    
    // Book borrowing and returning
    public void borrowBook(String memberId, String isbn) throws LibraryException {
        Member member = findMemberById(memberId);
        Book book = findBookByIsbn(isbn);
        
        if (!book.isAvailable()) {
            throw new LibraryException("Book is not available for borrowing");
        }
        
        member.borrowBook(book);
    }
    
    public void returnBook(String memberId, String isbn) throws LibraryException {
        Member member = findMemberById(memberId);
        Book book = findBookByIsbn(isbn);
        
        boolean returned = false;
        for (Book borrowedBook : member.getBorrowedBooks()) {
            if (borrowedBook.getIsbn().equals(isbn)) {
                returned = member.returnBook(borrowedBook);
                break;
            }
        }
        
        if (!returned) {
            throw new LibraryException("Member " + memberId + " has not borrowed book with ISBN " + isbn);
        }
    }
    
    // File handling methods
    public void saveToFile() throws LibraryException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(this);
            System.out.println("Library data saved successfully.");
        } catch (IOException e) {
            throw new LibraryException("Error saving library data: " + e.getMessage());
        }
    }
    
    public static Library loadFromFile() throws LibraryException {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new Library();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Library library = (Library) ois.readObject();
            System.out.println("Library data loaded successfully.");
            return library;
        } catch (IOException | ClassNotFoundException e) {
            throw new LibraryException("Error loading library data: " + e.getMessage());
        }
    }
}