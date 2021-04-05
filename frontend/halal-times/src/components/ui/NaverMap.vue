<template>
  <div>
    <naver-maps
      :height="height"
      :width="width"
      :mapOptions="mapOptions"
      :initLayers="initLayers"
      @load="onLoad"
    >
      <naver-info-window
        class="info-window"
        @load="onWindowLoad"
        :isOpen="info"
        :marker="marker"
      >
        <div class="info-window-container">
          <h1>{{ hello }}</h1>
        </div>
      </naver-info-window>
      <naver-marker
        :lat="lat"
        :lng="lng"
        @click="onMarkerClicked"
        @load="onMarkerLoaded"
      />
      <!-- <naver-circle :lat="lat" :lng="lng" :radius="88600" />
      <naver-rectangle :south="lat-1" :north="lat+1" :west="lng-1" :east="lng+1" />
      <naver-ellipse :bounds="{ south: lat-1, north: lat+1, west: lng-1, east: lng+1 }" />
      <naver-polygon
        :paths="[
          [
            { lat: 37, lng: 127 },
            { lat: 38, lng: 127 },
            { lat: 38, lng: 129 },
            { lat: 37, lng: 128 }
          ]
        ]"
      />
      <naver-polyline
        :path="[
          { lat: 37, lng: 127 },
          { lat: 38, lng: 129 }
        ]"
      />
      <naver-ground-overlay
        :url="'//logoproject.naver.com/img/img_about.gif'"
        :bounds="{ south: 36.7, north: 36.9, west: 126.5, east: 127.5 }"
      /> -->
    </naver-maps>
  </div>
</template>

<script>
export default {
  name: 'NaverMap',
  props: {
    lat: {
      type: Number,
      required: false,
      default: 37.5477
    },
    lng: {
      type: Number,
      required: false,
      default: 126.9229
    }
  },
  data() {
    return {
      width: '100%',
      height: '16rem',
      info: false,
      marker: null,
      count: 1,
      map: null,
      isCTT: false,
      mapOptions: {
        lat: this.lat,
        lng: this.lng,
        zoom: 10,
        zoomControl: true,
        zoomControlOptions: { position: 'TOP_RIGHT' },
        mapTypeControl: true
      },
      initLayers: [
        'BACKGROUND',
        'BACKGROUND_DETAIL',
        'POI_KOREAN',
        'TRANSIT',
        'ENGLISH',
        'CHINESE',
        'JAPANESE'
      ]
    };
  },
  computed: {
    hello() {
      return `Hello, World! × ${this.count}`;
    }
  },
  methods: {
    onLoad(vue) {
      this.map = vue;
    },
    onWindowLoad() {},
    onMarkerClicked() {
      this.info = !this.info;
    },
    onMarkerLoaded(vue) {
      this.marker = vue.marker;
    }
  },
  mounted() {
    setInterval(() => this.count++, 1000);
  }
};
</script>
<style scoped>
.info-window-container {
  padding: 10px;
  width: 300px;
  height: 100px;
}
</style>
