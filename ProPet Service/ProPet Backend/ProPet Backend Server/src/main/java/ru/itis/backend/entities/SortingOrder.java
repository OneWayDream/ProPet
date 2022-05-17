package ru.itis.backend.entities;

import ru.itis.backend.exceptions.search.IncorrectOrderException;

import java.util.Arrays;

public enum SortingOrder {

    ASCENDING("asc"),
    DESCENDING("desc");

    private final String value;

    SortingOrder(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static SortingOrder get(String value) {
        return Arrays.stream(SortingOrder.values())
                .filter(env -> env.value.equals(value))
                .findFirst().orElseThrow(IncorrectOrderException::new);
    }

}
