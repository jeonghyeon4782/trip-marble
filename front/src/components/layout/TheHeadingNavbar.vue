<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from "vue-router";
import { logoutMember } from '@/api/auth';

const router = useRouter();
const isLogin = ref({});

onMounted(() => {
    if (localStorage.getItem('isLogin') == "true") {
        isLogin.value = true;
    } else {
        isLogin.value = false;
    }
})

function logout() {
    console.log("로그아웃 요청")
    logoutMember(
        (response) => {
            if (response.status == 201) {
                localStorage.setItem('isLogin', false);
                location.reload();
                location.href = '/auth';
            }
        },
        (error) => {
            console.log(error)
        }
    )
}

</script>

<template>

    <header class="fixed-header">
        <!-- Header Content Here -->
        <h1>This is the Header</h1>
    </header>

    <nav class="fixed-nav">
        <!-- Navigation Bar Content Here -->
        <div class="center">
            <RouterLink :to="{ name: 'main' }">
                <p>Home</p>
            </RouterLink>
            <RouterLink :to="{ name: 'review-list' }">
                <p>Board</p>
            </RouterLink>
            <RouterLink :to="{ name: 'review-list' }">
                <p>Review</p>
            </RouterLink>
            <RouterLink :to="{ name: 'review-list' }">
                <p>Review</p>
            </RouterLink>
        </div>
        <div class="left">
            <RouterLink v-if="!isLogin" :to="{ name: 'auth' }">
                <p>Login</p>
            </RouterLink>
            <a @click="logout">
                <p>Logout</p>
            </a>
            <RouterLink :to="{ name: 'main' }">
                <p>Mypage</p>
            </RouterLink>
        </div>
    </nav>

</template>

<style scoped>
.fixed-header {
    position: fixed;
    /* 화면 상단에 고정 */
    top: 0;
    /* 위쪽 여백 없이 고정 */
    left: 0;
    /* 왼쪽에 고정 */
    width: 100%;
    /* 너비 100% */
    height: 10%;
    padding: 0px 30px;
    /* 패딩 추가 */
    z-index: 999;
    /* 다른 요소 위에 표시 */
}

.fixed-nav {
    position: fixed;
    /* 화면 상단에 고정 */
    top: 10%;
    /* 헤더의 높이만큼 아래로 이동 */
    left: 0;
    /* 왼쪽에 고정 */
    width: 100%;
    /* 너비 100% */
    height: 5%;
    padding: 10px;
    /* 패딩 추가 */
    z-index: 999;
    /* 다른 요소 위에 표시 */
    font-size: 20px;
}

header {
    background-color: #E3EFFA;
    padding: 20px;
}

nav {
    background-color: #E1CCEC;
    padding: 10px;
    display: flex;

}

.center {
    width: 80%;
    display: flex;
    align-items: center;
    justify-content: center;
}

.left {
    width: 20%;
    display: flex;
    align-items: center;
}

p:hover {
    color: #E3EFFA;
}

nav a {
    color: white;
    text-decoration: none;
    margin: 0 40px;
}
</style>