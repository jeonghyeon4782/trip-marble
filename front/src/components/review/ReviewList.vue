<script setup>
import { onMounted, ref } from 'vue';
import { listReview } from "@/api/review.js";


import ReviewListItem from '@/components/review/item/ReviewListItem.vue';

const reviews = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
    pgno: currentPage.value,
    spp: VITE_ARTICLE_LIST_SIZE,
    key: "",
    word: "",
});

onMounted(() => {
    getReviewList();
})

const getReviewList = () => {
    console.log("서버에 review 목록 요청", param.value);
    listReview(
        param.value,
        ({ data }) => {
            console.log(data);
            reviews.value = data.reviews;
            currentPage.value = data.currentPage;
            totalPage.value = data.totalPageCount;
        },
        (error) => {
            console.log(error);
        }
    );
};

</script>

<template>
    <div>
        리뷰 게시판이야
    </div>
    <div class="review-grid">
        <ReviewListItem v-for="review in reviews" :key="review.reviewId" :review="review"></ReviewListItem>
    </div>
</template>

<style scoped>
.review-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
}
</style>