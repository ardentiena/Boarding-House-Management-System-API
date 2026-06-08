package com.boardinghouse.boardinghouse_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.boardinghouse.boardinghouse_api.repository.RoomRepository;
import com.boardinghouse.boardinghouse_api.model.Room;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomRepository roomRepository;
    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    // Get all rooms
    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    
    // Get room by ID
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Get available rooms
    @GetMapping("/available")
    public List<Room> getAvailableRooms() {
        return roomRepository.findByIsAvailableTrue();
    }
    // Create new room
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        // Check if room number already exists
        Room existingRoom = roomRepository.findByRoomNumber(room.getRoomNumber());
        if(existingRoom != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }
    // Update room
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Room room = optionalRoom.get();
        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setPricePerMonth(roomDetails.getPricePerMonth());
        room.setIsAvailable(roomDetails.getIsAvailable());
        room.setDescription(roomDetails.getDescription());

        Room updatedRoom = roomRepository.save(room);
        return ResponseEntity.ok(updatedRoom);
    }
    // Delete room
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        if(!roomRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        roomRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
