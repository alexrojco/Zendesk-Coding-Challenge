package org.example;

import static org.example.TicketViewer.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TicketViewerTest {
    @Test
    public void getURLAuthTest(){                                   // Passes the test if the program doesn't crash on invalid credentials
        try {
            getURL("https://zccmail.zendesk.com/api/v2/tickets.json","email.that.is@invalid.com","randomchars");
            assertTrue( true );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void getURLWebsiteTest(){                                   // Passes the test if the program doesn't crash on invalid website
        try {
            getURL("https://zcccccccccccccccc.zendesk.com/ha/ha/haaaa","email.that.is@invalid.com","randomchars");
            assertTrue( true );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void viewTicketAuthTest(){                                   // Passes the test if the program doesn't crash on invalid credentials
        try {
            viewTicket(1,"email.that.is@invalid.com","randomchars");
            assertTrue( true );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void viewTicketIDTest(){                                   // Passes the test if the program doesn't crash on invalid ticket_id
        try {
            viewTicket(994949,"email.that.is@invalid.com","randomchars");
            assertTrue( true );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
