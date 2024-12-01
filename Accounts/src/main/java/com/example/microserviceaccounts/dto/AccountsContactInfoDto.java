package com.example.microserviceaccounts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * Data carriers
 * data not able to change after creation (values are going to be final)
 * can read using the getter methods and there won't be any setter methods
 * you can only initialize the data only once and you cannot change that
 */

@ConfigurationProperties(prefix = "accounts")
@Getter
@Setter
public class AccountsContactInfoDto{

    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
