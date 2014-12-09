package com.jadventure.game.bdd;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps {

	@Given("^starting a new game as '(.*?)'$")
	public void starting_a_new_game_as_SewerRat(final String character) throws Throwable {
		System.out.println("Start the new game as [" + character + "]");

//	    throw new PendingException();
	}

	@When("^giving command '(.*?)'$")
	public void giving_command_look(final String command) throws Throwable {
		System.out.println("Run the command [" + command + "]");
	    
//	    throw new PendingException();
	}

	@Then("^the feedback should be$")
	public void the_feedback_should_be(final String feedback) throws Throwable {
		System.out.println("Check the feedback [" + feedback + "]");

//	    throw new PendingException();
	}

}
