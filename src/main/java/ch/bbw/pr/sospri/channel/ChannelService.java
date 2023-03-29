package ch.bbw.pr.sospri.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository repository;

    public Iterable<Channel> getAll() {
        return repository.findAll();
    }

    public Channel getById(Long id) {
        return repository.findById(id).get();
    }

    public Channel getByTopic(String topic) {
        return repository.findDistinctByTopic(topic);
    }

    public void add(Channel channel) {
        repository.save(channel);
    }

    public void update(Long id, Channel channel) {
        repository.save(channel);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
