import http from './index';

class Roomservice {
  findAllHasPagination(pageRoom) {
    return http.get('/rooms', { params: pageRoom });
  }

  findAll() {
    return http.get('/room');
  }

  findByParamId(id) {
    return http.get('/room', { params: { room_id: id } });
  }

  createRoom(data) {
    return http.post('/room', JSON.stringify(data));
  }

  update(data) {
    return http.update(`/room/${data.roomId}`, JSON.stringify(data));
  }

  delete(ids) {
    return http.delete('room', JSON.stringify(ids));
  }

  findById(id) {
    return http.get(`/room/${id}`);
  }

  joindRoomByCode(data) {
    return http.post('/join-room', JSON.stringify(data));
  }
}

export default Roomservice;