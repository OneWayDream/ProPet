package ru.itis.backend.entities;

import ru.itis.backend.exceptions.IncorrectSearchVariableException;
import ru.itis.backend.exceptions.IncorrectSortingVariableException;

import java.util.Arrays;

public enum SearchVariable {

    CITY("city");

    private final String value;

    SearchVariable(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static SearchVariable get(String value) {
        return Arrays.stream(SearchVariable.values())
                .filter(env -> env.value.equals(value))
                .findFirst().orElseThrow(IncorrectSearchVariableException::new);
    }

}
