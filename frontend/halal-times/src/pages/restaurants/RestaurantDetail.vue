<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-2/3 mx-auto px-10 py-6"
  >
    <base-title-small>Halal Times</base-title-small>
    <div id="top-restaurant-title" class="flex flex-col text-center ">
      <section class="w-full border-line">
        <span class="G-market-sans-B text-3xl tracking-widest">{{
          restaurantName
        }}</span>
      </section>
      <span class="text-lg font-color-black-200 my-2">{{ address }}</span>
    </div>
    <div id="center-contents" class="flex">
      <section id="left-contents" class="w-7/12">
        <img
          :src="imgsrc"
          alt="restaurant photo"
          class="object-cover w-full h-96 border-line-full"
        />
        <article class="flex flex-col my-8">
          <span class="font-bold text-2xl tracking-wider text-center mt-8 mb-6"
            >사용자 리뷰</span
          >
          <div v-if="isLoading" class="my-32">
            <base-spinner></base-spinner>
          </div>
          <div v-else-if="reviews.length > 0">
            <review-card
              v-for="reviewItem in reviews"
              :key="reviewItem.id_review"
              :id_review="reviewItem.id_review"
              :id_user="reviewItem.id_user"
              :nickname="reviewItem.nickname"
              :id_store="reviewItem.id_store"
              :store_name="reviewItem.store_name"
              :score="reviewItem.score"
              :content="reviewItem.content"
              :upload_date="reviewItem.upload_date"
              :likeCnt="reviewItem.likeCnt"
            ></review-card>
          </div>
          <span
            v-else
            class="text-xl text-center my-32 font-S-CoreDream-medium font-bold font-color-black-200"
            >등록된 리뷰가 없습니다. 첫 번째로 리뷰를 남겨보세요!</span
          >
        </article>
      </section>
      <section class="flex flex-col w-5/12 h-auto mx-2">
        <kakao-map></kakao-map>
        <!-- <img
          src="@/assets/resources/default.png"
          alt="map"
          class="object-cover w-full h-64 border-line-full"
        /> -->
        <span class="font-bold text-lg border-line mt-1 pl-1">위치</span>
        <div class="flex border-line text-sm my-2 px-2 pb-2">
          <article
            class="cursor-pointer transition duration-100 ease-in-out transform hover:scale-105"
            @click="bookmarkRestaurant"
          >
            <img
              v-if="!bookmarked"
              src="@/assets/icon/heart.png"
              alt="bookmark icon"
              class="object-contain w-8 h-8"
            />
            <img
              v-else
              src="@/assets/icon/heart_filled.png"
              alt="bookmark icon"
              class="object-contain w-8 h-8"
            />
          </article>
          <article class="flex ml-auto justify-self-end text-sm">
            <base-button mode="secondary" @click="writeReview"
              >리뷰 작성</base-button
            >
            <base-dialog
              :open="reviewDialogIsVisible"
              @close="closeReviewDialog"
              custom="width-wide"
              class="flex flex-col justify-items-center z-40"
            >
              <span
                class="flex justify-self-end ml-auto cursor-pointer text-2xl mr-2" @click="closeReviewDialog"
                >X</span
              >
              <span class="text-2xl font-bold mt-2 mb-4">리뷰 작성하기</span>
              <section class="flex flex-col space-y-4 w-full">
                <textarea
                  class="input-base"
                  rows="8"
                  placeholder="리뷰를 작성하세요"
                  v-model.trim="reviewContents"
                />
              </section>
              <section class="flex space-x-2 mt-6 mb-4">
                <base-button
                  
                  type="submit"
                  @click="register"
                  mode="primary"
                  class="text-base"
                >
                  등록하기</base-button
                >
              </section>
            </base-dialog>
          </article>
        </div>
        <article class="flex flex-col mx-10 space-y-4 mb-6">
          <span class="font-bold text-2xl tracking-wider text-center my-2"
            >음식점 정보</span
          >
          <div class="flex items-center">
            <span class="font-bold w-16 mr-4 text-right">태그</span>
            <article class="flex flex-col space-y-2">
              <span
                v-if="foodCategory"
                class="w-max bg-brown inline-block rounded-full px-3 py-1 text-sm text-white"
                >{{ foodCategory }}</span
              >
              <span
                v-if="muslimFriendly"
                class="w-max inline-block rounded-full px-3 py-1 text-sm text-white"
                :class="getTagColor"
                >{{ muslimFriendly }}</span
              >
            </article>
          </div>
          <div class="flex items-center">
            <span class="font-bold w-16 mr-4 text-right">평점</span>
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
            <span class="text-sm ml-4">({{ score }})</span>
          </div>
          <div class="flex items-center">
            <span class="font-bold w-16 mr-4 text-right">전화번호</span>
            {{ tel }}
          </div>
          <div class="flex items-center">
            <span class="font-bold w-16 mr-4 text-right">주차</span>
            {{ parking }}
          </div>
          <div class="flex items-center">
            <span class="font-bold w-16 mr-4 text-right">영업시간</span>
            {{ workingTime }}
          </div>
        </article>
        <article class="flex mx-auto">
          <base-button id="btn" @click="goPreviousPage">목록으로</base-button>
        </article>
      </section>
    </div>
  </div>
</template>
<script>
import ReviewCard from '../../components/restaurants/ReviewCard.vue';
import KakaoMap from '../../components/ui/KakaoMap.vue';
export default {
  components: {
    ReviewCard,
    KakaoMap
  },
  props: ['restaurantId', 'restaurantName'],
  data() {
    return {
      isLoading: false,
      restaurant: null,
      reviewDialogIsVisible: false,
      tagColor: 1,
      score: null,
      bookmarked: true,
      reviewContents: ''
    };
  },
  computed: {
    getTagColor() {
      if (this.tagColor === 1) return 'tag-color-1';
      else if (this.tagColor === 2) return 'tag-color-2';
      else if (this.tagColor === 3) return 'tag-color-3';
      else return 'tag-color-4';
    },
    ratingToPercent() {
      //   const score = +this.score * 0.2;
      const score = +this.score * 20;
      return score + 1.5;
    },
    restaurantname() {
      console.log('음식점 이름: ', this.restaurantName);
      return this.restaurantName;
    },
    address() {
      return this.restaurant.address;
    },
    imgsrc() {
      return this.restaurant.imgpath;
    },
    reviews() {
      const reviews = this.$store.getters['restaurants/reviews'].filter(
        review => {
          console.log('review id: ', review.id_store);
          console.log('restaurant id: ', this.restaurantId);
          if (review.id_store === this.restaurantId) return true;
        }
      );
      console.log(reviews);
      return reviews;
    },
    averageScore() {
      return this.restaurant.averageScore;
    },
    foodCategory() {
      return this.restaurant.foodCategory;
    },
    muslimFriendly() {
      return this.restaurant.muslimFriendly;
    },
    tel() {
      return this.restaurant.tel;
    },
    parking() {
      return this.restaurant.parking;
    },
    workingTime() {
      return this.restaurant.working_time;
    }
  },
  created() {
    console.log('id: ', this.restaurantId);
    this.restaurant = this.$store.getters['restaurants/restaurants'].find(
      restaurant => restaurant.restaurantId.toString() === this.restaurantId
    );

    this.score = this.restaurant.averageScore;

    // this.loadReviews();
    switch (this.muslimFriendly) {
      case '무슬림 자가 인증':
        this.tagColor = 2;
        break;
      case '무슬림 프렌들리':
        this.tagColor = 3;
        break;
      case '포크프리':
        this.tagColor = 4;
        break;
    }
  },
  methods: {
    goPreviousPage() {
      this.$router.go(-1);
    },
    async loadReviews() {
      this.isLoading = true;
      try {
        await this.$store.dispatch('restaurants/loadRestaurants');
      } catch (error) {
        this.error = error.message || '리뷰를 불러오는데 문제가 발생했습니다.';
      }
      this.isLoading = false;
    },
    register() {},
    bookmarkRestaurant() {
      this.bookmarked = !this.bookmarked;
    },
    writeReview() {
      this.reviewDialogIsVisible = true;
    },
    closeReviewDialog() {
      this.reviewDialogIsVisible = false;
    }
  }
};
</script>
<style scoped>
#bg {
  background-color: #f4f2ea;
}

#btn {
  width: 20rem;
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
