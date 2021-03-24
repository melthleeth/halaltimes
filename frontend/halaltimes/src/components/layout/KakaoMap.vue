<template>
  <div id="map" class="map"></div>
</template>
<script>
export default {
  props: {
    storeInfo: {
      type: Object,
      default: () => {
        return {
          store_name: "어쨌든 한식 식당임",
          food_category: "한식",
          lat: 37.5477,
          lng: 126.9229,
        };
      },
    },
  },
  data() {
    return {};
  },
  methods: {
    initMap() {
      var mapContainer = document.getElementById("map");

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

      let initLat = this.storeInfo.lat,
        initLng = this.storeInfo.lng;
      console.log(this.storeInfo, initLat, initLng);
      var mapOption = {
        center: new kakao.maps.LatLng(initLat, initLng),
        level: 3,
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
        position: markerPosition,
        // image: markerImage,
      });

      marker.setMap(map);
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=2fe1212d76cf65fda0093720e74185d0";
      document.head.appendChild(script);
    }
  },
  updated() {
    this.initMap();
  },
};
</script>
<style scoped>
.map {
  width: 500px;
  height: 500px;
}
</style>