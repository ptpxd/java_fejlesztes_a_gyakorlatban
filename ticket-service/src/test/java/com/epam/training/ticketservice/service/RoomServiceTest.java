package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRoom() {
        when(roomRepository.existsById("Test Room")).thenReturn(false);

        String result = roomService.createRoom("Test Room", 10, 20);

        ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(roomCaptor.capture());
        Room savedRoom = roomCaptor.getValue();

        assertEquals("Room created successfully", result);
        assertEquals("Test Room", savedRoom.getName());
        assertEquals(10, savedRoom.getRows());
        assertEquals(20, savedRoom.getColumns());
    }

    @Test
    void testCreateRoomAlreadyExists() {
        when(roomRepository.existsById("Test Room")).thenReturn(true);

        String result = roomService.createRoom("Test Room", 10, 20);

        assertEquals("Room already exists", result);
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void testUpdateRoom() {
        Room room = new Room("Test Room", 10, 20);
        when(roomRepository.findById("Test Room")).thenReturn(Optional.of(room));

        String result = roomService.updateRoom("Test Room", 15, 25);

        assertEquals("Room updated successfully", result);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void testUpdateRoomNotFound() {
        when(roomRepository.findById("Test Room")).thenReturn(Optional.empty());

        String result = roomService.updateRoom("Test Room", 15, 25);

        assertEquals("Room not found", result);
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void testDeleteRoom() {
        when(roomRepository.existsById("Test Room")).thenReturn(true);
        doNothing().when(roomRepository).deleteById("Test Room");

        String result = roomService.deleteRoom("Test Room");

        assertEquals("Room deleted successfully", result);
        verify(roomRepository, times(1)).deleteById("Test Room");
    }

    @Test
    void testDeleteRoomNotFound() {
        when(roomRepository.existsById("Test Room")).thenReturn(false);

        String result = roomService.deleteRoom("Test Room");

        assertEquals("Room not found", result);
        verify(roomRepository, never()).deleteById(anyString());
    }

    @Test
    void testListRooms() {
        Room room = new Room("Test Room", 10, 20);
        when(roomRepository.findAll()).thenReturn(Collections.singletonList(room));

        String result = roomService.listRooms();

        assertEquals("Room Test Room with 200 seats, 10 rows and 20 columns", result);
    }

    @Test
    void testListRoomsEmpty() {
        when(roomRepository.findAll()).thenReturn(Collections.emptyList());

        String result = roomService.listRooms();

        assertEquals("There are no rooms at the moment", result);
    }
}