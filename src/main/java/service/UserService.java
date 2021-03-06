/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package service;

import user.User;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import java.util.List;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class UserService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<User> memberEventSrc;

	public void register(User user) throws Exception {
		log.info("Registering " + user.getName());
		em.persist(user);
		memberEventSrc.fire(user);
	}
	
	  public User findById(Long id) {
	        return em.find(User.class, id);
	    }

	    public User findByEmail(String email) {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<User> criteria = cb.createQuery(User.class);
	        Root<User> user = criteria.from(User.class);
	        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	        // feature in JPA 2.0
	        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
	        criteria.select(user).where(cb.equal(user.get("email"), email));
	        return em.createQuery(criteria).getSingleResult();
	    }

	    public List<User> findAllOrderedByName() {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<User> criteria = cb.createQuery(User.class);
	        Root<User> user = criteria.from(User.class);
	        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	        // feature in JPA 2.0
	        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
	        criteria.select(user).orderBy(cb.asc(user.get("name")));
	        return em.createQuery(criteria).getResultList();
	    }
	    
	    public User findByPasswordAndName(User userLogin) {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<User> criteria = cb.createQuery(User.class);
	        Root<User> user = criteria.from(User.class);
	        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	        // feature in JPA 2.0
	        criteria.select(user).where(cb.and(cb.equal(user.get("name"), userLogin.getName()), cb.equal(user.get("passwd"), userLogin.getPasswd())));
	        return em.createQuery(criteria).getSingleResult();
	    }
}
