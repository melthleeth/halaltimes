<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-2/3 mx-auto px-10 py-6"
  >
    <base-title>Community</base-title>
    <section v-if="username !== ''" class="flex flex-col">
      <span class="font-bold text-2xl text-center">{{ username }}님을 위한 추천 음식점</span>
      <div class="grid grid-cols-3 grid-flow-row gap-1 mx-auto">
        <restaurant-card
          v-for="restaurant in recommendRestaurants.slice(0, 6)"
          :key="restaurant.restaurantId"
          :restaurantId="restaurant.restaurantId"
          :imgpath="restaurant.imgpath"
          :restaurantName="restaurant.restaurantName"
          :averageScore="restaurant.averageScore"
          :address="restaurant.address"
          :hits="restaurant.hits"
          :reviews="restaurant.reviews"
          :foodCategory="restaurant.foodCategory"
          :muslimFriendly="restaurant.muslimFriendly"
        >
        </restaurant-card>
      </div>
    </section>
    <section class="flex">
      <article class="w-1/2 flex flex-col mx-2">
        <span class="font-bold text-xl text-center">최근 등록된 음식점</span>
        <div v-if="isLoadingRestaurant" class="my-16">
          <base-spinner></base-spinner>
        </div>
        <div v-else class="flex flex-col border-line-full bg-white">
          <section
            v-for="restaurant in restaurants.reverse().slice(0, 5)"
            :key="restaurant.id_store"
            class="m-2"
          >
          {{ restaurant.store_name }}
          </section>
        </div>
      </article>
      <article class="w-1/2 flex flex-col mx-2">
        <span class="font-bold text-xl text-center">최근 등록된 리뷰</span>
        <div v-if="isLoadingReview" class="my-16">
          <base-spinner></base-spinner>
        </div>
        <div v-else class="flex flex-col border-line-full bg-white">
          <section v-for="review in reviews" :key="review.id_review" class="flex flex-col border-line mx-4 my-2">
            <div class="flex items-center">
              <span>{{ review.store_name }}</span>
              <div class="star-ratings" ref="stars">
                <div
                  class="star-ratings-fill space-x-2"
                  :style="{ width: (review.score * 20 + 1.5) + '%' }"
                >
                  <span>★</span><span>★</span><span>★</span><span>★</span
                  ><span>★</span>
                </div>
                <div class="star-ratings-base space-x-2">
                  <span>★</span><span>★</span><span>★</span><span>★</span
                  ><span>★</span>
                </div>
              </div>
              <article class="flex ml-auto justify-self-end items-center space-x-2">
                <img
                  src="@/assets/resources/default.png"
                  alt="profile image"
                  class="w-8 h-8 rounded-full object-cover"
                />
                <span class="text-sm">{{ review.nickname }}</span
                ><span class="G-market-sans-L font-color-black-200 text-xs"
                  >| {{ review.upload_date }}</span
                >
              </article>
            </div>
            <div class="text-sm mb-2">{{ review.content }}</div>
          </section>
        </div>
      </article>
    </section>
  </div>
</template>
<script>
export default {
  data() {
    return {
      isLoadingRestaurant: false,
      isLoadingReview: false
    };
  },
  computed: {
    username() {
      return this.$store.getters.getUserName;
    },
    recommendRestaurants() {
      return this.$store.getters['restaurants/restaurantList'];
    },
    restaurants() {
      return this.$store.getters['restaurants/restaurantList'];
    },
    reviews() {
      return this.$store.getters['restaurants/reviewList'];
    },
  },
  created() {
    this.loadrecommendRestaurants();
    this.loadRestaurantList();
    this.loadReviewList();
    console.log("created: restaurantList", this.$store.getters['restaurants/restaurantList']);
    console.log("created: restaurantList", this.$store.getters['restaurants/restaurantList']);
  },
  methods: {
    loadrecommendRestaurants() {},
    async loadRestaurantList(refresh = true) {
      this.isLoadingRestaurant = true;
      try {
        await this.$store.dispatch('restaurants/loadRestaurants', {
          forceRefresh: refresh,
          restaurantList: true,
        });
      } catch (error) {
        this.error =
          error.message || '음식점을 불러오는데 문제가 발생했습니다.';
      }
      this.isLoadingRestaurant = false;
    },
    async loadReviewList(refresh = true) {
      this.isLoadingReview = true;
      try {
        await this.$store.dispatch('restaurants/loadReviews', {
          forceRefresh: refresh
        });
      } catch (error) {
        this.error = error.message || '리뷰를 불러오는데 문제가 발생했습니다.';
      }
      this.isLoadingReview = false;
    }
  }
};
</script>
<style scoped>

#bg {
  background-color: #f4f2ea;
}
</style>
