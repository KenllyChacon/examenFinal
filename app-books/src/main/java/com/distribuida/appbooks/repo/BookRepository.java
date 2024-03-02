package com.distribuida.appbooks.repo;

import com.distribuida.appbooks.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
@Transactional
public class BookRepository {
    @Inject
    private EntityManager entityManager;

    public Stream<Book> streamAll() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultStream();
    }

    public Optional<Book> findByIdOptional(Integer id) {
        Book book = entityManager.find(Book.class, id);
        return Optional.ofNullable(book);
    }

    public void persist(Book book) {
        entityManager.persist(book);
    }

    public Book findById(Integer id) {
        return entityManager.find(Book.class, id);
    }

    public void deleteById(Integer id) {
        Book book = findById(id);
        if (book != null) {
            entityManager.remove(book);
        }
    }
}