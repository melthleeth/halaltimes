<template>
  <div
    class="flex flex-col border-line mx-6 my-2 pb-2 transition duration-200 ease-in-out transform hover:scale-105 hover:shadow-xl"
    @click="viewRetaurantDetail()"
  >
    <div class="flex items-center">
      <span class="font-bold mr-2">{{ store_name }}</span>
      <div class="star-ratings" ref="stars">
        <div
          class="star-ratings-fill space-x-2"
          :style="{ width: score * 20 + 1.5 + '%' }"
        >
          <span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
        </div>
        <div class="star-ratings-base space-x-2">
          <span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
        </div>
      </div>
      <article class="flex ml-auto justify-self-end items-center space-x-2">
        <img
          :src="thumbnail"
          alt="profile image"
          class="w-6 h-6 rounded-full object-cover"
        />
        <span class="text-xs">{{ nickname }}</span
        ><span class="G-market-sans-L font-color-black-200 text-2xs"
          >| {{ upload_date }}</span
        >
      </article>
    </div>
    <div class="text-sm mt-2 mb-1 mx-2 overflow-ellipsis ">
      {{ content }}
    </div>
  </div>
</template>
<script>
export default {
  props: [
    'id_store',
    'store_name',
    'score',
    'thumbnail',
    'nickname',
    'upload_date',
    'content'
  ],
  methods: {
    viewRetaurantDetail() {
      this.$store.dispatch('restaurants/setRestaurantInfo', {
        id: this.id_store,
        name: this.store_name
      });
      this.$router.push({
        name: 'RestaurantDetail',
        params: {
          // restaurantId: this.restaurantId,
          restaurantName: this.restaurantName
        }
      });
    }
  }
};
</script>
