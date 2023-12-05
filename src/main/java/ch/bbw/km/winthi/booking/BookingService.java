package ch.bbw.km.winthi.booking;

import ch.bbw.km.winthi.room.Room;
import ch.bbw.km.winthi.room.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BookingService {

    @Autowired
    private BookingRepository repository;

    @Autowired
    RoomRepository roomRepository;

    public Iterable<Booking> getAll() {
        log.info("getting all channels");
        return repository.findAll();
    }

    public Booking getById(Long id) {
        log.info("getting booking by id {}", id);
        return repository.findById(id).get();
    }

    public Iterable<Booking> getByMember(String memberrId) {
        log.info("getting booking by member id {}", memberrId);
        return repository.findDistinctByMemberId(memberrId);
    }

    public Booking create(Booking booking) {
        log.info("creating new booking for member with id {}", booking.getMemberId());

        // Convert startDate and endDate from String to LocalDate
        LocalDate startDate = LocalDate.parse(booking.getStartDate());
        LocalDate endDate = LocalDate.parse(booking.getEndDate());

        // Check if the room is already booked
        Room room = roomRepository.findById(booking.getRoomId()).orElse(null);

        // Check if the time slot is already booked
        List<Booking> bookingsForRoom = repository.findAllByRoomId(booking.getRoomId());
        for (Booking existingBooking : bookingsForRoom) {
            LocalDate existingStartDate = LocalDate.parse(existingBooking.getStartDate());
            LocalDate existingEndDate = LocalDate.parse(existingBooking.getEndDate());

            if (startDate.isBefore(existingEndDate) && endDate.isAfter(existingStartDate)) {
                log.info("Time slot is already booked");
                return null;
            }
        }

        log.info("room is not booked, and time slot is available");
        repository.save(booking);

        if (room != null) {
            roomRepository.save(room);
            log.info("room is now booked");
            return booking;
        }
        return null;
    }


    public void deleteById(Long id) {
        Booking bookingToDelete = repository.findById(id).get();
        log.warn("deleting booking {}", bookingToDelete);
        repository.delete(bookingToDelete);
    }

    public void update(Long id, Booking value) {
        log.info("updating booking {}", value);
        Booking bookingToUpdate = repository.findById(id).get();
        bookingToUpdate.setStartDate(value.getStartDate());
        bookingToUpdate.setEndDate(value.getEndDate());
        bookingToUpdate.setPrice(value.getPrice());
        bookingToUpdate.setRoomId(value.getRoomId());
        repository.save(value);
    }
}
