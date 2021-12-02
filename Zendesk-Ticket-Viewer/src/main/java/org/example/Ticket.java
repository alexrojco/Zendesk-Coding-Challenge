package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class Ticket {

    private int id;
    private String subject;
    private String description;
    private String requester_id;
    private String error;
    private String priority;
    private String status;

    public int getId() { return id; }

    public String getSubject() { return subject; }

    public String getDescription() { return description; }

    public String getRequester_id() { return requester_id; }

    public String getError() { return error; }

    public String getPriority() { return priority; }

    public String getStatus() { return  status; }

    @Override
    public String toString() {
        return "Ticket #" + id + ": \t '" + subject + '\'';
    }
}
