package ch.bbw.km.winthi.room;

import org.springframework.data.repository.CrudRepository;

/**
 * RoomRepository
 * @author Marco Karpf
 * @version 13.04.2023
 */
public interface RoomRepository extends CrudRepository<Room, Long> {
     Iterable<Room> findAllByCategoryId(Long channelId);


}

