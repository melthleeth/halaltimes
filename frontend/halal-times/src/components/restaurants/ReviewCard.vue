<template>
  <div
    class="G-market-sans font-color-black-400 bg-white card-corner border-line-full my-3 p-4 pt-2 transition duration-200 ease-in-out transform hover:scale-105"
  >
    <section id="user-info" class="flex items-center">
      <img
        src="@/assets/resources/default.png"
        alt="profile image"
        class="w-10 h-10 rounded-full mr-2 object-cover"
      />
      <article class="flex flex-col">
        <div class="star-ratings" ref="stars">
          <div
            class="star-ratings-fill space-x-2 text-lg"
            :style="{ width: ratingToPercent + '%' }"
          >
            <span>★</span><span>★</span><span>★</span><span>★</span
            ><span>★</span>
          </div>
          <div class="star-ratings-base space-x-2 text-lg">
            <span>★</span><span>★</span><span>★</span><span>★</span
            ><span>★</span>
          </div>
        </div>
        <div class="flex space-x-2 items-center">
          <span class="text-sm">{{ id_user }}</span
          ><span class="G-market-sans-L font-color-black-200 text-xs"
            >| {{ upload_date }}</span
          >
        </div>
      </article>
        <article class="justify-self-end text-sm">
      <base-button>추천 {{ likeCnt }}</base-button>
      </article>
    </section>
    <section id="user-review" class="flex">
      <span class="mt-2">{{ content }}</span>
    </section>
  </div>
</template>
<script>
export default {
  props: [
    'id_review',
    'id_user',
    'id_store',
    'store_name',
    'score',
    'content',
    'upload_date',
    'likeCnt'
  ],
  data() {
    return {
      ratingWidth: null
    };
  },
  computed: {
    ratingToPercent() {
    //   const score = +this.score * 0.2;
    const score = +this.score * 20;
    return score + 1.5;
    }
  },
  methods: {
  }
};
</script>
<style scoped>
.card-corner {
  border-radius: 0 0 30px 0;
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
</style>
