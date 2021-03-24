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
  @font-face {
    font-family: 'GmarketSansMedium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

.G-market-sans {
  font-family: 'GmarketSansMedium';
}

.font-color-black-400 {
  color: #2B2A29;
}

.font-color-black-300 {
  color: #6A6969;
}

.font-color-black-200 {
  color: #AAA9A9;
}

.font-color-black-100 {
  color: #DAD3CB;
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
