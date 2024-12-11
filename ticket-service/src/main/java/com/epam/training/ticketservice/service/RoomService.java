package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public String createRoom(String name, int rows, int columns) {
        if (roomRepository.existsById(name)) {
            return "Room already exists";
        }
        roomRepository.save(new Room(name, rows, columns));
        return "Room created successfully";
    }

    public String updateRoom(String name, int rows, int columns) {
        Optional<Room> roomOptional = roomRepository.findById(name);
        if (!roomOptional.isPresent()) {
            return "Room not found";
        }
        Room room = roomOptional.get();
        room.setRows(rows);
        room.setColumns(columns);
        roomRepository.save(room);
        return "Room updated successfully";
    }

    public String deleteRoom(String name) {
        if (roomRepository.existsById(name)) {
            roomRepository.deleteById(name);
            return "Room deleted successfully";
        } else {
            return "Room not found";
        }
    }

    public String listRooms() {
        List<Room> rooms = roomRepository.findAll();
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        }
        return rooms.stream()
                .map(room -> String.format("Room %s with %d seats, %d rows and %d columns",
                        room.getName(), room.getRows() * room.getColumns(), room.getRows(), room.getColumns()))
                .collect(Collectors.joining("\n"));
    }
}