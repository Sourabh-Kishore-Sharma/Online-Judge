package com.algojudge.algojudge.dto;

import java.util.List;

public class PerplexityRequestDTO {
    private final String model;
    private final List<Message> messages;

    public PerplexityRequestDTO(String query) {
        this.model = "sonar";
        this.messages = List.of(new Message("user", query));
    }

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public static class Message {
        private final String role;
        private final String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public String getContent() {
            return content;
        }
    }
}
