package ru.polytech.course.pashnik.lines.DataBase;

import java.util.List;

public interface IDataBaseHandler {
    void addContact(Contact contact);

    List<Contact> getAllContacts();

    int getContactsCount();

    void deleteContact(Contact contact);

    void deleteContactById(int id);

    void deleteAll();
}
