package ch.bbw.pr.sospri.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChannelService {

    @Autowired
    private ChannelRepository repository;

    public Iterable<Channel> getAll() {
        log.info("getting all channels");
        return repository.findAll();
    }

    public Channel getById(Long id) {
        log.info("getting channel by id {}", id);
        return repository.findById(id).get();
    }

    public Channel getByTopic(String topic) {
        log.info("getting channel by topic {}", topic);
        return repository.findDistinctByTopic(topic);
    }

    public void add(Channel channel) {
        log.info("creating new channel {}", channel.getTopic());
        repository.save(channel);
    }

    public void update(Long id, Channel channel) {
        log.info("updating channel {}", channel.getTopic());
        repository.save(channel);
    }

    public void deleteById(Long id) {
        Channel channelToDelete = repository.findById(id).get();
        log.warn("deleting channel {}", channelToDelete);
        repository.delete(channelToDelete);
    }
}
