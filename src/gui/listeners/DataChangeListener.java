package gui.listeners;

import entities.Book;

public interface DataChangeListener {

    void onDataChanged();
    void onDataFilter(Book obj);
}
