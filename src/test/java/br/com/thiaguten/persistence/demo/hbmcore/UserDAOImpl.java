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

import br.com.thiaguten.persistence.core.GenericPersistence;
import br.com.thiaguten.persistence.demo.User;
import br.com.thiaguten.persistence.demo.UserDAO;
import br.com.thiaguten.persistence.spi.provider.hibernate.HibernateCriteriaPersistenceProvider;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository("userDAO")
public class UserDAOImpl extends GenericPersistence<Long, User> implements UserDAO {

    private final HibernateCriteriaPersistenceProvider persistenceProvider;

    @Autowired
    public UserDAOImpl(@Qualifier("hibernatePersistenceProvider") HibernateCriteriaPersistenceProvider persistenceProvider) {
        this.persistenceProvider = persistenceProvider;
    }

    @Override
    public HibernateCriteriaPersistenceProvider getPersistenceProvider() {
        return persistenceProvider;
    }


    @Override
    public List<User> findByName(String name) {
        List<Criterion> criterions = Collections.singletonList(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
        List<User> results = persistenceProvider.findByCriteria(getPersistenceClass(), criterions);
        if (results.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(results);
        }
    }
}
