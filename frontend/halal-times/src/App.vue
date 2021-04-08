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
import TheHeader from './components/layout/TheHeader.vue';
import TheFooter from './components/layout/TheFooter.vue';

export default {
  components: {
    TheHeader,
    TheFooter
  }
};
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Work+Sans:ital,wght@1,900&display=swap');

@font-face {
  font-family: 'GmarketSansLight';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansLight.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: 'GmarketSansMedium';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: 'GmarketSansBold';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansBold.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}

.work-sans {
  font-family: 'Work Sans', sans-serif;
  letter-spacing: 0.2em;
}

.G-market-sans-L {
  font-family: 'GmarketSansLight';
}

.G-market-sans {
  font-family: 'GmarketSansMedium';
}

.G-market-sans-B {
  font-family: 'GmarketSansBold';
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

.border-line-full {
  border: 3.2px solid #2b2a29;
}

.font-outline {
  -webkit-text-fill-color: transparent; /* Will override color (regardless of order) */
  -webkit-text-stroke-width: 2px;
  -webkit-text-stroke-color: #2b2a29;
}

.card-corner {
  border-radius: 0 0 30px 0;
}

.text-2xs {
  font-size: 0.5rem;
  line-height: 0.75rem;
}

.tag-color-1 {
  background-color: #3d1f5d;
}
.tag-color-2 {
  background-color: #3a9cff;
}
.tag-color-3 {
  background-color: #ff4900;
}
.tag-color-4 {
  background-color: #ff9400;
}

.input-dialog {
  border: 3px solid #2b2a29;
  padding: 0.5rem 0.75rem;
  text-align: center;
}

.input-dialog:hover,
.input-dialog:focus {
  background-color: #fff58c;
}

.input-base {
  border: 3.2px solid #2b2a29;
  padding: 0.5rem 0.75rem;
  text-align: left;
}

.input-base:hover,
.input-base:focus {
  background-color: #fff58c;
}

.bg-primary {
  background-color: #cf4f2e;
}

.bg-secondary {
  background-color: #4f8163;
}

.bg-secondary-light {
  background-color: #e1eedf;
}

.bg-brown {
  background-color: #d0ac73;
}

.bg-purple {
  background-color: #3d1f5d;
}

.color-primary {
  color: #cf4f2e;
}

.color-secondary {
  color: #4f8163;
}

.color-important {
  color: #fff58c;
}

#top-button {
  border-radius: 100%;
  width: 4rem;
  height: 4rem;
  text-align: center;
  padding-top: 0.9rem;
  padding-left: 0.95rem;
  background-color: #fff58c;
  /* box-shadow: 0px 7px 20px rgba(35, 35, 47, 0.2); */
  transition: .3s ease-out;
}

#top-button img {
  width: 2rem;
  height: 2rem;
}

#top-button:hover {
  background-color: gold;
  animation: gelatine 0.7s;
}

.gelatine {
  animation: gelatine 0.5s infinite;
}
@keyframes gelatine {
  from, to { transform: scale(1, 1); }
  25% { transform: scale(0.9, 1.1); }
  50% { transform: scale(1.1, 0.9); }
  75% { transform: scale(0.95, 1.05); }
}


:root {
  --primary-color: #cf4f2e;
  --primary-color-hover: #903720;

  --secondary-color: #4f8163;
  --secondary-color-hover: #2f4d3b;

  --base-color: #2b2a29;
  --base-color-hover: #6a6969;

  --important-color: #fff58c;
}

.star-ratings {
  color: #aaa9a9;
  position: relative;
  unicode-bidi: bidi-override;
  width: max-content;
  -webkit-text-fill-color: transparent; /* Will override color (regardless of order) */
  -webkit-text-stroke-width: 1.3px;
  -webkit-text-stroke-color: #2b2a29;
}

.star-ratings-fill {
  color: #fff58c;
  padding: 0;
  position: absolute;
  z-index: 1;
  display: flex;
  top: 0;
  left: 0;
  overflow: hidden;
  -webkit-text-fill-color: gold;
}

.star-ratings-base {
  z-index: 0;
  padding: 0;
}

.star-rating {
  display: flex;
  flex-direction: row-reverse;
  font-size: 2.25rem;
  line-height: 2.5rem;
  justify-content: space-around;
  padding: 0 0.2em;
  text-align: center;
  width: 5em;
}

.star-rating input {
  display: none;
}

.star-rating label {
  -webkit-text-fill-color: transparent; /* Will override color (regardless of order) */
  -webkit-text-stroke-width: 2.3px;
  -webkit-text-stroke-color: #2b2a29;
  cursor: pointer;
}

.star-rating :checked ~ label {
  -webkit-text-fill-color: gold;
}

.star-rating label:hover,
.star-rating label:hover ~ label {
  -webkit-text-fill-color: #fff58c;
}

/*
How it works
The stars are labels which each reference a radio button. They are written in the code in reverse order, 5-1.

By using display:flex and flex-direction:row-reverse on their container they appear in the browser the opposite way around, 1-5.

We can now use the general sibling selector ~ to style any subsequent elements - the ones that appear before on screen.
*/

body {
  margin: 0;
  scroll-behavior: smooth;
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
