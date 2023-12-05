package ch.bbw.km.winthi.booking;

import ch.bbw.km.winthi.channel.Channel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    Iterable<Booking> findDistinctByMemberId(String topic);

    List<Booking> findAllByRoomId(Long roomId);

}
