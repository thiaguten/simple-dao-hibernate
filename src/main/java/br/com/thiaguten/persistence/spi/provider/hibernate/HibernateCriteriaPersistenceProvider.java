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
import org.hibernate.criterion.Criterion;
import org.hibernate.transform.ResultTransformer;

import java.io.Serializable;
import java.util.List;

/**
 * Adpater interface to define operation for a hibernate criteria persistence provider implementation.
 *
 * @author Thiago Gutenberg Carvalho da Costa
 */
public interface HibernateCriteriaPersistenceProvider extends PersistenceProvider {

    /**
     * Find by criteria.
     *
     * @param entityClazz the entity class
     * @param criterions  the criterions
     * @param <ID>        the type of the identifier
     * @param <T>         the type of the entity
     * @return the list of entities
     */
    <ID extends Serializable, T extends Persistable<ID>> List<T> findByCriteria(Class<T> entityClazz, List<Criterion> criterions);

    /**
     * Find by criteria.
     *
     * @param entityClazz the entity class
     * @param criterions  the criterions
     * @param firstResult first result
     * @param maxResults  max result
     * @param <ID>        the type of the identifier
     * @param <T>         the type of the entity
     * @return the list of entities
     */
    <ID extends Serializable, T extends Persistable<ID>> List<T> findByCriteria(Class<T> entityClazz, int firstResult, int maxResults, List<Criterion> criterions);

    /**
     * Find by criteria.
     *
     * @param entityClazz the entity class
     * @param criterions  the criterions
     * @param cacheable   cacheable
     * @param firstResult first result
     * @param maxResults  max result
     * @param <ID>        the type of the identifier
     * @param <T>         the type of the entity
     * @return the list of entities
     */
    <ID extends Serializable, T extends Persistable<ID>> List<T> findByCriteria(Class<T> entityClazz, boolean cacheable, int firstResult, int maxResults, List<Criterion> criterions);

    /**
     * Find unique result by criteria.
     *
     * @param entityClazz the entity class
     * @param criterions  the criterions
     * @param <ID>        the type of the identifier
     * @param <T>         the type of the entity
     * @return the entity
     */
    <ID extends Serializable, T extends Persistable<ID>> T findUniqueResultByCriteria(Class<T> entityClazz, List<Criterion> criterions);

    /**
     * Find unique result by criteria.
     *
     * @param entityClazz the entity class
     * @param criterions  the criterions
     * @param cacheable   cacheable
     * @param <ID>        the type of the identifier
     * @param <T>         the type of the entity
     * @return the entity
     */
    <ID extends Serializable, T extends Persistable<ID>> T findUniqueResultByCriteria(Class<T> entityClazz, boolean cacheable, List<Criterion> criterions);

    /**
     * Count by criteria.
     *
     * @param entityClazz the entity class
     * @param resultClazz the result class
     * @param criterions  the criterions
     * @param <ID>        the type of the identifier
     * @param <T>         the type of the entity
     * @param <N>         the type of the count return
     * @return the count
     */
    <ID extends Serializable, T extends Persistable<ID>, N extends Number> N countByCriteria(Class<T> entityClazz, Class<N> resultClazz, List<Criterion> criterions);

    /**
     * Count by criteria.
     *
     * @param entityClazz       the entity class
     * @param resultClazz       the result class
     * @param resultTransformer strategy for transforming query results
     * @param criterions        the criterions
     * @param <ID>              the type of the identifier
     * @param <T>               the type of the entity
     * @param <N>               the type of the count return
     * @return the count
     */
    <ID extends Serializable, T extends Persistable<ID>, N extends Number> N countByCriteria(Class<T> entityClazz, Class<N> resultClazz, ResultTransformer resultTransformer, List<Criterion> criterions);
}
