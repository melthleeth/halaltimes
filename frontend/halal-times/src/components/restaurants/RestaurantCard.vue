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
      <span class="font-bold text-xl text-center">{{ restaurantName }}</span>
      <span class="G-market-sans-B color-primary">{{ averageScore }}</span>
    </section>
    <section
      class="G-market-sans-L flex space-x-2 text-xs font-color-black-200"
    >
      <span>조회 {{ hits }}</span>
      <span>리뷰 {{ reviewCounts }}</span>
    </section>
    <section id="tag-and-bookmark" class="flex items-center w-full mt-3 mb-1">
      <article class="space-x-2">
      <span class="w-max bg-brown rounded-full px-3 py-1 text-xs text-white">{{
        foodCategory
      }}</span>
      <span class="w-max bg-purple rounded-full px-3 py-1 text-xs text-white"
        :class="getTagColor"
      >{{
        muslimCategory
      }}</span>
      </article>
        <img
          src="../../assets/icon/bookmark@0.75x.png"
          class="object-contain justify-items-end ml-auto cursor-pointer"
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
    'averageScore',
    'locationRegion',
    'hits',
    'reviewCounts',
    'foodCategory',
    'muslimCategory'
  ],
  data() {
    return {
      imgsrc: this.imgpath,
      tagColor: 1,
    };
  },
  computed: {
    getTagColor() {
      if (this.tagColor === 1) return "tag-color-1";
      else if (this.tagColor === 2) return "tag-color-2";
      else if (this.tagColor === 3) return "tag-color-3";
      else return "tag-color-4";
    }
  },
  created() {
    switch(this.muslimCategory){
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
      this.$router.push({
        name: 'RestaurantDetail',
        params: {
          restaurantId: this.restaurantId,
          restaurantName: this.restaurantName
        }
      });
    }
  }
};
</script>
<style scoped>
.card-corner {
  border-radius: 0 0 30px 0;
}

.tag-color-1 {
  background-color: #3D1F5D;
}
.tag-color-2 {
  background-color: #3A9CFF;
}
.tag-color-3 {
  background-color: #FF4900;
}
.tag-color-4 {
  background-color: #FF9400;
}

</style>
