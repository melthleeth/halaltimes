<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-2/3 mx-auto px-10 py-6"
  >
    <base-title>Explore</base-title>
    <section
      id="search-bar"
      class="flex justify-items-center text-base items-center mx-16 mb-12"
    >
      <article class="flex w-full items-center">
        <input
          class="mx-auto text-base text-left appearance-none rounded-full w-5/6 pl-10 pr-20 py-4 leading-tight border-3 border-transparent hover:bg-gray-100 focus:outline-none focus:shadow-outline focus:border-gray-200"
          id="search"
          type="text"
          placeholder="음식점 이름, 태그로 검색해보세요 🧕👳‍♂️"
          v-model.trim="keyword"
        />
      </article>
    </section>
    <section class="flex flex-col">
      <div v-if="isLoading" class="my-32">
        <base-spinner></base-spinner>
      </div>
      <div
        v-else-if="filteredRestaurants.length > 0"
        class="grid grid-cols-3 grid-flow-row gap-1 mx-auto"
      >
        <restaurant-card
          v-for="restaurant in filteredRestaurants"
          :key="restaurant.restaurantId"
          :restaurantId="restaurant.restaurantId"
          :imgpath="restaurant.imgpath"
          :restaurantName="restaurant.restaurantName"
          :averageScore="restaurant.averageScore"
          :locationRegion="restaurant.locationRegion"
          :hits="restaurant.hits"
          :reviewCounts="restaurant.reviewCounts"
          :foodCategory="restaurant.foodCategory"
          :muslimFriendly="restaurant.muslimFriendly"
        >
        </restaurant-card>
      </div>
      <span
        v-else
        class="text-2xl text-center my-32 font-S-CoreDream-medium font-bold font-color-black-200"
        >등록된 레스토랑이 없습니다.</span
      >
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
      isLoading: false,
      error: null,
      keyword: ''
    };
  },
  watch: {
    keyword(value) {
      this.keyword = value;
      console.log(value);
    }
  },
  computed: {
    filteredRestaurants() {
      let restaurants = this.$store.getters['restaurants/restaurants'];
      console.log(restaurants);

      if (this.keyword !== "") {
        restaurants = restaurants.filter((restaurantItem) => {
          if (restaurantItem.restaurantName.includes(this.keyword)) return true;
          if (restaurantItem.foodCategory.includes(this.keyword)) return true;
          if (restaurantItem.muslimFriendly.includes(this.keyword)) return true;
          return false;
        });
      }

      // console.log(restaurants);
      return restaurants;
    }
  },
  created() {
    this.loadRestaurants();
    this.loadKeyword();
  },
  methods: {
    async loadRestaurants(refresh = true) {
      this.isLoading = true;
      try {
        await this.$store.dispatch('restaurants/loadRestaurants', {
          forceRefresh: refresh
        });
      } catch (error) {
        this.error =
          error.message || '음식점을 불러오는데 문제가 발생했습니다.';
      }
      this.isLoading = false;
    },
    loadKeyword() {
      const searchKeyword = this.$store.getters["restaurants/keyword"];
      if (searchKeyword !== null) this.keyword = searchKeyword;
      this.$store.dispatch("restaurants/setKeyword", null);
    },
  }
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
