/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.error.HodError;
import lombok.Data;

import java.util.List;

/**
 * Holds the result of a job action. Depending on the status, some of the fields will be null.
 * @param <T> The result of the action if the action was successful
 */
@Data
public class Action<T> {

    /**
     * @return The API that was called
     */
    private final String action;

    /**
     * @return The status of the job
     */
    private final Status status;

    /**
     * @return A list of errors returned by the job
     */
    private final List<HodError> errors;

    /**
     * @return The result of the job
     */
    private final T result;

    /**
     * @return The version of the API
     */
    private final String version;

    @JsonCreator
    public Action(
            @JsonProperty("action") final String action,
            @JsonProperty("status") final Status status,
            @JsonProperty("errors") final List<HodError> errors,
            @JsonProperty("result") final T result,
            @JsonProperty("version") final String version
    ) {
        this.action = action;
        this.status = status;
        this.errors = errors;
        this.result = result;
        this.version = version;
    }

}
