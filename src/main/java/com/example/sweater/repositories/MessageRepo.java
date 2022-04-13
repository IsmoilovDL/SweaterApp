package com.example.sweater.repositories;

import com.example.sweater.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Integer> {

}
