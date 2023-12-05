package ch.bbw.km.winthi.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RoomService
 *
 * @author Marco Karpf
 * @version 13.04.2023
 */

@Slf4j
@Service
public class MessageService {
    @Autowired
    private MessageRepository repository;

    public Iterable<Message> getAll() {
        log.info("getting all messages");
        return repository.findAll();
    }

    public Iterable<Message> getAllByChannelId(Long channelId) {
        log.info("getting all messages by channel id {}", channelId);
        return repository.findAllByChannelId(channelId);
    }

    public List<Message> findByChannelId(Long channelId) {
        log.info("getting all messages by channel id {}", channelId);
        return (List<Message>) repository.findAllByChannelId(channelId);
    }

    public Message getById(Long id) {
        log.info("getting message by id {}", id);
        return repository.findById(id).get();
    }

    public void add(Message message) {
        log.info("creating new message {}", message.getContent());
        repository.save(message);
    }

    public void update(Long id, Message message) {
        log.info("updating message {}", message.getContent());
        repository.save(message);
    }

    public void deleteById(Long id) {
        Message message = repository.findById(id).get();
        message.setChannel(null);
        log.warn("deleting message {}", message.getContent());
        repository.save(message);
        repository.deleteById(id);
    }

    public void deleteAll(List<Message> messages) {
        log.warn("deleting all messages");
        repository.deleteAll(messages);
    }
}
