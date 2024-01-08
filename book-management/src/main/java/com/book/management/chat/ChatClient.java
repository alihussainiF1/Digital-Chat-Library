package com.book.management.chat;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import java.nio.charset.StandardCharsets;

public class ChatClient extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextArea chatbox = new JTextArea(30, 30);
    private JTextField typechat = new JTextField(20);
    private JButton sendButton = new JButton("Send");

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    public ChatClient() {
        super("Chat Client");

        this.setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        chatbox.setEditable(false);
        chatbox.setLineWrap(true);        
        chatbox.setWrapStyleWord(true);   

        JScrollPane scrollPane = new JScrollPane(chatbox);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.add(typechat);
        inputPanel.add(sendButton);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ChatFieldListener());
        typechat.addActionListener(new ChatFieldListener());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    class ChatFieldListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = typechat.getText().trim();
            if (!message.isEmpty()) {
                updateChatBox("User: " + message + "\n"); 
                sendMessageToServer(message);
                typechat.setText("");
            }
        }

        private void sendMessageToServer(String message) {
            String json = "{\"content\": \"" + message.replace("\"", "\\\"") + "\"}";

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost post = new HttpPost("http://127.0.0.1:8081/process_message");
                post.setHeader("Content-Type", "application/json");
                post.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

                try (CloseableHttpResponse response = httpClient.execute(post)) {
                    String responseString = EntityUtils.toString(response.getEntity());
                    JSONObject jsonResponse = new JSONObject(responseString);
                    String reply = jsonResponse.optString("reply", "No reply from server"); // Extract the reply
                    updateChatBox("Server: " + reply + "\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                updateChatBox("Error: Unable to send message\n");
            }
        }
        private void updateChatBox(String text) {
            SwingUtilities.invokeLater(() -> chatbox.append(text));
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClient().setVisible(true));
    }
}
