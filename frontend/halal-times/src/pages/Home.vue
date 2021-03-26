<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-7/12 mx-auto px-10 py-6"
  >
    <base-title>Halal Times</base-title>
    <div class="flex space-x-4">
      <section
        id="main-section-left"
        class="flex flex-col justify-items-center text-center w-1/2 px-2"
      >
        <article
          class="flex flex-col xl:text-2xl lg:text-xl md:text-lg sm:text-base font-bold tracking-title"
        >
          <span>할랄에 대한 모든 것</span>
          <span>알아보러 가기</span>
        </article>
        <router-link to="/about">
          <article class="relative">
            <img
              src="../assets/resources/about_1.png"
              alt="home_1"
              class="cursor-pointer img-square"
            />
            <div
              class="text-overlay flex flex-col space-y-2 xl:text-2xl lg:text-xl md:text-lg sm:text-base"
            >
              <span class="">ABOUT</span>
              <span class="">HALAL</span>
            </div>
          </article>
        </router-link>
        <article
          id="left-text"
          class="text-left my-2 xl:text-base lg:text-sm md:text-sm sm:text-xs"
        >
          <span
            >할랄(Halal)은 이슬람 율법 샤리아(Sharia)에 따라 '허용되는 것' 또는
            '합법적인 것'을 뜻한다.</span
          >
        </article>
        <article id="decorate-space-1" class="mx-1">
          <div class="border-line mb-4"></div>
          <div class="border-line mb-4"></div>
          <div class="border-line mb-4"></div>
          <div class="border-line mb-4 w-7/12"></div>
        </article>
        <article id="decorate-space-box-1">
          <div class="h-20 bg-primary border-line-full"></div>
        </article>
      </section>
      <section
        id="main-section-right"
        class="flex flex-col justify-items-center text-center w-1/2 px-2"
      >
      <article
          id="right-search"
          class="flex flex-col xl:text-2xl lg:text-xl md:text-lg sm:text-base font-bold tracking-title"
        >
          <span>통합 검색</span>
        </article>
        <article id="right-search" class="relative mb-8">
          <input
            type="text"
            placeholder="키워드 검색"
            v-model.trim="keyword"
            @keyup.enter="search"
            class="input-base w-full placeholder-gray-500 placeholder-opacity-50 xl:text-base lg:text-sm md:text-sm sm:text-xs"
          />
          <div
            class="w-6 h-6 search-icon my-auto ml-auto mr-4 cursor-pointer"
            @click="search"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="3"
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
              />
            </svg>
          </div>
        </article>
        <article
          class="flex flex-col xl:text-2xl lg:text-xl md:text-lg sm:text-base font-bold tracking-title"
        >
          <span>할랄 음식점 둘러보기</span>
        </article>
        <router-link to="/explore">
          <article class="relative">
            <img
              src="../assets/resources/home_2.png"
              alt="home_2"
              class="cursor-pointer"
            />
            <div
              class="text-overlay flex flex-col space-y-2 xl:text-2xl lg:text-xl md:text-lg sm:text-base"
            >
              <span class="">EXPLORE</span>
            </div>
          </article>
        </router-link>
        <article
          id="right-text"
          class="text-left my-2 xl:text-base lg:text-sm md:text-sm sm:text-xs"
        >
          <span
            >주변에 위치한 할랄 음식점을 조회하고 새로운 경험을 시작해
            보세요!</span
          >
        </article>
        <article id="decorate-space-2" class="flex space-x-4">
          <div id="decorate-space-2-lines" class="w-full mx-1 my-2">
            <div class="border-line mb-4"></div>
            <div class="border-line mb-4"></div>
            <div class="border-line mb-4 w-7/12"></div>
          </div>
          <div class="w-7/12 h-16 bg-secondary-light border-line-full"></div>
        </article>
        <article
          id="right-recommendation"
          class="xl:text-xl lg:text-lg md:text-base sm:text-sm font-bold tracking-title w-full my-4"
        >
        <router-link to="/explore">
          <article class="relative">
            <img
              id="img-size-1"
              src="../assets/resources/home_3.jpg"
              alt="home_2"
              class="cursor-pointer object-cover"
            />
            <div
              class="text-overlay-fixed flex flex-col space-y-2 xl:text-xl lg:text-lg md:text-base sm:text-sm"
            >
              <span class=""
              ><span class="underline">{{ name }}</span
              >님을</span>
              <span> 위한 추천 음식점</span
            >
            </div>
          </article>
        </router-link>
        </article>
      </section>
    </div>
  </div>
</template>
<script>
// import KakaoMap from "@/components/layout/KakaoMap.vue";
import nameRaffle from "../data/nameRaffle.js";

function getRandomInt(max) {
  return Math.floor(Math.random() * Math.floor(max));
}

export default {
  //   components: { KakaoMap },
  data() {
    return {
      randomName: nameRaffle.name,
      keyword: "",
    };
  },
  computed: {
    name() {
      return localStorage.getItem("username") === null
        ? `익명의 ${this.randomName[getRandomInt(this.randomName.length)]}`
        : localStorage.getItem("username");
    },
  },
  methods: {
    search() {
      if (this.keyword === "") {
        alert("검색어를 입력하세요.");
        return;
      }
      this.$store.dispatch("restaurants/setKeyword", this.keyword);
      this.keyword = "";
      console.log("keyword", this.$store.getters["restaurants/keyword"]);
      this.$router.push("/explore");
    },
  },
};
</script>
<style scoped>
img {
  width: 100%;
  border: 3.2px solid #2b2a29;
}

#img-size-1 {
  width: 100%;
  height: 9rem;
}

.text-overlay {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  text-align: center;
  justify-content: center;
  letter-spacing: 0.25em;
  color: #ffffff;
  font-weight: bold;
  text-shadow: 0px 5px 15px rgba(244, 242, 234, 0.5);
  background: linear-gradient(
    129.01deg,
    #000000 0%,
    rgba(0, 0, 0, 0.7) 0.01%,
    rgba(0, 0, 0, 0.15) 48.96%,
    rgba(0, 0, 0, 0.7) 100%
  );
  opacity: 0;
  transition: opacity 0.6s;
}

.text-overlay:hover {
  opacity: 1;
}

.text-overlay-fixed {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  text-align: center;
  justify-content: center;
  letter-spacing: 0.25em;
  color: #ffffff;
  font-weight: bold;
  text-shadow: 0px 5px 5px rgba(53, 52, 49, 0);
  background: rgba(0, 0, 0, 0.35);
  transition: background 0.6s;
}

.text-overlay-fixed:hover {
  background: rgba(0, 0, 0, 0.7);
}

.tracking-title {
  letter-spacing: 0.2rem;
  line-height: 160%;
}

#bg {
  background-color: #f4f2ea;
}

.search-icon {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  justify-content: center;
  border-radius: 100%;
  transition: 0.3s ease-out;
}
</style>
