import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';

import authModule from './modules/auth/index.js';
import restaurantModule from './modules/restaurants/index.js';
import accountModule from './modules/account/index.js';
import recommModule from './modules/recomm/index.js';

// const SERVER_URL = process.env.VUE_APP_SERVER_URL;

const store = createStore({
  modules: {
    auth: authModule,
    restaurants: restaurantModule,
    account: accountModule,
    recomm: recommModule,
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
});

export default store;
