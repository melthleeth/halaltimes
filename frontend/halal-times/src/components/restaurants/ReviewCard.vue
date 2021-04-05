<template>
  <div
    class="G-market-sans font-color-black-400 bg-white card-corner border-line-full my-3 p-4 pt-2"
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
          <span class="text-sm">{{ nickname }}</span
          ><span class="G-market-sans-L font-color-black-200 text-xs"
            >| {{ upload_date }}</span
          >
        </div>
      </article>
      <article class="flex ml-auto justify-self-end text-sm">
        <div
          class="btn-like flex items-center justify-center cursor-pointer transition duration-100 ease-in-out transform hover:scale-105"
          :class="{ liked: liked }"
          @click="isLiked"
        >
          <img
            src="@/assets/icon/like@0.75x.png"
            alt="like icon"
            class="object-contain w-5 h-5"
          />
          <span class="G-market-sans-B text-xs ml-2 mt-1">
            {{ likeCount }}
          </span>
        </div>
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
    'nickname',
    'id_store',
    'store_name',
    'score',
    'content',
    'upload_date',
    'likeCnt'
  ],
  data() {
    return {
      ratingWidth: null,
      liked: false,
      likeCount: null,
    };
  },
  computed: {
    ratingToPercent() {
      //   const score = +this.score * 0.2;
      const score = +this.score * 20;
      return score + 1.5;
    }
  },
  created() {
      this.likeCount = this.likeCnt;
  },
  methods: {
      isLiked() {
          this.liked = !this.liked;
          if (this.liked) this.likeCount += 1;
          else this.likeCount -= 1;
      }
  }
};
</script>
<style scoped>
.card-corner {
  border-radius: 0 0 30px 0;
}

.btn-like {
    width: 5.25rem;
    color: #293644;
    background-color: #E1EEDF;
    border-radius: 50px;
    padding: 0.5rem 0.875rem;
}

.liked {
    background-color: #96CF90;
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
