package ch.bbw.km.winthi.message;

import org.springframework.data.repository.CrudRepository;

/**
 * RoomRepository
 * @author Marco Karpf
 * @version 13.04.2023
 */
public interface MessageRepository extends CrudRepository<Message, Long> {
     Iterable<Message> findAllByChannelId(Long channelId);


}

