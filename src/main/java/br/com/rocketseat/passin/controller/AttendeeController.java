package br.com.rocketseat.passin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/attendees")
public class AttendeeController {

    @GetMapping
    public String helloAttendees() {
        return "Hello Attendees!";
    }
}
