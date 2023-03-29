package ch.bbw.pr.sospri.message;

import ch.bbw.pr.sospri.channel.Channel;
import org.springframework.data.repository.CrudRepository;

/**
 * MessageRepository
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
public interface MessageRepository extends CrudRepository<Message, Long> {
     Iterable<Message> findAllByChannelId(Long channelId);


}

