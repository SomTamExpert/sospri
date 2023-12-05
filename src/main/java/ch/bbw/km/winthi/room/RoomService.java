package ch.bbw.km.winthi.room;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RoomService
 *
 * @author Marco Karpf
 * @version 13.04.2023
 */

@Slf4j
@Service
public class RoomService {
    @Autowired
    private RoomRepository repository;

    public Iterable<Room> getAll() {
        log.info("getting all rooms");
        return repository.findAll();
    }

    public Iterable<Room> getAllByCategory(Long categoryId) {
        log.info("getting all rooms by category id {}", categoryId);
        return repository.findAllByCategoryId(categoryId);
    }

    public List<Room> findByCategory(Long categoryId) {
        log.info("getting all rooms by category id {}", categoryId);
        return (List<Room>) repository.findAllByCategoryId(categoryId);
    }

    public Room getById(Long id) {
        log.info("getting room by id {}", id);
        return repository.findById(id).get();
    }

    public void add(Room room) {
        log.info("creating new room {}", room.getContent());
        repository.save(room);
    }

    public void update(Long id, Room room) {
        log.info("updating room {}", room.getContent());
        repository.save(room);
    }

    public void deleteById(Long id) {
        Room room = repository.findById(id).get();
        room.setCategory(null);
        log.warn("deleting room {}", room.getContent());
        repository.save(room);
        repository.deleteById(id);
    }

    public void deleteAll(List<Room> rooms) {
        log.warn("deleting all rooms");
        repository.deleteAll(rooms);
    }
}
