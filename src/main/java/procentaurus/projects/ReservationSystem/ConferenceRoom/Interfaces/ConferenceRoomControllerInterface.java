package procentaurus.projects.ReservationSystem.ConferenceRoom.Interfaces;

import org.springframework.http.ResponseEntity;
import procentaurus.projects.ReservationSystem.ConferenceRoom.ConferenceRoom;
import procentaurus.projects.ReservationSystem.ConferenceRoom.Dtos.ConferenceRoomUpdateDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ConferenceRoomControllerInterface {

    ResponseEntity<?> findSingleConferenceRoom(int number);

    ResponseEntity<List<ConferenceRoom>> findConferenceRooms(Map<String, String> params);

    ResponseEntity<List<ConferenceRoom>> findAvailableRooms(LocalDate startDate, int numberOfDays, boolean hasStage);

    ResponseEntity<?> deleteConferenceRoom(int number);

    ResponseEntity<?> updateConferenceRoom(int number, ConferenceRoomUpdateDto conferenceRoom);

    ResponseEntity<?> createConferenceRoom(ConferenceRoom conferenceRoom);
}
