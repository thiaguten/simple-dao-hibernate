/*
 * #%L
 * %%
 * Copyright (C) 2016 Thiago Gutenberg Carvalho da Costa.
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
package br.com.thiaguten.persistence.demo.hbmjpa;

import br.com.thiaguten.persistence.core.BasePersistence;
import br.com.thiaguten.persistence.demo.User;
import br.com.thiaguten.persistence.demo.UserDAO;
import br.com.thiaguten.persistence.spi.PersistenceProvider;

import br.com.thiaguten.persistence.spi.provider.hibernate.HibernateJpaPersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository("userJpaDAO")
public class UserDAOImpl extends BasePersistence<Long, User> implements UserDAO {

    private final HibernateJpaPersistenceProvider persistenceProvider;

    @Autowired
    public UserDAOImpl(@Qualifier("hibernateJpaPersistenceProvider") HibernateJpaPersistenceProvider persistenceProvider) {
        this.persistenceProvider = persistenceProvider;
    }

    @Override
    public HibernateJpaPersistenceProvider getPersistenceProvider() {
        return persistenceProvider;
    }

    @Override
    public List<User> findByName(String name) {
    	List<User> results = persistenceProvider.findByQuery(getPersistenceClass(), "from User u where lower(u.name) like ?1", name.toLowerCase());
        if (null == results || results.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(results);
        }
    }
}
