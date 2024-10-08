package com.wora.smartbank.loanRequest.infrastructure.persistence;

import com.wora.smartbank.config.UnitTestPersistenceUnitInfo;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.repository.RequestRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import com.wora.smartbank.loanRequest.infrastructure.seeder.RequestSeeder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class DefaultRequestRepositoryTest {

    private EntityManagerFactory emf;
    private RequestRepository repository;
    private EntityManager em;

    @BeforeEach
    public void setup() {
        emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new UnitTestPersistenceUnitInfo(), null);
        em = emf.createEntityManager();
        repository = new DefaultRequestRepository(emf);
    }

    @AfterEach
    public void tearDown() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    @DisplayName("find all - should return all requests")
    void findAll_ShouldReturnAllRequests() {
        Request r1 = RequestSeeder.getRequest();
        Request r2 = RequestSeeder.getRequest();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(r1);
            em.persist(r2);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }

        em.clear();

        List<Request> actual = repository.findAll();

        assertTrue(actual.stream().anyMatch(r -> r.id().equals(r1.id())));
        assertTrue(actual.stream().anyMatch(r -> r.id().equals(r2.id())));
    }

    @Test
    @DisplayName("findById - given valid Id - return request")
    public void findById_GivenValidId_ShouldReturnRequest() {
        Request request = RequestSeeder.getRequest();
        Request expected = repository.save(request);

        Optional<Request> actual = repository.findById(expected.id());

        assertEquals(actual.get().id(), expected.id());
    }

    @Test
    @DisplayName("findById - invalid id - should throw requestNotFoundException")
    public void findById_GivenInvalidId_ShouldReturnEmptyOptional() {
        RequestId id = new RequestId();
        Optional<Request> result = repository.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("create - valid data - should return created request")
    public void create_GivenValidRequest_ShouldReturnCreatedRequest() {
        Request expected = RequestSeeder.getRequest();

        Request actual = repository.save(expected);

        assertEquals(expected.customerDetails(), actual.customerDetails());
        assertEquals(expected.loanDetails(), actual.loanDetails());
    }

    @Test
    @DisplayName("update - given valid request - should return updated request")
    public void update_GivenValidRequest_ShouldReturnUpdatedRequest() {
        Request request = RequestSeeder.getRequest();
        Request saved = repository.save(request);
        final String purpose = "updated purpose";
        final String firstName = "ayamne udpate";
        final String lastName = "el maini update";

        Request expected = RequestSeeder.getRequest().setId(saved.id());

        Request actual = repository.save(expected);

        assertEquals(saved.id(), actual.id());
    }

    @Test
    @DisplayName("existsById - existing id - should return true")
    public void existsById_GivenExistingId_ShouldReturnTrue() {
        Request expected = RequestSeeder.getRequest();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(expected);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }

        boolean actual = repository.existsById(expected.id());

        assertTrue(actual);
    }

    @Test
    @DisplayName("delete - given valid request id - should delete")
    public void delete_GivenValidRequest_ShouldDeleteRequest() {
        Request saved = repository.save(RequestSeeder.getRequest());

        repository.delete(saved.id());

        assertFalse(repository.existsById(saved.id()));
    }

    @Test
    @DisplayName("delete - given invalid request id - should throw request not found exception")
    public void delete_GivenInvalidRequestId_ShouldThrowRequestNotFoundException() {
        assertThrows(PersistenceException.class, () -> repository.delete(new RequestId()));
    }

    @Test
    @DisplayName("existsById - not existing id - should return false")
    public void existsById_GivenUnexistingId_ShouldReturnFalse() {
        assertFalse(repository.existsById(new RequestId()));
    }
}