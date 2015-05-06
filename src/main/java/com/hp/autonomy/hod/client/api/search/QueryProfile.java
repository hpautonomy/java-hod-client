/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A query profile to send to HP Haven OnDemand for use with other actions
 */
@Data
public class QueryProfile {
    /**
     * @return The name of the query manipulation index to use
     */
    @JsonProperty("querymanipulationindex")
    private final String queryManipulationIndex;

    /**
     * @return The promotions settings for this query profile
     */
    private final QueryProfilePromotions promotions;


    private QueryProfile(final Builder builder) {
        queryManipulationIndex = builder.queryManipulationIndex;
        promotions = builder.promotions;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {

        @JsonProperty("querymanipulationindex")
        private String queryManipulationIndex;

        private QueryProfilePromotions promotions;

        public QueryProfile build() {
            return new QueryProfile(this);
        }

    }
}
