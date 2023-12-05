package ch.bbw.km.winthi.channel;

import org.springframework.data.repository.CrudRepository;

public interface ChannelRepository extends CrudRepository<Channel, Long> {
    Channel findDistinctByTopic(String topic);
}
