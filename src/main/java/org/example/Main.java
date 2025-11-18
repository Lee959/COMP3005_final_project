package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        // Test
        Member m1 = new Member("example@email.com", "password", "John", "Doe", null, "Male", "1234567890");

        Configuration config = new Configuration();
        config.addAnnotatedClass(org.example.Member.class);
        config.configure("hibernate.cfg.xml");

        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();


        Transaction transaction = session.beginTransaction();
        session.persist(m1);
        transaction.commit();

        session.close();
        factory.close();



    }
}