package org.baeldung.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.baeldung.persistence.model.MyUser;
import org.baeldung.persistence.model.User;
import org.baeldung.persistence.service.impl.MyUserService;
import org.baeldung.persistence.service.impl.UserService;
import org.baeldung.web.util.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private MyUserService myService;

    public UserController() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public List<User> findAll(@RequestParam(value = "search", required = false) final String search) {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();

        if (search != null) {
            final Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            final Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return service.searchUser(params);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/spec")
    @ResponseBody
    public List<User> findAllBySpecification(@RequestParam(value = "search", required = false) final String search) {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();

        if (search != null) {
            final Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            final Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return service.searchBySpecification(params);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/myusers")
    @ResponseBody
    public Iterable<MyUser> findAllByQuerydsl(@RequestParam(value = "search", required = false) final String search) {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();

        if (search != null) {
            final Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            final Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return myService.search(params);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/new")
    @ResponseBody
    public long addUser(@RequestParam("first") final String first, @RequestParam("last") final String last, @RequestParam("age") final int age) {
        final User user = new User();
        user.setFirstName(first);
        user.setLastName(last);
        user.setEmail("john@doe.com");
        user.setAge(age);
        service.saveUser(user);
        return user.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/myusers/new")
    @ResponseBody
    public long addMyUser(@RequestParam("first") final String first, @RequestParam("last") final String last, @RequestParam("age") final int age) {
        final MyUser user = new MyUser();
        user.setFirstName(first);
        user.setLastName(last);
        user.setEmail("john@doe.com");
        user.setAge(age);
        myService.save(user);
        return user.getId();
    }
}
