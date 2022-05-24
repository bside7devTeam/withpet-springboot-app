package org.gig.withpet.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
@Controller
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}

