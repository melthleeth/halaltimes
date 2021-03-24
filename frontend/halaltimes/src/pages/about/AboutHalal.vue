<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-7/12 mx-auto px-10 py-6"
  >
    <section class="flex flex-col justify-center mt-4 mb-6">
      <span
        class="text-5xl w-full text-center py-1 mt-4 mb-1 font-outline work-sans border-line"
        >About</span
      >
      <article class="flex justify-between text-xs border-line">
        <span>VOL 1. No.1</span>
        <span>EVERYTHING BUT HALAL</span>
        <span>{{ currDate }}</span>
      </article>
    </section>
    <section class="flex">
        <article class="w-1/2">
      <img src="../../assets/resources/about_1.png" alt="about-1" class="" />
      </article>
      <article class="flex flex-col w-1/2 h-auto mx-2">
      <span class="text-lg tracking-widest mt-3 mb-1">할랄의 의미는「허락된 것」</span>
        <span class="text-sm tracking-wider leading-relaxed"
          >할랄(HALAL)은 생활 전반에 걸쳐 이슬람법에 따라 사용이 허용되는 것을
          뜻합니다. 음식, 의약품, 화장품, 심지어 가전제품까지 일상생활에서
          사용하는 모든 것이 해당되며, 그 중에서도 이슬람 율법이 허락해 무슬림이
          먹을 수 있는 음식을 할랄 식품이라고 합니다. 고기는 이슬람식 도축방식인
          다비하(Dhabihah)에 따라 도축한 고기만을 할랄 식품으로 인정하며, 이
          외에 이슬람 법에 금지되는 것들을 하람(Haram)이라고 합니다. 정확히
          할랄이나 하람으로 분류되지 않은 애매하거나 의심스러운 식음료들은
          슈브하(Syubhah)에 속합니다.</span
        >
      </article>
    </section>
  </div>
</template>
<script>
export default {
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
