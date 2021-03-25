<template>
  <div>
    <the-header></the-header>
    <transition name="route" mode="out-in">
      <router-view v-slot="slotProps">
        <component :is="slotProps.Component"></component>
      </router-view>
    </transition>
    <the-footer></the-footer>
  </div>
</template>
<script>
import TheHeader from "./components/layout/TheHeader.vue";
import TheFooter from "./components/layout/TheFooter.vue";

export default {
  components: {
    TheHeader,
    TheFooter,
  },
};
</script>
<style>
@import url("https://fonts.googleapis.com/css2?family=Work+Sans:ital,wght@1,900&display=swap");

@font-face {
  font-family: "GmarketSansMedium";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

.work-sans {
  font-family: 'Work Sans', sans-serif;
  letter-spacing: 0.2em;
}

.G-market-sans {
  font-family: "GmarketSansMedium";
}

.font-color-black-400 {
  color: #2b2a29;
}

.font-color-black-300 {
  color: #6a6969;
}

.font-color-black-200 {
  color: #aaa9a9;
}

.font-color-black-100 {
  color: #dad3cb;
}

.border-line {
  border-bottom: 3.2px solid #2b2a29;
}

.font-outline {
  -webkit-text-fill-color: transparent; /* Will override color (regardless of order) */
  -webkit-text-stroke-width: 2px;
  -webkit-text-stroke-color: #2b2a29;
}

body {
  margin: 0;
}

input {
  outline: none;
}

.route-enter-from {
  opacity: 0;
  transform: translateY(-30px);
}

.route-leave-to {
  opacity: 0;
  transform: translateY(30px);
}

.route-enter-active {
  transition: all 0.3s ease-out;
}

.route-leave-active {
  transition: all 0.3s ease-in;
}

.route-enter-to,
.route-leave-from {
  opacity: 1;
  transform: translateY(0);
}
</style>
