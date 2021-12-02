package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class Content {
    private Ticket[] tickets;
    private Meta meta;
    private Links links;

    public Ticket[] getTickets() { return tickets; }

    public Meta getMeta() { return meta; }

    public Links getLinks() { return links; }

    @Override
    public String toString() {
        String output = "List of tickets: \n";
        for (Ticket r: tickets) {
            output = output.concat(r.toString()+"\n");
        }
        return output;
    }
}

