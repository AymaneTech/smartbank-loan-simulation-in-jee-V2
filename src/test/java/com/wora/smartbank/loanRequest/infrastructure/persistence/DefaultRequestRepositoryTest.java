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
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Default Request repository test ")
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

    @Nested
    @DisplayName("findAll() method tests")
    class FindAllTests {
        @Test
        @DisplayName("Should return all requests when exists")
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
    }

    @Nested
    @DisplayName("findById() method tests")
    class FindByIdTests {
        @Test
        @DisplayName("Should return Optional<Request> when exists")
        public void findById_GivenValidId_ShouldReturnRequest() {
            Request request = RequestSeeder.getRequest();
            Request expected = repository.save(request);

            Optional<Request> actual = repository.findById(expected.id());

            assertEquals(actual.get().id(), expected.id());
        }

        @Test
        @DisplayName("Should return empty optional when given invalid id")
        public void findById_GivenInvalidId_ShouldReturnEmptyOptional() {
            RequestId id = new RequestId();
            Optional<Request> result = repository.findById(id);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("save() method tests")
    class CreateTests {
        @Test
        @DisplayName("Should return created request when given valid data")
        public void create_GivenValidRequest_ShouldReturnCreatedRequest() {
            Request expected = RequestSeeder.getRequest();

            Request actual = repository.save(expected);

            assertEquals(expected.customerDetails(), actual.customerDetails());
            assertEquals(expected.loanDetails(), actual.loanDetails());
        }

        @Test
        @DisplayName("Should return updated request when given valid data")
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
    }

    @Nested
    @DisplayName("existsById() method tests")
    class ExistsByIdTests {
        @Test
        @DisplayName("Should return true when give existing id")
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
        @DisplayName("Should return false when given invalid id")
        public void existsById_GivenUnexistingId_ShouldReturnFalse() {
            assertFalse(repository.existsById(new RequestId()));
        }
    }

    @Nested
    @DisplayName("delete() method tests")
    class DeleteTests {
        @Test
        @DisplayName("Should delete request when given id exists")
        public void delete_GivenValidRequest_ShouldDeleteRequest() {
            Request saved = repository.save(RequestSeeder.getRequest());

            repository.delete(saved.id());

            assertFalse(repository.existsById(saved.id()));
        }

        @Test
        @DisplayName("Should delete throw request not found exception when given invalid id")
        public void delete_GivenInvalidRequestId_ShouldThrowRequestNotFoundException() {
            assertThrows(PersistenceException.class, () -> repository.delete(new RequestId()));
        }
    }
}