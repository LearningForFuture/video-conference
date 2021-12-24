import http from './index';
class RoleService {
  getRoles() {
    return http.get('/roles');
  }
}
export default RoleService;