<script setup>
defineProps({ review: Object });
</script>

<template>
  <RouterLink :to="{ name: 'review-view', params: { reviewid: review.reviewId } }">
    <div class="item">
      <img :src="review.reviewImageUrl" :alt="imageAlt">
      <div class="item-content">
        <div class="item-profile">
          <img :src="review.profileImageUrl" :alt="imageAlt">
          <span>{{ review.nickname }}</span>
        </div>
        <h3 class="item-title">{{ review.title }}</h3>
        <div class="item-info">
          <span>Likes: {{ review.likes }}</span>
          <span>Comments: {{ review.comments }}</span>
          <span>Views: {{ review.hits }}</span>
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