package com.helloit.householdtracker.ux.spring;

import com.helloit.householdtracker.common.IAccountService;
import com.helloit.householdtracker.common.repository.IUserRepository;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan
@Import(PersistenceConfig.class)
@PropertySource("classpath:app.properties")
public class AppConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public IAccountService accountService(final IUserRepository userRepository) {
        return new AccountService(userRepository);
    }
}