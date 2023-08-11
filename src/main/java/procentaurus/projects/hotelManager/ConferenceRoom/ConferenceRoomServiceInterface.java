package procentaurus.projects.hotelManager.ConferenceRoom;

import procentaurus.projects.hotelManager.Exceptions.NonExistingConferenceRoomException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ConferenceRoomServiceInterface {

    Optional<ConferenceRoom> findSingleConferenceRoom(int number);

    List<ConferenceRoom> findConferenceRooms(Map<String, String> params);

    List<ConferenceRoom> findAvailableRooms(LocalDate startDate, int numberOfDays, boolean hasStage) throws NonExistingConferenceRoomException;

    boolean deleteConferenceRoom(int number);

    Optional<ConferenceRoom> updateConferenceRoom(int number, Map<String, String> params);

    Optional<ConferenceRoom> createConferenceRoom(ConferenceRoom conferenceRoom);
}
