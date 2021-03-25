<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-2/3 mx-auto px-10 py-6"
  >
    <base-title-small>Halal Times</base-title-small>
    <div id="top-restaurant-title" class="flex flex-col text-center ">
      <section class="w-full border-line">
          <span class="G-market-sans-B text-3xl tracking-widest">{{ restaurantName }}</span>
      </section>
      <span class="text-lg font-color-black-200 my-2">{{ address }}</span>
    </div>
    <div id="center-contents" class="flex">
      <section id="left-contents" class="w-7/12">
        <img :src="imgsrc" alt="restaurant photo" class="object-cover w-full h-96 border-line-full" />
        
      </section>
      <article class="flex flex-col w-5/12 h-auto mx-2">
      <base-button @click="goPreviousPage">목록으로</base-button>
      </article>
    </div>
  </div>
</template>
<script>
export default {
  props: ['restaurantId', 'restaurantName'],
  data() {
    return {
        restaurant: null,
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

  },
  created() {
    this.restaurant = this.$store.getters['restaurants/restaurants'].find(
      (restaurant) => restaurant.restaurantId.toString() === this.restaurantId
    );
  },
  methods: {
      goPreviousPage() {
          this.$router.go(-1);
      }
  }
};
</script>
<style scoped>
#bg {
  background-color: #f4f2ea;
}
</style>
