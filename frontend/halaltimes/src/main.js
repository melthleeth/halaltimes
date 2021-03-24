import { createApp } from 'vue'
import App from './App.vue'
import router from "./router.js";
import store from "./store/index.js";

import "./assets/styles/index.css";
import BaseButton from "./components/ui/BaseButton.vue";
import BaseDialog from "./components/ui/BaseDialog.vue";

const app = createApp(App);

app.use(router);
app.use(store);

app.component("base-button", BaseButton);
app.component("base-dialog", BaseDialog);

app.mount("#app");