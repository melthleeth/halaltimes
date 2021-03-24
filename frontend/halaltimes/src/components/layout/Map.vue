<template>
  <div id="map" class="map"></div>
</template>
<script>
export default {
  methods: {
    initMap() {
      var mapContainer = document.getElementById("map");
      var mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      //   var map =
      new kakao.maps.Map(mapContainer, mapOption);
      //   map.setMapTypeId(kakao.maps.MapTypeId.HYBRID);

      //   var markerImageSrcs = [
      //       require("@/assets/map/marker/western_marker.png"),
      //       require('@/assets/map/marker/korean_marker.png'),
      //       require('@/assets/map/marker/cafe_marker.png'),
      //       require('@/assets/map/marker/india_middleeast_marker.png'),
      //       require('@/assets/map/marker/bakery_marker.png'),
      //       require('@/assets/map/marker/snackbar_marker.png'),
      //       require('@/assets/map/marker/chinese_marker.png'),
      //       require('@/assets/map/marker/pub_marker.png'),
      //       require('@/assets/map/marker/southeast_marker.png'),
      //       require('@/assets/map/marker/japanese_marker.png'),
      //   ];
    },
    addScript() {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=2fe1212d76cf65fda0093720e74185d0";
      document.head.appendChild(script);
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      this.addScript();
    }
  },
};
</script>
<style scoped>
.map {
  width: 500px;
  height: 500px;
}
</style>