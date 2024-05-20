<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from "vue-router";
import { logoutMember } from '@/api/auth';

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
        <h1>DJ TRIP</h1>
    </header>

    <nav class="fixed-nav">
        <!-- Navigation Bar Content Here -->
        <div class="center">
            <RouterLink :to="{ name: 'main' }">
                <p>Home</p>
            </RouterLink>
            <RouterLink v-if="isLogin" :to="{ name: 'review-list' }">
                <p>Board</p>
            </RouterLink>
            <RouterLink :to="{ name: 'review' }">
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
            <a v-else @click="logout">
                <p>Logout</p>
            </a>
            <RouterLink v-if="isLogin" :to="{ name: 'mypage' }">
                <p>Mypage</p>
            </RouterLink>
        </div>
    </nav>

</template>

<style scoped>
.fixed-header {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 10%;
    padding: 0px 30px;
    z-index: 999;
}

.fixed-nav {
    position: fixed;
    top: 10%;
    left: 0;
    width: 100%;
    height: 5%;
    display: flex;
    justify-content: space-between;
    padding: 10px;
    z-index: 999;
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
    display: flex;
    align-items: center;
    justify-content: center;
}

.left {
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
    cursor: pointer;
    height: 100%;
    display: flex;
    align-items: center;
}
</style>