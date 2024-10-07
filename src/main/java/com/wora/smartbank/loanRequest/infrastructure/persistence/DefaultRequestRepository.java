package com.wora.smartbank.loanRequest.infrastructure.persistence;

import com.wora.smartbank.common.util.TransactionManager;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.exception.RequestNotFoundException;
import com.wora.smartbank.loanRequest.domain.repository.RequestRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
public class DefaultRequestRepository implements RequestRepository {
    private static final Logger logger = LoggerFactory.getLogger(DefaultRequestRepository.class);
    private final EntityManagerFactory emf;

    @Inject
    public DefaultRequestRepository(EntityManagerFactory emf) {
        this.emf = emf;
        logger.info("DefaultRequestRepository initialized with EntityManagerFactory");
    }

    @Override
    public List<Request> findAll() {
        logger.debug("Fetching all requests");
        return TransactionManager.executeWithResult(emf, em -> {
            List<Request> requests = em.createQuery("SELECT r FROM Request r", Request.class).getResultList();
            logger.info("Found {} requests", requests.size());
            return requests;
        });
    }

    @Override
    public Optional<Request> findById(RequestId id) {
        logger.debug("Searching for request with ID: {}", id.value());
        return TransactionManager.executeWithResult(emf, em -> {
            Optional<Request> request = Optional.ofNullable(em.find(Request.class, id));
            logger.info("Request with ID {} {}", id.value(), request.isPresent() ? "found" : "not found");
            return request;
        });
    }

    @Override
    public Request save(Request request) {
        logger.debug("Saving request: {}", request);
        return TransactionManager.executeWithResult(emf, saveOperation(request));
    }

    private Function<EntityManager, Request> saveOperation(Request request) {
        return em -> Optional.ofNullable(request.id())
                .map(id -> update(em, request))
                .orElseGet(() -> create(em, request));
    }

    @Override
    public void delete(RequestId id) {
        logger.debug("Attempting to delete request with ID: {}", id.value());
        TransactionManager.executeWithoutResult(emf, em -> {
            Request request = em.find(Request.class, id.value());
            if (request != null) {
                em.remove(request);
                logger.info("Request with ID {} deleted successfully", id.value());
            } else {
                logger.warn("Request with ID {} not found for deletion", id.value());
                throw new RequestNotFoundException(id);
            }
        });
    }

    @Override
    public boolean existsById(RequestId id) {
        logger.debug("Checking existence of request with ID: {}", id.value());
        boolean exists = TransactionManager.executeWithResult(emf, em ->
                em.find(Request.class, id.value()) != null
        );
        logger.info("Request with ID {} {}", id.value(), exists ? "exists" : "does not exist");
        return exists;
    }

    private Request create(EntityManager em, Request request) {
        logger.debug("Creating new request");
        request.setId(new RequestId());
        em.persist(request);
        logger.info("New request created with ID: {}", request.id().value());
        return request;
    }

    private Request update(EntityManager em, Request request) {
        logger.debug("Updating request with ID: {}", request.id().value());
        Request updatedRequest = em.merge(request);
        logger.info("Request with ID {} updated successfully", request.id().value());
        return updatedRequest;
    }
}