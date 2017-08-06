package com.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String index() {

        return "index";
    }

    @GetMapping(value = {"/fragments/add-data", "/fragments/search-data"})
    public String indexIncludePage(HttpServletRequest request) {
        // request.getServletPath() => /fragments/add-data.html
        String path = request.getServletPath();

        return path.substring(0, path.length() - 5);
    }

}
