<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-7/12 mx-auto px-10 py-6"
  >
    <base-title>Halal Times</base-title>
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
