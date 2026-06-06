package com.boardinghouse.boardinghouse_api.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.boardinghouse.boardinghouse_api.model.Room;
import com.boardinghouse.boardinghouse_api.repository.RoomRepository;

@Component
public class DataLoader implements CommandLineRunner{

    private final RoomRepository roomRepository;

    public DataLoader(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seeds sample room data if the database is empty
        if (roomRepository.count() == 0) {
            Room room1 = new Room();
            room1.setRoomNumber("A101");
            room1.setPricePerMonth(new BigDecimal("1500000"));
            room1.setIsAvailable(true);
            room1.setDescription("Standard room with AC");
            
            Room room2 = new Room();
            room2.setRoomNumber("A102");
            room2.setPricePerMonth(new BigDecimal("1500000"));
            room2.setIsAvailable(true);
            room2.setDescription("Standard room with fan");
            
            Room room3 = new Room();
            room3.setRoomNumber("B201");
            room3.setPricePerMonth(new BigDecimal("1750000"));
            room3.setIsAvailable(true);
            room3.setDescription("Deluxe room with balcony");
            
            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);
            
            System.out.println("Sample rooms seeded to database.");
        }
    }

}
