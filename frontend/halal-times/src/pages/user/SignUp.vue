<template>
  <div
    id="bg"
    class="G-market-sans font-color-black-400 w-7/12 mx-auto px-10 py-6"
  >
    <base-title>Sign UP</base-title>
    <form
      @submit.prevent="submitForm"
      class="mt-14 space-y-6 xl:mx-28 lg:mx-10"
    >
      <article class="form-control">
        <label for="email">이메일</label>
        <input
          id="email"
          type="email"
          v-model="email"
          placeholder="halal@halaltimes.com"
          required
        />
        <base-button
          id="btn"
          class="text-sm text-button"
          mode="secondary"
          @click="emailSend"
          >인증메일 전송</base-button
        >
      </article>
      <section class="flex flex-col">
        <article class="form-control">
          <label for="code">인증코드 입력</label>
          <input
            id="code"
            type="text"
            placeholder="이메일로 전송된 코드 입력"
            v-model="code"
            required
          />
          <base-button
            id="btn"
            class="text-sm"
            :disabled="codeAuth"
            @click="codeCheck"
            >확인</base-button
          >
        </article>
        <span class="checkMessageColor">{{ emailmessage }}</span>
      </section>
      <section class="flex">
        <!-- 출생년도 -->
        <article class="flex items-center">
          <label class="font-bold w-32" for="born_year">출생 연월</label>
          <input
            id="born_year"
            class="mx-4 px-4 py-1 w-44"
            type="month"
            v-model="born_year"
            required
          />
        </article>
        <!-- 성별 -->
        <article class="flex items-center space-x-2 w-full pr-2">
          <label class="font-bold text-center mx-auto" for="gender">성별</label>
          <label>
            <input
              type="radio"
              name="gender"
              value="0"
              v-model="gender"
              checked
            />
            <div class="px-4 py-2 flex justify-center items-center text-center">
              남성
            </div>
          </label>
          <label>
            <input type="radio" name="gender" value="1" v-model="gender" />
            <div class="px-4 py-2 flex justify-center items-center text-center">
              여성
            </div>
          </label>
          <!-- <input type="radio" />남성 
          <input type="radio" />여성 -->
          <!-- <select
            id="gender"
            class="w-28 px-4 py-2 hover:bg-gray-100 focus:outline-none"
            v-model="gender"
            required
          >
            <option value="" selected disabled hidden>선택</option>
            <option value="0">남성</option>
            <option value="1">여성</option>
          </select> -->
        </article>
      </section>
      <section class="flex flex-col">
        <article class="form-control">
          <label for="nickname">닉네임</label>
          <input
            id="nickname"
            type="text"
            minlength="2"
            maxlength="12"
            v-model="nickname"
            pattern="{2,12}+"
            required
            @keyup="nicknameCheck()"
          />
          <base-button id="btn" class="text-sm" @click="modulesCheckNickname"
            >중복 확인</base-button
          >
        </article>
          <span class="checkMessageColor">{{ nicknamemessage }}</span>
          <p class="alertText">
            * 닉네임은 최소 2자, 최대 12자까지 입력이 가능합니다.<br />*
            회원가입 후에도 자유롭게 변경할 수 있습니다.
          </p>
      </section>
      <section class="flex flex-col">
        <article class="form-control">
          <label for="password">비밀번호</label>
          <input
            id="password"
            type="password"
            minlength="8"
            maxlength="20"
            pattern="^(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}$"
            v-model="password"
            required
            @keyup="passwordCheck()"
          />
        </article>
        <span class="checkMessageColor">{{ passwordmessage }}</span>
        <p class="alertText">
          * 영문과 숫자를 반드시 포함하여 최소 8자, 최대 20자까지 입력이
          가능합니다.
        </p>
      </section>
      <section class="flex flex-col">
        <article class="form-control">
          <label for="confirm">비밀번호 확인</label>
          <input
            id="confirm"
            type="password"
            minlength="8"
            maxlength="20"
            pattern="^(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}$"
            v-model="confirm"
            required
            @keyup="passwordConfirmCheck()"
          />
        </article>
        <span class="checkMessageColor">{{ passwordconfirmmessage }}</span>
      </section>
      <section class="flex w-full justify-center space-x-1 pt-10">
        
        <input type="checkbox" v-model="checkAgree" required/><label class=""></label>
        <span class="text-sm">모든
        <base-modal></base-modal>
        <span class="underline font-bold">이용약관</span>과
        <base-modal></base-modal>
        <span class="underline font-bold">개인정보이용정책</span>에 동의합니다.</span>
        
      </section>
      <section class="flex w-full justify-center space-x-4 pb-2">
        <!-- v-bind:disabled="registerSignup" -->
        <base-button mode="primary" @click="signupCheck">회원가입</base-button>
        <base-button @click="goHome()">돌아가기</base-button>
      </section>
    </form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      email: null,
      code: null,
      nickname: null,
      born_year: null,
      gender: '0',
      emailmessage: '',
      nicknamemessage: '',
      passwordmessage: '',
      passwordconfirmmessage: '',

      codeAuth: true,
      codeFromSpring: '',

      registerSignup: true,

      // 인증 체크
      checkAgree: false,
      checkEmail: false,
      checkNickname: false,
      checkPassword: false,
      checkPasswordConfirm: false
    };
  },
  methods: {
    async emailSend() {
      const result = await this.$store.dispatch(
        'account/sendCodeCheck',
        this.email
      );
      if (result === 'SUCCESS') {
        alert('인증코드가 전송되었습니다.');
        this.codeAuth = false;
      } else {
        alert('인증코드 전송을 실패했습니다.');
        this.codeAuth = true;
      }
    },
    async codeCheck() {
      const result = await this.$store.dispatch(
        'account/emailCodeCheck',
        this.code
      );
      if (result === 'SUCCESS') {
        this.emailmessage = '이메일 인증 성공';
        this.codeAuth = true; // 클릭 비활성화
        this.checkEmail = true;
      } else {
        this.emailmessage = '이메일 인증 실패';
        this.codeAuth = false; // 클릭 활성화
        this.checkEmail = false;
      }
      this.listCheck();
    },
    nicknameCheck() {
      if (
        this.nickname == null ||
        this.nickname.length < 2 ||
        this.nickname.length > 12
      ) {
        this.nicknamemessage = '글자 수를 확인 바랍니다.';
      } else {
        this.nicknamemessage = '';
      }
    },
    async modulesCheckNickname() {
      const result = await this.$store.dispatch(
        'account/nicknameCheck',
        this.nickname
      );
      if (result === 'SUCCESS') {
        this.nicknamemessage = '사용 가능합니다.';
        this.checkNickname = true;
      } else {
        this.nicknamemessage = '사용 불가합니다.';
        this.checkNickname = false;
      }
      this.listCheck();
    },
    passwordCheck() {
      var check = new RegExp('^(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}$');
      if (check.test(this.password)) {
        this.passwordmessage = '사용 가능';
        this.checkPassword = true;
        return this.listCheck();
      }
      this.passwordmessage = '영문포함 필수';
      this.checkPassword = false;
      this.listCheck();
    },
    passwordConfirmCheck() {
      if (this.password === this.confirm) {
        this.passwordconfirmmessage = '비밀번호가 동일합니다.';
        this.checkPasswordConfirm = true;
      } else {
        this.passwordconfirmmessage = '비밀번호가 다릅니다.';
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
        return (this.registerSignup = true);
      }
      return (this.registerSignup = false);
    },
    // signupCheck() {
    //   var yearMonth = this.born_year.replace('-', '');
    //   if (yearMonth < 190001) {
    //     alert('가입할 수 없는 출생 연월입니다.');
    //   } else if (yearMonth > 202103) {
    //     alert('탄생 예정을 축하합니다.');
    //   }
    // },
    async signupCheck() {
      this.listCheck();
      if (!this.registerSignup) return;

      const signupData = {
        email: this.email,
        password: this.password,
        nickname: this.nickname,
        born_year: this.born_year.replace('-', ''),
        gender: this.gender
      };

      const result = await this.$store.dispatch(
        'account/signupRegister',
        signupData
      );
      if (result === 'SUCCESS') {
        console.log('회원가입성공');
      } else {
        console.log('실패');
      }

      this.$router.go(-1);
    },
    goHome() {
      this.$router.go(-1);
    }
  },
  computed: {}
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

#btn {
  width: 8rem;
}

.form-control {
  display: flex;
  align-items: center;
}

.form-control label {
  width: 8rem;
  font-weight: 600;
  font-size: 1rem;
  line-height: 1.5rem;
}

.form-control input {
  width: 50%;
  margin-left: 1rem;
  margin-right: 1rem;
  padding: 0.5rem 1.125rem;
  text-align: left;
  font-size: 0.875rem;
  line-height: 1.25rem;
}
input:hover {
  --tw-bg-opacity: 1;
  background-color: rgba(243, 244, 246, var(--tw-bg-opacity));
}

input[type='text']:focus {
  border: 1px solid rgb(153, 152, 152);
}

/* input:focus {
  outline: 2px solid transparent;
  outline-offset: 2px;
  border: 2px solid gray;
  border-radius: 9999px;
} */

.checkMessageColor {
  color: #cf4f2e;
  font-size: 0.875rem;
  line-height: 1.25rem;
  margin-top: 0.5rem;
  margin-left: 9rem;
}

.alertText {
  color: #c6c6c6;
  font-size: 0.75rem;
  line-height: 1rem;
  margin-top: 0.5rem;
  margin-left: 9rem;
}

::placeholder {
  color: #c6c6c6;
}

[type='radio'] {
  position: absolute;
  opacity: 0;
  width: 0;
  height: 0;
}

/* default STYLES */
[type='radio'] + div {
  cursor: pointer;
  color: white;
  background: #aaa9a9;
  font-size: 0.875rem;
  line-height: 1.25rem;
}

[type='radio']:checked + div {
  background: #4f8163;
}

</style>
