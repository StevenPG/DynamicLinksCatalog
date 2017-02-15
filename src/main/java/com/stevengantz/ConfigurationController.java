package com.stevengantz;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Steven Gantz on 2/15/2017.
 *
 * HTTP Request handler
 */
@RestController
public class ConfigurationController {

    @RequestMapping
    public LinksConfiguration applyConfiguration() {
        return new LinksConfiguration();
    }
}
