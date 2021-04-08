import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
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
      .then(response => {
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
      .catch(error => {
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
      .then(response => {
        console.log(response);
        context.commit('REGIST', response.data);
        axios.defaults.headers.common[
          'auth-token'
        ] = `${response.data['auth-token']}`;
      })
      .error(() => {});
  },
  setProfileImage(context, payload) {
    context.commit('setProfileImage', payload);
  }
};
