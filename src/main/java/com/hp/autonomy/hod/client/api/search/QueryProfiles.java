/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.hod.client.api.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The response object from the ListQueryProfiles API.  Contains a set of query profile names.
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = QueryProfiles.Builder.class)
public class QueryProfiles {

    /**
     * Names of query profiles.  Set because ordering from the server is not guaranteed.
     */
    @JsonProperty("query_profiles")
    private final Set<QueryProfileName> queryProfiles;

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        @SuppressWarnings("FieldMayBeFinal")
        @JsonProperty("query_profiles")
        private Set<QueryProfileName> queryProfiles = Collections.emptySet();

        public QueryProfiles build() {
            return new QueryProfiles(queryProfiles);
        }
    }

    /**
     * Util method for getting the query profile names as strings directly.
     * @return  Unique string names of query profiles.
     */
    public Set<String> getNames() {
        final Set<String> names = new HashSet<>();

        for (QueryProfileName qpn : queryProfiles) {
            names.add(qpn.getName());
        }

        return names;
    }
}
