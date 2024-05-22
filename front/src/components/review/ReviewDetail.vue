<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { deleteReview, detailReview, createLike, deleteLike } from "@/api/review";

import favorite from '@/assets/favorite.svg';
import favoriteFill from '@/assets/favorite_fill.svg';
import location from '@/assets/location.svg';
import defaultImage from '@/assets/defaultImage.jpg';

import CommentListItem from '@/components/review/item/CommentListItem.vue';


const route = useRoute();
const router = useRouter();

const { reviewid } = route.params;

const review = ref({});

const comments = ref({});
const isLogin = ref({});

onMounted(() => {
    if (localStorage.getItem('isLogin') == "true") {
        isLogin.value = true;
    } else {
        isLogin.value = false;
    }
    getReview();
})

const getReview = () => {
    console.log(reviewid + "번 리뷰 요청");
    detailReview(
        reviewid,
        ({ data }) => {
            console.log(data);
            review.value = data.data;
            console.log(review);
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

const favoriteIcon = computed(() => {
    console.log(review.value.isCheckLike);
    return review.value.isCheckLike ? favoriteFill : favorite;
})

function favoriteClick() {

    if (review.value.isCheckLike) {
        subLikes()
    } else {
        addLikes()
    }

    console.log(review.value.isCheckLike, review.value.likes)
    review.value.isCheckLike = !review.value.isCheckLike;
}

function addLikes() {
    createLike(
        reviewid,
        (response) => {
            console.log(response)
            if (response.status == 201) {
                review.value.likes++;
            }
        },
        (error) => {
            console.log(error);
        }
    );
}

function subLikes() {
    deleteLike(
        reviewid,
        (response) => {
            if (response.status == 204) {
                review.value.likes--;
            }
        },
        (error) => {
            console.log(error);
        }
    );

}

function updateComments(newCount) {
    comments.value = newCount;
}
</script>

<template>
    <div class="container">
        <div class="link">
            <RouterLink :to="{ name: 'review-list' }">Back to Posts</RouterLink>
            <span class="user-link">
                <RouterLink :to="{ name: 'review-modify' }" v-if="review.isWriteByMe">modify</RouterLink>
                <a @click="onDeleteReview" v-if="review.isWriteByMe">delete</a>
            </span>
        </div>

        <!-- 게시판 -->
        <div class="review">
            <div class="review-content">
                <h2 class="review-title">{{ review.title }}</h2>
                <div class="top-info">
                    <div class="review-profile">
                        <img :src="review.profileImageUrl ? review.profileImageUrl : defaultImage"
                            class="profile-image">
                        <span class="username">{{ review.nickname }}</span>
                        <a>
                            <img :src="location" class="location-icon">
                            <span class="location-text">{{ review.attractionName }}</span>
                        </a>
                        <span class="review-date">{{ review.createDate }}</span>
                    </div>
                    <img v-if="!review.isWriteByMe && isLogin.value" :src="favoriteIcon" class="favorite-icon"
                        @click="favoriteClick">
                </div>
                <img :src="review.reviewImageUrl" v-if="review.reviewImageUrl" class="review-image">
                <p class="review-description">{{ review.content }}</p>
                <div class="review-info">
                    <span>Likes: {{ review.likes }}</span>
                    <span>Comments: {{ comments }}</span>
                    <span>Views: {{ review.hits }}</span>
                </div>
            </div>
        </div>
        <CommentListItem @update-comment-count="updateComments" :reviewId="reviewid" />
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
    /* text-align: center; */
    align-items: center;
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
    margin: 20px 0;
    height: 500px;
    overflow-y: auto;
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