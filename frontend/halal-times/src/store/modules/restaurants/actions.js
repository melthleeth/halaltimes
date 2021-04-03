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
        foodCategory: responseData[key].food_category,
        address: responseData[key].address,
        tel: responseData[key].tel,
        working_time: responseData[key].working_time,
        days_closed: responseData[key].days_closed === null ? "없음" : responseData[key].days_closed,
        parking: responseData[key].parking === "1" ? "가능" : "불가능",
        hits: responseData[key].hits,
        reviews: responseData[key].reviews,
        // reviews: 26,
        muslimFriendly: responseData[key].muslim_friendly,
        // like: responseData[key].like === "1" ? true : false,
      };
      // console.log(key, responseData[key].img_path);
      restaurants.push(restaurant);
    }

    context.commit('setRestaurants', restaurants);
  },
  setKeyword(context, payload) {
    context.commit("setKeyword", payload);
  },
  async loadStoreInfoReviews(context, payload) {
    const id_store = payload;
    const email = context.rootGetters.getUserEmail;

    const response = await fetch(`${SERVER_URL}/store?id_store=${id_store}&email=${email}`, {
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
        Accept: 'application/json;',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*'
      },
      method: 'GET',
    });
    const responseData = await response.json();
    console.log(responseData);

    if (!response.ok) {
      console.log("통신 error");
    }

    // console.log("storeInfo from actions: ", responseData.storeInfo);
    // console.log("like from actions: ", responseData.like);

    const storeInfo = {
      id_store: responseData.storeInfo.id_store,
      store_name: responseData.storeInfo.store_name,
      muslim_friendly: responseData.storeInfo.muslim_friendly,
      food_category: responseData.storeInfo.food_category,
      address: responseData.storeInfo.address,
      tel: responseData.storeInfo.tel,
      working_time: responseData.storeInfo.working_time,
      parking: responseData.storeInfo.parking,
      // imgpath: responseData.storeInfo.image,
      imgpath: "https://i.stack.imgur.com/y9DpT.jpg",
      lat: +responseData.storeInfo.lat,
      lng: +responseData.storeInfo.lng,
      averageScore: +responseData.storeInfo.averageScore,
    };

    const reviews = [];

    for (const key in responseData.reviewList) {
      const review = {
        id_review: responseData[key].id_review,
        id_user: responseData[key].id_user,
        nickname: responseData[key].nickname,
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
    context.commit('setBookmarked', responseData.like);
    context.commit('setStoreInfo', storeInfo);
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
        nickname: responseData[key].nickname,
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
  async toggleBookmark(context, payload) {
    const email = context.rootGetters.getUserEmail;
    const bookmarkData = {
      email: email,
      id_store: payload
    };

    const response = await fetch(`${SERVER_URL}/store/bookmark`, {
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
        Accept: 'application/json;',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*'
      },
      method: 'PUT',
      body: JSON.stringify(bookmarkData)
    });
    const responsedata = response.json(); // id_store, active

    if (!response.ok) {
      alert("북마크 실패!");
    } else {
      alert("북마크 성공");
    }

    const bookmarked = responsedata.active === "1" ? true : false;
    context.commit('setBookmarked', bookmarked);
  }
};
