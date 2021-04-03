<template>
  <div id="map" class="map border-line-full"></div>
</template>
<script>
export default {
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
    },
    storeInfo: {
      type: Object,
      default: () => {
        return {
          store_name: '어쨌든 한식 식당임',
          food_category: '한식',
          lat: 37.5477,
          lng: 126.9229
        };
      }
    }
  },
  data() {
    return {};
  },
  mounted() {
    window.kakao && window.kakao.maps ? this.initMap() : this.addScript();
  },
  updated() {
    this.initMap();
  },
  methods: {
    addScript() {
      const script = document.createElement('script');
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAO_API_KEY}`;
      document.head.appendChild(script);
    },
    initMap() {
      var mapContainer = document.getElementById('map');

      // var markerImageSrcs = [
      //   require("@/assets/map/marker/western_marker.png"),
      //   require("@/assets/map/marker/korean_marker.png"),
      //   require("@/assets/map/marker/cafe_marker.png"),
      //   require("@/assets/map/marker/india_middleeast_marker.png"),
      //   require("@/assets/map/marker/bakery_marker.png"),
      //   require("@/assets/map/marker/snackbar_marker.png"),
      //   require("@/assets/map/marker/chinese_marker.png"),
      //   require("@/assets/map/marker/pub_marker.png"),
      //   require("@/assets/map/marker/southeast_marker.png"),
      //   require("@/assets/map/marker/japanese_marker.png"),
      // ];

      const initLat = this.lat;
      const initLng = this.lng;
      console.log(this.storeInfo, initLat, initLng);
      var mapOption = {
        center: new kakao.maps.LatLng(initLat, initLng),
        level: 3
      };

      var map = new kakao.maps.Map(mapContainer, mapOption);
      //   map.setMapTypeId(kakao.maps.MapTypeId.HYBRID);

      var markerPosition = new kakao.maps.LatLng(initLat, initLng);

      // 이미지 조건 설정
      // var imageSrc = markerImageSrcs[0];

      // var imageSize = new kakao.maps.Size(56, 56);
      // var imageOption = { offset: new kakao.maps.Point(27, 69) };

      // var markerImage = new kakao.maps.MarkerImage(
      //   imageSrc,
      //   imageSize,
      //   imageOption
      // );

      var marker = new kakao.maps.Marker({
        position: markerPosition
        // image: markerImage,
      });

      marker.setMap(map);
    }
  }
};
</script>
<style scoped>
.map {
  width: 100%;
  height: 16rem;
}
</style>
