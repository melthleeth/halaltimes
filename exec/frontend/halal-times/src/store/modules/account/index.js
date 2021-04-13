// 전체 회원 관리
import mutations from './mutations.js';
import actions from './actions.js';
import getters from './getters.js';

export default {
  namespaced: true, // 이름으로 쓰겠다.
  state() {
    return {
      userInfo: null
    };
  },
  mutations,
  actions,
  getters
};
