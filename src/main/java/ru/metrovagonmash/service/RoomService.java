package ru.metrovagonmash.service;

import java.util.List;

public interface RoomService<T,ID> {
    T save(T model);
    T update(T model, ID id);
    List<T> findAll();
    T deleteById(ID id);
}
