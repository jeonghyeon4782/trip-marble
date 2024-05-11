<script setup>
import { getModifyReview, modifyReview, registReview } from '@/api/review';
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter()
const route = useRoute()

const props = defineProps({ type: String })

const review = ref({
    reviewId: 1,
    title: '리뷰 제목',
    content: '리뷰 내용을 작성해 주세요.',
    attractionInfoId: '',
    imageUrl: null
});


if (props.type == "modify") {
    let { reviewid } = route.params
    console.log(reviewid + "번 리뷰 수정 요청");
    getModifyReview(
        reviewid,
        ({ data }) => {
            review.value = data
        },
        (error) => {
            console.log(error)
        }
    )
}

const titleErrMsg = ref("")
const contentErrMsg = ref("")
watch(
    () => review.value.title,
    (value) => {
        let len = value.length
        if (len == 0 || len > 30) {
            titleErrMsg.value = "제목을 확인해 주세요!!!"
        } else titleErrMsg.value = ""
    },
    { immediate: true }
)
watch(
    () => review.value.content,
    (value) => {
        let len = value.length
        if (len == 0 || len > 500) {
            contentErrMsg.value = "내용을 확인해 주세요!!!"
        } else contentErrMsg.value = ""
    },
    { immediate: true }
)

function onSubmit() {
    // event.preventDefault();

    if (titleErrMsg.value) {
        alert(titleErrMsg.value)
    } else if (contentErrMsg.value) {
        alert(contentErrMsg.value)
    } else {
        props.type === "regist" ? writeReview() : updateReivew()
    }
}

function writeReview() {
    console.log("리뷰 등록 요청", reivew.value)
    registReview(
        article.value,
        (response) => {
            let msg = "리뷰등록 처리시 문제 발생했습니다."
            if (response.status == 201) msg = "리뷰등록이 완료되었습니다."
            alert(msg)
            const reviewId = response.data.reviewid;
            moveDetail(reviewId)
        },
        (error) => console.log(error)
    )
}

function updateReivew() {
    console.log(review.value.reviewId + "번 리뷰 수정 요청", review.value)
    modifyReview(
        review.value,
        (response) => {
            let msg = "리뷰수정 처리시 문제 발생했습니다."
            if (response.status == 201) msg = "리뷰정보 수정이 완료되었습니다."
            alert(msg)
            const reviewId = response.data.reviewId;
            moveDetail(reviewId)

        },
        (error) => console.log(error)
    )
}


function moveDetail(reviewId) {
    router.replace({ name: 'review-view', params: { reviewid: reviewId } });
}


// 이미지 업로드 처리
const handleImageUpload = (event) => {
    const file = event.target.files[0];
    review.value.imageUrl = file;
    review.value.imageUrl = URL.createObjectURL(file);
};
</script>

<template>
    <div class="item">
        <div class="review-image">
            <img v-if="review.imageUrl" :src="review.imageUrl" :alt="imageAlt">
        </div>
        <form @submit.prevent="onSubmit">
            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" id="title" v-model="review.title" required>
            </div>
            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" v-model="review.content" rows="6" required></textarea>
            </div>
            <div class="form-group">
                <label for="address">어트렉션 추가</label>
                <input type="text" id="address" v-model="review.address">
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
.review-image>img {
    max-width: 97%;
    border-radius: 10px;
}

.item {
    background-color: #F1F1F6;
    border-radius: 10px;
    overflow: hidden;
    text-align: center;
    padding: 20px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
    font-size: 20px;
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
    font-size: 20px;
}

.form-group textarea {
    height: 400px;
}

.btn {
    display: block;
    width: 100%;
    height: 50px;
    padding: 10px;
    border: none;
    border-radius: 5px;
    background-color: #E1CCEC;
    color: #fff;
    text-align: center;
    cursor: pointer;
    font-size: 20px;
}

.btn:hover {
    background-color: #E3EFFA;
}
</style>