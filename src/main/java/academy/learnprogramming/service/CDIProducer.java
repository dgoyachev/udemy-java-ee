package academy.learnprogramming.service;

import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CDIProducer {

    @Produces
    @PersistenceContext
    EntityManager entityManager;
}
