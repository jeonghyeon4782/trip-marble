<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { logoutMember } from "@/api/auth";
import { getSidoList } from "@/api/board";
import { listAttractionInfo } from "@/api/attractionInfo";
import Swal from "sweetalert2";
import homeImage from "@/assets/home.png";

import SeoImage from "@/assets/서두나.png";
import ParkImage from "@/assets/박찬호.png";
import KimImage from "@/assets/김태균.png";
import WelcomeImage from "@/assets/welcome.jpg";

const router = useRouter();
const isLogin = ref(false);

onMounted(() => {
  if (localStorage.getItem("isLogin") === "true") {
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
    confirmButtonText: '확인'
  });
};

function showBoardMain(sidoId, imageId) {
  router.push({ name: "board", params: { sidoId, imageId } });
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

const confirmLogin = () => {
  listAttractionInfo(
    (response) => {
      onGetSidoList();
    },
    (error) => {
      showSwal("error", "로그인을 해주세요", null);
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
              Swal.fire({
                title: "캐릭터를 선택해주세요!",
                html: `
  <button class="image-button" data-id="1" style="margin: 5px; border: none; background: none;">
    <img src="${SeoImage}" style="border-radius: 50%; width: 100px; height: 100px; cursor: pointer;" alt="서두나">
    <span style="display: block; text-align: center;">서두나</span>
  </button>
  <button class="image-button" data-id="2" style="margin: 5px; border: none; background: none;">
    <img src="${ParkImage}" style="border-radius: 50%; width: 100px; height: 100px; cursor: pointer;" alt="박찬호">
    <span style="display: block; text-align: center;">박찬호</span>
  </button>
  <button class="image-button" data-id="3" style="margin: 5px; border: none; background: none;">
    <img src="${KimImage}" style="border-radius: 50%; width: 100px; height: 100px; cursor: pointer;" alt="김태균">
    <span style="display: block; text-align: center;">김태균</span>
  </button>
`,

                showCloseButton: true,
                showConfirmButton: false,
                focusConfirm: false,
                allowOutsideClick: false,
                didOpen: () => {
                  const imageButtons =
                    Swal.getHtmlContainer().querySelectorAll(".image-button");
                  imageButtons.forEach((imageButton) => {
                    imageButton.addEventListener("click", () => {
                      const imageId = imageButton.getAttribute("data-id");
                      Swal.close();
                      Swal.fire({
                        title: "부루마블 시작!",
                        text: "즐거운 부루마블하세요🎉🎉",
                        imageUrl: WelcomeImage,
                        imageWidth: 400,
                        imageHeight: 200,
                        imageAlt: "환영",
                        confirmButtonText: "확인",
                      });
                      showBoardMain(sidoId, imageId);
                    });
                  });
                },
              });
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
  confirmLogin();
}
</script>

<template>
  <nav class="fixed-nav">
    <div class="left">
      <RouterLink :to="{ name: 'main' }">
        <img :src="homeImage" alt="Home" class="logo" />
      </RouterLink>
    </div>
    <div class="center">
      <a @click.prevent="handleBoardClick">🎲 부루마블</a>
      <RouterLink :to="{ name: 'review' }">✍️ 여행일기 </RouterLink>
    </div>
    <div class="right">
  <RouterLink v-if="!isLogin" :to="{ name: 'auth' }">🔐 로그인 </RouterLink>
  <RouterLink v-if="isLogin" :to="{ name: 'mypage' }">👤 내 정보 </RouterLink>
  <a v-if="isLogin" @click="logout">🔐 로그아웃 </a>
</div>
  </nav>
</template>
<style scoped>
* {
    font-family: "Gaegu", cursive;
}

.fixed-nav {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 12%;
  display: flex;
  justify-content: space-between;
  padding: 10px;
  z-index: 999;
  font-size: 20px;
  border-bottom: 2px solid #f1ebeb;
  background-color: white;
}

header {
  background-color: white;
  padding: 20px;
}

nav {
  background-color: white;
  padding: 10px;
  display: flex;
  align-items: center;
}

.left {
  display: flex;
  margin-left: 20px;
  align-items: center;
  margin-right: auto;
}

.center {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-grow: 1;
  gap: 70px; /* 각 버튼 사이의 간격을 조절합니다. */
}

.right {
  display: flex;
  align-items: center;
  gap: 70px; /* 각 요소 사이의 간격을 조절합니다. */
  margin-right: 100px;
}

nav a {
  font-size: 30px;
  color: black;
  text-decoration: none;
  margin: 0 20px;
  cursor: pointer;
  display: flex;
  align-items: center;
  padding: 10px 0;
  line-height: 1.5;
}

.center a:hover, .right a:hover{
  background-color: #e3effa; /* 배경색도 변경 */
}

.logo {
  width: 140px;
  height: 110px;
}
</style>

<style>

* {
    font-family: "Gaegu", cursive;
}

.custom-popup-class {
  width: 1100px !important;
  height: 1100px !important;
  overflow: hidden !important;
}

.sido-button {
  margin: 5px;
  padding: 10px 20px;
  border-radius: 5px;
  border: none;
  background-color: white;
  color: black;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  font-size: 20px;
}

.sido-button:hover {
  background-color: #e3effa;
}
</style>