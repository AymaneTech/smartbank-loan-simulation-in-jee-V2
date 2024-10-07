package com.wora.smartbank.loanRequest.infrastructure.persistence;

import com.wora.smartbank.config.UnitTestPersistenceUnitInfo;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.repository.RequestRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class DefaultRequestRepositoryTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private RequestRepository repository;

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
        Request r1 = createRequest("mchrou3", "aymane", "el maini");
        Request r2 = createRequest("tajine", "hamza", "lamin");
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

        List<Request> result = repository.findAll();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> r.id().equals(r1.id())));
        assertTrue(result.stream().anyMatch(r -> r.id().equals(r2.id())));
    }


    private Request createRequest(String purpose, String firstName, String lastName) {
        return new Request(
                new LoanDetails(purpose, 15000.00, 36.2, 450.00),
                new CustomerDetails(
                        Title.MR, firstName, lastName,
                        firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com",
                        "123-456-7890", "Engineer",
                        new Cin("ABC123456789"), LocalDate.of(1990, 5, 20), LocalDate.of(2015, 1, 10),
                        5000.00, false
                )
        ).setId(new RequestId());
    }
}