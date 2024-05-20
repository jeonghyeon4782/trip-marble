<script setup>
import horseImage from "@/assets/horse.gif";
import face from "@/assets/face.png";
import { ref, watch, defineProps } from "vue";

const props = defineProps({
  now: Number,
});
const rotateXVal = ref(0);
const rotateYVal = ref(0);
const rotateZVal = ref(0);
const faceMarginLeft = ref(90);
const faceMarginBottom = ref(10);
const faceMarginTop = ref(0);

watch(
  () => props.now,
  (newValue) => {
    if (0 <= newValue && newValue < 7) {
      rotateXVal.value = 0;
      rotateYVal.value = 180;
      rotateZVal.value = 0;
      faceMarginLeft.value = 31;
      faceMarginBottom.value = 3;
      faceMarginTop.value = 0;
    } else if (7 <= newValue && newValue < 14) {
      rotateXVal.value = 0;
      rotateYVal.value = 180;
      rotateZVal.value = -90;
      faceMarginLeft.value = 104;
      faceMarginBottom.value = 100;
      faceMarginTop.value = 0;
    } else if (14 <= newValue && newValue < 21) {
      rotateXVal.value = 0;
      rotateYVal.value = 0;
      rotateZVal.value = 0;
      faceMarginLeft.value = 95;
      faceMarginBottom.value = 98;
      faceMarginTop.value = 0;
    } else {
      rotateXVal.value = 0;
      rotateYVal.value = 180;
      rotateZVal.value = 90;
      faceMarginLeft.value = 15;
      faceMarginBottom.value = 0;
      faceMarginTop.value = 85;
    }
  }
);
</script>

<template>
  <div class="horse-container">
    <img
      class="horse"
      :src="horseImage"
      :style="{
        transform: `rotateX(${rotateXVal}deg) rotateY(${rotateYVal}deg) rotateZ(${rotateZVal}deg)`,
      }"
      alt="Horse Image"
    />
    <img
      class="face"
      :src="face"
      :style="{
        transform: `rotateX(${rotateXVal}deg) rotateY(${rotateYVal}deg) rotateZ(${rotateZVal}deg)`,
        marginLeft: `${faceMarginLeft}px`,
        marginBottom: `${faceMarginBottom}px`,
        marginTop: `${faceMarginTop}px`,
      }"
      alt="Face Image"
    />
  </div>
</template>

<style scoped>
.horse-container {
  width: 180px;
  height: 150px;
  position: relative;
}

img {
  width: 180px;
  height: 150px;
  transition: transform 0.5s ease;
  position: absolute;
}

.horse {
  z-index: 1; 
}

.face {
  width: 60px;
  height: 60px;
  z-index: 2; 
  transition: transform 0.5s ease;
}
</style>
