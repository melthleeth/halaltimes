import mutations from './mutations.js';
import actions from './actions.js';
import getters from './getters.js';

export default {
  state() {
    return {
      // articles: require('@/data/articles.json'),
      drawer: false,
      items: [
        {
          text: '홈으로',
          href: ''
        }
        // {
        //   text: '게시판',
        //   href: '/board',
        // },
      ],
      accessToken: null,
      userEmail: '',
      userName: '',
      role: ''
    };
  },
  mutations,
  actions,
  getters
};
