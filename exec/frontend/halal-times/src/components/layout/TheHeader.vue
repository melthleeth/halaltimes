<template>
  <div class="G-market-sans font-color-black-400 py-4">
    <nav class="flex justify-between items-center mx-10">
      <router-link to="/home">
        <img
          src="../../assets/halaltimes_icon.png"
          alt="logo"
          class="w-10 h-10 z-10"
        />
        <img
          src="../../assets/logo_circular_text_sm.png"
          alt="logo"
          class="absolute w-28 h-28 -top-5 left-1 animate-spin z-0"
        />
      </router-link>
      <ul
        id="menu"
        class="flex text-xs justify-contents-center items-center space-x-8"
      >
        <li><router-link to="/about">ABOUT</router-link></li>
        <li><router-link to="/explore">EXPLORE</router-link></li>
        <li><router-link to="/community">COMMUNITY</router-link></li>
      </ul>

      <section v-if="!getAccessToken" class="">
        <base-button class="text-xs" @click="showLoginDialog"
          >로그인</base-button
        >
        <base-dialog
          :open="loginDialogIsVisible"
          @close="hideLoginDialog"
          class="flex flex-col justify-items-center z-40"
        >
          <span class="text-2xl font-bold mt-10 mb-4">로그인</span>
          <section class="flex flex-col space-y-4">
            <input
              class="input-dialog"
              type="text"
              placeholder="이메일"
              v-model.trim="user.email"
            />
            <input
              class="input-dialog"
              type="password"
              placeholder="비밀번호"
              v-model.trim="user.password"
            />
          </section>
          <section class="flex space-x-2 mt-6">
            <base-button
              type="submit"
              @click="login"
              mode="primary"
              class="text-sm"
            >
              로그인</base-button
            >
            <base-button class="text-sm" @click="hideLoginDialog"
              >취소</base-button
            >
          </section>
          <section class="font-color-black-300 text-xs mt-3 mb-6">
            <span>아직 회원이 아니신가요?</span>
            <span class="underline text-link cursor-pointer" @click="moveToSignUp">회원가입</span>
          </section>
        </base-dialog>
      </section>
      <section v-else class="flex items-center space-x-6">
        <router-link to="/mypage">
          <img
            class="h-10 w-10 cursor-pointer rounded-full object-cover"
            :src="getProfileImage"
            alt="user profile"
          />
        </router-link>
        <base-button class="text-xs" @click="logout">로그아웃</base-button>
      </section>
    </nav>
  </div>
</template>
<script>
import { mapGetters, mapMutations } from 'vuex';
export default {
  data() {
    return {
      user: {
        email: '',
        password: ''
      },
      isLoginned: false,
      loginDialogIsVisible: false
    };
  },
  computed: {
    imgsrc() {
      //   return this.$store.getters.profileImage;
      return 'https://media1.giphy.com/media/Mc75LHdpFvI9Ta42TY/giphy.gif';
    },
    ...mapGetters([
      // 'links',
      'getAccessToken',
      'getUserEmail',
      'getUserName',
      'getRole',
      'getProfileImage'
    ])
  },
  methods: {
    showLoginDialog() {
      this.loginDialogIsVisible = true;
    },
    hideLoginDialog() {
      this.loginDialogIsVisible = false;
    },
    ...mapMutations(['toggleDrawer']),
    onClick(e, item) {
      this.$router.push(item.href).catch(error => {
        if (error.name === 'NavigationDuplicated') {
          // 같은 경로 클릭시 새로고침 되게
          location.reload();
        }
      });
    },
    login(event) {
      console.log(this.user.email);
      console.log(this.user.password);
      event.preventDefault();
      this.dialog = false;
      // LOGIN 액션 실행
      // 서버와 통신(axios)을 해 토큰값을 얻어야 하므로 Actions를 호출.
      this.$store.dispatch('LOGIN', this.user);
      console.log(this.$store.getters.getAccessToken);
      this.$emit('loginSuccess');
      this.user.email = '';
      this.user.password = '';
    },
    logout() {
      this.$store
        .dispatch('LOGOUT')
        .then(() => this.$router.replace('/').catch(() => {}));
      console.log(localStorage);
      localStorage.clear;
      console.log(localStorage);
    },
    moveToSignUp() {
      this.loginDialogIsVisible = false;
      this.$router.push({ path: '/signup'});
    },
  }
};
</script>
<style scoped>
#menu li a {
  transition: 0.5s ease-out;
  padding: 0.25rem 1rem;
  background: linear-gradient(135deg, #fff58c 50%, transparent 50%);
  background-size: 200% 100%;
  background-position: right bottom;
  border-radius: 100%;
}

#menu li a:hover,
#menu li a.router-link-active {
  background-position: left bottom;
}

.animate-spin {
  animation-duration: 6s;
}

.text-link {
  transition: .3s;
}

.text-link:hover {
  color: #2b2a29;
}

</style>
