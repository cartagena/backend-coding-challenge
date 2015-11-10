package com.github.cartagena.alchemytec.html;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class HtmlController {

    @RequestMapping(method = GET)
    public RedirectView home() {
        return new RedirectView("default.html");
    }

}
