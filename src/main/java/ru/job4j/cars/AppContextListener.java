package ru.job4j.cars;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HibernateUtils.getSessionFactory();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtils.releaseSessionFactory();
    }
}
