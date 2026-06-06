package com.boardinghouse.boardinghouse_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.boardinghouse.boardinghouse_api.model.Room;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByIsAvailableTrue(boolean isAvailable);
    Room findByRoomNumber(String roomNumber);
}
