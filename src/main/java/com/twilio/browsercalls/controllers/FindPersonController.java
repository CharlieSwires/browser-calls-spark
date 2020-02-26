package com.twilio.browsercalls.controllers;

import com.twilio.browsercalls.models.Person;
import com.twilio.browsercalls.models.PersonService;
import spark.ModelAndView;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class FindPersonController {
  private PersonService personService;

  public FindPersonController(PersonService personService) {
    this.personService = personService;
  }

  public TemplateViewRoute index = (request, response) -> {
    Map map = new HashMap();

    List<Person> persons = personService.findAll();
    map.put("person", persons);

    return new ModelAndView(map, "findPerson.mustache");
  };
}