package com.algojudge.algojudge.dto;

import java.util.List;

public class PerplexityRequestDTO {
    private final String model;
    private final List<Message> messages;

    public PerplexityRequestDTO(String query, String problemInfo) {
        this.model = "sonar-reasoning";
        this.messages = List.of(
                new Message("system",
                        "You are a helpful assistant for DSA and programming. " +
                                "Rules:\n" +
                                "1. Be precise and concise.\n" +
                                "2. Only answer questions related to DSA, programming, or design patterns.\n" +
                                "3. Do NOT provide full code snippets — only hints and approaches.\n" +
                                "4. If asked for code or unrelated topics, respond with: " +
                                "'I am only allowed to respond to design patterns or programming or DSA queries.'\n" +
                                "5. Never include internal reasoning or <think> tags.\n\n" +
                                "Context: The following is the problem description provided for reference.\n" +
                                "----------------------\n" +
                                 problemInfo + "\n" +
                                "----------------------"
                ),
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
