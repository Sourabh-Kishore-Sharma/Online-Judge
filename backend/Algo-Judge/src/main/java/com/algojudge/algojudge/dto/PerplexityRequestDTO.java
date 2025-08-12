package com.algojudge.algojudge.dto;

import java.util.List;

public class PerplexityRequestDTO {
    private final String model;
    private final List<Message> messages;

    public PerplexityRequestDTO(String query) {
        this.model = "sonar-reasoning";
        this.messages = List.of(
                new Message("system", "Be precise and concise. Only answer questions related to DSA, programming, or design patterns. " +
                        "Do not provide any code snippets — only give hints. " +
                        "If the user asks for code or something outside these topics, respond with: " +
                        "'I am only allowed to respond to design patterns or programming or DSA queries.'"),
                new Message("user", query));
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
