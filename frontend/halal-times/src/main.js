import { createApp } from 'vue';
import App from './App.vue';
import router from './router.js';
import store from './store/index.js';

import './assets/styles/index.css';
import BaseButton from './components/ui/BaseButton.vue';
import BaseDialog from './components/ui/BaseDialog.vue';
import BaseTitle from './components/ui/BaseTitle.vue';
import BaseTitleSmall from './components/ui/BaseTitleSmall.vue';
import BaseSpinner from './components/ui/BaseSpinner.vue';
// plugins
import naver from 'vue-naver-maps';
import Toaster from "@meforma/vue-toaster";

const app = createApp(App);
const API_KEY = process.env.VUE_APP_NAVER_MAP_KEY;

app.use(router);
app.use(store);
app.use(naver, {
  clientID: API_KEY,
  useGovAPI: false, //공공 클라우드 API 사용 (선택)
  subModules: '' // 서브모듈 (선택)
});
app.use(Toaster);

app.component('base-button', BaseButton);
app.component('base-dialog', BaseDialog);
app.component('base-title', BaseTitle);
app.component('base-title-small', BaseTitleSmall);
app.component('base-spinner', BaseSpinner);

app.mount('#app');
