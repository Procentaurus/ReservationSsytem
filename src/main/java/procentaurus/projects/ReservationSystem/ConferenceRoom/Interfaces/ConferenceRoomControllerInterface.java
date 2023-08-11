package procentaurus.projects.ReservationSystem.ConferenceRoom.Interfaces;

import org.springframework.http.ResponseEntity;
import procentaurus.projects.ReservationSystem.ConferenceRoom.ConferenceRoom;

import java.util.List;
import java.util.Map;

public interface ConferenceRoomControllerInterface {

    ResponseEntity<?> findSingleConferenceRoom(int number);

    ResponseEntity<List<ConferenceRoom>> findConferenceRooms(Map<String, String> params);

    ResponseEntity<?> deleteConferenceRoom(int number);

    ResponseEntity<?> updateConferenceRoom(int number, Map<String, String> params);

    ResponseEntity<?> createConferenceRoom(ConferenceRoom conferenceRoom);
}
