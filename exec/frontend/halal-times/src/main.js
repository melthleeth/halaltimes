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
import Toaster from "@meforma/vue-toaster";

const app = createApp(App);

app.use(router);
app.use(store);
// plugins
app.use(Toaster);

app.component('base-button', BaseButton);
app.component('base-dialog', BaseDialog);
app.component('base-title', BaseTitle);
app.component('base-title-small', BaseTitleSmall);
app.component('base-spinner', BaseSpinner);

app.mount('#app');
