package com.helloit.householdtracker.ux.spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 */
@Controller
public class HomeController {

    public static final String CURRENT_PRINCIPAL_TAG = "CurrentPrincipal";
    private static final String HOME = "homepage";

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String create(final ModelMap model, final HttpSession session) {
        final String result;

        final Object currentPrincipal = session.getAttribute(CURRENT_PRINCIPAL_TAG);

        if (currentPrincipal != null) {
            result = HOME;
        } else {
            result = "redirect:account/loginpage.html";
        }


        return result;
    }


}
