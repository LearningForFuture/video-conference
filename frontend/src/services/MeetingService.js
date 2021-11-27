import http from './index';

class MeetingService {
  createMeetings(roomId, data) {
    return http.post(`/teams/room/${roomId}/meeting`, JSON.stringify(data));
  }

  updateMeeting(roomId, meetingId, data) {
    return http.put(`/teams/room/${roomId}/meeting/${meetingId}`, JSON.stringify(data));
  }
}

export default MeetingService;