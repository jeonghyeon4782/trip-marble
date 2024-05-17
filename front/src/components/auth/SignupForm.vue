<script setup>
import { registMember } from '@/api/auth';
import { ref, watch } from 'vue';
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const { email, oauthServerType } = route.query;
console.log(email, oauthServerType);

const member = ref({
  memberId: "",
  password: "",
  repassword: "",
  nickname: "",
  email: "",
  oauthServiceType: "GENERAL"
});

if (email) {
  member.value.email = email;
}

if (oauthServerType) {
  member.value.oauthServiceType = oauthServerType;
}

const memberIdErrMsg = ref("")
const nicknameErrMsg = ref("")
const emailErrMsg = ref("")
const passwordErrMsg = ref("")

watch(
  () => member.value.memberId,
  (value) => {
    let len = value.length
    const regex = /^[a-z0-9]+$/;

    if (len < 6 || len > 12) {
      memberIdErrMsg.value = "아이디는 6자 이상 12자 이하입니다"
    } else if (!regex.test(value)) {
      memberIdErrMsg.value = "아이디는 영어 소문자와 숫자로만 허용되며, 공백이 없어야 합니다."
    }
    else memberIdErrMsg.value = ""
  },
  { immediate: true }
)
watch(
  () => member.value.nickname,
  (value) => {
    let len = value.length
    const regex = /^[a-zA-Z가-힣0-9]*$/;

    if (len < 3 || len > 10) {
      nicknameErrMsg.value = "닉네임은 3자 이상 10자 이하입니다."
    } else if (!regex.test(value)) {
      nicknameErrMsg.value = "닉네임은 영어 소문자, 대문자, 한글, 숫자로만 이루어져야 하며, 공백이 없어야 합니다."
    } else nicknameErrMsg.value = ""
  },
  { immediate: true }
)
watch(
  () => member.value.email,
  (value) => {
    let len = value.length;
    const regex = /^[^\s]+$/;

    if (len == 0 || len > 500) {
      emailErrMsg.value = "이메일을 확인해 주세요!!!"
    } else if (!regex.test(value)) {
      emailErrMsg.value = "이메일에 공백을 포함할 수 없습니다.";
    } else emailErrMsg.value = ""
  },
  { immediate: true }
)

watch(
  [() => member.value.password, () => member.value.repassword],
  ([password, repassword]) => {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@_!*$%#])[A-Za-z\d@_!*$%#]{8,}$/;

    if (password != repassword) {
      console.log(password, repassword)
      passwordErrMsg.value = "비밀번호가 서로 다릅니다.";
    } else if (password.length < 10 || password.length > 20) {
      passwordErrMsg.value = "비밀번호는 10자 이상 20자 이하입니다.";
    } else if (!regex.test(password)) {
      passwordErrMsg.value = "비밀번호는 영어 소문자, 대문자, 숫자, 특수 문자(@ _ ! * $ % #)를 모두 포함하여 8자 이상이어야 합니다."
    }
    else {
      passwordErrMsg.value = "";
    }
  },
  { immediate: true }
);


function onSubmit() {
  if (memberIdErrMsg.value) {
    alert(memberIdErrMsg.value)
  } else if (nicknameErrMsg.value) {
    alert(nicknameErrMsg.value)
  } else if (emailErrMsg.value) {
    alert(emailErrMsg.value)
  } else if (passwordErrMsg.value) {
    alert(passwordErrMsg.value)
  } else {
    signup()
  }

}

function signup() {
  console.log("회원가입 요청", member.value)
  registMember(
    member.value,
    (response) => {
      let msg = "회원가입 처리시 문제 발생했습니다."
      if (response.status == 201) {
        msg = "회원가입이 완료되었습니다."
        router.replace({ name: 'login' })
      }
      alert(msg)
    },
    (error) => console.log(error)
  )
}

</script>

<template>
  <section>
    <div class="container">
      <h2>회원가입</h2>
      <form @submit.prevent="onSubmit">
        <div class="form-group">
          <label for="id">아이디</label>
          <input type="text" id="id" name="id" required v-model="member.memberId">
        </div>
        <div class="form-group">
          <label for="nickname">닉네임</label>
          <input type="text" id="nickname" name="nickname" required v-model="member.nickname">
        </div>
        <div class="form-group">
          <label for="email">이메일</label>
          <input type="email" id="email" name="email" required v-if="!email" v-model="member.email">
          <input type="email" id="email" name="email" required v-else :value="email" disabled>
        </div>
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input type="password" id="password" name="password" required v-model="member.password">
        </div>
        <div class="form-group">
          <label for="confirm_password">비밀번호 확인</label>
          <input type="password" id="confirm_password" name="confirm_password" required v-model="member.repassword">
        </div>
        <button type="submit" class="btn">회원가입</button>
      </form>
    </div>
    <RouterLink :to="{ name: 'login' }" class="back-link">Login</RouterLink>
  </section>
</template>

<style scoped>
.container {
  width: 500px;
  /* 폼 너비 조정 */
  margin: 20px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px #F1F1F6;
}

.container h2 {
  text-align: center;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 95%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.btn {
  display: block;
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 5px;
  background-color: #E1CCEC;
  color: #fff;
  text-align: center;
  cursor: pointer;
}

.btn:hover {
  background-color: #E3EFFA;
}

.back-link {
  margin-top: 10px;
  /* 로그인 링크 위쪽 간격 조정 */
  margin-left: 10px;
  text-align: center;
}
</style>