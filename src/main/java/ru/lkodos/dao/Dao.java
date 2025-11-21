package ru.lkodos.dao;

import java.util.List;
import java.util.Optional;

// TODO: Нужен ли третий дженерик
public interface Dao<K, T, E> {

    List<T> getAll();

    Optional<T> get(K key);

    T save(T t);

    T update (E rate);
}