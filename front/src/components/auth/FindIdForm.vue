<script setup>
import { findMemberId } from '@/api/auth';
import { ref } from 'vue';
import Swal from "sweetalert2";


const data = ref({
    email: "",
});

const onFindMemberId = () => {
    findMemberId(
        data.value.email,
    (response) => {
        console.log(response.data.msg)
        showSwal("success", response.data.msg, "당신의 아이디는 " + response.data.data + "입니다.");
    },
    (error) => {
        console.log(error.response.data.msg)
        showSwal("error", error.response.data.msg, null);
    }
  );
}

const showSwal = (icon, title, text) => {
  Swal.fire({
    icon: icon,
    title: title,
    text: text,
  });
};

</script>

<template>
    <section>
        <div class="container">
            <h2>아이디 찾기</h2>
            <form @submit.prevent="onFindMemberId">
                <div class="form-group">
                    <label for="username">Email</label>
                    <input type="text" id="email" name="email" required v-model="data.email">
                </div>
                <button class="btn">확인</button>
            </form>
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
    margin-top: 10px;
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