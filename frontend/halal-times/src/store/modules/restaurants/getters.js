export default {
  restaurants(state) {
    return state.restaurants;
  },
  keyword(state) {
    return state.keyword;
  },
  restaurantId(state) {
      return state.restaurantId;
  },
  restaurantName(state) {
      return state.restaurantName;
  },
  restaurantList(state) {
    return state.restaurantList;
  },
  reviews(state) {
    return state.reviews;
  },
  reviewList(state) {
    return state.reviewList;
  },
  bookmarked(state) {
    return state.bookmarked;
  },
  averageScore(state) {
    return state.averageScore;
  },
  shouldUpdate(state) {
    const lastFetch = state.lastFetch;
    if (!lastFetch) {
      return true;
    }
    const currentTimeStamp = new Date().getTime();
    return (currentTimeStamp - lastFetch) / 1000 > 60;
  }
};
