package com.qa.recipe;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Recipe2Application {

	public static void main(String[] args) {


		ApplicationContext context = SpringApplication.run(Recipe2Application.class, args);
	}

}