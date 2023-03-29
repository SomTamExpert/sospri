package ch.bbw.pr.sospri.message;

import ch.bbw.pr.sospri.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MessageService
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Service
public class MessageService {
   @Autowired
   private MessageRepository repository;

   public Iterable<Message> getAll() {
      return repository.findAll();
   }

    public Iterable<Message> getAllByChannelId(Long channelId) {
       return repository.findAllByChannelId(channelId);
    }

    public Message getById(Long id) {
        return repository.findById(id).get();
    }

   public void add(Message message) {
      repository.save(message);
   }

   public void update(Long id, Message message) {
      repository.save(message);
   }

   public void deleteById(Long id) {
      repository.deleteById(id);
   }
}
