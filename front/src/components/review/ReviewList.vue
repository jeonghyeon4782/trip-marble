<script setup>
import { onMounted, ref } from 'vue';
import { listReview,updateHits } from "@/api/review.js";


import ReviewListItem from '@/components/review/item/ReviewListItem.vue';

const reviews = ref([]);
const currentPage = ref(0);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
    pageno: currentPage.value,
    pagesize: VITE_ARTICLE_LIST_SIZE,
    key: "",
    word: "",
    order: ""
});

onMounted(() => {
    getReviewList();
})

const getReviewList = () => {
    console.log("서버에 review 목록 요청", param.value);
    listReview(
        param.value,
        ({ data }) => {
            console.log(data.msg)
            console.log(data);
            console.log(data.data.reviewInfos);
            reviews.value = data.data.reviewInfos;
            currentPage.value = data.data.page;
            totalPage.value = data.data.total;
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
                review.value.hits++;
            }
        },
        (error) => {
            console.log(error);
        }
    );
}

</script>

<template>
    <div>
        리뷰 게시판이야
    </div>
    <div>
        <RouterLink :to="{ name: 'review-write' }">write</RouterLink>
    </div>
    <div class="review-grid">
        <ReviewListItem v-for="review in reviews" :key="review.reviewId" :review="review" @increment-hits="incrementHits">
        </ReviewListItem>
    </div>
</template>

<style scoped>
.review-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
}
</style>