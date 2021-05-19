package com.farzan.chat.service;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageListService {

    private List<String> messages;

    public MessageListService(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return new ArrayList<>(this.messages);
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }
}
