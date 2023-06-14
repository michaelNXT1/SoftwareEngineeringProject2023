package Repositories;

import BusinessLayer.Category;

import java.util.List;

public interface ISystemManagerSession {
    void addString(Category string);
    void removeString(Category string);
    List<Category> getAllCategory();
    void clear();
}
