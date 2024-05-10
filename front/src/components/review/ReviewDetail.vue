<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

import imageSrc from '@/assets/image1.jpg';
import favorite from '@/assets/favorite.svg';
import favoriteFill from '@/assets/favorite_fill.svg';
import location from '@/assets/location.svg';


const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { reviewid } = route.params;

const review = ref({
    id: 1, title: "제목1", nickname: "유저", location: " 서울", createdDate: "2024-05-10 12:11:23",
    content: "내일은 즐거운 주말이야 조금만 더 힘내자", likes: 30, comments: 30, hits: 20,
});
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
                <RouterLink :to="{ name: 'review-list' }">delete</RouterLink>
            </span>
        </div>

        <!-- 게시판 -->
        <div class="review">
            <div class="review-content">
                <h2 class="review-title">{{ review.title }}</h2>
                <div class="top-info">
                    <div class="review-profile">
                        <img :src="imageSrc" class="profile-image">
                        <span class="username">{{ review.nickname }}</span>
                        <img :src="location" class="location-icon">
                        <span class="location-text">{{ review.location }}</span>
                        <span class="review-date">{{ review.createdDate }}</span>
                    </div>
                    <img :src="favorite" class="favorite-icon">
                </div>
                <img class="review-image" :src="imageSrc">
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