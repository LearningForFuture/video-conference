import http from './index';

class ParticipantMeetingService {
  joindMeeting(data) {
    return http.post(`/meeting/${data.meetingId}/join-meeting`, JSON.stringify(data));
  }

  leftMeeting(data) {
    return http.put(`/meeting/${data.meetingId}/left-meeting`, JSON.stringify(data));
  }
}

export default ParticipantMeetingService;