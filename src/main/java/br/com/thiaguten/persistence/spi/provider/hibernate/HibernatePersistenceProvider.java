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

import br.com.thiaguten.persistence.Persistable;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.ResultTransformer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Hibernate implementation of the PersistenceProvider.
 *
 * @author Thiago Gutenberg
 */
@SuppressWarnings("unchecked")
public abstract class HibernatePersistenceProvider implements HibernateCriteriaPersistenceProvider {

    /**
     * Get session
     *
     * @return session
     */
    public abstract Session getSession();

    /**
     * Find an entity by its primary key.
     *
     * @param entityClazz the entity class
     * @param pk          the primary key
     * @return the entity
     */
    @Override
    public <T extends Persistable<? extends Serializable>, PK extends Serializable> T findById(Class<T> entityClazz, PK pk) {
        return (T) getSession().get(entityClazz, pk);
    }

    /**
     * Load all entities.
     *
     * @param entityClazz the entity class
     * @return the list of entities
     */
    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findAll(Class<T> entityClazz) {
        return findByCriteria(entityClazz, null);
    }

    /**
     * Load entities.
     *
     * @param entityClazz the entity class
     * @param firstResult the value of first result
     * @param maxResults  the value of max result
     * @return the list of entities
     */
    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findAll(Class<T> entityClazz, int firstResult, int maxResults) {
        return findByCriteria(entityClazz, firstResult, maxResults, null);
    }

    /**
     * Find by named query.
     *
     * @param entityClazz the entity class
     * @param queryName   the name of the query
     * @param params      the query parameters
     * @return the list of entities
     */
    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByNamedQuery(Class<T> entityClazz, String queryName, Object... params) {
        Query hibernateQuery = getSession().getNamedQuery(queryName);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                hibernateQuery.setParameter(i + 1, params[i]);
            }
        }
        return hibernateQuery.list();
    }

    /**
     * Find by named query.
     *
     * @param entityClazz the entity class
     * @param queryName   the name of the query
     * @param params      the query parameters
     * @return the list of entities
     */
    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByNamedQueryAndNamedParams(Class<T> entityClazz, String queryName, Map<String, ?> params) {
        Query hibernateQuery = getSession().getNamedQuery(queryName);
        if (params != null && !params.isEmpty()) {
            for (final Map.Entry<String, ?> param : params.entrySet()) {
                hibernateQuery.setParameter(param.getKey(), param.getValue());
            }
        }
        return hibernateQuery.list();
    }

    /**
     * Find by query (JPQL/HQL, etc) and parameters.
     *
     * @param entityClazz the entity class
     * @param query       the typed query
     * @param params      the typed query parameters
     * @return the list of entities
     */
    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByQueryAndNamedParams(Class<T> entityClazz, String query, Map<String, ?> params) {
        Query hibernateQuery = getSession().createQuery(query);
        if (params != null && !params.isEmpty()) {
            for (final Map.Entry<String, ?> param : params.entrySet()) {
                hibernateQuery.setParameter(param.getKey(), param.getValue());
            }
        }
        return hibernateQuery.list();
    }

    /**
     * Count all entities.
     *
     * @param entityClazz the entity class
     * @return the number of entities
     */
    @Override
    public <T extends Persistable<? extends Serializable>> long countAll(Class<T> entityClazz) {
        return countByCriteria(entityClazz, Long.class, null);
    }

    /**
     * Count by named query and parameters.
     *
     * @param resultClazz the number class
     * @param queryName   the named query
     * @param params      the named query parameters
     * @return the count of entities
     */
    @Override
    public <T extends Number> T countByNamedQueryAndNamedParams(Class<T> resultClazz, String queryName, Map<String, ?> params) {
        Query hibernateQuery = getSession().getNamedQuery(queryName);
        if (params != null && !params.isEmpty()) {
            for (final Map.Entry<String, ?> param : params.entrySet()) {
                hibernateQuery.setParameter(param.getKey(), param.getValue());
            }
        }
        return (T) hibernateQuery.uniqueResult();
    }

    /**
     * Count by (JPQL/HQL, etc) and parameters.
     *
     * @param resultClazz the number class
     * @param query       the typed query
     * @param params      the typed query parameters
     * @return the count of entities
     */
    @Override
    public <T extends Number> T countByQueryAndNamedParams(Class<T> resultClazz, String query, Map<String, ?> params) {
        Query hibernateQuery = getSession().createQuery(query);
        if (params != null && !params.isEmpty()) {
            for (final Map.Entry<String, ?> param : params.entrySet()) {
                hibernateQuery.setParameter(param.getKey(), param.getValue());
            }
        }
        return (T) hibernateQuery.uniqueResult();
    }

    /**
     * Save an entity.
     *
     * @param entity the entity to save
     * @return the saved entity
     */
    @Override
    public <T extends Persistable<? extends Serializable>> T save(T entity) {
        Serializable id = entity.getId();
        if (id != null) {
            getSession().saveOrUpdate(entity);
        } else {
            id = getSession().save(entity);
        }
        entity = (T) findById(entity.getClass(), id);
        return entity;
    }

    /**
     * Update an entity.
     *
     * @param entity the entity to update
     * @return the updated entity
     */
    @Override
    public <T extends Persistable<? extends Serializable>> T update(T entity) {
        return save(entity);
    }

    /**
     * Delete an entity.
     *
     * @param entityClazz the entity class
     * @param entity      the entity to delete
     */
    @Override
    public <T extends Persistable<? extends Serializable>> void delete(Class<T> entityClazz, T entity) {
        deleteByEntityOrId(entityClazz, entity, null);
    }

    /**
     * Delete an entity.
     *
     * @param entityClazz the entity class
     * @param pk          primary key of the entity to delete
     */
    @Override
    public <T extends Persistable<? extends Serializable>, PK extends Serializable> void deleteById(Class<T> entityClazz, PK pk) {
        deleteByEntityOrId(entityClazz, null, pk);
    }

    /**
     * Find by criteria
     *
     * @param entityClazz the entity class
     * @param criterions  the criterions
     * @param <T>         entity
     * @return the list of entities
     */
    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByCriteria(Class<T> entityClazz, List<Criterion> criterions) {
        return findByCriteria(entityClazz, -1, -1, criterions);
    }

    /**
     * Find by criteria
     *
     * @param entityClazz the entity class
     * @param criterions  the criterions
     * @param firstResult first result
     * @param maxResults  max result
     * @param <T>         entity
     * @return the list of entities
     */
    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByCriteria(Class<T> entityClazz, int firstResult, int maxResults, List<Criterion> criterions) {
        return findByCriteria(entityClazz, false, firstResult, maxResults, criterions);
    }

    /**
     * Find by criteria
     *
     * @param entityClazz the entity class
     * @param criterions  the criterions
     * @param cacheable   cacheable
     * @param firstResult first result
     * @param maxResults  max result
     * @param <T>         entity
     * @return the list of entities
     */
    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByCriteria(Class<T> entityClazz, boolean cacheable, int firstResult, int maxResults, List<Criterion> criterions) {
        Criteria criteria = getSession().createCriteria(entityClazz);
        if (criterions != null) {
            for (Criterion c : criterions) {
                criteria.add(c);
            }
        }
        return criteriaRange(criteria, firstResult, maxResults).setCacheable(cacheable).list();
    }

    /**
     * Find unique result by criteria
     *
     * @param entityClazz the entity class
     * @param criterions  the criterions
     * @param <T>         entity
     * @return the entity
     */
    @Override
    public <T extends Persistable<? extends Serializable>> T findUniqueResultByCriteria(Class<T> entityClazz, List<Criterion> criterions) {
        return findUniqueResultByCriteria(entityClazz, false, criterions);
    }

    /**
     * Find unique result by criteria
     *
     * @param entityClazz the entity class
     * @param criterions  the criterions
     * @param cacheable   cacheable
     * @param <T>         entity
     * @return the entity
     */
    @Override
    public <T extends Persistable<? extends Serializable>> T findUniqueResultByCriteria(Class<T> entityClazz, boolean cacheable, List<Criterion> criterions) {
        Criteria criteria = getSession().createCriteria(entityClazz);
        if (criterions != null) {
            for (Criterion c : criterions) {
                criteria.add(c);
            }
        }
        return (T) criteria.setCacheable(cacheable).uniqueResult();
    }

    /**
     * Count by criteria
     *
     * @param entityClazz the entity class
     * @param resultClazz the result class
     * @param criterions  the criterions
     * @param <T>         entity
     * @param <N>         number pojo
     * @return the count
     */
    @Override
    public <T extends Persistable<? extends Serializable>, N extends Number> N countByCriteria(Class<T> entityClazz, Class<N> resultClazz, List<Criterion> criterions) {
        return countByCriteria(entityClazz, resultClazz, Criteria.DISTINCT_ROOT_ENTITY, criterions);
    }

    /**
     * Count by criteria
     *
     * @param entityClazz       the entity class
     * @param resultClazz       the result class
     * @param resultTransformer strategy for transforming query results
     * @param criterions        the criterions
     * @param <T>               entity
     * @param <N>               number pojo
     * @return the count
     */
    @Override
    public <T extends Persistable<? extends Serializable>, N extends Number> N countByCriteria(Class<T> entityClazz, Class<N> resultClazz, ResultTransformer resultTransformer, List<Criterion> criterions) {
        Criteria criteria = getSession().createCriteria(entityClazz);
        criteria.setProjection(Projections.rowCount());
        if (criterions != null) {
            for (Criterion c : criterions) {
                criteria.add(c);
            }
        }
        return (N) criteria.setResultTransformer(resultTransformer).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    private <T extends Persistable<? extends Serializable>, PK extends Serializable> void deleteByEntityOrId(Class<T> entityClazz, T entity, PK pk) {
        if (pk == null && (entity == null || entity.getId() == null)) {
            throw new HibernateException("Could not delete. ID is null.");
        }

        T t;
        if (pk != null) {
            t = findById(entityClazz, pk);
        } else {
            t = entity;
        }
        getSession().delete(t);
    }

    private Criteria criteriaRange(Criteria criteria, int firstResult, int maxResults) {
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
