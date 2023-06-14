package DataAccessLayer.DAOs.Interfaces;

import BusinessLayer.Store;

import java.util.List;

public interface StoreDAO {
    Store getById(Integer id);
    List<Store> getAll();
    // Add other CRUD operations as needed
}
