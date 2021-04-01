const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  async loadRestaurants(context, payload) {
    if (!payload.forceRefresh && !context.getters.shouldUpdate) {
        return;
      }

    const response = await fetch(`${SERVER_URL}/store/list`, {
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
        Accept: 'application/json;',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*'
      },
      method: 'GET'
    });
    const responseData = await response.json();
    if (!response.ok) {
      // error..
    }

    const restaurants = [];

    for (const key in responseData) {
      const restaurant = {
        restaurantId: responseData[key].id_store,
        // imgpath: responseData[key].image,
        imgpath: "https://i.stack.imgur.com/y9DpT.jpg",
        restaurantName: responseData[key].store_name,
        // averageScore: responseData[key].average_score,
        averageScore: 4.1,
        locationRegion: responseData[key].location_region,
        // hits: responseData[key].hits,
        hits: 41,
        // reviewCounts: responseData[key].review_counts,
        reviewCounts: 26,
        muslimFriendly: responseData[key].muslim_friendly,
        foodCategory: responseData[key].food_category,
        address: "",
      };
      // console.log(key, responseData[key].img_path);
      restaurants.push(restaurant);
    }

    context.commit('setRestaurants', restaurants);
  },
  setKeyword(context, payload) {
    context.commit("setKeyword", payload);
  },
  async loadReviews(context) {
    const response = await fetch(`${SERVER_URL}/review/list`, {
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
        Accept: 'application/json;',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*'
      },
      method: 'GET'
    });
    const responseData = await response.json();
    if (!response.ok) {
      // error..
    }

    const reviews = [];

    for (const key in responseData) {
      const review = {
        id_review: responseData[key].id_review,
        id_user: responseData[key].id_user,
        id_store: responseData[key].id_store,
        store_name: responseData[key].store_name,
        score: +responseData[key].score,
        content: responseData[key].content,
        upload_date: responseData[key].upload_date,
        likeCnt: +responseData[key].likeCnt,
      };
      reviews.push(review);
    }
    context.commit('setReviews', reviews);
  },
};
