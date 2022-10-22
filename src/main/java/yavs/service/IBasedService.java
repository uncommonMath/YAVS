package yavs.service;

public interface IBasedService<T, U> {

    T save(T obj);

    T update(T obj);

    T getById(U id);

    void delete(U id);
}
