import http from './index';

class MessageService {
  findMessageByMeetingId(data) {
    return http.get(`/rooms/${data.roomId}/meeting/${data.meetingId}/messages`);
  }

}
export default MessageService;