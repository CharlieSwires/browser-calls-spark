package com.twilio.browsercalls.controllers;

import com.twilio.browsercalls.lib.FieldsValidator;
import com.twilio.browsercalls.models.Person;
import com.twilio.browsercalls.models.PersonService;
import spark.ModelAndView;
import spark.Request;
import spark.TemplateViewRoute;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PersonController {
  PersonService PersonService;

  public PersonController(PersonService PersonService) {
    this.PersonService = PersonService;
  }

  public TemplateViewRoute create = (request, response) -> {
    return new ModelAndView(createPerson(request), "home.mustache");
  };

  public Map createPerson(Request request) {
    FieldsValidator validator =
        new FieldsValidator(new String[] {"name", "phone_number"});

    Map map = new HashMap();

    /** Validates that the required parameters are sent on the request. */
    if (validator.valid(request)) {
      String name = request.queryParams("name");
      String phoneNumber = request.queryParams("phone_number");
      Date date = new Date();

      Person Person = new Person(name, phoneNumber);
      PersonService.create(Person);
      map.put("message", true);
      map.put("notice", "Person created successfully");

      return map;
    } else {
      map.put("error", true);
      map.put("errors", validator.errors());
      return map;
    }
  }
}
