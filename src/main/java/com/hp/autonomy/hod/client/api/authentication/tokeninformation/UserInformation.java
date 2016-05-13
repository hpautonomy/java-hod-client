/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.userstore.user.Account;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Information about the user component of an authentication token.
 */
@Data
public class UserInformation implements Serializable {
    private static final long serialVersionUID = -2610523494602694306L;

    /**
     * @return The UUID of the user
     */
    private final UUID uuid;

    /**
     * @return Information about the mechanism used to authenticate the user
     */
    private final AuthenticationInformation authentication;

    /**
     * @return Accounts associated with the user
     */
    private final List<Account> accounts;

    public UserInformation(
        @JsonProperty("uuid") final UUID uuid,
        @JsonProperty("auth") final AuthenticationInformation authentication,
        @JsonProperty("accounts") final List<Account> accounts
    ) {
        this.uuid = uuid;
        this.authentication = authentication;
        this.accounts = accounts;
    }
}
