<script setup>
import defaultImage from '@/assets/defaultImage.jpg'
const imageAlt = "image1"; // alt 속성 설정
import { ref, watch, onMounted } from 'vue';

import { getTopAttractionInfo } from "@/api/attractionInfo";
import { listReview, updateHits } from "@/api/review.js";


const attractions = ref({});
const reviews = ref([]);

onMounted(() => {
    getAttractions();
    getReviewList();
})

const param = ref({
    pageno: 1,
    pagesize: 4,
    keyword: "",
    order: "like",
    sidos: []
});

const getAttractions = () => {
    console.log("관광지 정보 요청");
    getTopAttractionInfo(
        ({ data }) => {
            console.log(data);
            attractions.value = data.data;
            console.log(attractions);
        },
        (error) => {
            console.log(error);
        }
    );
};

const getReviewList = () => {
    console.log("서버에 review 목록 요청", param.value);
    listReview(
        param.value,
        ({ data }) => {
            console.log(data.msg)
            console.log(data);
            console.log(data.data.reviewInfos);
            reviews.value = data.data.reviewInfos;
        },
        (error) => {
            console.log(error);
        }
    );
};

function incrementHits(reviewid) {
    updateHits(
        reviewid,
        (response) => {
            if (response.status == 200) {
                reviews.value.hits++;
            }
        },
        (error) => {
            console.log(error);
        }
    );
}


</script>

<template>
    <main class="main-wrapper">
        <div class="description">
            <h2>핫한 여행지</h2>
            <div class="image-wrapper">
                <div class="moving-image">
                    <img v-for="(image, index) in images" :key="index" :src="image.src" :alt="image.alt" @click="moveSlide(index)">
                </div>
            </div>

            <!-- Description Content Here -->
            <p>Description of the images goes here.</p>
        </div>

        <div class="gray-band">
            <h2>핫한 여행지</h2>
            <div class="image-gallery">
                <div class="image-item" v-for="(attraction, index) in attractions" :key="index">
                    <img :src="attraction.url" class="profile-image">
                    <div class="overlay">
                        <div class="text">{{ attraction.name }}</div>
                        <div class="text">{{ attraction.sidoName }}</div>
                    </div>
                </div>
            </div>
        </div>
        <!--  -->
        <div class="description">
            <h2>최신 인기 게시물</h2>
            <div class="image-gallery">
                <div class="image-item" v-for="review in reviews" :key="review.reviewId">
                    <RouterLink :to="{ name: 'review-view', params: { reviewid: review.reviewId } }"
                        @click="incrementHits">
                        <img :src="review.reviewImageUrl ? review.reviewImageUrl : defaultImage" class="profile-image">
                        <div class="text">{{ review.title }}</div>
                        <div class="text"> likes: {{ review.likes }}</div>
                    </RouterLink>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped>
.image-wrapper {
  overflow: hidden;
}

.moving-image {
  display: flex;
}

.moving-image img {
  flex: 0 0 auto;
  width: 100%;
  height: auto;
}

.description {
    background-color: #E1CCEC;
    padding: 20px;
    clear: both;
    margin-bottom: 30px;
}

.gray-band {
    background-color: #E3EFFA;
    padding: 20px;
    margin-bottom: 30px;
}

.image-gallery {
    display: flex;
    gap: 10px;
    text-align: center;
    font-weight: 800;
}

.image-item {
    position: relative;
    width: 25%;
    /* 각 이미지가 4개씩 가로로 배치되도록 25% 너비 */
}

.profile-image {
    width: 100%;
    display: block;
}

.overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    /* 어두운 오버레이 */
    color: white;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    opacity: 0;
    transition: opacity 0.3s ease;
}

.image-item:hover .overlay {
    opacity: 1;
}

.text {
    margin: 5px 0;
}
</style>