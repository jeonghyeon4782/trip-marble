<script setup>
import { getModifyReview, modifyReview, registReview } from '@/api/review';
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter()
const route = useRoute()

const props = defineProps({ type: String })

const imageUrl = ref({})

const review = ref({
    reviewId: 0,
    title: '',
    content: '',
    attractionInfoId: '',
});

const attractions = ref([
    { attractionInfoId: 1, name: '어트렉션1', location: '위치1', created_at: '2024-05-18' },
    { attractionInfoId: 2, name: '어트렉션2', location: '위치2', created_at: '2024-05-19' },
    // 기존의 어트렉션 데이터...
])


if (props.type == "modify") {
    let { reviewid } = route.params
    console.log(reviewid + "번 리뷰 수정 요청");
    getModifyReview(
        reviewid,
        ({ data }) => {
            review.value = data.data
        },
        (error) => {
            console.log(error)
        }
    )
}

const titleErrMsg = ref("")
const contentErrMsg = ref("")
const attractionErrMsg = ref("")

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

watch(
    () => review.value.attractionInfoId,
    (value) => {
        if (value == "") {
            attractionErrMsg.value = "어트렉션을 선택해주세요."
        } else attractionErrMsg.value = ""
    },
    { immediate: true }
)

function onSubmit() {
    if (titleErrMsg.value) {
        alert(titleErrMsg.value)
    } else if (contentErrMsg.value) {
        alert(contentErrMsg.value)
    } else if (attractionErrMsg.value) {
        alert(attractionErrMsg.value)
    } else {
        props.type === "regist" ? writeReview() : updateReivew()
    }
}

function writeReview() {
    console.log("리뷰 등록 요청", review.value)
    registReview(
        review.value,
        imageUrl,
        (response) => {
            let msg = "리뷰등록 처리시 문제 발생했습니다."
            console.log(response)
            if (response.status == 201) msg = "리뷰등록이 완료되었습니다."
            alert(msg)
            const reviewId = response.data.data.reviewId;
            moveDetail(reviewId)
        },
        (error) => console.log(error)
    )
}

function updateReivew() {
    console.log(review.value.reviewId + "번 리뷰 수정 요청", review.value)
    modifyReview(
        review.value,
        review.value.reviewId,
        imageUrl,
        (response) => {
            let msg = "리뷰수정 처리시 문제 발생했습니다."
            if (response.status == 201) msg = "리뷰정보 수정이 완료되었습니다."
            alert(msg)
            const reviewId = response.data.data.reviewId;
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

function goBack(){
    router.go(-1);
}

</script>

<template>
    <a @click="goBack">Back to Posts</a>
    <div class="item">
        <div class="review-image">
            <img v-if="review.imageUrl" :src="review.imageUrl" :alt="imageAlt">
        </div>
        <form @submit.prevent="onSubmit">
            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" id="title" v-model="review.title">
            </div>
            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" v-model="review.content" rows="6"></textarea>
            </div>
            <!-- <div class="form-group">
                <label for="address">어트렉션 추가</label>
                <input type="text" id="address" v-model="review.address">
            </div> -->
            <div class="form-group">
                <label for="attractionList">어트렉션 목록</label>
                <select id="attractionList" v-model="review.attractionInfoId">
                    <option disabled value="">어트렉션을 선택하세요</option>
                    <option v-for="(attraction, index) in attractions" :key="index"
                        :value="attraction.attractionInfoId">
                        이름: {{ attraction.name }} | 위치: {{ attraction.location }} | 생성일: {{ attraction.created_at }}
                    </option>
                </select>
            </div>
            <!--  -->
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
.form-group textarea,
#attractionList {
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