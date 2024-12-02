package com.codingapi.neo4j.example.em;

public enum Gender {

    MAN,
    WOMAN,
    UNKNOWN;

    public static Gender parser(String gender) {
        if (gender.equals("男")) {
            return MAN;
        }
        if (gender.equals("女")) {
            return WOMAN;
        }
        return UNKNOWN;
    }
}
