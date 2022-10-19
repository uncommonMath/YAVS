package yavs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yavs.service.InviteService;

@RestController
@RequestMapping("/invite")
public class InviteController {
    private final InviteService service;

    public InviteController(InviteService service) {
        this.service = service;
    }
}