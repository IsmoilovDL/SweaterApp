package com.example.sweater.service;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.MessageDTO;
import com.example.sweater.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    public Page<MessageDTO> messageList(Pageable pageable, String filter, User user){
        if(filter!=null && !filter.isEmpty()) {
            return messageRepo.findByTag(filter, pageable, user);
        }else {
            return messageRepo.findAll(pageable, user);
        }
    }

    public Page<MessageDTO> messageListForUser(Pageable pageable, User currantUser, User author) {
        return messageRepo.findByUser(pageable,author, currantUser);
    }
}
