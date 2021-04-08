export default {
  setDrawer: (state, payload) => (state.drawer = payload),
  toggleDrawer: state => (state.drawer = !state.drawer),
  LOGIN(state, payload) {
    state.accessToken = payload['auth-token'];
    state.userEmail = payload['user-email'];
    state.userName = payload['user-name'];
    state.role = payload['role'];
    state.profileImage = payload['profile_image'];
  },
  LOGOUT(state) {
    state.accessToken = null;
    state.userEmail = '';
    state.userName = '';
    state.role = '';
    state.profileImage = '';
  },
  REGIST(state, payload) {
    state.accessToken = payload['auth-token'];
    state.userEmail = payload['user-email'];
    state.userName = payload['user-name'];
  },
  setProfileImage(state, payload) {
    state.profileImage = payload;
  }
};
