package procentaurus.projects.ReservationSystem.ConferenceRoom.Interfaces;

import procentaurus.projects.ReservationSystem.ConferenceRoom.ConferenceRoom;
import procentaurus.projects.ReservationSystem.Exceptions.NonExistingConferenceRoomException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ConferenceRoomServiceInterface {

    Optional<ConferenceRoom> findSingleConferenceRoom(int number);

    List<ConferenceRoom> findConferenceRooms(Map<String, String> params);

    List<ConferenceRoom> findAvailableConferenceRooms(LocalDate startDate, int numberOfDays, boolean hasStage) throws NonExistingConferenceRoomException;

    boolean deleteConferenceRoom(int number);

    Optional<ConferenceRoom> updateConferenceRoom(int number, Map<String, String> params);

    Optional<ConferenceRoom> createConferenceRoom(ConferenceRoom conferenceRoom);
}
