/*
 * DirectIOEventRequest.java
 *
 * Created on March 13 2008, 13:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.shtrih.jpos.events;

import jpos.events.DirectIOEvent;
import jpos.services.EventCallbacks;

/**
 * @author V.Kravtsov
 */

public final class DirectIOEventRequest implements Runnable {
    private final EventCallbacks cb;
    private final DirectIOEvent event;

    public DirectIOEventRequest(EventCallbacks cb, DirectIOEvent event) {
        this.cb = cb;
        this.event = event;
    }

    public void run() {
        cb.fireDirectIOEvent(event);
    }
}
