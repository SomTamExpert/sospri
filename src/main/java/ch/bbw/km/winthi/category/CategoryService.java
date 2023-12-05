package ch.bbw.km.winthi.category;

import ch.bbw.km.winthi.channel.ChannelRepository;
import ch.bbw.km.winthi.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Iterable<Category> getAll() {
        log.info("getting all categories");
        return repository.findAll();
    }

    public Category getById(Long id) {
        log.info("getting category by id {}", id);
        return repository.findById(id).get();
    }

}
