/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.error;

/**
 * Interface called when Exceptions are thrown when communicating with HP Haven OnDemand
 */
public interface HodErrorHandler {

    Throwable handleError(RuntimeException e);

}
