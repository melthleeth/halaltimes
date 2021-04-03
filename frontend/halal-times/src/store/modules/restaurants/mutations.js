export default {
    setRestaurants(state, payload) {
        state.restaurants = payload;
    },
    setKeyword(state, payload) {
        state.keyword = payload;
    },
    setReviews(state, payload) {
        state.reviews = payload;
    },
    setBookmarked(state, payload) {
        state.bookmarked = payload;
    },
    setStoreInfo(state, payload) {
        console.log("payload: ", payload);
        state.storeInfo = payload;
    },
};