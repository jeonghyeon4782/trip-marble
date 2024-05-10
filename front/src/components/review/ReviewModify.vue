<script setup>

import ReviewFormItem from '@/components/review/item/ReviewFormItem.vue';
</script>

<template>
    <div>
        수정 게시판이야
    </div>
    <div class="container">
        <ReviewFormItem type="modify" />
    </div>
</template>

<style scoped>
.container {
    margin: 0 auto;
    width: 80%;
    height: 100%;
    padding: 20px;
}
</style>