package ch.bbw.km.winthi.category;

import ch.bbw.km.winthi.channel.Channel;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findDistinctByTitle(String topic);
}
