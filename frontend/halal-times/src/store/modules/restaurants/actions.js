const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  async loadRestaurants(context, payload) {
    if (!payload.forceRefresh && !context.getters.shouldUpdate) {
        return;
      }

    const response = await fetch(`${SERVER_URL}/article/all`, {
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
        Accept: 'application/json;',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*'
      },
      method: 'POST',
      body: JSON.stringify(payload)
    });
    const responseData = await response.json();
    if (!response.ok) {
      // error..
    }

    const restaurants = [];

    for (const key in responseData) {
      const restaurant = {
        restaurantId: responseData[key].id_store,
        imgpath: responseData[key].image,
        restaurantName: responseData[key].store_name,
        averageScore: responseData[key].average_score,
        locationRegion: responseData[key].location_region,
        hits: responseData[key].hits,
        reviewCounts: responseData[key].review_counts,
        muslimFriendly: responseData[key].muslim_friendly,
        foodCategory: responseData[key].food_category
      };
      // console.log(key, responseData[key].img_path);
      restaurants.push(restaurant);
    }

    context.commit('setRestaurants', restaurants);
  }
};
