export default {
  setRestaurants(state, payload) {
    state.restaurants = payload;
  },
  setKeyword(state, payload) {
    state.keyword = payload;
  },
  setRestaurantId(state, payload) {
      state.restaurantId = payload;
  },
  setRestaurantName(state, payload) {
      state.restaurantName = payload;
  },
  setReviews(state, payload) {
    state.reviews = payload;
  },
  setBookmarked(state, payload) {
    state.bookmarked = payload;
  },
  setFetchTimestamp(state) {
    state.lastFetch = new Date().getTime();
  }
};
