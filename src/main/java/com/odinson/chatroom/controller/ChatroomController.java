package com.odinson.chatroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.net.UnknownHostException;

@Controller
public class ChatroomController {

    /**
     * Login Page
     */
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    /**
     * Chatroom Page
     */
    @GetMapping("/index")
    public ModelAndView index(@PathParam("username") String username, HttpServletRequest request) throws IOException, ServletException {
        //TODO: add code for login to chatroom.
        ModelAndView modelAndView = new ModelAndView("/chat");
        modelAndView.addObject("username", username);
        return modelAndView;
    }
}
