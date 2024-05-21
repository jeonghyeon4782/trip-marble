<script setup>
import { defineProps, defineEmits } from 'vue';
import defaultImage from '@/assets/defaultImage.jpg'

const props = defineProps({ boardLog: Object });

const imageAlt = "Post Image";

</script>

<template>
    <!-- <RouterLink :to="{ name: 'review-view', params: { reviewid: review.reviewId } }" @click="incrementHits"> -->
    <div class="item" >
      {{ boardLog.isWrite }}
      <img :src="boardLog.imageUrl ? boardLog.imageUrl : defaultImage" :alt="imageAlt">
      <div class="item-content">
        <div class="item-profile">
          <span>{{ boardLog.sidoName }}</span>
        </div>
        <h3 class="item-title">{{ boardLog.name }}</h3>
        <div class="item-info">
          <span>Date: {{ boardLog.createDate }}</span>
        </div>
      </div>
    </div>
  <!-- </RouterLink> -->
</template>

<style scoped>

.item {
  background-color: #F1F1F6;
  border-radius: 10px;
  overflow: hidden;
  height: 450px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.item img {
  width: 100%;
  height: 70%;
  object-fit: cover;
}

.item-content {
  padding: 20px;
}

.item-profile {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.item-profile img {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
}

.item-title {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.item-info {
  display: flex;
  justify-content: space-between;
  font-size: 0.9rem;
  color: gray;
}
</style>