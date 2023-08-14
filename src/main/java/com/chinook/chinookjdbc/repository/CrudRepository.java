package com.chinook.chinookjdbc.repository;

import java.util.List;

public interface CrudRepository <T, ID>{
    
    List<T> findAll();

    T findById(ID id);

    void createEntry(T t);

    void updateEntry(T t);

    void deleteEntry(ID id);
}
