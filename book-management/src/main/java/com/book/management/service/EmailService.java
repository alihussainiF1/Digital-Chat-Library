package com.book.management.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private AmazonSimpleEmailService sesClient;

    public EmailService() {

        AWSCredentials awsCredentials = new BasicAWSCredentials("", "");
        this.sesClient = AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    public void sendEmail(String emailSubject, String messageText) {
        String to = ""; // Recipient's email
        String from = "";
        Content subject = new Content().withData(emailSubject);
        Content textBody = new Content().withData(messageText);
        Body body = new Body().withText(textBody);

        // Assemble the email
        SendEmailRequest request = new SendEmailRequest().withSource(from)
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message().withSubject(subject).withBody(body));

        try {
            sesClient.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.err.println("Error sending email: " + ex.getMessage());
        }
    }
}
