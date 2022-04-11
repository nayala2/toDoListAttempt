package com.softwaredevii.todolistattempt;

import entity.TodoList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.List;

public class SQLSingleton {

    EntityTransaction transaction;
    EntityManager entityManager;
    EntityManagerFactory entityManagerFactory;

    private static final Logger logger = LogManager.getLogger(HelloServlet.class);

    private static SQLSingleton firstInstance = null;
    static boolean firstThread = true;

    // Created to keep users from instantiation
    // Only Singleton will be able to instantiate this class
    private SQLSingleton() {
    }

    public static SQLSingleton getInstance() {

        logger.debug("SQLSingleton getInstance...");

        if (firstInstance == null) {
            logger.debug("SQLSingleton getInstance firstInstance == null");

            if (firstThread) {
                logger.debug("SQLSingleton getInstance firstInstance == null firstThread == true");

                firstThread = false;
                logger.debug("SQLSingleton getInstance firstInstance == null firstThread set to false");

                try {
                    Thread.currentThread();
                    Thread.sleep(1000);
                    logger.debug("SQLSingleton getInstance thread.sleep successful");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error("SQLSingleton getInstance thread interrupted at thread.sleep");

                }
            }

            synchronized (SQLSingleton.class) {
                if (firstInstance == null) {

                    firstInstance = new SQLSingleton();
                }
            }
        }
        return firstInstance;
    }

    protected void setup() {
        logger.debug("SQLSingleton setup()...");

        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        logger.debug("SQLSingleton set(): entitymanagerfactory success");

        entityManager = entityManagerFactory.createEntityManager();
        logger.debug("SQLSingleton set(): entitymanager success");

        transaction = entityManager.getTransaction();
        logger.debug("SQLSingleton set(): transaction success");
    }

    protected void viewAll() {

        try {

            logger.debug("SQLSingleton viewAll...");
            firstInstance.setup();
            logger.debug("SQLSingleton viewAll firstInstance.setup success");

            transaction.begin();
            logger.debug("SQLSingleton viewAll transaction.begin success");

            Query viewAllItems = entityManager.createNativeQuery("SELECT * FROM todoList", TodoList.class);
            logger.debug("SQLSingleton viewAll SQL query" + viewAllItems.toString());

            List<TodoList> results = viewAllItems.getResultList();

            HelloServlet.items = results;

            for (TodoList item : results) {
                logger.debug(item);
            }

            transaction.commit();
            logger.debug("SQLSingleton viewAll transaction.commit success");

        } finally {
            if (transaction.isActive()) {
                logger.warn("SQLSingleton viewAll transaction.isActive");

                transaction.rollback();
                logger.warn("SQLSingleton viewAll transaction.rollback");

            }
            entityManager.close();
            logger.debug("SQLSingleton viewAll entityManager.close()");

            entityManagerFactory.close();
            logger.debug("SQLSingleton viewAll entityManagerFactory.close()");

        }

    }

    public void addItemToList(String item) {

        try {
            logger.debug("SQLSingleton addItemToList...");
            firstInstance.setup();
            logger.debug("SQLSingleton addItemToList firstInstance.setup success");

            transaction.begin();
            logger.debug("SQLSingleton addItemToList transaction.begin success");

            TodoList newItem = new TodoList();

            newItem.setToDoItem(item);
            entityManager.persist(newItem);
            logger.debug(item);
            transaction.commit();
            logger.debug("SQLSingleton addItemToList transaction.commit");

        } finally {
            if (transaction.isActive()) {
                logger.warn("SQLSingleton addItemToList transaction.isActive");

                transaction.rollback();
                logger.warn("SQLSingleton addItemToList transaction.rollback");

            }
            entityManager.close();
            logger.debug("SQLSingleton addItemToList entityManager.close");

            entityManagerFactory.close();
            logger.debug("SQLSingleton addItemToList entityManagerFactory.close");

        }
    }

    public void deleteItemFromList(String item) {

        try {
            logger.debug("SQLSingleton deleteItemFromList...");
            firstInstance.setup();
            logger.debug("SQLSingleton deleteItemFromList firstInstance.setup success");

            transaction.begin();
            logger.debug("SQLSingleton deleteItemFromList transaction.begin success");

            Query deleteItem = entityManager.createNativeQuery("DELETE FROM todoList WHERE toDoItem = :id")
                    .setParameter("id", item);

            logger.debug("SQLSingleton deleteItemFromList SQL query" + deleteItem.toString());

            deleteItem.executeUpdate();
            logger.debug("SQLSingleton deleteItemFromList.executeUpdate success");

            transaction.commit();
            logger.debug("SQLSingleton deleteItemFromList transaction.commit");

        } finally {
            if (transaction.isActive()) {
                logger.warn("SQLSingleton deleteItemFromList transaction.isActive");

                transaction.rollback();
                logger.warn("SQLSingleton deleteItemFromList transaction.rollback");
            }

            entityManager.close();
            logger.debug("SQLSingleton deleteItemFromList entityManager.close");

            entityManagerFactory.close();
            logger.debug("SQLSingleton deleteItemFromList entityManagerFactory.close");

        }
    }
}
