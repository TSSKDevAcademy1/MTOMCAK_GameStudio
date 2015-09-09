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

import game.Game;
import game.Score;

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
public class GameService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Game> gameEventSrc;
	
	@Inject
	private Event<Score> scoreEventSrc;

	public void gameStore(Game game) throws Exception {
		log.info("Registering " + game.getName());
		em.persist(game);
		gameEventSrc.fire(game);
	}
	
	public void scoreStore(Score score) throws Exception {
		log.info("Registering " + score.getScore());
		em.persist(score);
		scoreEventSrc.fire(score);
	}
	    
    public List<Score> loadScores(Game game) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Score> criteria = cb.createQuery(Score.class);
        Root<Score> score = criteria.from(Score.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        criteria.select(score).where(cb.equal(score.get("game"), game));
        return em.createQuery(criteria).getResultList();
    }
}
