package com.videoconference.controller;

import com.videoconference.converter.RoomConverter;
import com.videoconference.dto.room.RoomDTO;
import com.videoconference.entity.Room;
import com.videoconference.exception.ExpiredTokenException;
import com.videoconference.exception.UserNotFoundException;
import com.videoconference.security.CookieRequestFilter;
import com.videoconference.security.JwtRequestFilter;
import com.videoconference.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin
public class RoomController {
    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);
    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomConverter roomConverter;

    @Autowired
    private CookieRequestFilter cookieRequestFilter;

    @PostMapping("/room")
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomDTO roomDTO, HttpServletRequest request) {
        roomDTO.setCreatedBy(Integer.valueOf(cookieRequestFilter.getUserId(request)));
        RoomDTO room = roomConverter.toDTO(roomService.createRoom(roomDTO));
        return ResponseEntity.status(200).body(room);
    }

    @GetMapping("/room")
    public ResponseEntity<List<RoomDTO>> getRoom(HttpServletRequest request) {
        String user_id = cookieRequestFilter.getUserId(request);
        List<RoomDTO> rooms = roomService.findByCreateBy(Integer.valueOf(user_id))
                .stream().map(room -> roomConverter.toDTO(room))
                .collect(Collectors.toList());
        return ResponseEntity.status(200).body(rooms);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<RoomDTO> getDetailRoom(@PathVariable @NotBlank String roomId, HttpServletRequest request) {
        String user_id = cookieRequestFilter.getUserId(request);
        Room room = roomService.findByRoomIdAndUserId(Integer.valueOf(roomId), Integer.valueOf(user_id));
        return ResponseEntity.status(200).body(roomConverter.toDTO(room));
    }
}
