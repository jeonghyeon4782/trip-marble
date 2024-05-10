<script setup>
import { ref } from 'vue';

import ReviewListItem from '@/components/review/item/ReviewListItem.vue';

const reviews = ref([
{ id: 1, title: "제목1", nickname:"유저", location:" 서울", createDate:"2024-05-10 12:11:23",
content:"내일은 즐거운 주말이야 조금만 더 힘내자", likes:30, comments:30, hits:20,
 },
 { id: 2, title: "제목2", nickname:"유저2", location:" 서울2", createDate:"2024-05-10 12:11:23",
content:"내일은 즐거운 주말이야 조금만 더 힘내자", likes:30, comments:30, hits:20,
 },
 { id: 3, title: "제목3", nickname:"유저3", location:" 서울3", createDate:"2024-05-10 12:11:23",
content:"내일은 즐거운 주말이야 조금만 더 힘내자", likes:30, comments:30, hits:20,
 },
 { id: 4, title: "제목4", nickname:"유저4", location:" 서울", createDate:"2024-05-10 12:11:23",
content:"내일은 즐거운 주말이야 조금만 더 힘내자", likes:30, comments:30, hits:20,
 },
 { id: 5, title: "제목5", nickname:"유저5", location:" 서울", createDate:"2024-05-10 12:11:23",
content:"내일은 즐거운 주말이야 조금만 더 힘내자", likes:30, comments:30, hits:20,
 },
 { id: 6, title: "제목6", nickname:"유저6", location:" 서울", createDate:"2024-05-10 12:11:23",
content:"내일은 즐거운 주말이야 조금만 더 힘내자", likes:30, comments:30, hits:20,
 },
 { id: 7, title: "제목7", nickname:"유저7", location:" 서울", createDate:"2024-05-10 12:11:23",
content:"내일은 즐거운 주말이야 조금만 더 힘내자", likes:30, comments:30, hits:20,
 },
])
</script>

<template>
    <div>
        리뷰 게시판이야
    </div>
    <div class="review-grid">
        <ReviewListItem v-for="review in reviews" :key="review.reviewid" :review="review"></ReviewListItem>
    </div>
</template>

<style scoped>
.review-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
}
</style>