package com.triple.mileage.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum EventAction {
    ADD,
    MOD,
    DELETE;

    @JsonCreator
    public static EventAction from(String s) {
        return EventAction.valueOf(s.toUpperCase());
    }
}
