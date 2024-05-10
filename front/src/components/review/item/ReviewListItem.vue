<script setup>
import imageSrc from '@/assets/image1.jpg';
defineProps({ review: Object });
</script>

<template>
  <RouterLink :to="{ name: 'review-view', params: { reviewid: review.id } }">
    <div class="item">
      <img :src="imageSrc" :alt="imageAlt">
      <div class="item-content">
        <div class="item-profile">
          <img :src="imageSrc" :alt="imageAlt">
          <span>Username</span>
        </div>
        <h3 class="item-title">Post Title</h3>
        <div class="item-info">
          <span>Likes: 100</span>
          <span>Comments: 20</span>
          <span>Views: 500</span>
        </div>
      </div>
    </div>
  </RouterLink>
</template>

<style scoped>
.item {
  background-color: #F1F1F6;
  border-radius: 10px;
  overflow: hidden;
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