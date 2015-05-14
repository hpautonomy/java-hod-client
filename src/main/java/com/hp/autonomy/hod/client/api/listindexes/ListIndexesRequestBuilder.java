/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.listindexes;

import com.hp.autonomy.hod.client.api.textindex.IndexFlavor;
import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Setter
@Accessors(chain = true)
public class ListIndexesRequestBuilder {
    /**
     * @param indexTypes The value of the type parameter
     */
    @SuppressWarnings("FieldMayBeFinal")
    private Set<IndexType> indexTypes = new HashSet<>();

    /**
     * @param indexFlavors The value of the flavor parameter
     */
    @SuppressWarnings("FieldMayBeFinal")
    private Set<IndexFlavor> indexFlavors = new HashSet<>();

    /**
     * @return A map of parameters suitable for use with {@link ListIndexesService}.
     */
    public Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();

        for (final IndexType indexType : indexTypes) {
            map.put("type", indexType);
        }

        for (final IndexFlavor indexFlavor : indexFlavors) {
            map.put("flavor", indexFlavor);
        }

        return map;
    }
}
