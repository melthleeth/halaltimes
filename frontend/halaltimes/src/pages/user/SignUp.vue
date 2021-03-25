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
              required
            />
            <base-button v-show="codeAuth" @click="codeCheck()"
              >확인</base-button
            >
            <span class="checkMessageColor">{{ emailmessage }}</span>
          </section>
        </article>

        <!-- 출생/성별 -->
        <article>
          <!-- 출생년도 -->
          <section>
            <label for="born">출생년도</label>
            <input id="born" type="month" />
          </section>
          <!-- 성별 -->
          <section>
            <label for="gender">성별</label>
            <!-- <input type="radio" />남성 
          <input type="radio" />여성 -->
            <select id="gender" style="height: 30px">
              <option value="0">남성</option>
              <option value="1">여성</option>
            </select>
          </section>
        </article>

        <!-- 닉네임 -->
        <article>
          <section>
            <label for="nickname">닉네임</label>
            <input id="nickname" type="text" minlength="2" maxlength="12" />
            <base-button>중복 확인</base-button>
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
            <input id="password" type="password" minlength="8" maxlength="20" />
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
            <input id="confirm" type="password" minlength="8" maxlength="20" />
            <span class="checkMessageColor">{{ passwordconfirmmessage }}</span>
          </section>
        </article>
      </body>
      <footer>
        <!-- 개인정보 이용 정책 -->
        <article>
          <section>
            <label><input type="checkbox" /></label> 모든
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
            <base-button>회원가입</base-button>
            <base-button>취소</base-button>
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
      emailmessage: "이메일",
      nicknamemessage: "닉네임",
      passwordmessage: "비밀번호",
      passwordconfirmmessage: "비밀번호확인",
      codeAuth: true,
      codeFromSpring: "",

      checkEmail: false,
    };
  },
  methods: {
    emailSend() {
      // 초기화 필요
      // this.emailmessage = ""
      // 인증메일 확인 버튼 활성화시킴
      if (this.email == null) {
        alert("이메일 주소를 입력하시기 바랍니다.");
      }
    },
    codeCheck() {
      // 인증메일 전송 눌러야 활성화 가능
      if (this.codeFromSpring == this.code) {
        this.checkEmail = true;
        this.codeAuth = false;
        this.emailmessage = "이메일 인증 성공";
      } else {
        this.emailmessage = "이메일 인증 실패";
      }
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
