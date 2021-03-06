package com.helloit.householdtracker.ux.spring.account;


import com.helloit.householdtracker.common.service.IAccountService;
import com.helloit.householdtracker.ux.common.SecurityFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("account")
public class AccountController {

    public static final String MESSAGE_TAG = "message";
    public static final String ACCOUNT_ERROR = "account/error";
    public static final String ACCOUNT_SUCCESS = "account/success";
    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    @Autowired
    private IAccountService accountService;

    @RequestMapping(path = "create", method = RequestMethod.POST)
    public String create(final ModelMap model, final String userName, final String password, final String retypedPassword) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Creating new account: " + userName);
        }

        final String result;

        final IAccountService.CreationOutcomes outcome = accountService.createAccount(userName, password, retypedPassword);

        switch (outcome) {
            case SUCCESS: {
                result = ACCOUNT_SUCCESS;
                break;
            }
            case RETYPED_PASSWORD_DO_NOT_MATCH: {
                result = ACCOUNT_ERROR;
                model.addAttribute(MESSAGE_TAG, "Retyped password did not match!");
                break;
            }
            case EXISTING_ACCOUNT: {
                result = ACCOUNT_ERROR;
                model.addAttribute(MESSAGE_TAG, "Account '" + userName + "' already exists!");
                break;
            }
            default: {
                throw new UnsupportedOperationException("Not supported case!");
            }
        }

        return result;
    }

    @RequestMapping(path = "login", method = RequestMethod.POST)
    public String login(final ModelMap model, final HttpSession session, final String userName, final String password) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Logging into account: " + userName);
        }

        final String result;

        if (accountService.authenticate(userName, password)) {
            session.setAttribute(SecurityFilter.CURRENT_PRINCIPAL_TAG, userName);
            result = "redirect:/";
        } else {
            result = ACCOUNT_ERROR;
            model.addAttribute(MESSAGE_TAG, "Authentication failure!");
        }

        return result;
    }


    @RequestMapping(path = "loginAjax", method = RequestMethod.POST)
    public
    @ResponseBody
    LoginResponseDTO loginAJAX(final HttpSession session, final String userName, final String password) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Logging into account: " + userName);
        }

        final LoginResponseDTO result;

        if (accountService.authenticate(userName, password)) {
            session.setAttribute(SecurityFilter.CURRENT_PRINCIPAL_TAG, userName);
            result = new LoginResponseDTO(LoginResponseDTO.Kind.SUCCESS);
        } else {
            result = new LoginResponseDTO(LoginResponseDTO.Kind.ERROR, "Authentication failure!");
        }

        return result;
    }

    @RequestMapping(path = "logout", method = RequestMethod.GET)
    public String logout(final ModelMap model, final HttpSession session) {

        if (LOGGER.isDebugEnabled()) {
            final Object principal = session.getAttribute(SecurityFilter.CURRENT_PRINCIPAL_TAG);
            LOGGER.debug("Logging out from account: %s", principal);
        }

        session.invalidate();

        return "redirect:/account/loginpage.html";

    }
}