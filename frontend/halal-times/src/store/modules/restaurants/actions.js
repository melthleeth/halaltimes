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
      const error = new Error(
        responseData.message || 'actions: loadRestaurants 실패'
      );
      throw error;
    }

    const restaurants = [];

    for (const key in responseData) {
      const restaurant = {
        restaurantId: +responseData[key].id_store,
        // imgpath: responseData[key].image,
        imgpath: 'https://i.stack.imgur.com/y9DpT.jpg',
        restaurantName: responseData[key].store_name,
        averageScore: responseData[key].averageScore,
        locationRegion: responseData[key].location_region,
        foodCategory: responseData[key].food_category,
        address: responseData[key].address,
        tel: responseData[key].tel,
        working_time: responseData[key].working_time,
        days_closed:
          responseData[key].days_closed === null
            ? '없음'
            : responseData[key].days_closed,
        parking: responseData[key].parking === '1' ? '가능' : '불가능',
        hits: responseData[key].hits,
        reviews: responseData[key].reviews,
        muslimFriendly: responseData[key].muslim_friendly
      };
      restaurants.push(restaurant);
    }

    context.commit('setRestaurants', restaurants);
    context.commit('setFetchTimestamp');
  },
  setKeyword(context, payload) {
    context.commit('setKeyword', payload);
  },
  setRestaurantInfo(context, payload) {
    // console.log("actions: setRestaurantInfo", payload);
    context.commit('setRestaurantId', payload.id);
    context.commit('setRestaurantName', payload.name);
  },
  async loadLikeReviews(context) {
    // console.log("actions: loadLikeReviews/id_store", context.getters.restaurantId);
    const id_store = context.getters.restaurantId;
    const email = context.rootGetters.getUserEmail;

    const response = await fetch(
      `${SERVER_URL}/store?id_store=${+id_store}&email=${email}`,
      {
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
          Accept: 'application/json;',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Headers': '*'
        },
        method: 'GET'
      }
    );
    const responseData = await response.json();
    // console.log('actions: loadLikeReviews/responseData', responseData);
    const reviewList = responseData.reviewList;

    if (!response.ok) {
      const error = new Error(
        responseData.message || 'actions: loadLikeReviews 실패'
      );
      throw error;
    }

    const bookmarked = responseData.like === '1' ? true : false;
    const reviews = [];
    for (const key in reviewList) {
      const review = {
        id_review: reviewList[key].id_review,
        id_store: reviewList[key].id_store,
        id_user: reviewList[key].id_user,
        nickname: reviewList[key].nickname,
        store_name: reviewList[key].store_name,
        score: +reviewList[key].score,
        content: reviewList[key].content,
        // upload_date: responseData[key].upload_date,
        upload_date: '2021.04.',
        likeCnt: +reviewList[key].likeCnt
      };
      reviews.push(review);
    }
    // console.log('actions: loadLikeReviews/reviews', reviews);
    context.commit('setBookmarked', bookmarked);
    context.commit('setReviews', reviews);
  },
  async toggleBookmark(context) {
    // console.log(
    //   'actions: loadLikeReviews/id_store',
    //   context.getters.restaurantId
    // );
    const id_store = context.getters.restaurantId;
    const email = context.rootGetters.getUserEmail;

    const response = await fetch(
      `${SERVER_URL}/store/bookmark?id_store=${id_store}&email=${email}`,
      {
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
          Accept: 'application/json;',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Headers': '*'
        },
        method: 'PUT'
      }
    );
    const responseData = response.json(); // id_store, active

    responseData.then(value => {
      // console.log("actions: toggleBookmark/value", value);
      if (!response.ok) {
        alert('북마크 실패!');
        const error = new Error(
          responseData.message || 'actions: toggleBookmark 실패'
        );
        throw error;
      }
      // console.log('actions: toggleBookmark/value.active', value.active);
      const bookmarked = value.active === 1 ? true : false;
      // console.log('bookmarked', bookmarked);
      context.commit('setBookmarked', bookmarked);
    });
  }
};
