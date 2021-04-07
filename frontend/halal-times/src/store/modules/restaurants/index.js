import mutations from './mutations.js';
import actions from './actions.js';
import getters from './getters.js';

export default {
  namespaced: true,
  state() {
    return {
      lastFetch: null,
      keyword: null,
      restaurantId: 0,
      restaurantName: "",
      bookmarked: '',
      storeInfo: null,
      averageScore: 0,
      restaurants: [
        {
          restaurantId: 1,
          imgpath:
            'https://halaltimesbucket.s3.ap-northeast-2.amazonaws.com/9bdd2e29c6664ec0a7e6acbdc05581e9.jpg',
          restaurantName: '우스마니아',
          averageScore: '3.4',
          locationRegion: '서울',
          hits: 113,
          reviewCounts: 23,
          foodCategory: '파키스탄 음식',
          muslimCategory: '무슬림 자가 인증',
          address: '서울 용산구 우사단로 34 3층'
        },
        {
          restaurantId: 2,
          imgpath:
            'https://halaltimesbucket.s3.ap-northeast-2.amazonaws.com/4cb7a96d45174d9594ab3a819cc427f9.jpg',
          restaurantName: '미스터케밥 2호점',
          averageScore: '4.5',
          locationRegion: '서울',
          hits: 43,
          reviewCounts: 10,
          foodCategory: '터키 음식',
          muslimCategory: '할랄 공식 인증',
          address: '서울 용산구 이태원로 192'
        },
        {
          restaurantId: 3,
          imgpath:
            'https://halaltimesbucket.s3.ap-northeast-2.amazonaws.com/d027f5b4cf714f23abf4a346975699d7.jpg',
          restaurantName: '카파도키아',
          averageScore: '4.0',
          locationRegion: '부산',
          hits: 53,
          reviewCounts: 12,
          foodCategory: '터키 음식',
          muslimCategory: '무슬림 자가 인증',
          address: '부산 금정구 금단로 123-9'
        }
      ],
      reviews: [
        {
          id_review: 1,
          id_user: 'max@gmail.com',
          nickname: '맥스',
          id_store: 'aba38fa3-8e09-11eb-aeb0-020358a1ded6',
          store_name: '우스마니아',
          score: 4.0,
          content: '너무 맛있어요',
          upload_date: '2021.01.29.',
          likeCnt: 4
        },
        {
          id_review: 2,
          id_user: 'bruce@gmail.com',
          nickname: '브루스',
          id_store: 'aba38fa3-8e09-11eb-aeb0-020358a1ded6',
          store_name: '우스마니아',
          score: 3.0,
          content: '그냥 그래요',
          upload_date: '2021.01.29.',
          likeCnt: 20
        },
        {
          id_review: 3,
          id_user: 'kelly@gmail.com',
          nickname: '켈리',
          id_store: 'aba38fa3-8e09-11eb-aeb0-020358a1ded6',
          store_name: '우스마니아',
          score: 1.0,
          content: '별로에요',
          upload_date: '2021.01.29.',
          likeCnt: 13
        },
        {
          id_review: 4,
          id_user: 'kelly@gmail.com',
          nickname: '켈리',
          id_store: 'aba39760-8e09-11eb-aeb0-020358a1ded6',
          store_name: '카파도키아',
          score: 3.5,
          content: '먹을만 해요',
          upload_date: '2021.01.29.',
          likeCnt: 12
        },
        {
          id_review: 5,
          id_user: 'karl@gmail.com',
          nickname: '칼',
          id_store: 'aba39760-8e09-11eb-aeb0-020358a1ded6',
          store_name: '카파도키아',
          score: 1.5,
          content: '그럭저럭',
          upload_date: '2021.01.29.',
          likeCnt: 2
        },
      ]
    };
  },
  mutations,
  actions,
  getters
};
