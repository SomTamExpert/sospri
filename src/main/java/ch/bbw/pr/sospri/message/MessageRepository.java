package ch.bbw.pr.sospri.message;

import ch.bbw.pr.sospri.channel.Channel;
import org.springframework.data.repository.CrudRepository;

/**
 * MessageRepository
 * @author Marco Karpf
 * @version 13.04.2023
 */
public interface MessageRepository extends CrudRepository<Message, Long> {
     Iterable<Message> findAllByChannelId(Long channelId);


}

