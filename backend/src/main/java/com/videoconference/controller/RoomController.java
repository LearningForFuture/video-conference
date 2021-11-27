package com.videoconference.controller;

import com.videoconference.converter.RoomConverter;
import com.videoconference.dto.pagination.PaginationParams;
import com.videoconference.dto.pagination.PaginationResponse;
import com.videoconference.dto.room.JoinByRoomCodeDTO;
import com.videoconference.dto.room.RoomDTO;
import com.videoconference.entity.Room;
import com.videoconference.security.CookieRequestFilter;
import com.videoconference.service.RoomService;
import com.videoconference.util.PaginationAndSortUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
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

    @GetMapping("/rooms")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getRooms(@Valid PaginationParams params) {
        Page<RoomDTO> pageRoomDTO = roomService.getRooms(params.getPage(),
                params.getSize(), params.getSort(), params.getKeyword());

        PaginationResponse<RoomDTO> response = PaginationAndSortUtil.map(pageRoomDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rooms/{room-id}")
    //    @PreAuthorize("hasAnyRole('ADMIN')")
    public RoomDTO getRoomByRoomId(@PathVariable("room-id") Integer roomId) {
        // tra ve room theo id
        return roomService.getRoomByRoomId(roomId);
    }

    @GetMapping("/rooms/{room-id}/users")
    public ResponseEntity<?> getUsersByRoomId(@PathVariable("room-id") Integer roomId) {
        // tra ve room ma user do tham gia
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/rooms/{room-id}/users")
    public ResponseEntity<?> addUser(@PathVariable("room-id") Integer roomId,
                                     @RequestBody String[] emails) {
        // add user into room
        roomService.addUser(roomId, emails);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping("/rooms/{room-id}/users/{user-id}")
    public ResponseEntity<?> removeUser(@PathVariable("room-id") Integer roomId,
                                        @PathVariable("user-id") Integer userId) {
        //remove user out of room
        roomService.removeUser(roomId, userId);
        return ResponseEntity.ok("OK");
    }

    //Join phong bang ma phong
    @PostMapping("/join-room/")
    public ResponseEntity<?> joinRoomByRoomCode(@Valid @RequestBody JoinByRoomCodeDTO joinRoomDTO) {
        // get user in security context
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        roomService.joinRoomByRoomCode(joinRoomDTO.getRoomCode(), userDetails.getUsername());
        return ResponseEntity.ok("OK");
    }
}
