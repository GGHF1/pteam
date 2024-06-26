package com.store.pteam.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PathController implements ErrorController {

    private static final String PATH = "/error"; // For all incorrect paths

    @RequestMapping(value = PATH)
    public String handleError() {
        return "error";
    }
}
