/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.TypedOutput;

import java.util.Map;

interface FindRelatedConceptsBackend {
    String URL = "/2/api/sync/textindex/query/findrelatedconcepts/v1";

    /**
     * Query Micro Focus Haven OnDemand for documents matching query text using the given token
     * @param token The token to use to authenticate the request
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Response findRelatedConceptsWithText(
        @Header("token") AuthenticationToken<?, ?> token,
        @Query("text") String text,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Query Micro Focus Haven OnDemand for documents using query text from an object store object using the given token
     * @param token The token to use to authenticate the request
     * @param reference An Micro Focus Haven OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Response findRelatedConceptsWithReference(
        @Header("token") AuthenticationToken<?, ?> token,
        @Query("reference") String reference,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Query Micro Focus Haven OnDemand for documents using query text from a url using the given token
     * @param token The token to use to authenticate the request
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Response findRelatedConceptsWithUrl(
        @Header("token") AuthenticationToken<?, ?> token,
        @Query("url") String url,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Query Micro Focus Haven OnDemand for documents using query text in a file using the given token
     * @param token The token to use to authenticate the request
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @Multipart
    @POST(URL)
    Response findRelatedConceptsWithFile(
        @Header("token") AuthenticationToken<?, ?> token,
        @Part("file") TypedOutput file,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;
}
