package src.main.utcluj.ssatr.book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class BookRentalApp {

    private Connection con;
    private PreparedStatement insertRental;
    private PreparedStatement selectAvailableBooks;
    private PreparedStatement updateBookStatus;

    public BookRentalApp() {
        // Connect to the database
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/library;create=true", "root", "password");
            // Prepare the SQL statements
            insertRental = con.prepareStatement("INSERT INTO rentals (book_id, renter_name, renter_email, renter_phone, rental_date, return_date) VALUES (?,?,?,?,?,?)");
            selectAvailableBooks = con.prepareStatement("SELECT id, title, author FROM books WHERE status = 'AVAILABLE'");
            updateBookStatus = con.prepareStatement("UPDATE books SET status = ? WHERE id = ?");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        // Create the main frame
        JFrame frame = new JFrame("Book Rental App");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create a list model for the available books
        DefaultListModel<Book> model = new DefaultListModel<>();
        // Populate the list model with the available books from the database
        try {
            ResultSet rs = selectAvailableBooks.executeQuery();
            while (rs.next()) {
                Object id = rs.getObject("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                model.addElement(new Book(title, author));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create a list for displaying the available books
        JList<Book> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new BookCellRenderer());
        JScrollPane listPanel = new JScrollPane(list);

        // Create a panel for the rental form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2));
        formPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField();
        formPanel.add(phoneField);
        // Create a button for submitting the rental form
        JButton rentButton = new JButton("Rent");
        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected book from the list
                Book book = list.getSelectedValue();
                if (book != null) {
                    try {
                        // Insert the rental into the database
                        insertRental.setObject(1, book.getId());
                        insertRental.setString(2, nameField.getText());
                        insertRental.setString(3, emailField.getText());
                        insertRental.setString(4, phoneField.getText());
                        insertRental.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
                        insertRental.setDate(6, null);
                        insertRental.executeUpdate();
                        // Update the book's status in the database
                        updateBookStatus.setString(1, "RENTED");
                        updateBookStatus.setObject(2, book.getId());
                        updateBookStatus.executeUpdate();
                        // Remove the book from the list model
                        model.removeElement(book);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        formPanel.add(rentButton);

        // Add the list and form panels to the main frame
        frame.add(listPanel, BorderLayout.WEST);
        frame.add(formPanel, BorderLayout.EAST);

        // Show the main frame
        frame.setVisible(true);
    }
}
