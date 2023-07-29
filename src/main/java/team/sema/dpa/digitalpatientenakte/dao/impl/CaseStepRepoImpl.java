package team.sema.dpa.digitalpatientenakte.dao.impl;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import team.sema.dpa.digitalpatientenakte.dao.CaseStepRepo;
import team.sema.dpa.digitalpatientenakte.models.CaseStepEntity;
import team.sema.dpa.digitalpatientenakte.state.Autowired;
import team.sema.dpa.digitalpatientenakte.state.Component;

import java.util.List;
import java.util.UUID;

@Component
@Setter
@NoArgsConstructor
public class CaseStepRepoImpl implements CaseStepRepo {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public CaseStepEntity findById(UUID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CaseStepEntity.class, id);
        }
    }

    @Override
    public List<CaseStepEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CaseStepEntity", CaseStepEntity.class)
                    .list();
        }
    }

    @Override
    public CaseStepEntity save(CaseStepEntity patient) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(patient);
            session.getTransaction().commit();
            return patient;
        }
    }

    @Override
    public void delete(CaseStepEntity patient) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(patient);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<CaseStepEntity> findByCaseId(UUID caseId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from CaseStepEntity where caseE.id = :caseId", CaseStepEntity.class)
                    .setParameter("caseId", caseId)
                    .list();
        }
    }
}
