package team.sema.dpa.digitalpatientenakte.dao.impl;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import team.sema.dpa.digitalpatientenakte.dao.FlowChartElementRepo;
import team.sema.dpa.digitalpatientenakte.models.FlowChartElementEntity;
import team.sema.dpa.digitalpatientenakte.state.Autowired;
import team.sema.dpa.digitalpatientenakte.state.Component;

import java.util.List;
import java.util.UUID;

@Component
@Setter
@NoArgsConstructor
public class FlowChartElementRepoImpl implements FlowChartElementRepo {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public FlowChartElementEntity findById(UUID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(FlowChartElementEntity.class, id);
        }
    }

    @Override
    public List<FlowChartElementEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from FlowChartElementEntity", FlowChartElementEntity.class).list();
        }
    }

    @Override
    public FlowChartElementEntity save(FlowChartElementEntity patient) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(patient);
            session.getTransaction().commit();
            return patient;
        }
    }

    @Override
    public void delete(FlowChartElementEntity patient) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(patient);
            session.getTransaction().commit();
        }
    }
}