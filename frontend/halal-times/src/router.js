import { createRouter, createWebHistory } from "vue-router";

import Home from "./pages/Home.vue";
import NotFound from "./pages/NotFound.vue";
// user
import MyPage from "./pages/user/MyPage.vue";
import SignUp from "./pages/user/SignUp.vue";
import FindPassword from "./pages/user/FindPassword.vue";
// about
import About from "./pages/about/AboutHalal.vue";
// explore
import RestaurantList from "./pages/restaurants/RestaurantList.vue";
import RestaurantDetail from "./pages/restaurants/RestaurantDetail.vue";
// community
import Community from "./pages/community/Community.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    // { path: , component: },
    { path: "/", redirect: "/home" },
    // user
    { path: "/mypage", component:MyPage },
    { path: "/signup", component:SignUp },
    { path: "/findpw", component:FindPassword },
    // home
    { path: "/home", component: Home },
    // about
    { path: "/about", component: About },
    // explore
    { path: "/explore", component: RestaurantList },
    { path: "/explore/:restaurantName", name: 'RestaurantDetail', props: true, component: RestaurantDetail },
    // community
    { path: "/community", component: Community },
    // not found
    { path: "/:notFound(.*)", component: NotFound },
  ],
});

router.beforeEach(() => {
  window.scrollTo(0, 0);
});

export default router;
