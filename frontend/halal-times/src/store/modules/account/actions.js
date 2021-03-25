const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  // 인증메일 전송
  async sendCodeCheck(_, payload) {
    const response = await fetch(`${SERVER_URL}/service/mail?email=${payload}`, {
      headers: {
        "Content-Type": "application/json; charset=utf-8",
        'Accept': '*/*',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*',
      },
      method: "POST",
    })
    const responseData = await response.text();

    if (responseData === "1") {
      return "FAIL";
    } else {
      return "SUCCESS";
    }

  },
  // 인증코드 확인
  async emailCodeCheck(_, payload) {

    const response = await fetch(`${SERVER_URL}/service/verifyCode?code=${payload}`, {
      headers: {
        "Content-Type": "application/json; charset=utf-8",
        'Accept': '*/*',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*',
      },
      method: "POST",
    })
    const responseData = await response.text();
    if (responseData === "0") {
      return "FAIL";
    } else {
      return "SUCCESS";
    }

  },

  // 닉네임중복확인
  async nicknameCheck(_, payload) {
    const response = await fetch(`${SERVER_URL}/user/nameCheck?nickname=${payload}`, {
      headers: {
        "Content-Type": "application/json; charset=utf-8",
        'Accept': '*/*',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*',
      },
      method: "GET",
    })

    const responseData = await response.text();
    // 존재하면 true, 없으면 false
    if (responseData === "true") {
      // alert("중복된 닉네임입니다.");
      return "FAIL";
    } else {
      // alert("사용가능한 닉네임입니다.");
      return "SUCCESS";
    }
  },

  // 회원가입
  async signupRegister(_, payload) {
    console.log("payload: ", payload)
    const signupData = {
      email: payload.email,
      password: payload.password,
      nickname: payload.nickname,
      born_year: payload.born_year,
      gender: payload.gender,
    }

    const response = await fetch(`${SERVER_URL}/user`, {
      headers: {
        "Content-Type": "application/json; charset=utf-8",
        'Accept': '*/*',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*',
      },
      method: "POST",
      body: JSON.stringify(signupData),
    });

    console.log("res: ", response)
    const responseData = await response.text();
    console.log(responseData);

    if (responseData === "0") {
      // alert("중복된 닉네임입니다.");
      return "FAIL";
    } else {
      // alert("사용가능한 닉네임입니다.");
      return "SUCCESS";
    }
  }
};