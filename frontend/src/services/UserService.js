import http from './index';
class UserService {
  findAll(pageUser) {
    return http.get('/users', { params: pageUser });
  }

  findById(id) {
    return http.get(`/users/${id}`);
  }

  createUserHasFullInfo(user) {
    return http.post('/users', JSON.stringify(user));
  }

  updateUser(user) {
    return http.post(`/users/${user.userId}`, JSON.stringify(user));
  }

  delete(userId) {
    return http.delete(`/users/${userId}`);
  }

  createUser(data) {
    return http.post('/register', JSON.stringify(data));
  }

  confirmEmailRegistration(token) {
    return http.get('/register/registration-confirm/' + token);
  }

  login(data) {
    return http.post('/login', JSON.stringify(data));
  }

  update(data) {
    return http.put(`/user/${data.userId}`, JSON.stringify(data));
  }

  // delete(ids) {
  //   return http.delete('/user', JSON.stringify(ids));
  // }

  // findAll() {
  //   return http.get('/user');
  // }

  // findById(id) {
  //   return http.get('/user', { params: { user_id: id } });
  // }
}

export default UserService;