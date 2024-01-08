//package com.book.management;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class BookManagementApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(BookManagementApplication.class, args);
//	}
//
//}
//
//
//
package com.book.management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.book.management.chat.ChatClient;

import javax.swing.SwingUtilities;

@SpringBootApplication
public class BookManagementApplication implements CommandLineRunner {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        SpringApplication.run(BookManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // This will ensure the chat client runs on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and display the chat client
                ChatClient chatClient = new ChatClient();
                chatClient.setVisible(true);
            }
        });
    }
}
