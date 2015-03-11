/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.search;

import java.util.List;
import java.util.Map;

import com.hp.autonomy.iod.client.error.IodClientErrorException;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.TypedInput;

/**
 * Interface representing the QueryTextIndex API.
 */
public interface QueryTextIndexService {

    String URL = "/sync/querytextindex/v1";

    /**
     * Query IDOL OnDemand for documents matching query text
     * @param apiKey The API key to use to authenticate the request
     * @param text The query text
     * @param indexes List of indexes to query for documents. Set this to null if you wish to query all the indexes
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Documents queryTextIndexWithText(@Query("apiKey") String apiKey, @Query("text") String text, @Query("indexes") List<String> indexes, @QueryMap Map<String, Object> params) throws IodClientErrorException;

    /**
     * Query IDOL OnDemand for documents using query text from an object store object
     * @param apiKey The API key to use to authenticate the request
     * @param reference An IDOL OnDemand reference obtained from either the Expand Container or Store Object API. The contents of the object will be used as the query text
     * @param indexes List of indexes to query for documents. Set this to null if you wish to query all the indexes
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Documents queryTextIndexWithReference(@Query("apiKey") String apiKey, @Query("reference") String reference, @Query("indexes") List<String> indexes, @QueryMap Map<String, Object> params) throws IodClientErrorException;

    /**
     * Query IDOL OnDemand for documents using query text from a url
     * @param apiKey The API key to use to authenticate the request
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param indexes List of indexes to query for documents. Set this to null if you wish to query all the indexes
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Documents queryTextIndexWithUrl(@Query("apiKey") String apiKey, @Query("url") String url, @Query("indexes") List<String> indexes, @QueryMap Map<String, Object> params) throws IodClientErrorException;

    /**
     * Query IDOL OnDemand for documents using query text in a file
     * @param apiKey The API key to use to authenticate the request
     * @param file A file containing the query text
     * @param indexes List of indexes to query for documents. Set this to null if you wish to query all the indexes
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @Multipart
    @POST(URL)
    Documents queryTextIndexWithFile(@Part("apiKey") String apiKey, @Part("file") TypedInput file, @Part("indexes") List<String> indexes, @PartMap Map<String, Object> params) throws IodClientErrorException;

}
