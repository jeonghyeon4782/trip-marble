<script setup>
import { RouterView } from 'vue-router'
import TheHeadingNavbar from '@/components/layout/TheHeadingNavbar.vue';

</script>

<template>
  <div class="main">
    <TheHeadingNavbar />
    <RouterView class="main-view"/>
  </div>
</template>

<style scoped>
.main{
  height: auto;
  width: 1200px;
}

.main-view{
  margin-top: 200px;
  height: auto;
}
</style>