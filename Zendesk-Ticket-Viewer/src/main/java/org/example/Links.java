package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {
    private String prev;
    private String next;

    public String getNext() {
        return next;
    }

    public String getPrev() {
        return prev;
    }

}
