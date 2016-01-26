/*
 * #%L
 * %%
 * Copyright (C) 2015 - 2016 Thiago Gutenberg Carvalho da Costa.
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Thiago Gutenberg Carvalho da Costa. nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package br.com.thiaguten.persistence.spi.provider.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
//import org.hibernate.resource.transaction.spi.TransactionStatus;

/**
 * Utility class to create a sesson factory and a session.
 *
 * @author Thiago Gutenberg
 */
public final class HibernateUtils {

    private static Session session;
    private static SessionFactory sessionFactory;

    private HibernateUtils() {
        // suppress default constructor
        // for noninstantiability
        throw new AssertionError();
    }

    /**
     * Build session factory with hibernate resource ("hibernate.cfg.xml")
     *
     * @return session factory
     */
    public static SessionFactory buildSessionFactory() {
        return buildSessionFactory("hibernate.cfg.xml");
    }

    /**
     * Build session factory with the specified hibernate resource
     *
     * @param resource The hibernate resource to use
     * @return session factory
     */
    public static SessionFactory buildSessionFactory(String resource) {
        Configuration cfg = new Configuration();
        sessionFactory = cfg.configure(resource)
                .buildSessionFactory(new StandardServiceRegistryBuilder()
                        .applySettings(toMap(cfg.getProperties())).build());

        session = sessionFactory.getCurrentSession();
        return sessionFactory;
    }

    @SuppressWarnings("unchecked")
    private static Map toMap(Properties properties) {
        Map map = new HashMap();
        for (String name : properties.stringPropertyNames()) {
            map.put(name, properties.getProperty(name));
        }
        return map;
    }

    /**
     * Get session
     *
     * @return session
     */
    public static Session getSession() {
        return getSession(null);
    }

    /**
     * Get session
     *
     * @param sessionFactoryHibernateResource session factory resource
     * @return session
     */
    public static Session getSession(final String sessionFactoryHibernateResource) {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            if (sessionFactoryHibernateResource != null && !sessionFactoryHibernateResource.trim().isEmpty()) {
                sessionFactory = buildSessionFactory(sessionFactoryHibernateResource);
            } else {
                sessionFactory = buildSessionFactory();
            }
        }
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            if (!session.isOpen()) {
                session = sessionFactory.getCurrentSession();
            }
            return session;
        }
        throw new ExceptionInInitializerError("SessionFactory not created or is closed. Should be re-created through the method buildSessionFactory");
    }

    /**
     * Get session transaction
     *
     * @return session transaction
     */
    public static Transaction getTransaction() {
        return getSession().getTransaction();
    }

    /**
     * Close session
     */
    public static void closeSession() {
        if (getSession().isOpen()) {
            getSession().close();
        }
    }

    /**
     * Begin transaction
     */
    public static void beginTransaction() {
        if (!isTransactionActive()) {
            getTransaction().begin();
        }
    }

    /**
     * Commit transaction
     */
    public static void commitTransaction() {
        if (isTransactionActive()) {
            getTransaction().commit();
        }
    }

    /**
     * Rollback transaction
     */
    public static void rollbackTransaction() {
        if (isTransactionActive()) {
            getTransaction().rollback();
        }
    }

    /**
     * Checks if a transaction is active
     *
     * @return true if the transaction is active, otherwise false
     */
    public static boolean isTransactionActive() {
        return getTransaction().isActive(); // hibernate 4 approach
//        return getTransaction().getStatus().isOneOf(TransactionStatus.ACTIVE); // hibernate 5 approach
    }

    /**
     * Apply criteria range
     *
     * @param criteria    criteriaQuery
     * @param firstResult first result
     * @param maxResults  max result
     * @return criteriaQuery
     */
    public static Criteria criteriaRange(Criteria criteria, int firstResult, int maxResults) {
        if (criteria != null) {
            if (maxResults >= 0) {
                criteria.setMaxResults(maxResults);
            }
            if (firstResult >= 0) {
                criteria.setFirstResult(firstResult);
            }
        }
        return criteria;
    }

}
