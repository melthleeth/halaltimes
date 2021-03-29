<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-7/12 mx-auto px-10 py-6"
  >
    <base-title>My Page</base-title>
    <section class="flex">
      <article class="w-1/2">
        <span class="text-lg mt-3 mb-1 underline">내 정보</span>
        <div
          class="mt-1 mx-5 flex justify-center px-6 pt-5 pb-6 border-2 border-gray-300 border-dashed rounded-md my-3"
        >
          <div class="space-y-1 my-10 mx-10 text-center">
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
            <!-- <img
              id="blah"
              :src="user.profile_image"
              onerror=""
              alt="프로필 이미지"
            />
             <input id="pic" class="pis" @change="addProfile" type="file" /> -->
            <div class="flex text-sm text-gray-600">
              <label
                for="file-upload"
                class="relative cursor-pointer rounded-md font-medium text-indigo-600 hover:text-indigo-500 focus-within:outline-none focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-indigo-500"
              >
                <span
                  >* 이미지를 클릭하여 프로필 사진을 등록/업데이트 할 수
                  있어요</span
                >
              </label>
            </div>
            <input
              id="file-upload"
              name="file-upload"
              type="file"
              class="sr-only pis"
              @change="addProfile"
            />
          </div>
        </div>
      </article>
      <article class="w-1/2 h-auto mx-2 my-10">
        <div class="flex flex-row my-3">
          <div class="flex flex-row w-1/4">이메일</div>
          <div class="flex flex-row w-1/4">
            <div>{{ user.email }}</div>
          </div>
        </div>
        <div class="flex flex-row my-3">
          <div class="flex flex-row w-1/4">출생년도</div>
          <div class="flex flex-row w-1/4">{{ user.born_year }}</div>
        </div>
        <div class="flex flex-row my-3">
          <div class="flex flex-row w-1/4">성별</div>
          <div class="flex flex-row w-1/4">{{ user.gender }}</div>
        </div>
        <div class="flex flex-row my-3">
          <div class="flex flex-row w-1/4">닉네임</div>
          <input
            class="flex flex-row w-1/4 text-center"
            v-model="user.nickname"
          />
          <base-button
            class="text-sm flex flex-row mx-4"
            mode="secondary"
            @click="modifyNickname"
            >닉네임 변경</base-button
          >
        </div>
        <div class="flex flex-row">
          <div class="flex flex-row w-1/4"></div>
          <div class="flex flex-row text-xs text-right">
            * 닉네임은 최소 2자, 최대 10자까지 입력이 가능해요
            <br />
            * 수정한 정보는 할랄타임즈의 서비스에 바로 적용돼요
          </div>
        </div>
      </article>
    </section>
    <section class="flex">
      <article class="w-1/2">
        <span class="text-lg mt-3 mb-1 underline">북마크한 식당</span>
        <div v-for="(bookmark, index) in bookmarks" :key="index">
          <BookmarkDesign :value="bookmark" />
        </div>
      </article>
      <article class="w-1/2">
        <span class="text-lg mt-3 mb-1 underline">활동 기록</span>
        <div v-for="(review, index) in reviews" :key="index">
          <ReviewDesign :value="review" />
        </div>
      </article>
    </section>
  </div>
</template>

<script>
import axios from 'axios';
import { mapGetters } from 'vuex';
import ReviewDesign from './ReviewDesign.vue';
import BookmarkDesign from './BookmarkDesign.vue';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  data() {
    return {
      user: {},
      bookmarks: {},
      reviews: {},
      //
      isLoading: false,
      userInfo: null
    };
  },
  components: {
    ReviewDesign,
    BookmarkDesign
  },
  computed: {
    currDate() {
      const event = new Date();
      const options = {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      };
      return event.toLocaleDateString(undefined, options);
    },
    ...mapGetters(['getAccessToken', 'getUserEmail', 'getUserName', 'getRole'])
  },
  created() {
    this.loadMyInfo();
    const params = new URLSearchParams();
    params.append('email', this.getUserEmail);
    axios.get(`${SERVER_URL}/user`, { params }).then(response => {
      console.log(response);
      //   this.user = null;
      this.user = response.data.info;
      if (this.user.gender == 1) {
        this.user.gender = '여성';
      } else {
        this.user.gender = '남성';
      }
      this.reviews = response.data.reviewList;
      this.bookmarks = response.data.bookmarkList;
      this.user.profile_image =
        'https://halaltimesbucket.s3.ap-northeast-2.amazonaws.com/' +
        response.data.info.profile_image;
    });
    //   .catch(() => {
    //     this.$router.push({
    //       path: '/Error',
    //       query: { status: error.response.status },
    //     });
    //   });
  },
  methods: {
    //
    async loadMyInfo(refresh = true) {
      this.isLoading = true;
      console.log('loadMyInfo 메소드 들어옴');
      try {
        await this.$store.dispatch('account/loadMyInfo', {
          forceRefresh: refresh
        });
      } catch (error) {
        this.error =
          error.message || '내 정보를 불러오는데 문제가 발생했습니다.';
      }
      this.isLoading = false;
    },
    addProfile: function(input) {
      if (input.target.files[0]) {
        if (this.user.profile_image) {
          const params = new URLSearchParams();
          params.append('email', this.user.email);
          axios
            .get(`${SERVER_URL}/user/profilepic/delete`, { params })
            .then(response => {
              console.log(response);
            });
          // .catch((err) => {
          //   this.$router.push({
          //     path: '/Error',
          //     query: { status: error.response.status },
          //   });
          // });
        }
        var frm = new FormData();
        var photoFile = input.target.files[0];
        frm.append('profile_image', photoFile);
        frm.append('email', this.user.email);
        axios
          .post(`${SERVER_URL}/user/profilepic/upload`, frm, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
          .then(response => {
            console.log(response);
            alert('프로필 업로드 완료');
            const params = new URLSearchParams();
            params.append('email', this.getUserEmail);
            axios
              .get(`${SERVER_URL}/user`, { params })
              .then(response => {
                console.log(response);
                this.user.profile_image =
                  'https://halaltimesbucket.s3.ap-northeast-2.amazonaws.com/' +
                  response.data.info.profile_image;
                console.log('check' + this.user.profile_image);
              })
              .catch(error => {
                this.$router.push({
                  path: '/Error',
                  query: { status: error.response.status }
                });
              });
          });
        // .catch((error) => {
        //   this.$router.push({
        //     path: '/Error',
        //     query: { status: error.response.status },
        //   });
        // });
      }
    },
    // showmodifyForm: function() {
    //   //   this.modify = true;
    // },
    modifyNickname() {
      axios
        .put(`${SERVER_URL}/user/nickname`, {
          email: this.user.email,
          nickname: this.user.nickname
        })
        .then(response => {
          console.log(response);
          //   this.modify = false;
        })
        .catch(error => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status }
          });
        });
    }
  }
};
</script>
<style scoped>
img {
  width: 100%;
  height: 100%;
}

#bg {
  background-color: #f4f2ea;
}
</style>
