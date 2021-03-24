<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-7/12 mx-auto px-10 py-6"
  >
    <section class="flex flex-col justify-center mt-4 mb-6">
      <span
        class="text-5xl w-full text-center py-1 mt-4 mb-1 font-outline work-sans border-line"
        >Halal Times</span
      >
      <article class="flex justify-between text-xs border-line">
        <span>VOL 1. No.1</span>
        <span>EVERYTHING BUT HALAL</span>
        <span>{{ currDate }}</span>
      </article>
    </section>
    <section class="flex">
      <article class="w-1/2"></article>
      <article class="flex flex-col w-1/2 h-auto mx-2"></article>
    </section>
  </div>
</template>
<script>
// import KakaoMap from "@/components/layout/KakaoMap.vue";
export default {
  //   components: { KakaoMap },
  data() {
    return {};
  },
  computed: {
    currDate() {
      const event = new Date();
      const options = {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      };
      return event.toLocaleDateString(undefined, options);
    },
  },
};
</script>
<style scoped>
img {
  width: 100%;
  height: 100%;
}

#bg {
  background-color: #f4f2ea;
}
</style>
