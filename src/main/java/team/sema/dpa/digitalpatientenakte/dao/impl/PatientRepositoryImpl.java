package team.sema.dpa.digitalpatientenakte.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import team.sema.dpa.digitalpatientenakte.dao.PatientRepo;
import team.sema.dpa.digitalpatientenakte.models.PatientEntity;

import java.util.List;
import java.util.UUID;

public class PatientRepositoryImpl implements PatientRepo {
    private final SessionFactory sessionFactory;

    public PatientRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PatientEntity findById(UUID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(PatientEntity.class, id);
        }
    }

    @Override
    public List<PatientEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PatientEntity", PatientEntity.class).list();
        }
    }

    @Override
    public PatientEntity save(PatientEntity patient) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(patient);
            session.getTransaction().commit();
            return patient;
        }
    }

    @Override
    public void delete(PatientEntity patient) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(patient);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<PatientEntity> findByQuery(String input) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PatientEntity where lower(cast(idNumber as text)) like :input or lower(firstName) like :input or lower(lastName) like :input", PatientEntity.class)
                    .setParameter("input", "%" + input.toLowerCase() + "%")
                    .list();
        }
    }
}