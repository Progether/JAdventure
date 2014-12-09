package com.jadventure.game.bdd;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@cucumber.api.CucumberOptions(plugin = { "pretty", "html:target/cucumber" }, features = { "src/bdd/" })
public class TestFeatures {

}
