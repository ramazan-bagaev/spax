package com.kanasuki.game.test.event;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashSet;

@Singleton
public class EventManager {

    private final Collection<EventListener> eventListeners;

    @Inject
    public EventManager() {
        eventListeners = new HashSet<>();
    }

    public void fire(EventType eventType) {
        for (EventListener eventListener: eventListeners) {
            eventListener.onEvent(eventType);
        }
    }

    public void addListener(EventListener eventListener) {
        eventListeners.add(eventListener);
    }
}
