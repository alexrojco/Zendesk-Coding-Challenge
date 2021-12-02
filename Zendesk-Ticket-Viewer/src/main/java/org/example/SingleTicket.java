package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class SingleTicket {
    
    private Ticket ticket;
    
    public Ticket getTicket() { return ticket; }
    
}
