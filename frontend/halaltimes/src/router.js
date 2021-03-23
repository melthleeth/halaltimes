import { createRouter, createWebHistory } from "vue-router";

import Home from "./pages/Home.vue";
import NotFound from "./pages/NotFound.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    // { path: , component: },
    { path: "/", redirect: "/home" },
    // home
    { path: "/home", component: Home },
    // not found
    { path: "/:notFound(.*)", component: NotFound },
  ],
});

router.beforeEach(() => {
    window.scrollTo(0, 0);
  });
  
  export default router;