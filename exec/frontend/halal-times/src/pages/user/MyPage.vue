<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-7/12 mx-auto px-10 py-6 flex flex-col"
  >
    <base-title>My Page</base-title>
    <span class="G-market-sans-B text-3xl mt-3 mb-4 mx-auto px-1 border-line"
      >내 정보</span
    >
    <section class="flex mb-12">
      <article class="w-1/2">
        <div class="mt-4 mx-auto flex flex-col justify-center">
          <!-- <div v-if="!user.profile_image">
            <svg
                class="mx-auto h-12 w-12 text-gray-400"
                stroke="currentColor"
                fill="none"
                viewBox="0 0 48 48"
                aria-hidden="true"
              >
                <path
                  d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12a4 4 0 01-4-4v-4m32-4l-3.172-3.172a4 4 0 00-5.656 0L28 28M8 32l9.172-9.172a4 4 0 015.656 0L28 28m0 0l4 4m4-24h8m-4-4v8m-12 4h.02"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
          </div> -->
          <div class="flex text-sm text-gray-600 justify-center">
            <label
              for="file-upload"
              class="relative cursor-pointer rounded-md font-medium text-indigo-600 hover:text-indigo-500 focus-within:outline-none focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-indigo-500"
            >
              <img
                :src="user.profile_image"
                alt="프로필 이미지"
                class="w-80 h-80 object-cover border-line-full"
              />
            </label>
          </div>
          <input
            id="file-upload"
            name="file-upload"
            type="file"
            class="sr-only pis"
            @change="addProfile"
          />
          <span class="text-xs font-color-black-300 mt-2 text-center"
            >* 이미지를 클릭하면 프로필 사진을 변경할 수 있어요</span
          >
        </div>
      </article>
      <article class="w-1/2 h-auto mx-2 my-10">
        <div class="flex flex-row my-3">
          <span class="flex flex-row w-1/4 font-bold">이메일</span>
          <span class="flex flex-row w-1/4 text-sm">
            {{ user.email }}
          </span>
        </div>
        <div class="flex flex-row my-3">
          <span class="flex flex-row w-1/4 font-bold">출생년도</span>
          <span class="flex flex-row w-1/4 text-sm">
            {{ born_year.slice(0, 4) }}년 {{ born_year.slice(4, 6) }}월
          </span>
        </div>
        <div class="flex flex-row my-3">
          <span class="flex flex-row w-1/4 font-bold">성별</span>
          <span class="flex flex-row w-1/4 text-sm">{{ user.gender }}</span>
        </div>
        <div class="flex flex-row my-3">
          <span class="flex flex-row w-1/4 font-bold">닉네임</span>
          <input
            class="flex flex-row w-40 text-center text-sm"
            v-model="user.nickname"
          />
          <base-button
            class="text-sm flex flex-row mx-4"
            mode="secondary"
            @click="modifyNickname"
            >변경하기</base-button
          >
        </div>

        <div class="flex flex-row">
          <div class="flex flex-row w-1/4"></div>
          <div class="flex flex-row text-xs text-right font-color-black-300">
            * 닉네임은 최소 2자, 최대 10자까지 입력이 가능해요
            <br />
            * 수정한 정보는 할랄타임즈의 서비스에 바로 적용돼요
          </div>
        </div>

        <div v-if="getRole == 1" class="flex flex-row my-3 items-center">
          <div class="flex flex-row w-1/4">관리자</div>
          <base-button
            class="text-sm flex flex-row"
            mode="brown"
            @click="recommUpdate"
            >추천업데이트</base-button
          >
          <div class="flex flex-row text-xs text-right mx-4 checkMessageColor">
            {{ rocommloadingmessage }}
          </div>
        </div>
      </article>
    </section>
    <section class="flex">
      <article class="flex flex-col w-1/2">
        <span class="G-market-sans-B text-2xl my-4 mx-auto px-1 border-line"
          >최근 북마크한 식당</span
        >
        <div class="bg-white border-line-full mx-4">
          <section v-for="(bookmark, index) in bookmarks" :key="index">
            <bookmark-design :value="bookmark" />
          </section>
          <!-- <restaurant-card-small
            v-for="bookmark in bookmarks.slice(0, 5)"
            :key="bookmark.restaurantId"
            :restaurantId="bookmark.restaurantId"
            :restaurantName="bookmark.store_name"
            :imgpath="bookmark.imgpath"
            :address="bookmark.address"
            :foodCategory="bookmark.foodCategory"
            :muslimFriendly="bookmark.muslimFriendly"
          /> -->
        </div>
      </article>
      <article class="flex flex-col w-1/2">
        <span class="G-market-sans-B text-2xl my-4 mx-auto px-1 border-line"
          >최근 활동 기록</span
        >
        <div class="bg-white border-line-full mx-4">
          <section v-for="(review, index) in reviews.slice(0, 5)" :key="index">
            <review-design :value="review" />
          </section>
          <!-- <review-card-small
            v-for="review in reviews.slice(0, 5)"
            :key="review.id_review"
            :id_store="review.id_store"
            :store_name="review.store_name"
            :score="review.score"
            :thumbnail="review.thumbnail"
            :nickname="review.nickname"
            :upload_date="review.upload_date"
            :content="review.content"
          /> -->
        </div>
      </article>
    </section>

    <section class="mx-auto my-5">
      <base-button class="text-xs" @click="showDeleteDialog"
        >회원 탈퇴</base-button
      >
      <base-dialog
        :open="deleteDialogIsVisible"
        @close="hideDeleteDialog"
        class="flex flex-col justify-items-center z-40"
      >
        <span class="text-2xl font-bold mt-10 mb-4"
          >정말 탈퇴하시겠습니까?</span
        >
        <section class="flex space-x-2 mt-6">
          <base-button
            type="submit"
            @click="deleteUser"
            mode="primary"
            class="text-sm"
          >
            네</base-button
          >
          <base-button class="text-sm" @click="hideDeleteDialog"
            >아니오</base-button
          >
        </section>
      </base-dialog>
    </section>
  </div>
</template>

<script>
import axios from 'axios';
import { mapGetters } from 'vuex';
import ReviewDesign from './ReviewDesign.vue';
import BookmarkDesign from './BookmarkDesign.vue';
// import RestaurantCardSmall from '@/components/restaurants/RestaurantCardSmall.vue';
// import ReviewCardSmall from '@/components/restaurants/ReviewCardSmall.vue';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    ReviewDesign,
    BookmarkDesign,
    // RestaurantCardSmall,
    // ReviewCardSmall,
  },
  data() {
    return {
      user: {},
      bookmarks: {},
      reviews: {},
      //
      isLoading: false,
      userInfo: null,
      born_year: '190001',

      rocommloadingmessage: '',

      isDeleted: false,
      deleteDialogIsVisible: false,
    };
  },
  computed: {
    currDate() {
      const event = new Date();
      const options = {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
      };
      return event.toLocaleDateString(undefined, options);
    },
    ...mapGetters(['getAccessToken', 'getUserEmail', 'getUserName', 'getRole']),
  },
  created() {
    this.loadMyInfo();
  },
  methods: {
    showDeleteDialog() {
      this.deleteDialogIsVisible = true;
    },
    hideDeleteDialog() {
      this.deleteDialogIsVisible = false;
    },
    deleteUser() {
      console.log('axios 호출');
      console.log('this.user.email : ', this.user.email);
      const email = this.user.email;
      axios.put(`${SERVER_URL}/user/delete?email=${email}`).then(() => {
        alert('회원탈퇴 완료');
        this.logout();
        // this.$emit('deleted', this.user);
      });

      // this.$router.push('/board/upload');
    },
    async loadMyInfo(refresh = true) {
      //비동기
      this.isLoading = true;
      try {
        await this.$store.dispatch('account/loadMyInfo', {
          forceRefresh: refresh,
        });
      } catch (error) {
        this.error =
          error.message || '내 정보를 불러오는데 문제가 발생했습니다.';
      }
      this.isLoading = false;
      let userInfo = this.$store.getters['account/userInfo'];
      this.user = userInfo.info;
      if (this.user.gender == 1) {
        this.user.gender = '여성';
      } else {
        this.user.gender = '남성';
      }
      this.reviews = userInfo.reviewList;
      this.bookmarks = userInfo.bookmarkList;
      this.born_year = userInfo.info.born_year;
    },
    addProfile: function (input) {
      if (input.target.files[0]) {
        if (this.user.profile_image) {
          const params = new URLSearchParams();
          params.append('email', this.user.email);
          axios
            .get(`${SERVER_URL}/user/profilepic/delete`, { params })
            .then(() => {});
        }
        var frm = new FormData();
        var photoFile = input.target.files[0];
        frm.append('profile_image', photoFile);
        frm.append('email', this.user.email);
        axios
          .post(`${SERVER_URL}/user/profilepic/upload`, frm, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          })
          .then(() => {
            alert('프로필 업로드 완료');
            const params = new URLSearchParams();
            params.append('email', this.getUserEmail);
            axios
              .get(`${SERVER_URL}/user`, { params })
              .then((response) => {
                this.user.profile_image = response.data.info.profile_image;
              })
              .catch((error) => {
                this.$router.push({
                  path: '/Error',
                  query: { status: error.response.status },
                });
              });
          });
      }
    },
    async modifyNickname() {
      let nicknameCheck;
      try {
        nicknameCheck = await this.$store.dispatch(
          'account/nicknameCheck',
          this.user.nickname
        );
      } catch (error) {
        this.error =
          error.message || '닉네임 중복 확인하는데 문제가 발생했습니다.';
      }

      if (nicknameCheck == 'FAIL') {
        alert('중복된 닉네임입니다.');
      } else {
        let result;
        try {
          const modifiedData = {
            nickname: this.user.nickname,
            email: this.user.email,
          };
          result = await this.$store.dispatch(
            'account/modifyNickname',
            modifiedData
          );
        } catch (error) {
          this.error =
            error.message || '닉네임을 변경하는데 문제가 발생했습니다.';
        }
        if (result == 'SUCCESS') {
          alert('닉네임 변경 완료');
        } else {
          alert('닉네임 변경 실패!');
        }
      }
    },
    async recommUpdate() {
      this.rocommloadingmessage = 'In Progress';
      const result = await this.$store.dispatch('recomm/connectDjano');
      if (result == 'SUCCESS') {
        this.rocommloadingmessage = 'SUCCESS';
      } else {
        this.rocommloadingmessage = 'ERROR';
      }
    },
    logout() {
      this.$store
        .dispatch('LOGOUT')
        .then(() => this.$router.replace('/').catch(() => {}));
      // console.log(localStorage);
      localStorage.clear;
      // console.log(localStorage);
    },
  },
};
</script>
<style scoped>
#bg {
  background-color: #f4f2ea;
}

.checkMessageColor {
  color: #cf4f2e;
}
</style>
