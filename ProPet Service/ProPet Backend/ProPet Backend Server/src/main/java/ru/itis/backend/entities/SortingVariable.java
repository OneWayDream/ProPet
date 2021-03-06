package ru.itis.backend.entities;

import ru.itis.backend.exceptions.search.IncorrectSortingVariableException;

import java.util.Arrays;

public enum SortingVariable {

    AGE("age"),
    RATING("rating");

    private final String value;

    SortingVariable(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static SortingVariable get(String value) {
        return Arrays.stream(SortingVariable.values())
                .filter(env -> env.value.equals(value))
                .findFirst().orElseThrow(IncorrectSortingVariableException::new);
    }

}
