<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-7/12 mx-auto px-10 py-6"
  >
    <base-title>Sign UP</base-title>
    <form @submit.prevent="submitForm">
      <body>
        <!-- 이메일 -->
        <article>
          <!-- 이메일 전송 -->
          <section>
            <label for="email">이메일</label>
            <input
              id="email"
              type="email"
              v-model="email"
              placeholder="halal@halaltimes.com"
              required
            />
            <base-button mode="secondary" @click="emailSend()"
              >인증메일 전송</base-button
            >
          </section>
          <!-- 이메일 인증 코드 -->
          <section>
            <label for="code">인증코드 입력</label>
            <input
              id="code"
              type="text"
              placeholder="이메일로 전송된 코드 입력"
              v-model="code"
              required
            />
            <base-button v-bind:disabled="codeAuth" @click="codeCheck()"
              >확인</base-button
            >
            <span class="checkMessageColor">{{ emailmessage }}</span>
          </section>
        </article>

        <!-- 출생/성별 -->
        <article class="flex items-center">
          <!-- 출생년도 -->
          <section>
            <label for="born">출생 연월</label>
            <input id="born" type="month" v-model="born" required />
          </section>
          <!-- 성별 -->
          <section>
            <label for="gender">성별</label>
            <!-- <input type="radio" />남성 
          <input type="radio" />여성 -->
            <select
              id="gender"
              class="text-base text-left px-6 py-3 hover:bg-gray-100 focus:outline-none"
              required
            >
              <option value="" hidden>선택</option>
              <option value="0">남성</option>
              <option value="1">여성</option>
            </select>
          </section>
        </article>

        <!-- 닉네임 -->
        <article>
          <section>
            <label for="nickname">닉네임</label>
            <input
              id="nickname"
              type="text"
              minlength="2"
              maxlength="12"
              v-model="nickname"
              pattern="{2,12}+"
              required
            />
            <base-button @click="nicknameCheck()">중복 확인</base-button>
            <span class="checkMessageColor">{{ nicknamemessage }}</span>
            <p class="alertText">
              * 닉네임은 최소 2자, 최대 12자까지 입력이 가능합니다.<br />*
              회원가입 후에도 자유롭게 변경할 수 있습니다.
            </p>
          </section>
        </article>

        <!-- 비밀번호 -->
        <article>
          <section>
            <label for="password">비밀번호</label>
            <input
              id="password"
              type="password"
              minlength="8"
              maxlength="20"
              pattern="^(?=.*[A-Za-z])(?=.[0-9])[A-Za-z0-9]+{8,20}$"
              v-model="password"
              required
              @keyup="passwordCheck()"
            />
            <span class="checkMessageColor">{{ passwordmessage }}</span>
            <p class="alertText">
              * 영문과 숫자를 반드시 포함하여 최소 8자, 최대 20자까지 입력이
              가능합니다.
            </p>
          </section>
        </article>

        <!-- 비밀번호 확인 -->
        <article>
          <section>
            <label for="confirm">비밀번호 확인</label>
            <input
              id="confirm"
              type="password"
              minlength="8"
              maxlength="20"
              pattern="^(?=.*[A-Za-z])(?=.[0-9])[A-Za-z0-9]+{8,20}$"
              v-model="confirm"
              required
              @keyup="passwordConfirmCheck()"
            />
            <span class="checkMessageColor">{{ passwordconfirmmessage }}</span>
          </section>
        </article>
      </body>
      <footer>
        <!-- 개인정보 이용 정책 -->
        <article>
          <section>
            <label
              ><input type="checkbox" v-model="checkAgree" required
            /></label>
            모든
            <base-modal></base-modal>
            <span style="text-decoration: underline; font-weight: bold"
              >이용약관</span
            >과
            <base-modal></base-modal>
            <span style="text-decoration: underline; font-weight: bold"
              >개인정보이용정책</span
            >에 동의합니다.
          </section>
        </article>

        <!-- 버튼 -->
        <article>
          <section>
            <base-button v-bind:disabled="registerSignup" @click="signupCheck()"
              >회원가입</base-button
            >
            <base-button @click="goHome()">돌아가기</base-button>
          </section>
        </article>
      </footer>
    </form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      emailmessage: "",
      nicknamemessage: "",
      passwordmessage: "",
      passwordconfirmmessage: "",

      codeAuth: true,
      codeFromSpring: "",

      registerSignup: true,

      // 인증 체크
      checkAgree: false,
      checkEmail: false,
      checkNickname: false,
      checkPassword: false,
      checkPasswordConfirm: false,
    };
  },
  methods: {
    emailSend() {
      // 초기화 필요
      // this.emailmessage = ""
      // this.codeAuth = false (클릭 가능해짐)
      if (this.email == null) {
        alert("이메일 주소를 입력하시기 바랍니다.");
      }
      this.listCheck();
    },
    codeCheck() {
      // 인증메일 전송 눌러야 활성화 가능
      if (this.codeFromSpring == this.code) {
        this.codeAuth = true; // 클릭 비활성화
        this.checkEmail = true;
        this.emailmessage = "이메일 인증 성공";
      } else {
        this.emailmessage = "이메일 인증 실패";
      }
      this.listCheck();
    },
    nicknameCheck() {
      if (
        this.nickname == null ||
        this.nickname.length < 2 ||
        this.nickname.length > 12
      ) {
        this.nicknamemessage = "글자 수를 확인 바랍니다.";
      } else {
        this.nicknamemessage = "허허";
      }
      this.listCheck();
    },
    passwordCheck() {
      var check = new RegExp("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}$");
      if (check.test(this.password)) {
        this.passwordmessage = "사용 가능";
        this.checkPassword = true;
        return this.listCheck();
      }
      this.passwordmessage = "영문포함 필수";
      this.checkPassword = false;
      this.listCheck();
    },
    passwordConfirmCheck() {
      if (this.password === this.confirm) {
        this.passwordconfirmmessage = "비밀번호가 동일합니다.";
        this.checkPasswordConfirm = true;
      } else {
        this.passwordconfirmmessage = "비밀번호가 다릅니다.";
        this.checkPasswordConfirm = false;
      }
      this.listCheck();
    },
    listCheck() {
      if (
        this.checkAgree &&
        this.checkEmail &&
        this.checkNickname &&
        this.checkPassword &&
        this.checkPasswordConfirm
      ) {
        return (this.registerSignup = false);
      }
      return (this.registerSignup = true);
    },
    signupCheck() {
      var yearMonth = this.born.replace("-", "");
      if (yearMonth < 190001) {
        alert("가입할 수 없는 출생 연월입니다.");
      } else if (yearMonth > 202103) {
        alert("탄생 예정을 축하합니다.");
      }
    },
    goHome() {
      this.$router.go(-1);
    },
  },
  computed: {},
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

.checkMessageColor {
  color: red;
}

input {
  padding-left: 1.5rem;
  padding-right: 1.5rem;
  padding-top: 0.75rem;
  padding-bottom: 0.75rem;
  margin-left: 1rem;
  margin-right: 1rem;
  text-align: left;
  font-size: 1rem;
  line-height: 1.5rem;
  /* border-radius: 9999px; */
}
input:hover {
  --tw-bg-opacity: 1;
  background-color: rgba(243, 244, 246, var(--tw-bg-opacity));
}

input[type="text"]:focus {
  border: 1px solid rgb(153, 152, 152);
}

/* input:focus {
  outline: 2px solid transparent;
  outline-offset: 2px;
  border: 2px solid gray;
  border-radius: 9999px;
} */

.alertText {
  color: #c6c6c6;
}

::placeholder {
  color: #c6c6c6;
}
</style>
