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

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
@Model
public class ScoreController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private ScoreService scoreService;
    
    @Produces
    @Named
    private Score score;

    @PostConstruct
    public void initNewScore() {
    	score = new Score();
    }
    
    public void store() throws Exception {
        try {
        	scoreService.store(score);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Store!", "Score store successful");
            facesContext.addMessage(null, m);
            initNewScore();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Score store unsuccessful");
            facesContext.addMessage(null, m);
        }
    }
    
    public void loadScores(Game game) throws Exception {
        try {
        	scoreService.loadScores(game);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Load!", "Score load successful");
            facesContext.addMessage(null, m);
            initNewScore();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Score load unsuccessful");
            facesContext.addMessage(null, m);
        }
    }
    
    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }
}
