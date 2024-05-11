<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";

import imageSrc from '@/assets/image1.jpg';
import favorite from '@/assets/favorite.svg';
import favoriteFill from '@/assets/favorite_fill.svg';
import location from '@/assets/location.svg';
import { deleteReview, detailReview } from "@/api/review";


let route = useRoute();
let router = useRouter();

let { reviewid } = route.params;

let review = ref({});

onMounted(() => {
    getReview();
})

let getReview = () => {
    console.log(reviewid + "번 리뷰 요청");
    detailReview(
        reviewid,
        ({ data }) => {
            console.log(data);
            review.value = data;
        },
        (error) => {
            console.log(error);
        }
    );
};

function onDeleteReview() {
    if (window.confirm("정말 삭제하시겠습니까?")) {
        console.log(reviewid + "번 리뷰 삭제 요청");
        deleteReview(
            reviewid,
            (response) => {
                if (response.status == 204) router.replace({ name: "review-list" });
            },
            (error) => {
                console.log(error);
            }
        );
    }
}

let favoriteIcon = computed(() => {
    console.log(review.value.isCheckLike);
    return review.value.isCheckLike ? favoriteFill : favorite;
})

function favoriteClick() {
    review.value.isCheckLike = !review.value.isCheckLike;
}

</script>

<template>
    <div>
        상세 게시판이야
    </div>
    <div class="container">
        <div class="link">
            <RouterLink :to="{ name: 'review-list' }">Back to Posts</RouterLink>
            <span class="user-link">
                <RouterLink :to="{ name: 'review-modify' }">modify</RouterLink>
                <a :to="{ name: 'review-list' }" @click="onDeleteReview">delete</a>
            </span>
        </div>

        <!-- 게시판 -->
        <div class="review">
            <div class="review-content">
                <h2 class="review-title">{{ review.title }}</h2>
                <div class="top-info">
                    <div class="review-profile">
                        <img :src="review.profileImageUrl" class="profile-image">
                        <span class="username">{{ review.nickname }}</span>
                        <img :src="location" class="location-icon">
                        <span class="location-text">{{ review.attractionName }}</span>
                        <span class="review-date">{{ review.createDate }}</span>
                    </div>
                    <img :src="favoriteIcon" class="favorite-icon" @click="favoriteClick">
                </div>
                <img :src="review.reviewImageUrl" class="review-image">
                <p class="review-description">{{ review.content }}</p>
                <div class="review-info">
                    <span>Likes: {{ review.likes }}</span>
                    <span>Comments: {{ review.comments }}</span>
                    <span>Views: {{ review.hits }}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.container {
    margin: 0 auto;
    width: 80%;
    height: 100%;
    padding: 20px;
}

.link {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;
    text-align: right;
    padding: 0 10px;
}

.user-link>a {
    margin-right: 20px;
    cursor: pointer;
}

.review {
    background-color: #F1F1F6;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.review-content {
    text-align: center;
    padding: 20px;
}

.review-title {
    margin: 20px;
}

.review-profile {
    display: flex;
    text-align: center;
    margin-left: 10px;
}

.profile-image {
    width: 30px;
    height: 30px;
    border-radius: 50%;
    margin-right: 10px;
}

.username {
    margin-right: 30px;
}

.top-info {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 10px;
    margin: 0 10%;
}

.review-date {
    margin-left: 20px;
}

.location-icon {
    width: 30px;
    height: 30px;
    margin-right: 5px;
}

.location-text {
    font-size: 0.9rem;
}

.favorite-icon {
    margin-right: 20px;
    margin-bottom: 20px;
}

.review-title {
    font-size: 2rem;
    font-weight: bold;
    margin-bottom: 10px;
}

.review-description {
    font-size: 1.2rem;
    margin-bottom: 20px;
}

.review-image {
    width: 80%;
    height: auto;
    margin-bottom: 20px;
    border-radius: 5px;
}

.review-info {
    display: flex;
    justify-content: space-between;
    font-size: 0.9rem;
    color: gray;
    margin: 0 10%;
}
</style>