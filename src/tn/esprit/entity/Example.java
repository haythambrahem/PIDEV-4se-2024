/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entity;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
/**
 *
 * @author fadi saidi
 */
// Install the Java helper library from twilio.com/docs/java/install



public class Example {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = "AC9a1268e7aefa6ba923e27df703fe77c4";
    public static final String AUTH_TOKEN = "2fc773b6619813f79cb0bf3b9c0ca47e";

    public static void sendTwilioSMS(String recipientPhoneNumber, String messageText) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(recipientPhoneNumber),
                new com.twilio.type.PhoneNumber("+16073885748"), // Replace with your Twilio phone number
                messageText)
            .create();

        System.out.println(message.getSid());
    }
}