package src.main.utcluj.ssatr.book;
import javax.swing.*;
import java.awt.*;

class BookCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Book) {
            Book book = (Book) value;
            setText(book.getTitle() + " by " + book.getAuthor());
        }
        return this;
    }
}