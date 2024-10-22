/*
 *  Copyright (c) 2011-2015 The original author or authors
 *  ------------------------------------------------------
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *       The Eclipse Public License is available at
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *       The Apache License v2.0 is available at
 *       http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.stomp.tests.integration;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.ext.stomp.StompClient;
import io.vertx.ext.stomp.StompClientOptions;

/**
 * A verticle subscribing to a STOMP destination.
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class StompConsumer extends VerticleBase {

  private StompClient client;

  @Override
  public Future<?> start() throws Exception {
    System.out.println("Starting client");
    client = StompClient.create(vertx, new StompClientOptions(config()));
    return client
      .connect()
      .compose(conn -> conn
        .subscribe("/queue/event", frame -> System.out.println("Frame received : " + frame.getBodyAsString())));
  }

  @Override
  public Future<?> stop() throws Exception {
    return client.close();
  }
}
