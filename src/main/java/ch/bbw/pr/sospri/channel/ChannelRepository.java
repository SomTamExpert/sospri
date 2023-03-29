package ch.bbw.pr.sospri.channel;

import org.springframework.data.repository.CrudRepository;

public interface ChannelRepository extends CrudRepository<Channel, Long> {
    Channel findDistinctByTopic(String topic);
}
