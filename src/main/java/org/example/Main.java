package org.example;

import org.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/*
public class Main {
    public static void main(String[] args) {
        // Test
        Member generate_member = new Member();
        System.out.println(generate_member);

        Configuration config = new Configuration();
        config.addAnnotatedClass(Member.class);
        config.addAnnotatedClass(FitnessGoal.class);
        config.addAnnotatedClass(HealthMetric.class);
        config.addAnnotatedClass(PersonalTrainingSession.class);
        config.addAnnotatedClass(Room.class);
        config.addAnnotatedClass(Trainer.class);

        config.configure("hibernate.cfg.xml");

        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(generate_member);
        transaction.commit();
        session.close();
        factory.close();
    }
}

 */
