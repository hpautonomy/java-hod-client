/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindexing;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.AbstractJobService;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobStatus;
import com.hp.autonomy.hod.client.job.PollingJobStatusRunnable;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Service for managing jobs ids from the DeleteFromTextIndex API
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
public class DeleteFromTextIndexJobService extends AbstractJobService {

    private final DeleteFromTextIndexService deleteFromTextIndexService;

    /**
     * Creates a new DeleteFromTextIndexJobService
     * @param deleteFromTextIndexService The underlying service which will communicate with HP Haven OnDemand
     */
    public DeleteFromTextIndexJobService(final DeleteFromTextIndexService deleteFromTextIndexService) {
        super();

        this.deleteFromTextIndexService = deleteFromTextIndexService;
    }

    /**
     * Creates a new DeleteFromTextIndexJobService
     * @param deleteFromTextIndexService The underlying service which will communicate with HP Haven OnDemand
     * @param executorService The executor service to use while polling for status updates
     */
    public DeleteFromTextIndexJobService(final DeleteFromTextIndexService deleteFromTextIndexService, final ScheduledExecutorService executorService) {
        super(executorService);

        this.deleteFromTextIndexService = deleteFromTextIndexService;
    }

    /**
     * Deletes the documents with the given references using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param index The index to delete from
     * @param references The references of the documents to delete
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs with the request
     */
    public void deleteReferencesFromTextIndex(
            final String index,
            final List<String> references,
            final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = deleteFromTextIndexService.deleteReferencesFromTextIndex(index, references);

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(jobId, callback));
    }

    /**
     * Deletes the documents with the given references
     * @param apiKey The API key to use to authenticate the request
     * @param index The index to delete from
     * @param references The references of the documents to delete
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs with the request
     */
    public void deleteReferencesFromTextIndex(
            final String apiKey,
            final String index,
            final List<String> references,
            final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = deleteFromTextIndexService.deleteReferencesFromTextIndex(apiKey, index, references);

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(apiKey, jobId, callback));
    }

    /**
     * Deletes all the documents from the given text index using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param index The index to delete from
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs with the request
     */
    public void deleteAllDocumentsFromTextIndex(
            final String index,
            final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = deleteFromTextIndexService.deleteAllDocumentsFromTextIndex(index);

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(jobId, callback));
    }

    /**
     * Deletes all the documents from the given text index using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param index The index to delete from
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs with the request
     */
    public void deleteAllDocumentsFromTextIndex(
            final String apiKey,
            final String index,
            final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = deleteFromTextIndexService.deleteAllDocumentsFromTextIndex(apiKey, index);

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(apiKey, jobId, callback));
    }

    private class DeleteFromTextIndexPollingStatusRunnable extends PollingJobStatusRunnable<DeleteFromTextIndexResponse> {

        public DeleteFromTextIndexPollingStatusRunnable(final JobId jobId, final HodJobCallback<DeleteFromTextIndexResponse> callback) {
            super(jobId, callback, getExecutorService());
        }

        public DeleteFromTextIndexPollingStatusRunnable(final String apiKey, final JobId jobId, final HodJobCallback<DeleteFromTextIndexResponse> callback) {
            super(apiKey, jobId, callback, getExecutorService());
        }

        @Override
        public JobStatus<DeleteFromTextIndexResponse> getJobStatus(final JobId jobId) throws HodErrorException {
            return deleteFromTextIndexService.getJobStatus(jobId);
        }

        @Override
        public JobStatus<DeleteFromTextIndexResponse> getJobStatus(final String apiKey, final JobId jobId) throws HodErrorException {
            return deleteFromTextIndexService.getJobStatus(apiKey, jobId);
        }
    }

}
