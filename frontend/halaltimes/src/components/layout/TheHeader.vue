<template>
  <div class="G-market-sans font-color-black-400 py-4">
    <nav class="flex justify-between items-center mx-10">
      <router-link to="/home">
        <img
          src="../../assets/logo_circular_text_sm.png"
          alt="logo"
          class="absolute w-28 h-28 -top-5 left-1 animate-spin"
        />
        <img
          src="../../assets/halaltimes_icon.png"
          alt="logo"
          class="w-10 h-10"
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

      <section v-if="!isLoginned" class="">
        <base-button class="text-xs" @click="showLoginDialog"
          >로그인</base-button
        >
        <base-dialog
          :open="loginDialogIsVisible"
          @close="hideLoginDialog"
          class="flex flex-col justify-items-center z-40"
        >
          <span class="text-2xl mt-6 mb-4">로그인</span>
          <section class="flex flex-col space-y-3">
            <input
              class="dialog-input"
              type="text"
              placeholder="이메일"
              v-model.trim="email"
            />
            <input
              class="dialog-input"
              type="password"
              placeholder="비밀번호"
              v-model.trim="password"
            />
          </section>
          <section class="flex space-x-2 mt-4 mb-6">
            <base-button class="text-sm" mode="primary" @click="login"
              >로그인</base-button
            >
            <base-button class="text-sm" mode="" @click="hideLoginDialog"
              >취소</base-button
            >
          </section>
        </base-dialog>
      </section>
      <section v-else class="flex items-center space-x-6">
        <router-link to="/mypage">
        <img
          class="h-10 w-10 cursor-pointer rounded-full object-cover"
          :src="imgsrc"
          alt="user profile"
        />
        </router-link>
        <base-button class="text-xs" @click="logout">로그아웃</base-button>
      </section>
    </nav>
  </div>
</template>
<script>
export default {
  data() {
    return {
      email: "",
      password: "",
      isLoginned: false,
      loginDialogIsVisible: false,
    };
  },
  computed: {
    imgsrc() {
      //   return this.$store.getters.profileImage;
      return "https://media1.giphy.com/media/Mc75LHdpFvI9Ta42TY/giphy.gif";
    },
  },
  methods: {
    showLoginDialog() {
      this.loginDialogIsVisible = true;
    },
    hideLoginDialog() {
      this.loginDialogIsVisible = false;
    },
    login() {
      // login logic
      const userInfo = {
        email: this.email,
        password: this.password,
      };
      console.log(userInfo);
      this.isLoginned = !this.isLoginned;
    },
    logout() {
      this.isLoginned = !this.isLoginned;
      this.loginDialogIsVisible = false;
    },
  },
};
</script>
<style scoped>
#menu li {
  transition: 0.3s ease-out;
}

#menu li:hover {
  background-color: red;
}

.dialog-input {
  border: 3px solid #2b2a29;
  padding: 0.5rem 0.75rem;
  text-align: center;
}

.animate-spin {
  animation-duration: 6s;
}
</style>
