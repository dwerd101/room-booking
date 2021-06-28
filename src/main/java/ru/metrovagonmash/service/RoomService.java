package ru.metrovagonmash.service;

import java.util.List;

public interface RoomService<T,ID> {
    T save(T model);
    T update(T model);
    List<T> findAll();
    Boolean deleteById(ID id);
}
