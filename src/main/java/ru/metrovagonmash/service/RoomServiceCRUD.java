package ru.metrovagonmash.service;



public interface RoomServiceCRUD<T,ID> extends RoomService<T,ID> {
    T update(T model, ID id);
    T deleteById(ID id);
}
