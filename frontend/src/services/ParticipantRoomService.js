import http from './index';
class ParticipantRoomService {
  addParticipantRoom(data) {
    return http.post('/join-room', JSON.stringify(data));
  }
	
  findByRoomId(roomId) {
    return http.get(`/room/${roomId}/participants`);
  }

  getParticipantsByRoomId(roomId) {
    return http.get(`rooms/${roomId}/participant-room`);
  }

}
export default ParticipantRoomService;