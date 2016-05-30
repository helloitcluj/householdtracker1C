package com.helloit.householdtracker.ux.spring;

import com.helloit.householdtracker.tools.SchemaManager;
import com.helloit.householdtracker.ux.spring.account.AccountController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, WebConfig.class})
@WebAppConfiguration
public class AccountControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(this.wac).build();

        final SchemaManager schemaManager = new SchemaManager();
        schemaManager.recreateSchema();
    }

    @Test
    public void mismatchingPasswordTest() throws Exception {
        mockMvc.perform(post("/account/create")
                .param("userName", "aron")
                .param("password", "123").param("retypedPassword", "1234"))
                .andExpect(status().isOk())
                .andExpect(view().name(AccountController.ACCOUNT_ERROR))
                .andExpect(model().attribute(AccountController.MESSAGE_TAG, not(is(""))));
    }
}
