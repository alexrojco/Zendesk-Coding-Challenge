#Zendesk Ticket Viewer 

### To start:
This project requires `com.fasterxml.jackson.core` library which is easily found through maven (version 2.12).

`TicketViewer.java` features a main method which you can run to test the functionality. 
Although it has been coded to use my credentials, the first 2 lines of the main method are email and password, and you can easily swap them out for your own.

Otherwise, this project does not require any installation, and it should run just fine if you download all the code (tested on IntelliJ, with Java 15 and JUnit 4.11).

###Usage:
To view a list of all the tickets, type `l` and it will show you the first 25 (or less if there's less than 25 of course). The code will notify you if there's more pages and you can type `n` to access the next page and you can repeat until there are no more pages or just press any other button to go back to the main menu.

To see details for a specific ticket, type `t` and then type the `ticket_id`. If such a ticket exists, the program will show you all the relevant info, otherwise, it will say that something wrong has occured and you'll be back in the main menu.

And of course, to exit you can type `q`. If a command is not recognized, you'll be given a smaller version of this paragraph on the CLI.