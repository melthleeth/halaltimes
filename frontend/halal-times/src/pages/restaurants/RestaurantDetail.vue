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
      <article class="flex flex-col w-5/12 h-auto mx-2">
        <base-button @click="goPreviousPage">목록으로</base-button>
      </article>
    </div>
  </div>
</template>
<script>
import ReviewCard from '../../components/restaurants/ReviewCard.vue';
export default {
  components: {
    ReviewCard
  },
  props: ['restaurantId', 'restaurantName'],
  data() {
    return {
      isLoading: false,
      restaurant: null
    };
  },
  computed: {
    restaurantname() {
      console.log('음식점 이름: ', this.restaurantName);
      return this.restaurantName;
    },
    imgsrc() {
      return this.restaurant.imgpath;
    },
    averageScore() {
      return this.restaurant.averageScore;
    },
    foodCategory() {
      return this.restaurant.foodCategory;
    },
    muslimCategory() {
      return this.restaurant.muslimCategory;
    },
    address() {
      return this.restaurant.address;
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
    }
  },
  created() {
    console.log('id: ', this.restaurantId);
    this.restaurant = this.$store.getters['restaurants/restaurants'].find(
      restaurant => restaurant.restaurantId.toString() === this.restaurantId
    );
    // this.loadReviews();
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
    }
  }
};
</script>
<style scoped>
#bg {
  background-color: #f4f2ea;
}
</style>
