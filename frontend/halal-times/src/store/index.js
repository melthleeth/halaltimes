import { createStore } from 'vuex';
import axios from 'axios';
import createPersistedState from 'vuex-persistedstate';

import authModule from './modules/auth/index.js';
import restaurantModule from './modules/restaurants/index.js';
import accountModule from './modules/account/index.js';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

const store = createStore({
  modules: {
    auth: authModule,
    restaurants: restaurantModule,
    account: accountModule,
  },
  plugins: [
    createPersistedState({
      key: 'vuex',
      reducer(val) {
        if (val.accessToken === null) {
          // return empty state when user logged out
          return {};
        }
        return val;
      },
    }),
  ],
  state: {
    // articles: require('@/data/articles.json'),
    drawer: false,
    items: [
      {
        text: '홈으로',
        href: '',
      },
      // {
      //   text: '게시판',
      //   href: '/board',
      // },
    ],
    accessToken: null,
    userEmail: '',
    userName: '',
    role: '',
  },
  getters: {
    getAccessToken(state) {
      return state.accessToken;
    },
    getUserEmail(state) {
      return state.userEmail;
    },
    getUserName(state) {
      return state.userName;
    },
    getRole(state) {
      return state.role;
    },
  },
  mutations: {
    setDrawer: (state, payload) => (state.drawer = payload),
    toggleDrawer: (state) => (state.drawer = !state.drawer),
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
    },
  },
  actions: {
    LOGIN(context, user) {
      const params = new URLSearchParams();
      params.append('email', user.email);
      params.append('password', user.password);

      return axios
        .post(
          `${SERVER_URL}/login/confirm/login`,
          params // pwd: user.pwd,
          // name:'',
          // role:''
        )
        .then((response) => {
          if (response.data.message) {
            console.log('받았다', response);
            alert('아이디 또는 비밀번호를 틀렸습니다.', '', 'warning');
          } else {
            console.log(response);
            context.commit('LOGIN', response.data);
            axios.defaults.headers.common[
              'auth-token'
            ] = `${response.data['auth-token']}`;
            location.reload();
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    LOGOUT(context) {
      context.commit('LOGOUT');
      axios.defaults.headers.common['auth-token'] = undefined;
      location.href = '/';
    },
    REGIST(context, user) {
      return axios
        .post(`${SERVER_URL}/member/join`, user)
        .then((response) => {
          console.log(response);
          context.commit('REGIST', response.data);
          axios.defaults.headers.common[
            'auth-token'
          ] = `${response.data['auth-token']}`;
        })
        .error(() => {});
    },
  },
});

export default store;
