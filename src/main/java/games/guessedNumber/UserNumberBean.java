package games.guessedNumber;

/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */

import java.io.Serializable;
import java.util.Random;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class UserNumberBean implements Serializable {

	private static final long serialVersionUID = 5443351151396868724L;
	Integer randomInt = null;
	private Integer userNumber = null;
	String response = null;
	private int maximum = 10;
	private int minimum = 0;

	public UserNumberBean() {
		generateNumber();
	}

	/**
	 * generate random number.
	 */
	private void generateNumber() {
		Random randomGR = new Random();
		randomInt = new Integer(randomGR.nextInt((maximum - minimum) + 1));
		// Print number to server log
		System.out.println("Duke's number: " + randomInt);
	}

	public void setUserNumber(Integer user_number) {
		userNumber = user_number;
	}

	public Integer getUserNumber() {
		return userNumber;
	}

	public String getResponse() {
		if (userNumber != null) {
			if (userNumber.compareTo(randomInt) != 0) {
				if (userNumber > randomInt) {
					return "Number is less than " + userNumber;
				}
				if (userNumber < randomInt) {
					return "Number is more than " + userNumber;
				}
			} else if (userNumber.compareTo(randomInt) == 0) {
				generateNumber();
				return "Yay! You got it!";
			}
		} else {
			return "Insert number !";
		}
		return "";
	}

	public int getMaximum() {
		return (this.maximum);
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public int getMinimum() {
		return (this.minimum);
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
}
