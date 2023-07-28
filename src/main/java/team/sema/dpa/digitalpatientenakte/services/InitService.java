package team.sema.dpa.digitalpatientenakte.services;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import team.sema.dpa.digitalpatientenakte.state.Bean;
import team.sema.dpa.digitalpatientenakte.state.Component;

@Component
public class InitService {

    @Bean
    public static SessionFactory sessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }
}
