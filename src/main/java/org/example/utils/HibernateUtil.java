package org.example.utils;

import java.util.Properties;
import org.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.MappingSettings;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HibernateUtil {

    private static SessionFactory factory;

    public static SessionFactory getSessionFactory() {

        try {
            factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            System.out.println("Hibernate SessionFactory created successfully!");
        } catch (Throwable e) {
            System.err.println("Initial SessionFactory creation failed: " + e);
        }

        if (factory == null){
            Properties props = new Properties();
            props.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");
            props.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/comp3005_final_project");
            props.setProperty("hibernate.connection.username", "postgres");
            props.setProperty("hibernate.connection.password","zhuixingren");
            props.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
            props.setProperty("hibernate.hbm2ddl.auto","update");
            props.setProperty("hibernate.show_sql","true");
            props.setProperty("hibernate.format_sql","true");

            Configuration config = new Configuration();
            config.addAnnotatedClass(Member.class);
            config.addAnnotatedClass(FitnessGoal.class);
            config.addAnnotatedClass(HealthMetric.class);
            config.addAnnotatedClass(PersonalTrainingSession.class);
            config.addAnnotatedClass(Room.class);
            config.addAnnotatedClass(Trainer.class);

            try {
                ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(props).build();
                factory = config.buildSessionFactory(registry);
            } catch (Throwable e) {
                throw new ExceptionInInitializerError(e);
            }
        }
        return factory;
    }

    public static void shutdown() {
        if (factory != null && !factory.isClosed()) {
            factory.close();
            System.out.println("Hibernate SessionFactory closed.");

        }
    }
}
