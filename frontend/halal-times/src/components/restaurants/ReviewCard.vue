<template>
  <div
    class="G-market-sans font-color-black-400 bg-white card-corner border-line-full my-3 p-4 pt-2"
  >
    <section id="user-info" class="flex items-center">
      <img
        :src="thumbnail"
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
      <article class="flex ml-auto justify-self-end items-center text-sm">
        <div v-if="identification === nickname" class="space-x-2 mr-2">
          <base-button mode="small-round-brown" @click="openDialog('modify')"
            >수정</base-button
          >
          <base-button mode="small-round-primary" @click="openDialog('delete')"
            >삭제</base-button
          >
        </div>
        <base-dialog
          :open="modifyDialogIsVisible"
          @close="closeDialog('modify')"
          custom="width-wide"
          class="flex flex-col justify-items-center z-40"
        >
          <span
            class="flex justify-self-end ml-auto cursor-pointer text-2xl mr-2 icon-close"
            @click="closeDialog('modify')"
            >X</span
          >
          <span class="text-2xl font-bold mt-2 mb-4">리뷰 수정하기</span>
          <section class="flex flex-col space-y-4 w-full justify-center">
            <div class="star-rating space-x-4 mx-auto">
              {{ ratingEmoji }}
              <input
                type="radio"
                id="5-stars"
                name="rating"
                value="5"
                v-model="ratings"
              />
              <label for="5-stars" class="star pr-4">★</label>
              <input
                type="radio"
                id="4-stars"
                name="rating"
                value="4"
                v-model="ratings"
              />
              <label for="4-stars" class="star">★</label>
              <input
                type="radio"
                id="3-stars"
                name="rating"
                value="3"
                v-model="ratings"
              />
              <label for="3-stars" class="star">★</label>
              <input
                type="radio"
                id="2-stars"
                name="rating"
                value="2"
                v-model="ratings"
              />
              <label for="2-stars" class="star">★</label>
              <input
                type="radio"
                id="1-star"
                name="rating"
                value="1"
                v-model="ratings"
              />
              <label for="1-star" class="star">★</label>
            </div>
            <textarea
              class="input-base"
              rows="8"
              placeholder="리뷰를 작성하세요"
              v-model.trim="reviewContents"
            />
            <!-- <input type="file" accept=".png, .jpg, .jpeg, .gif" @change="uploadImage" /> -->
          </section>
          <section class="flex space-x-2 mt-6 mb-4">
            <base-button
              type="submit"
              @click="modifyReview"
              mode="brown"
              class="text-base"
            >
              수정하기</base-button
            >
          </section>
        </base-dialog>
        <base-dialog
          :open="deleteDialogIsVisible"
          @close="closeDialog('delete')"
          class="flex flex-col justify-items-center z-40"
        >
          <span
            class="flex justify-self-end ml-auto cursor-pointer text-2xl mr-2 icon-close"
            @click="closeDialog('delete')"
            >X</span
          >
          <span class="text-2xl font-bold mt-2 mb-4">리뷰 삭제</span>
          <section
            class="flex flex-col space-y-2 mt-6 mb-12 w-full justify-center text-center"
          >
            <span class="text-lg">정말 삭제하시겠어요?</span>
            <span class="text-xs">(되돌릴 수 없어요😭)</span>
          </section>
          <base-button
            type="submit"
            @click="deleteReview"
            mode="primary"
            class="text-base mb-2"
          >
            삭제하기</base-button
          >
        </base-dialog>
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
      <span class="mt-2 whitespace-pre">{{ content }}</span>
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
    'likeCnt',
    'likeCheck',
    'thumbnail'
  ],
  data() {
    return {
      ratingWidth: null,
      // liked: 0,
      // likeCount: null,
      modifyDialogIsVisible: false,
      deleteDialogIsVisible: false,
      ratings: 0,
      reviewContents: '',
      like: false
    };
  },
  computed: {
    ratingToPercent() {
      //   const score = +this.score * 0.2;
      const score = +this.score * 20;
      return score + 1.5;
    },
    identification() {
      return this.$store.getters.getUserName;
    },
    ratingEmoji() {
      if (this.ratings === '5') return '😍';
      else if (this.ratings === '4') return '😎';
      else if (this.ratings === '3') return '😄';
      else if (this.ratings === '2') return '😒';
      else if (this.ratings === '1') return '😠';
      else return '';
    },
    likeCount() {
      const reviews = this.$store.getters['restaurants/reviews'];
      const index = reviews.findIndex(
        review => review.id_review === +this.id_review
      );

      return this.$store.getters['restaurants/reviews'][index].likeCnt;
    },
    liked() {
      const reviews = this.$store.getters['restaurants/reviews'];
      const index = reviews.findIndex(
        review => review.id_review === +this.id_review
      );

      return this.$store.getters['restaurants/reviews'][index].likeCheck;
    }
  },
  created() {
    // this.likeCount = this.likeCnt;
    // this.liked = this.likeCheck;
    console.log("created: thumbnail", this.thumbnail);
  },
  methods: {
    updateAverageScore() {
      this.$store.dispatch('restaurants/refreshAverageScore');
    },

    async isLiked() {
      if (this.$store.getters.getUserEmail === '') {
        alert('로그인이 필요한 기능입니다.');
        return;
      }

      try {
        await this.$store.dispatch(
          'restaurants/toggleReviewLike',
          +this.id_review
        );
      } catch (error) {
        this.error = error.message || '리뷰 좋아요 중 문제가 발생했습니다.';
      }
      const reviews = this.$store.getters['restaurants/reviews'];
      const index = reviews.findIndex(
        review => review.id_review === +this.id_review
      );
      const likeCheck = this.$store.getters['restaurants/reviews'][index]
        .likeCheck;
      console.log('methods: isLiked/likeCheck', likeCheck);
      if (!likeCheck)
        this.$toast.info(
          `<span class="G-market-sans-L font-bold text-sm tracking-wide">😍 이 리뷰가 도움이 된다고 추천하였습니다.</span>`
        );
      else
        this.$toast.show(
          `<span class="G-market-sans-L font-bold text-sm tracking-wide">😥 추천을 취소합니다.</span>`
        );
    },
    openDialog(type) {
      if (type === 'modify') {
        this.modifyDialogIsVisible = true;
        this.ratings = this.score;
        this.reviewContents = this.content;
      } else this.deleteDialogIsVisible = true;
    },
    closeDialog(type) {
      if (type === 'modify') this.modifyDialogIsVisible = false;
      else this.deleteDialogIsVisible = false;
    },
    async modifyReview() {
      let result;
      try {
        result = await this.$store.dispatch('restaurants/modifyReview', {
          id_review: this.id_review,
          content: this.reviewContents,
          score: this.ratings
        });
      } catch (error) {
        this.error = error.message || '리뷰를 수정하는 중 문제가 발생했습니다.';
      }
      if (result === 'success')
        this.$toast.success(
          `<span class="G-market-sans-L font-bold text-sm tracking-wide">🌞 리뷰가 수정되었습니다.</span>`
        );
      else
        this.$toast.error(
          `<span class="G-market-sans-L font-bold text-sm tracking-wide">❌ 리뷰 수정에 실패하였습니다.</span>`
        );

      this.updateAverageScore();
      this.closeDialog('modify');
    },
    async deleteReview() {
      let result;
      try {
        result = await this.$store.dispatch(
          'restaurants/deleteReview',
          this.id_review
        );
      } catch (error) {
        this.error = error.message || '리뷰를 삭제하는 중 문제가 발생했습니다.';
      }
      if (result === 'success')
        this.$toast.success(
          `<span class="G-market-sans-L font-bold text-sm tracking-wide">🌞 리뷰가 삭제되었습니다.</span>`
        );
      else
        this.$toast.error(
          `<span class="G-market-sans-L font-bold text-sm tracking-wide">❌ 리뷰 삭제에 실패하였습니다.</span>`
        );

      this.updateAverageScore();
      this.closeDialog('delete');
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
  background-color: #e1eedf;
  border-radius: 50px;
  padding: 0.5rem 0.875rem;
}

.liked {
  background-color: #96cf90;
}

.icon-close:hover {
  color: #cf4f2e;
}


</style>
