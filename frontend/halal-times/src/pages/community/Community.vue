<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-2/3 mx-auto px-10 py-6"
  >
    <base-title>Community</base-title>
    <section v-if="username !== ''" class="flex flex-col">
      <span class="G-market-sans-B font-bold text-3xl text-center mt-10 mb-8 tracking-wider"
        ><span class="">{{ username }}</span>님을 위한 추천 음식점</span
      >
      <div class="grid grid-cols-3 grid-flow-row gap-1 mx-auto">
        <restaurant-card
          v-for="restaurant in recommendRestaurants"
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
    <section class="flex mt-20 mb-4">
      <article class="w-1/2 flex flex-col mx-2">
        <span class="G-market-sans-B text-2xl text-center mb-6 tracking-wider">최근 등록된 음식점</span>
        <div v-if="isLoadingRestaurant" class="my-16">
          <base-spinner></base-spinner>
        </div>
        <div v-else class="flex flex-col border-line-full bg-white py-4">
          <section
            v-for="restaurant in restaurants.slice(0, 5)"
            :key="restaurant.restaurantId"
            class="flex m-2 border-line mx-4 py-4 items-center transition duration-200 ease-in-out transform hover:scale-105 hover:shadow-xl"
            @click="viewRetaurantDetail(restaurant.restaurantId, restaurant.restaurantName)"
          >
            <img
              class="w-20 h-20 object-cover ml-2"
              :src="restaurant.imgpath"
              alt="restaurant image"
            />
            <div class="flex flex-col ml-4">
              <span class="font-bold">{{ restaurant.restaurantName }}</span>
              <span class="text-xs font-color-black-300 mb-2">{{
                restaurant.address
              }}</span>

              <article class="space-x-2 flex">
                <span
                  v-if="restaurant.foodCategory.length > 0"
                  class="w-max bg-brown inline-block rounded-full px-3 py-1 text-xs text-white"
                  >{{ restaurant.foodCategory }}</span
                >
                <span
                  v-if="restaurant.muslimFriendly.length > 0"
                  class="w-max inline-block rounded-full px-3 py-1 text-xs text-white"
                  :class="getTagColor(restaurant.muslimFriendly)"
                  >{{ restaurant.muslimFriendly }}</span
                >
              </article>
            </div>
          </section>
        </div>
      </article>
      <article class="w-1/2 flex flex-col mx-2">
        <span class="G-market-sans-B text-2xl text-center mb-6 tracking-wider">최근 등록된 리뷰</span>
        <div v-if="isLoadingReview" class="my-16">
          <base-spinner></base-spinner>
        </div>
        <div v-else class="flex flex-col border-line-full bg-white pt-2 pb-4">
          <section
            v-for="review in reviews"
            :key="review.id_review"
            class="flex flex-col border-line mx-6 my-2 pb-2 transition duration-200 ease-in-out transform hover:scale-105 hover:shadow-xl"
          >
            <div class="flex items-center">
              <span class="font-bold mr-2">{{ review.store_name }}</span>
              <div class="star-ratings" ref="stars">
                <div
                  class="star-ratings-fill space-x-2"
                  :style="{ width: review.score * 20 + 1.5 + '%' }"
                >
                  <span>★</span><span>★</span><span>★</span><span>★</span
                  ><span>★</span>
                </div>
                <div class="star-ratings-base space-x-2">
                  <span>★</span><span>★</span><span>★</span><span>★</span
                  ><span>★</span>
                </div>
              </div>
              <article
                class="flex ml-auto justify-self-end items-center space-x-2"
              >
                <img
                  src="@/assets/resources/default.png"
                  alt="profile image"
                  class="w-6 h-6 rounded-full object-cover"
                />
                <span class="text-xs">{{ review.nickname }}</span
                ><span class="G-market-sans-L font-color-black-200 text-2xs"
                  >| {{ review.upload_date }}</span
                >
              </article>
            </div>
            <div class="text-sm mt-2 mb-1 mx-2 overflow-ellipsis ">{{ review.content }}</div>
          </section>
        </div>
      </article>
    </section>
  </div>
</template>
<script>
import RestaurantCard from '../../components/restaurants/RestaurantCard.vue';
export default {
  components: {
    RestaurantCard
  },
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
    }
  },
  created() {
    this.loadrecommendRestaurants();
    this.loadRestaurantList();
    this.loadReviewList();
    console.log(
      'created: restaurantList',
      this.$store.getters['restaurants/restaurantList']
    );
    console.log(
      'created: restaurantList',
      this.$store.getters['restaurants/restaurantList']
    );
  },
  methods: {
    viewRetaurantDetail(restaurantId, restaurantName) {
      this.$store.dispatch('restaurants/setRestaurantInfo', {
        id: restaurantId,
        name: restaurantName
      });
      this.$router.push({
        name: 'RestaurantDetail',
        params: {
          // restaurantId: this.restaurantId,
          restaurantName: restaurantName
        }
      });
    },
    loadrecommendRestaurants() {},
    async loadRestaurantList(refresh = true) {
      this.isLoadingRestaurant = true;
      try {
        await this.$store.dispatch('restaurants/loadRestaurants', {
          forceRefresh: refresh,
          restaurantList: true
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
    },
    getTagColor(keyword) {
      if (keyword === '할랄 공식 인증') return 'tag-color-1';
      else if (keyword === '무슬림 자가 인증') return 'tag-color-2';
      else if (keyword === '무슬림 프렌들리') return 'tag-color-3';
      else return 'tag-color-4';
    }
  }
};
</script>
<style scoped>
#bg {
  background-color: #f4f2ea;
}
</style>
