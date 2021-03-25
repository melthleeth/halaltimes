<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-7/12 mx-auto px-10 py-6"
  >
    <base-title>About</base-title>
    <section class="flex">
      <article class="w-1/2">
        <img src="../../assets/resources/about_1.png" alt="about-1" class="mt-4" />
      </article>
      <article class="flex flex-col w-1/2 h-auto ml-4 mr-2">
        <span class="text-xl tracking-widest mt-3 mb-2 font-bold"
          ><span class="halal-color">할랄</span>의 의미는 「허락된 것」</span
        >
        <span class="text-sm tracking-wide leading-7 my-1"
          ><span class="halal-color">할랄</span>(HALAL)은 생활 전반에 걸쳐
          이슬람법에 따라 사용이 허용되는 것을 뜻합니다. 음식, 의약품, 화장품,
          심지어 가전제품까지 일상생활에서 사용하는 모든 것이 해당되며, 그
          중에서도 이슬람 율법이 허락해 무슬림이 먹을 수 있는 음식을 할랄
          식품이라고 합니다. <br />고기는 이슬람식 도축방식인 다비하(Dhabihah)에 따라
          도축한 고기만을 할랄 식품으로 인정하며, 이 외에 이슬람 법에 금지되는
          것들을 <span class="haram-color">하람</span>(Haram)이라고 합니다.
          정확히 할랄이나 하람으로 분류되지 않은 애매하거나 의심스러운
          식음료들은 <span class="syu-color">슈브하</span>(Syubhah)에
          속합니다.</span
        >
      </article>
    </section>
    <section class="flex mt-4">
      <article class="flex flex-col w-1/2 mb-auto  ml-2 mr-4">
        <span class="text-xl tracking-widest mt-3 mb-2 font-bold"
          >전 세계 무슬림 인구는 약 21억 명으로 전 세계 인구의 25%. 
          <span class="halal-color">할랄시장</span>은 수출 시장의 핵심
          키워드</span
        >
        <span class="text-sm tracking-wide leading-7 my-1">
          무슬림 인구는 2020년에는 19억 명으로 세계인구의 24.9%, 2030년에는 22억
          명으로 세계인구의 26.4%를 차지하는 것으로 예상하고 있습니다.
          <br /><br />
          주요 거주지는 중동, 동남아, 아프리카 지역이나 최근 유럽,
          미주지역에서도 빠르게 인구수가 증가하고 있으며, 인구 증가에 따라
          무슬림시장도 급격하게 확대되고 있어 전체 할랄시장의 규모는 2018년 2조
          2000억 달러에서 2024년 3조 2000억 달러로 연평균 6.2% 늘어날
          전망입니다. <br /><br />
          무슬림 시장은 17억 명의 거대한 소비시장으로 풍부한 자원 기반의 경제력,
          급속한 인구 증가율, 왕성한 소비성향 등의 장점을 갖춰 매력적인 시장으로
          급부상되고 있으며, 시장의 확대에 따라 할랄인증의 중요성도 점차 가속화
          되고 있습니다.
        </span>
      </article>
      <article class="flex flex-col w-1/2 h-auto mx-2">
        <img src="@/assets/resources/about_2.png" alt="about-2" class="mt-4" />
        <img src="@/assets/resources/about_3.png" alt="about-3" class="mt-2" />
      </article>
    </section>
    <section class="mt-4">
      <article class="mr-2">
        <img src="@/assets/resources/about_4.png" alt="about-4" class="mt-4" />
      </article>
    </section>
    <section class="flex mt-4">
      <article class="w-1/2">
        <img src="@/assets/resources/about_5.png" alt="about-5" class="mt-4" />
      </article>
      <article class="flex flex-col w-1/2 h-auto mb-auto ml-4 mr-2">
        <span class="text-xl tracking-widest mt-3 mb-2 font-bold"
          >무엇 보다 <span class="halal-color">할랄인증</span>이 중요하다</span
        >
        <span class="text-sm tracking-wide leading-7 my-1"
          >할랄 인증은 이슬람국가의 정부 또는 민간기관이 주도하여 제품이 이슬람
          율법에 어긋나지 않은 제품임을 인증하는 제도입니다.<br /><br />할랄
          인증은 최근 식품뿐 아니라 패션, 화장품, 의약품, 관광 및 물류 산업까지
          범위를 확장하고 있으며 신앙을 중시하는 이슬람 사회에 진출하기 위한
          필수요건입니다.<br />
          국내에서 유일한 공식 할랄인증기관 한국이슬람교중앙회와
          해외인증대행기관 3개가 있으며 할랄교육과 심사 및 인증절차까지 한 번에
          해결할 수 있습니다.</span
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
  border: 3.2px solid #2b2a29;
}

#bg {
  background-color: #f4f2ea;
}

.halal-color {
  color: #4f8163;
}

.haram-color {
  color: #cf4f2e;
}

.syu-color {
  color: #723424;
}
</style>
