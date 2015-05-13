/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.util.TestCallback;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(Parameterized.class)
public class CreateAndDeleteTextIndexITCase extends AbstractHodClientIntegrationTest {

    private static final String testIndexName = "ice cream";

    private CreateTextIndexJobService createTextIndexService;
    private DeleteTextIndexJobService deleteTextIndexService;

    public CreateAndDeleteTextIndexITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();

        createTextIndexService = new CreateTextIndexJobService(getRestAdapter().create(CreateTextIndexService.class));
        deleteTextIndexService = new DeleteTextIndexJobService(getRestAdapter().create(DeleteTextIndexService.class));
    }

    @After
    public void tearDown() {
        createTextIndexService.destroy();
        deleteTextIndexService.destroy();
    }

    @Test
    public void testCreateAndDeleteTextIndex() throws HodErrorException, InterruptedException {
        final Map<String, Object> createParams = new CreateTextIndexRequestBuilder()
                .setDescription("A text index")
                .build();

        final CountDownLatch latch = new CountDownLatch(1);

        final DeleteIndexTestCallback callback = new DeleteIndexTestCallback(latch);

        createTextIndexService.createTextIndex(
                getToken(),
                testIndexName,
                IndexFlavor.explorer,
                createParams,
                callback);

        latch.await();

        final CreateTextIndexResponse createResponse = callback.getCreateResponse();
        assertThat(createResponse.getIndex(), is(testIndexName));
        assertThat(createResponse.getMessage(), is("index created"));

        final DeleteTextIndexResponse deleteResponse = callback.getInnerResult();

        assertThat(deleteResponse.getIndex(), is(testIndexName));
        assertThat(deleteResponse.isDeleted(), is(true));
    }

    private class DeleteIndexTestCallback extends TestCallback<CreateTextIndexResponse> {

        @Getter
        private CreateTextIndexResponse createResponse;

        private final TestCallback<DeleteTextIndexResponse> callback;

        public DeleteIndexTestCallback(final CountDownLatch latch) {
            super(latch);
            callback = new TestCallback<>(latch);
        }

        public DeleteTextIndexResponse getInnerResult() {
            return callback.getResult();
        }

        @Override
        public void success(final CreateTextIndexResponse result) {
            createResponse = result;

            log.debug("Index created successfully");

            try {
                deleteTextIndexService.deleteTextIndex(
                        getToken(),
                        testIndexName,
                        callback);
            } catch (final HodErrorException e) {
                log.error("Error deleting document", e);

                latch.countDown();
            }
        }
    }
}
