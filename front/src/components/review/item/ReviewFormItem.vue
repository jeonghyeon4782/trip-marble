<script setup>
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter()
const route = useRoute()

const props = defineProps({ type: String })

const post = ref({
    id: 1,
    title: '게시판 제목',
    content: '게시판 내용이 여기에 들어갑니다.',
    address: '',
    image: null
});

function moveList() {
    router.replace({ name: "review-list" })
}


// 이미지 업로드 처리
const handleImageUpload = (event) => {
    const file = event.target.files[0];
    post.value.image = file;
};
</script>

<template>
    <div class="item">
        <h2>게시판 수정</h2>
        <form @submit.prevent="onSubmit">
            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" id="title" v-model="post.title" required>
            </div>
            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" v-model="post.content" rows="6" required></textarea>
            </div>
            <div class="form-group">
                <label for="address">주소 추가</label>
                <input type="text" id="address" v-model="post.address">
            </div>
            <div class="form-group">
                <label for="image">이미지 추가</label>
                <input type="file" id="image" accept="image/*" @change="handleImageUpload">
            </div>
            <button type="submit" class="btn">완료</button>
        </form>
    </div>

</template>

<style scoped>
/* section {
  display: flex;
  justify-content: center;
} */

.item {
    background-color: #F1F1F6;
    border-radius: 10px;
    overflow: hidden;
    text-align: center;
    padding: 20px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.item h2 {
    text-align: center;
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    display: block;
    margin-bottom: 5px;
}

.form-group input,
.form-group textarea {
    width: 97%;
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
</style>