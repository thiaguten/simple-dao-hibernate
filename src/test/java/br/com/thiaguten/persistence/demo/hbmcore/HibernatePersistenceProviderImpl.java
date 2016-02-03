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
package br.com.thiaguten.persistence.demo.hbmcore;

import br.com.thiaguten.persistence.Persistable;
import br.com.thiaguten.persistence.spi.provider.hibernate.HibernatePersistenceProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@Service("hibernatePersistenceProvider")
public class HibernatePersistenceProviderImpl extends HibernatePersistenceProvider {

    private final SessionFactory sessionFactory;

    @Autowired
    public HibernatePersistenceProviderImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public <T extends Persistable<? extends Serializable>> T save(T entity) {
        return super.save(entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public <T extends Persistable<? extends Serializable>> T update(T entity) {
        return super.update(entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public <T extends Persistable<? extends Serializable>> void delete(Class<T> entityClazz, T entity) {
        super.delete(entityClazz, entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public <T extends Persistable<? extends Serializable>, PK extends Serializable> void deleteById(Class<T> entityClazz, PK pk) {
        super.deleteById(entityClazz, pk);
    }

    @Override
    public <T extends Persistable<? extends Serializable>, N extends Number> N countByCriteria(Class<T> entityClazz, Class<N> resultClazz, ResultTransformer resultTransformer, List<Criterion> criterions) {
        return super.countByCriteria(entityClazz, resultClazz, resultTransformer, criterions);
    }

    @Override
    public <T extends Persistable<? extends Serializable>, PK extends Serializable> T findById(Class<T> entityClazz, PK pk) {
        return super.findById(entityClazz, pk);
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findAll(Class<T> entityClazz) {
        return super.findAll(entityClazz);
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findAll(Class<T> entityClazz, int firstResult, int maxResults) {
        return super.findAll(entityClazz, firstResult, maxResults);
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByNamedQuery(Class<T> entityClazz, String queryName, Object... params) {
        return super.findByNamedQuery(entityClazz, queryName, params);
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByNamedQueryAndNamedParams(Class<T> entityClazz, String queryName, Map<String, ?> params) {
        return super.findByNamedQueryAndNamedParams(entityClazz, queryName, params);
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByQueryAndNamedParams(Class<T> entityClazz, String query, Map<String, ?> params) {
        return super.findByQueryAndNamedParams(entityClazz, query, params);
    }

    @Override
    public <T extends Persistable<? extends Serializable>> long countAll(Class<T> entityClazz) {
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
    public <T extends Persistable<? extends Serializable>> List<T> findByCriteria(Class<T> entityClazz, List<Criterion> criterions) {
        return super.findByCriteria(entityClazz, criterions);
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByCriteria(Class<T> entityClazz, int firstResult, int maxResults, List<Criterion> criterions) {
        return super.findByCriteria(entityClazz, firstResult, maxResults, criterions);
    }

    @Override
    public <T extends Persistable<? extends Serializable>> List<T> findByCriteria(Class<T> entityClazz, boolean cacheable, int firstResult, int maxResults, List<Criterion> criterions) {
        return super.findByCriteria(entityClazz, cacheable, firstResult, maxResults, criterions);
    }

    @Override
    public <T extends Persistable<? extends Serializable>> T findUniqueResultByCriteria(Class<T> entityClazz, List<Criterion> criterions) {
        return super.findUniqueResultByCriteria(entityClazz, criterions);
    }

    @Override
    public <T extends Persistable<? extends Serializable>> T findUniqueResultByCriteria(Class<T> entityClazz, boolean cacheable, List<Criterion> criterions) {
        return super.findUniqueResultByCriteria(entityClazz, cacheable, criterions);
    }

    @Override
    public <T extends Persistable<? extends Serializable>, N extends Number> N countByCriteria(Class<T> entityClazz, Class<N> resultClazz, List<Criterion> criterions) {
        return super.countByCriteria(entityClazz, resultClazz, criterions);
    }
}
