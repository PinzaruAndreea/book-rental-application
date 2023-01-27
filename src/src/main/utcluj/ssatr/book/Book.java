package src.main.utcluj.ssatr.book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

class Book {
    private UUID id;
    private String title;
    private String author;
    private String renterName;
    private String renterEmail;
    private String renterPhone;
    private String rentalDate;
    private String returnDate;

    public Book(String title, String author) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.author = author;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void rent(String renterName, String renterEmail, String renterPhone) {
        this.renterName = renterName;
        this.renterEmail = renterEmail;
        this.renterPhone = renterPhone;
        this.rentalDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.returnDate = "";
    }
    public void setReturnDate(){
        this.returnDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getRenterEmail() {
        return renterEmail;
    }

    public void setRenterEmail(String renterEmail) {
        this.renterEmail = renterEmail;
    }

    public String getRenterPhone() {
        return renterPhone;
    }

    public void setRenterPhone(String renterPhone) {
        this.renterPhone = renterPhone;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
