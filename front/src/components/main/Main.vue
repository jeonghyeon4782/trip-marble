<script setup>
import defaultImage from '@/assets/defaultImage.jpg'
import { ref, watch, onMounted } from 'vue';


import { getTopAttractionInfo } from "@/api/attractionInfo";
import { listReview, updateHits } from "@/api/review.js";
import { getSidoList } from "@/api/board.js";

const carousel = ref(null);
const attractions = ref({});
const reviews = ref([]);
const sidos = ref([]);

onMounted(() => {
    getAttractions();
    getReviewList();
    getSidos();
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

const getSidos = () => {
    console.log("sido 목록 요청");
    getSidoList(
        ({ data }) => {
            console.log(data.msg)
            console.log(data);
            sidos.value = data.data;
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

const handleImageClick = (sidoId) => {
    console.log("해당 시도를 찾습니다." + sidoId);
    // url 요청
};

const nextSlide = () => {
    if (carousel.value) {
        carousel.value.next();
    }
};

const prevSlide = () => {
    if (carousel.value) {
        carousel.value.prev();
    }
};


</script>

<template>
    <main class="main-wrapper">

        <div class="carousel-container">
            <Carousel ref="carousel" :items-to-show="1" :items-to-scroll="1" :wrapAround="true" :autoplay="2000"
                pauseAutoplayOnHover="true">
                <Slide v-for="sido in sidos" :key="sido" class="carousel-slide">
                    <div class="sido-info">
                        <p>
                            {{ sido.name }}
                        </p>
                    </div>
                    <img :src="sido.imageUrl" alt="carousel image" @click="handleImageClick(sido.sidoId)"
                        class="carousel-image" />
                </Slide>
            </Carousel>
        </div>
        <button @click="prevSlide" class="carousel-nav-button prev-button">‹</button>
        <button @click="nextSlide" class="carousel-nav-button next-button">›</button>

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
.carousel-container {
    position: relative;
    width: 100%;
    /* max-width: 1200px; */
    margin: 0 auto;
    overflow: hidden;
}

.carousel-slide {
    display: flex;
    /* justify-content: center; */
    /* align-items: center; */
    width: 100%;
    height: 400px;
    /* Fixed height for the slide */
}

.carousel-image {
    /* height: 00%; */
    /* object-fit: ; */
    /* Ensures the image covers the slide without distortion */
    cursor: pointer;
}

.carousel-nav-button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background-color: rgba(0, 0, 0, 0.5);
    color: white;
    border: none;
    padding: 10px;
    cursor: pointer;
    z-index: 1;
}

.prev-button {
    left: 10px;
}

.next-button {
    right: 10px;
}

.sido-info {
    font-size: 30px;
    width: 100%;
    text-align: left;
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