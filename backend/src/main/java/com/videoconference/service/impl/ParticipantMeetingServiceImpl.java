package com.videoconference.service.impl;

import com.videoconference.converter.ParticipantMeetingConverter;
import com.videoconference.dto.ParticipantMeeting.ParticipantMeetingDTO;
import com.videoconference.entity.Meeting;
import com.videoconference.entity.ParticipantMeeting;
import com.videoconference.entity.ParticipantMeetingPK;
import com.videoconference.entity.User;
import com.videoconference.exception.MeetingNotFoundException;
import com.videoconference.exception.ParticipantMeetingNotFound;
import com.videoconference.exception.UserNotFoundException;
import com.videoconference.repository.MeetingRepository;
import com.videoconference.repository.ParticipantMeetingRepository;
import com.videoconference.repository.UserRepository;
import com.videoconference.service.ParticipantMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipantMeetingServiceImpl implements ParticipantMeetingService {
    @Autowired
    private ParticipantMeetingRepository participantMeetingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private ParticipantMeetingConverter participantMeetingConverter;

    @Override
    public boolean isExistParticipantByMeetingIdAndParticipantId(UUID meetingId, Integer participantId) {
        return participantMeetingRepository.findByMeetingIdAndParticipantId(meetingId, participantId).isPresent();
    }

    @Override
    public ParticipantMeetingDTO joinOrLeftMeeting(ParticipantMeetingDTO participantMeetingDTO) {
        User user = userRepository.findById(participantMeetingDTO.getParticipantId())
                .orElseThrow(() -> new UserNotFoundException());
        Meeting meeting = meetingRepository.findById(participantMeetingDTO.getMeetingId())
                .orElseThrow(() -> new MeetingNotFoundException());
        ParticipantMeetingPK participantMeetingPK = new ParticipantMeetingPK();
        participantMeetingPK.setMeetingId(meeting.getMeetingId());
        participantMeetingPK.setParticipantId(user.getUserId());

        Optional<ParticipantMeeting> participantMeeting = participantMeetingRepository.findById(participantMeetingPK);

        participantMeeting.ifPresent(participantMeeting_old -> {
            participantMeeting_old.setLeftAt(Timestamp.valueOf(LocalDateTime.now()));
        });



        return participantMeetingConverter.toDTO(participantMeetingRepository
                    .save(participantMeeting.orElseGet(() -> {
                        ParticipantMeeting participantMeeting_new =  new ParticipantMeeting();
                        participantMeeting_new.setId(participantMeetingPK);
                        participantMeeting_new.setMeeting(meeting);
                        participantMeeting_new.setUser(user);
                        participantMeeting_new.setJoinedAt(Timestamp.valueOf(LocalDateTime.now()));
                        return participantMeeting_new;
                    })));
    }

    @Override
    public ParticipantMeetingDTO leftMeeting(ParticipantMeetingDTO participantMeetingDTO) {
        ParticipantMeetingPK participantMeetingPK = new ParticipantMeetingPK();
        participantMeetingPK.setParticipantId(participantMeetingDTO.getParticipantId());
        participantMeetingPK.setMeetingId(participantMeetingDTO.getMeetingId());
        ParticipantMeeting participantMeeting = participantMeetingRepository.findById(participantMeetingPK)
                .orElseThrow(() -> new ParticipantMeetingNotFound());

        participantMeeting.setLeftAt(Timestamp.valueOf(LocalDateTime.now()));
        return participantMeetingConverter.toDTO(participantMeetingRepository.save(participantMeeting));
    }
}
