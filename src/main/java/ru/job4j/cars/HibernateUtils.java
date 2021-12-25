package ru.job4j.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateUtils.class);
    private static final StandardServiceRegistry REGISTRY =
            new StandardServiceRegistryBuilder().configure().build();
    private static final SessionFactory SF =
            new MetadataSources(REGISTRY).buildMetadata().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return SF;
    }

    public static void releaseSessionFactory() {
        if (SF.isOpen()) {
            SF.close();
            StandardServiceRegistryBuilder.destroy(REGISTRY);
        }
    }

    public static <T> T select(Function<Session, T> command) {
        T result = null;
        Session session = SF.openSession();
        Transaction tx = session.beginTransaction();
        boolean error = false;
        try {
            result = command.apply(session);
        } catch (Exception ex) {
            error = true;
            LOG.error("Ошибка выполнения запроса", ex);
        } finally {
            if (error) {
                tx.rollback();
            } else {
                tx.commit();
            }
            session.close();
        }
        return result;
    }

    public static boolean execute(Consumer<Session> dmlCommand) {
        Session session = SF.openSession();
        Transaction tx = session.beginTransaction();
        boolean error = false;
        try {
            dmlCommand.accept(session);
        } catch (Exception ex) {
            error = true;
            LOG.error("Ошибка выполнения запроса", ex);
        } finally {
            if (error) {
                tx.rollback();
            } else {
                tx.commit();
            }
            session.close();
        }
        return !error;
    }
}