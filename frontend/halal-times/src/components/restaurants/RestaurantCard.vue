<template>
  <div
    @click="viewRetaurantDetail"
    class="G-market-sans font-color-black-400 flex flex-col border-line-full justify-center card-corner max-w-sm rounded overflow-hidden bg-white m-2 shadow-lg p-2 transition duration-200 ease-in-out transform hover:scale-105"
  >
    <img
      class="w-full h-60 object-cover"
      :src="imgsrc"
      alt="restaurant image"
    />
    <section class="flex items-center mt-2 space-x-2">
      <span class="font-bold text-xl text-center truncate">{{
        restaurantName
      }}</span>
      <span v-if="averageScore > 0" class="G-market-sans-B color-primary">{{ averageScore.toFixed(1) }}</span>
      <span v-else class="font-color-black-200 font-bold"> - </span>
    </section>
    <section class="flex space-x-2 text-xs font-color-black-200">
      <span>{{ shortenAddress }}</span>
    </section>
    <section
      class="G-market-sans-L flex space-x-2 text-xs font-color-black-200"
    >
      <span>조회 {{ hits }}</span>
      <span>리뷰 {{ reviews }}</span>
    </section>
    <section id="tag-and-bookmark" class="flex items-center w-full mt-3 mb-1">
      <article class="space-x-2">
        <span
          v-if="foodCategory.length > 0"
          class="w-max bg-brown inline-block rounded-full px-3 py-1 text-xs text-white"
          >{{ foodCategory }}</span
        >
        <span
          v-if="muslimFriendly.length > 0"
          class="w-max inline-block rounded-full px-3 py-1 text-xs text-white"
          :class="getTagColor"
          >{{ muslimFriendly }}</span
        >
      </article>
      <img
        src="../../assets/icon/bookmark@0.75x.png"
        class="object-contain justify-items-end ml-auto cursor-pointer mr-2"
        alt="bookmark icon"
      />
    </section>
  </div>
</template>
<script>
export default {
  props: [
    'restaurantId',
    'imgpath',
    'restaurantName',
    'address',
    'averageScore',
    'hits',
    'reviews',
    'foodCategory',
    'muslimFriendly'
  ],
  data() {
    return {
      imgsrc: this.imgpath,
      tagColor: 1
    };
  },
  computed: {
    getTagColor() {
      if (this.tagColor === 1) return 'tag-color-1';
      else if (this.tagColor === 2) return 'tag-color-2';
      else if (this.tagColor === 3) return 'tag-color-3';
      else return 'tag-color-4';
    },
    shortenAddress() {
      const address = this.address.split(' ');
      return `${address[0]} ${address[1]}`;
    }
  },
  created() {
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
    }
  }
};
</script>
<style scoped></style>
