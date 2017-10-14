package com.syolab.demos.empmgr.config;


import com.syolab.demos.empmgr.dao.domain.Address;
import com.syolab.demos.empmgr.dao.domain.Employee;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

public class RestRepositoryConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Employee.class);

        super.configureRepositoryRestConfiguration(config);
    }
}
