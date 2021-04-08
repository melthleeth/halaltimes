export default {
  setDrawer: (state, payload) => (state.drawer = payload),
  toggleDrawer: state => (state.drawer = !state.drawer),
  LOGIN(state, payload) {
    state.accessToken = payload['auth-token'];
    state.userEmail = payload['user-email'];
    state.userName = payload['user-name'];
    state.role = payload['role'];
  },
  LOGOUT(state) {
    state.accessToken = null;
    state.userEmail = '';
    state.userName = '';
    state.role = '';
  },
  REGIST(state, payload) {
    state.accessToken = payload['auth-token'];
    state.userEmail = payload['user-email'];
    state.userName = payload['user-name'];
  }
};
