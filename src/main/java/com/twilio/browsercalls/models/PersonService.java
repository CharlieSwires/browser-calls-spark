package com.twilio.browsercalls.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * Class that provides an abstraction to a Person's entity database access
 */
public class PersonService {
  private EntityManager entityManager;

  public PersonService(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Person getPerson(Long id) {
    return entityManager.find(Person.class, id);
  }

  public void create(Person person) {
    getTransaction().begin();
    entityManager.persist(person);
    getTransaction().commit();
  }

  public void delete(Person person) {
    getTransaction().begin();
    entityManager.remove(person);
    getTransaction().commit();
  }

  @SuppressWarnings("unchecked")
  public List<Person> findAll() {
    Query query = entityManager.createQuery("SELECT a FROM Person a order by a.name");
    return query.getResultList();
  }

  public void deleteAll() {
    getTransaction().begin();
    Query query = entityManager.createQuery("DELETE FROM Person");
    query.executeUpdate();
    getTransaction().commit();
  }

  public Long count() {
    Query query = entityManager.createQuery("SELECT COUNT(a) FROM Person a");
    return (Long) query.getSingleResult();
  }

  private EntityTransaction getTransaction() {
    return entityManager.getTransaction();
  }
}
