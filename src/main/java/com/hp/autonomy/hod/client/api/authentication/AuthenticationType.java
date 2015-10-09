/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import lombok.Data;

/**
 * Represents an HP Haven OnDemand authentication type. The static instances of this class should be used where possible.
 */
@Data
public class AuthenticationType {
    /**
     * The legacy API key authentication type.
     */
    public static final AuthenticationType LEGACY_API_KEY = new AuthenticationType("LEGACY_APIKEY");

    private final String name;

    public AuthenticationType(final String name) {
        this.name = name;
    }
}
