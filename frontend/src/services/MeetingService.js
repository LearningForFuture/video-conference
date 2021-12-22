import http from './index';

class MeetingService {
  createMeetings(roomId, data) {
    return http.post(`/teams/room/${roomId}/meeting`, JSON.stringify(data));
  }

  updateMeeting(roomId, meetingId, data) {
    return http.put(`/teams/room/${roomId}/meeting/${meetingId}`, JSON.stringify(data));
  }

  getAllMeetingByRoomId(roomId) {
    return http.get(`/teams/room/${roomId}/meeting`);
  }

  findByMeetingId(data) {
    return http.get(`/teams/room/${data.roomId}/meeting/${data.meetingId}`);
  }

  getUsersInCall(data) {
    return http.get(`/teams/room/${data.roomId}/meeting/${data.meetingId}/user-online-status`);
  }
}

export default MeetingService;