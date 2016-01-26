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
package br.com.thiaguten.persistence.demo.manual.hibernate.dao.provider;

import br.com.thiaguten.persistence.Persistable;
import br.com.thiaguten.persistence.spi.provider.hibernate.HibernatePersistenceProvider;
import br.com.thiaguten.persistence.spi.provider.hibernate.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Hibernate persistence strategy
 *
 * @author Thiago Gutenberg
 */
public class HibernatePersistenceProviderManualImpl extends HibernatePersistenceProvider {

    @Override
    public Session getSession() {
        return HibernateUtils.getSession();
    }

    @Override
    public <T extends Persistable<? extends Serializable>, PK extends Serializable> T findById(Class<T> entityClazz, PK pk) {
        T t = null;
        try {
            HibernateUtils.beginTransaction();
            t = super.findById(entityClazz, pk);
            HibernateUtils.commitTransaction();
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction();
            throw new HibernateException("Erro ao obter: " + e.getLocalizedMessage());
        } finally {
            HibernateUtils.closeSession();
        }
        return t;
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findAll(Class<T> entityClazz) {
        try {
            return super.findAll(entityClazz);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findAll(Class<T> entityClazz, int firstResult, int maxResults) {
        try {
            return super.findAll(entityClazz, firstResult, maxResults);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByNamedQuery(Class<T> entityClazz, String queryName, Object... params) {
        try {
            return super.findByNamedQuery(entityClazz, queryName, params);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByNamedQueryAndNamedParams(Class<T> entityClazz, String queryName, Map<String, ?> params) {
        try {
            return super.findByNamedQueryAndNamedParams(entityClazz, queryName, params);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByQueryAndNamedParams(Class<T> entityClazz, String query, Map<String, ?> params) {
        try {
            return super.findByQueryAndNamedParams(entityClazz, query, params);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>> long countAll(Class<T> entityClazz) {
        try {
            return super.countAll(entityClazz);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Number> T countByNamedQueryAndNamedParams(Class<T> resultClazz, String queryName, Map<String, ?> params) {
        try {
            return super.countByNamedQueryAndNamedParams(resultClazz, queryName, params);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Number> T countByQueryAndNamedParams(Class<T> resultClazz, String query, Map<String, ?> params) {
        try {
            return super.countByQueryAndNamedParams(resultClazz, query, params);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>> T save(T entity) {
        T t = null;
        try {
            HibernateUtils.beginTransaction();
            t = super.save(entity);
            HibernateUtils.commitTransaction();
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction();
            throw new HibernateException("Erro ao salvar: " + e.getLocalizedMessage());
        } finally {
            HibernateUtils.closeSession();
        }
        return t;
    }

    @Override
    public <T extends Persistable<? extends Serializable>> T update(T entity) {
        T t = null;
        try {
            HibernateUtils.beginTransaction();
            t = super.update(entity);
            HibernateUtils.commitTransaction();
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction();
            throw new HibernateException("Erro ao atualizar: " + e.getLocalizedMessage());
        } finally {
            HibernateUtils.closeSession();
        }
        return t;
    }

    @Override
    public <T extends Persistable<? extends Serializable>> void delete(Class<T> entityClazz, T entity) {
        try {
            HibernateUtils.beginTransaction();
            super.delete(entityClazz, entity);
            HibernateUtils.commitTransaction();
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction();
            throw new HibernateException("Erro ao deletar: " + e.getLocalizedMessage());
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>, PK extends Serializable> void deleteById(Class<T> entityClazz, PK id) {
        try {
            HibernateUtils.beginTransaction();
            super.deleteById(entityClazz, id);
            HibernateUtils.commitTransaction();
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction();
            throw new HibernateException("Erro ao deletar: " + e.getLocalizedMessage());
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>, N extends Number> N countByCriteria(Class<T> entityClazz, Class<N> resultClazz, List<Criterion> criterions) {
        try {
            return super.countByCriteria(entityClazz, resultClazz, criterions);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByCriteria(Class<T> entityClazz, List<Criterion> criterions) {
        try {
            return super.findByCriteria(entityClazz, criterions);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByCriteria(Class<T> entityClazz, int firstResult, int maxResults, List<Criterion> criterions) {
        try {
            return super.findByCriteria(entityClazz, firstResult, maxResults, criterions);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByCriteria(Class<T> entityClazz, boolean cacheable, int firstResult, int maxResults, List<Criterion> criterions) {
        try {
            return super.findByCriteria(entityClazz, cacheable, firstResult, maxResults, criterions);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>> T findUniqueResultByCriteria(Class<T> entityClazz, List<Criterion> criterions) {
        try {
            return super.findUniqueResultByCriteria(entityClazz, criterions);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Override
    public <T extends Persistable<? extends Serializable>> T findUniqueResultByCriteria(Class<T> entityClazz, boolean cacheable, List<Criterion> criterions) {
        try {
            return super.findUniqueResultByCriteria(entityClazz, cacheable, criterions);
        } finally {
            HibernateUtils.closeSession();
        }
    }
}
