package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;
import java.util.Scanner;

public class TicketViewer {

    static class SimpleJSON{                                                // Object that contains the response code of the http request and the formatted data from JSON
        Content posts;
        int status;

        public Content getPosts() { return posts; }
        void setPosts(Content posts) { this.posts = posts; }

        public int getStatus() { return status; }
        public void setStatus(int status) { this.status = status; }
    }

    public static String viewTicket(int ticket_id, String email, String password) throws IOException, InterruptedException {              // Requests JSON ticket info from the API and returns a String with the most relevant info

        HttpClient client = HttpClient.newHttpClient();                                                     // Encodes password and email and connects to the API
        HttpRequest request = HttpRequest.newBuilder().GET()
                .header("Authorization", "Basic "+
                        Base64.getEncoder().encodeToString((email+":"+password).getBytes(StandardCharsets.UTF_8.toString())))
                .uri(URI.create("https://zccmail.zendesk.com/api/v2/tickets/"+ticket_id+".json")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200) return "Failed to connect to the API or invalid ticket_id.";       // Checks whether the request was successful and data was retrieved

        ObjectMapper mapper = new ObjectMapper();                                                           // parsing the ticket
        SingleTicket result = mapper.readValue(response.body(), new TypeReference<SingleTicket>() {});
        Ticket ticket = result.getTicket();

        if (ticket.getError() != null) return "Error: invalid ticket_id.";                                  // In case the connection was a success but JSON data was invalid
        else {                                                                                              // Otherwise, presenting data in a pretty format
            String ticketDetails = ticket.getStatus().substring(0,1).toUpperCase(Locale.ROOT)+ticket.getStatus().substring(1) +
                    " ticket #" + ticket.getId() + " with "+ ticket.getPriority() + " priority : "+ ticket.getSubject() +
                    "\n\t requested by "+ ticket.getRequester_id() + "\n" + ticket.getDescription();
            return ticketDetails;
        }
    }

    public static SimpleJSON getURL(String url, String email, String password) throws IOException, InterruptedException {         // Requests JSON data from the API and parses it into easy to use objects
        SimpleJSON content = new SimpleJSON();

        HttpClient client = HttpClient.newHttpClient();                                             // Encodes password and email and connects to the API
        HttpRequest request = HttpRequest.newBuilder().GET()
                .header("Authorization", "Basic "+
                        Base64.getEncoder().encodeToString((email+":"+password).getBytes(StandardCharsets.UTF_8.toString())))
                .uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        content.setStatus(response.statusCode());                                                   // Passing the response code in case connection failed to avoid null pointer exceptions
        if (response.statusCode() != 200) return content;

        ObjectMapper mapper = new ObjectMapper();                                                   // parsing tickets and next pages
        content.setPosts(mapper.readValue(response.body(), new TypeReference<Content>() {}) );
        return content;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final String email = "alex.rojco@mail.mcgill.ca";
        final String password = "6Bnd6gAin*&Z";

        System.out.println("\nWelcome to Zendesk CLI Ticket Viewer!\n");            // A welcome message and basic instructions

        System.out.println("\tTo list your tickets, type l.");
        System.out.println("\tTo view a specific ticket, type t");
        System.out.println("\tTo exit, type q.");

        System.out.print("\nPlease enter a command: ");

        Scanner sc = new Scanner(System.in);                                        // Reading the command
        char input = sc.next().charAt(0);

        SimpleJSON content;

        String zendesk = "https://zccmail.zendesk.com/api/v2/tickets.json?page[size]=25";

        while (input != 'q') {
            switch (input){
                case 'l':                                                           // Listing all tickets, 25 per page
                    content = getURL(zendesk,email,password);

                    if (content.getStatus() != 200) {                              // Printing the error code in case connection was unsuccessful
                        System.out.println("Failed to connect to the API: Error code " + content.getStatus());
                        break;
                    } else {

                        System.out.println(content.getPosts());                     // Printing the tickets

                        while (content.getPosts().getMeta().getHas_more()) {        // Looping until no more pages are left or the user wants to leave
                            System.out.print("To view next page, type n. To stop, type any other letter:");

                            sc = new Scanner(System.in);                            // Reading the command
                            input = sc.next().charAt(0);

                            if (input == 'n') {
                                content = getURL(content.getPosts().getLinks().getNext(), "alex.rojco@mail.mcgill.ca","6Bnd6gAin*&Z");
                                System.out.println(content.getPosts());
                            } else break;
                        }
                    }
                    break;

                case 't':                                                           // Requesting a detailed view of a ticket
                    System.out.print("\nPlease enter the ticket ID: ");             // Reading the command
                    sc = new Scanner(System.in);
                    int ticket_id = sc.nextInt();

                    System.out.println(viewTicket(ticket_id,email,password));       // Helper function that returns details of a ticket
                    break;

                default:                                                            // Default case when the command is not understood
                    System.out.println("Command unknown.");
                    System.out.println("\tTo list your tickets, type l.");
                    System.out.println("\tTo view a specific ticket, type t");
                    System.out.println("\tTo exit, type q.");
            }

            System.out.print("\nPlease enter a command: ");                         // Reading the command
            sc = new Scanner(System.in);
            input = sc.next().charAt(0);
        }
    }
}
