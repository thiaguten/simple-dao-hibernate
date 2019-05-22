/*
 * #%L
 * %%
 * Copyright (C) 2015 - 2016 Thiago Gutenberg Carvalho da Costa.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package br.com.thiaguten.persistence.spi.provider.hibernate;

import br.com.thiaguten.persistence.core.Persistable;
import br.com.thiaguten.persistence.spi.PersistenceProvider;

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
 * @author Thiago Gutenberg Carvalho da Costa
 */
@SuppressWarnings("unchecked")
public abstract class AbstractHibernatePersistenceProvider implements HibernatePersistenceProvider {

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> T findById(Class<T> entityClazz,
      ID id) {
    return getSession().get(entityClazz, id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findAll(
      Class<T> entityClazz) {
    return findByCriteria(entityClazz, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findAll(Class<T> entityClazz,
      int firstResult, int maxResults) {
    return findByCriteria(entityClazz, firstResult, maxResults, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findByNamedQuery(
      Class<T> entityClazz, String queryName, Object... params) {
    return findByNamedQuery(entityClazz, false, queryName, params);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findByNamedQuery(
      Class<T> entityClazz, boolean cacheable, String queryName, Object... params) {
    Query hibernateQuery = getSession().getNamedQuery(queryName);
    hibernateQuery.setCacheable(cacheable);
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        hibernateQuery.setParameter(i, params[i]); // HQL Positional Parameters starts from 0
      }
    }
    return hibernateQuery.list();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findByNamedQueryAndNamedParams(
      Class<T> entityClazz, String queryName, Map<String, ?> params) {
    return findByNamedQueryAndNamedParams(entityClazz, false, queryName, params);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findByNamedQueryAndNamedParams(
      Class<T> entityClazz, boolean cacheable, String queryName, Map<String, ?> params) {
    Query hibernateQuery = getSession().getNamedQuery(queryName);
    hibernateQuery.setCacheable(cacheable);
    if (params != null) {
      params.forEach(hibernateQuery::setParameter);
    }
    return hibernateQuery.list();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findByQuery(
      Class<T> entityClazz, String query, Object... params) {
    return findByQuery(entityClazz, false, query, params);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findByQuery(
      Class<T> entityClazz, boolean cacheable, String query, Object... params) {
    Query hibernateQuery = getSession().createQuery(query);
    hibernateQuery.setCacheable(cacheable);
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        hibernateQuery.setParameter(i, params[i]); // HQL Positional Parameters starts from 0
      }
    }
    return hibernateQuery.list();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findByQueryAndNamedParams(
      Class<T> entityClazz, String query, Map<String, ?> params) {
    return findByQueryAndNamedParams(entityClazz, false, query, params);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findByQueryAndNamedParams(
      Class<T> entityClazz, boolean cacheable, String query, Map<String, ?> params) {
    Query hibernateQuery = getSession().createQuery(query);
    hibernateQuery.setCacheable(cacheable);
    if (params != null) {
      params.forEach(hibernateQuery::setParameter);
    }
    return hibernateQuery.list();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> long countAll(Class<T> entityClazz) {
    return countByCriteria(entityClazz, Long.class, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T extends Number> T countByNamedQueryAndNamedParams(Class<T> resultClazz,
      String queryName, Map<String, ?> params) {
    Query hibernateQuery = getSession().getNamedQuery(queryName);
    if (params != null) {
      params.forEach(hibernateQuery::setParameter);
    }
    return (T) hibernateQuery.uniqueResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T extends Number> T countByQueryAndNamedParams(Class<T> resultClazz, String query,
      Map<String, ?> params) {
    Query hibernateQuery = getSession().createQuery(query);
    if (params != null) {
      params.forEach(hibernateQuery::setParameter);
    }
    return (T) hibernateQuery.uniqueResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> T save(T entity) {
    ID id = entity.getId();

    if (id != null) {
      getSession().saveOrUpdate(entity);
    } else {
      id = (ID) getSession().save(entity);
    }

//    Class<T> entityClazz = (Class<T>) entity.getClass();
//    T t = getSession().load(entityClazz, id);

//    return t;
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> T update(T entity) {
    return save(entity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> void delete(Class<T> entityClazz,
      T entity) {
    deleteByEntityOrId(entityClazz, entity, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> void deleteById(Class<T> entityClazz,
      ID id) {
    deleteByEntityOrId(entityClazz, null, id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> void deleteByEntityOrId(
      Class<T> entityClazz, T entity, ID id) {
    if (id == null && (entity == null || entity.getId() == null)) {
      throw new HibernateException("Could not delete. ID is null.");
    }

    ID _id = id;
    if (_id == null) {
      _id = entity.getId();
    }

    T t = getSession().load(entityClazz, _id);

    getSession().delete(t);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findByCriteria(
      Class<T> entityClazz, List<Criterion> criterions) {
    return findByCriteria(entityClazz, -1, -1, criterions);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findByCriteria(
      Class<T> entityClazz, int firstResult, int maxResults, List<Criterion> criterions) {
    return findByCriteria(entityClazz, false, firstResult, maxResults, criterions);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> List<T> findByCriteria(
      Class<T> entityClazz, boolean cacheable, int firstResult, int maxResults,
      List<Criterion> criterions) {
    Criteria criteria = getSession().createCriteria(entityClazz);
    if (criterions != null) {
      for (Criterion c : criterions) {
        criteria.add(c);
      }
    }
    return criteriaRange(criteria, firstResult, maxResults).setCacheable(cacheable).list();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> T findUniqueResultByCriteria(
      Class<T> entityClazz, List<Criterion> criterions) {
    return findUniqueResultByCriteria(entityClazz, false, criterions);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>> T findUniqueResultByCriteria(
      Class<T> entityClazz, boolean cacheable, List<Criterion> criterions) {
    Criteria criteria = getSession().createCriteria(entityClazz);
    if (criterions != null) {
      for (Criterion c : criterions) {
        criteria.add(c);
      }
    }
    return (T) criteria.setCacheable(cacheable).uniqueResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>, N extends Number> N countByCriteria(
      Class<T> entityClazz, Class<N> resultClazz, List<Criterion> criterions) {
    return countByCriteria(entityClazz, resultClazz, Criteria.DISTINCT_ROOT_ENTITY, criterions);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID extends Serializable, T extends Persistable<ID>, N extends Number> N countByCriteria(
      Class<T> entityClazz, Class<N> resultClazz, ResultTransformer resultTransformer,
      List<Criterion> criterions) {
    Criteria criteria = getSession().createCriteria(entityClazz);
    criteria.setProjection(Projections.rowCount());
    if (criterions != null) {
      for (Criterion c : criterions) {
        criteria.add(c);
      }
    }
    return (N) criteria.setResultTransformer(resultTransformer).uniqueResult();
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
