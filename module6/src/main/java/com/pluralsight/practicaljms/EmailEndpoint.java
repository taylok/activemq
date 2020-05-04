package com.pluralsight.practicaljms;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@Path("messages")
@Singleton
public class EmailEndpoint {

    @EJB
    private JMS2EmailSender emailSender;

    @GET
    public String get() {
        return "TEST";
    }

    @POST
    public void post(String address) {
        emailSender.sendEmail(address, "Test", "TEXT");
    }
}
