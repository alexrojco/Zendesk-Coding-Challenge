package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
    private boolean has_more;

    public boolean getHas_more() {
        return has_more;
    }
}
