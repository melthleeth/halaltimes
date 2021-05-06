export default {
  setDrawer: (state, payload) => (state.drawer = payload),
  toggleDrawer: state => (state.drawer = !state.drawer),
  LOGIN(state, payload) {
    state.accessToken = payload['auth-token'];
    state.userEmail = payload['user-email'];
    state.userName = payload['user-name'];
    state.role = payload['role'];
    state.profileImage = payload['profile_image'] === null ? 'https://halaltimesbucket.s3.ap-northeast-2.amazonaws.com/%ED%84%B0%EB%B2%88.png' : payload['profile_image'];
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
  },
  setUserName(state, payload) {
    state.userName = payload;
  }
};