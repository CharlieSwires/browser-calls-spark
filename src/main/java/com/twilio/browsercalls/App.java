package com.twilio.browsercalls;

import com.twilio.browsercalls.controllers.*;
import com.twilio.browsercalls.lib.AppSetup;
import com.twilio.browsercalls.lib.DbSeeder;
import com.twilio.browsercalls.logging.LoggingFilter;
import com.twilio.browsercalls.models.TicketService;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import javax.persistence.EntityManagerFactory;

import static spark.Spark.*;
import static spark.Spark.afterAfter;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
/**
 * Main application class. The environment is set up here, and all necessary services are run.
 */
public class App {
  public static void main(String[] args) {
    AppSetup appSetup = new AppSetup();

    /**
     * Sets the port in which the application will run. Takes the port value from PORT
     * environment variable, if not set, uses Spark default port 4567.
     */
    port(appSetup.getPortNumber());

    /**
     * Gets the entity manager based on environment variable DATABASE_URL and injects it into
     * AppointmentService which handles all DB operations.
     */
    EntityManagerFactory factory = appSetup.getEntityManagerFactory();

    /**
     * Specifies the directory within resources that will be publicly available when the
     * application is running. Place static web files in this directory (JS, CSS).
     */
    Spark.staticFileLocation("/public");

    TicketService ticketService = new TicketService(factory.createEntityManager());

    /**
     * Seed the database with example data if no records exist.
     */
    DbSeeder seeder = new DbSeeder(ticketService);
    seeder.seedIfDbEmpty();

    get("/", new HomeController().index, new MustacheTemplateEngine());
    post("/token/generate", new TokenController().getToken);
    get("/dashboard", new DashboardController(ticketService).index, new MustacheTemplateEngine());
    post("/call/connect", new CallController().connect);
    post("/ticket/create", new TicketController(ticketService).create,
        new MustacheTemplateEngine());
//    Thread t = new Thread(){
//    	@Override
//    	public void run(){
    		try{
    			InetAddress ip = InetAddress.getLocalHost();
    			String command = "ngrok http -subdomain=twiliovizionnetwork"+ip.getHostName()+" 4567";
//    			String command = "ngrok http 4567";
//    			Process process = Runtime.getRuntime().exec(command, null, null);
//    			StringBuilder output = new StringBuilder();
//
//    			BufferedReader reader = new BufferedReader(
//    					new InputStreamReader(process.getInputStream()));
//
//    			String line;
//    			while ((line = reader.readLine()) != null) {
//    				output.append(line + "\n");
//    			}
    			System.out.println(command);
//    			System.out.println(output);
    		} catch (Exception ex){
    			ex.printStackTrace();
    		}
//    	}
//    };
//    t.start();
    afterAfter(new LoggingFilter());

  }
}
