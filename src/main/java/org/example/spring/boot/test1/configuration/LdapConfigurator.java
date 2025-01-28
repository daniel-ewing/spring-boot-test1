package org.example.spring.boot.test1.configuration;

import org.camunda.bpm.engine.impl.plugin.AdministratorAuthorizationPlugin;
import org.camunda.bpm.identity.impl.ldap.plugin.LdapIdentityProviderPlugin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LdapConfigurator {

    @Bean
    @ConfigurationProperties(prefix="security.ldap")
    public LdapIdentityProviderPlugin ldapIdentityProviderPlugin(){
        return new LdapIdentityProviderPlugin();
    }

    @Bean
    @ConfigurationProperties(prefix="security.administrator")
    public AdministratorAuthorizationPlugin administratorAuthorizationPlugin(){
        return new AdministratorAuthorizationPlugin();
    }
}
