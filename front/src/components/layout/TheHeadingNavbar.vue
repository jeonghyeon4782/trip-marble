<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { logoutMember } from "@/api/auth";
import { getSidoList } from "@/api/board";
import Swal from "sweetalert2";

import { createApp } from "vue";

const router = useRouter();
const isLogin = ref({});

onMounted(() => {
  if (localStorage.getItem("isLogin") == "true") {
    isLogin.value = true;
  } else {
    isLogin.value = false;
  }
});

const showSwal = (icon, title, text) => {
  Swal.fire({
    icon: icon,
    title: title,
    text: text,
  });
};

function showBoardMain() {
  router.push({ name: "board" });
}

function logout() {
  console.log("로그아웃 요청");
  logoutMember(
    (response) => {
      if (response.status == 201) {
        localStorage.setItem("isLogin", false);
        location.reload();
        location.href = "/auth";
      }
    },
    (error) => {
      console.log(error);
    }
  );
}

const onGetSidoList = () => {
  getSidoList(
    (response) => {
      const sidoList = response.data.data;
      Swal.fire({
        title: "지역을 선택해주세요!",
        html: sidoList
          .map(
            (sido) => `
        <button 
            class="sido-button" 
            data-id="${sido.sidoId}" 
            style="margin: 5px; padding: 10px 20px; border-radius: 5px; border: none; background-color: #4CAF50; color: white; cursor: pointer;"
            onmouseover="this.style.backgroundColor='#45a049'"
            onmouseout="this.style.backgroundColor='#4CAF50'"
          >
            ${sido.name}
          </button>
        `
          )
          .join(""),
        showCloseButton: true,
        showConfirmButton: false,
        focusConfirm: false,
        allowOutsideClick: false,
        didOpen: () => {
          const buttons =
            Swal.getHtmlContainer().querySelectorAll(".sido-button");
          buttons.forEach((button) => {
            button.addEventListener("click", () => {
              const sidoId = button.getAttribute("data-id");
              console.log(`선택된 sido의 id: ${sidoId}`);
              Swal.fire({
                title: "부루마블 시작!",
                text: "즐거운 부루마블하세요🎉🎉",
                imageUrl: "src/assets/welcome.jpg",
                imageWidth: 400,
                imageHeight: 200,
                imageAlt: "환영",
                confirmButtonText: "확인",
              });
              showBoardMain();
            });
          });
        },
      });
    },
    (error) => {
      console.log(error.response.data.msg);
    }
  );
};

// board 클릭 시
function handleBoardClick() {
  onGetSidoList();
}
</script>

<template>
  <header class="fixed-header">
    <!-- Header Content Here -->
    <h1>DJ TRIP</h1>
  </header>

  <nav class="fixed-nav">
    <div class="center">
      <RouterLink :to="{ name: 'main' }">
        <p>Home</p>
      </RouterLink>
      <a @click.prevent="handleBoardClick">Board</a>
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
      <RouterLink v-if="isLogin" :to="{ name: 'main' }">
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
  background-color: #e3effa;
  padding: 20px;
}

nav {
  background-color: #e1ccec;
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
  color: #e3effa;
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

<style>
.custom-popup-class {
  width: 1100px !important;
  height: 1100px !important;
  overflow: hidden !important;
}
</style>
