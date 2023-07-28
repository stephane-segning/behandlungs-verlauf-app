package team.sema.dpa.digitalpatientenakte.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import team.sema.dpa.digitalpatientenakte.dao.CaseRepo;
import team.sema.dpa.digitalpatientenakte.models.CaseEntity;

import java.util.List;
import java.util.UUID;

public class CaseRepoImpl implements CaseRepo {
    private final SessionFactory sessionFactory;

    public CaseRepoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CaseEntity findById(UUID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CaseEntity.class, id);
        }
    }

    @Override
    public List<CaseEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CaseEntity", CaseEntity.class).list();
        }
    }

    @Override
    public CaseEntity save(CaseEntity patient) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(patient);
            session.getTransaction().commit();
            return patient;
        }
    }

    @Override
    public void delete(CaseEntity patient) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(patient);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<CaseEntity> findByPatientIdQuery(UUID patientId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CaseEntity where patient.id = :patientId", CaseEntity.class)
                    .setParameter("patientId", patientId)
                    .list();
        }
    }

    @Override
    public List<CaseEntity> findByPatientIdQuery(UUID patientId, String query) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CaseEntity where patient.id = :patientId and lower(id::text) like :query", CaseEntity.class)
                    .setParameter("patientId", patientId)
                    .setParameter("query", "%" + query + "%")
                    .list();
        }
    }
}