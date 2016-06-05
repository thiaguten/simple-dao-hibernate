/*
 * #%L
 * %%
 * Copyright (C) 2016 Thiago Gutenberg Carvalho da Costa.
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
package br.com.thiaguten.persistence.demo.hbmjpa;

import br.com.thiaguten.persistence.core.Persistable;
import br.com.thiaguten.persistence.spi.provider.hibernate.HibernateJpaPersistenceProvider;
import org.hibernate.criterion.Criterion;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@Service("hibernateJpaPersistenceProvider")
public class HibernateJpaPersistenceProviderImpl extends HibernateJpaPersistenceProvider {

    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <ID extends Serializable, T extends Persistable<ID>> T save(T entity) {
        return super.save(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <ID extends Serializable, T extends Persistable<ID>> T update(T entity) {
        return super.update(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <ID extends Serializable, T extends Persistable<ID>> void delete(Class<T> entityClazz, T entity) {
        super.delete(entityClazz, entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <ID extends Serializable, T extends Persistable<ID>> void deleteById(Class<T> entityClazz, ID id) {
        super.deleteById(entityClazz, id);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>> T findById(Class<T> entityClazz, ID id) {
        return super.findById(entityClazz, id);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>> List<T> findAll(Class<T> entityClazz) {
        return super.findAll(entityClazz);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>> List<T> findAll(Class<T> entityClazz, int firstResult, int maxResults) {
        return super.findAll(entityClazz, firstResult, maxResults);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>> List<T> findByNamedQuery(Class<T> entityClazz, String queryName, Object... params) {
        return super.findByNamedQuery(entityClazz, queryName, params);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>> List<T> findByNamedQueryAndNamedParams(Class<T> entityClazz, String queryName, Map<String, ?> params) {
        return super.findByNamedQueryAndNamedParams(entityClazz, queryName, params);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>> List<T> findByQueryAndNamedParams(Class<T> entityClazz, String query, Map<String, ?> params) {
        return super.findByQueryAndNamedParams(entityClazz, query, params);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>> long countAll(Class<T> entityClazz) {
        return super.countAll(entityClazz);
    }

    @Override
    public <T extends Number> T countByNamedQueryAndNamedParams(Class<T> resultClazz, String queryName, Map<String, ?> params) {
        return super.countByNamedQueryAndNamedParams(resultClazz, queryName, params);
    }

    @Override
    public <T extends Number> T countByQueryAndNamedParams(Class<T> resultClazz, String query, Map<String, ?> params) {
        return super.countByQueryAndNamedParams(resultClazz, query, params);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>> List<T> findByCriteria(Class<T> entityClazz, List<Criterion> criterions) {
        return super.findByCriteria(entityClazz, criterions);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>> List<T> findByCriteria(Class<T> entityClazz, int firstResult, int maxResults, List<Criterion> criterions) {
        return super.findByCriteria(entityClazz, firstResult, maxResults, criterions);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>> List<T> findByCriteria(Class<T> entityClazz, boolean cacheable, int firstResult, int maxResults, List<Criterion> criterions) {
        return super.findByCriteria(entityClazz, cacheable, firstResult, maxResults, criterions);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>> T findUniqueResultByCriteria(Class<T> entityClazz, List<Criterion> criterions) {
        return super.findUniqueResultByCriteria(entityClazz, criterions);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>> T findUniqueResultByCriteria(Class<T> entityClazz, boolean cacheable, List<Criterion> criterions) {
        return super.findUniqueResultByCriteria(entityClazz, cacheable, criterions);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>, N extends Number> N countByCriteria(Class<T> entityClazz, Class<N> resultClazz, List<Criterion> criterions) {
        return super.countByCriteria(entityClazz, resultClazz, criterions);
    }

    @Override
    public <ID extends Serializable, T extends Persistable<ID>, N extends Number> N countByCriteria(Class<T> entityClazz, Class<N> resultClazz, ResultTransformer resultTransformer, List<Criterion> criterions) {
        return super.countByCriteria(entityClazz, resultClazz, resultTransformer, criterions);
    }
}
