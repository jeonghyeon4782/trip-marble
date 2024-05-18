<script setup>
import { loginMember } from '@/api/auth';
import { ref } from 'vue';

import googleIcon from '@/assets/google_login.svg';
import kakaoIcon from '@/assets/kakao_login.svg';
import naverIcon from '@/assets/naver_login.svg';

const member = ref({
    memberId: "",
    password: ""
});

function onSubmit() {
    console.log("로그인 요청", member.value)
    loginMember(
        member.value,
        (response) => {
            if (response.status == 200) {
                localStorage.setItem('isLogin', true);
                location.reload();
                location.href = '/';
            }
        },
        (error) => {
            let msg = error.response.data.msg;
            alert(msg)
            console.log(error)
        }
    )
}

function googleLogin() {
    window.location.href = 'https://accounts.google.com/o/oauth2/v2/auth?client_id=825050218681-l73t52gvefmitanqdcdfbt9nllmodg82.apps.googleusercontent.com&redirect_uri=http://localhost:3000/oauth/google&response_type=code&scope=openid%20email%20profile';
}

function kakaoLogin() {
    window.location.href = 'https://kauth.kakao.com/oauth/authorize?client_id=dd52c20acaab911207c5de72b3089744&redirect_uri=http://localhost:3000/oauth/kakao&response_type=code&scope=account_email%20profile_nickname';
}
</script>

<template>
    <section>
        <div class="link">
            <RouterLink :to="{ name: 'main' }">Back</RouterLink>
            <RouterLink :to="{ name: 'signup' }">Signup</RouterLink>
        </div>
        <div class="container">
            <h2>Login</h2>
            <form @submit.prevent="onSubmit">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required v-model="member.memberId">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required v-model="member.password">
                </div>
                <button type="submit" class="btn">Login</button>
            </form>
            <div class="icon-container">
                <img :src="googleIcon" :alt="imageAlt" class="icon" @click="googleLogin">
                <img :src="kakaoIcon" :alt="imageAlt" class="icon" @click="kakaoLogin">
                <img :src="naverIcon" :alt="imageAlt" class="icon">
            </div>
        </div>
    </section>
</template>

<style scoped>
section {
    margin: 0 auto;
    /**/
    width: 27%;
    /**/
    display: felx;
    justify-content: center;
}

.container {
    width: 300px;
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
    width: 100%;
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

.link {
    display: flex;
    justify-content: space-between;
    margin: auto 0;
    text-align: right;
    padding: 0 10px;
}

.icon-container {
    display: flex;
    justify-content: space-around;
    margin-top: 30px;
}

.icon {
    margin: 10px;
    width: 50px;
    height: 50px;
    cursor: pointer;
}
</style>