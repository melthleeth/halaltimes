<template>
  <div
    class="flex m-2 border-line mx-4 py-4 items-center transition duration-200 ease-in-out transform hover:scale-105 hover:shadow-xl"
    @click="viewRetaurantDetail()"
  >
    <img
      class="w-20 h-20 object-cover ml-2"
      :src="imgpath"
      alt="restaurant image"
    />
    <section class="flex flex-col ml-4">
      <span class="font-bold">{{ restaurantName }}</span>
      <span class="text-xs font-color-black-300 mb-2">{{ address }}</span>

      <article class="space-x-2 flex">
        <span
          v-if="foodCategory.length > 0"
          class="w-max bg-brown inline-block rounded-full px-3 py-1 text-xs text-white"
          >{{ foodCategory }}</span
        >
        <span
          v-if="muslimFriendly.length > 0"
          class="w-max inline-block rounded-full px-3 py-1 text-xs text-white"
          :class="getTagColor(muslimFriendly)"
          >{{ muslimFriendly }}</span
        >
      </article>
    </section>
  </div>
</template>
<script>
export default {
  props: [
    'restaurantId',
    'restaurantName',
    'imgpath',
    'address',
    'foodCategory',
    'muslimFriendly'
  ],
  methods: {
    viewRetaurantDetail() {
      this.$store.dispatch('restaurants/setRestaurantInfo', {
        id: this.restaurantId,
        name: this.restaurantName
      });
      this.$router.push({
        name: 'RestaurantDetail',
        params: {
          // restaurantId: this.restaurantId,
          restaurantName: this.restaurantName
        }
      });
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
