import mutations from './mutations.js';
import actions from './actions.js';
import getters from './getters.js';

export default {
  namespaced: true,
  state() {
    return {
      restaurants: [
        {
          restaurantId: 1,
          imgpath:
            'https://halaltimesbucket.s3.ap-northeast-2.amazonaws.com/default.png',
          restaurantName: '우스마니아',
          averageScore: '3.4',
          locationRegion: '서울',
          hits: 113,
          reviewCounts: 23,
          foodCategory: '파키스탄 음식',
          muslimCategory: '무슬림 자가 인증',
          address: "서울 용산구 우사단로 34 3층",
        },
        {
          restaurantId: 2,
          imgpath:
            'https://halaltimesbucket.s3.ap-northeast-2.amazonaws.com/4f1b0384b2a448bea8d6fa2fec929ec3.jpg',
          restaurantName: '미스터케밥 2호점',
          averageScore: '4.5',
          locationRegion: '서울',
          hits: 43,
          reviewCounts: 10,
          foodCategory: '터키 음식',
          muslimCategory: '할랄 공식 인증',
          address: "서울 용산구 이태원로 192",
        },
        {
          restaurantId: 3,
          imgpath:
            'https://halaltimesbucket.s3.ap-northeast-2.amazonaws.com/9f121c2f5ce2405186c3bc48240a476b.jpg',
          restaurantName: '카파도키아',
          averageScore: '4.0',
          locationRegion: '부산',
          hits: 53,
          reviewCounts: 12,
          foodCategory: '터키 음식',
          muslimCategory: '무슬림 자가 인증',
          address: "부산 금정구 금단로 123-9",
        }
      ]
    };
  },
  mutations,
  actions,
  getters
};
