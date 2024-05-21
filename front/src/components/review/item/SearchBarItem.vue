<script setup>
import { ref, watch, defineEmits } from 'vue';

const emit = defineEmits(['search'])

const regions = ref([
  { id: '1', name: '서울' },
  { id: '2', name: '경기' },
  { id: '3', name: '세종' },
  { id: '4', name: '강원' },
  { id: '5', name: '충청' },
  { id: '6', name: '전북' },
  { id: '7', name: '전라' },
  { id: '8', name: '경상' },
  { id: '9', name: '제주' }
]);

const showFilterModal = ref(false);
const keyword = ref('');
const sortOrder = ref('latest');
const selectedRegions = ref([]);
const allCheckbox = ref(false);
let regionId = ref([]);

function filterModal() {
  showFilterModal.value = !showFilterModal.value;
};

function search() {
  setRegionId();
  console.log('검색어:', keyword.value);
  console.log('정렬 순:', sortOrder.value);
  console.log('선택 지역:', selectedRegions.value);

  emit('search', keyword.value, sortOrder.value, regionId.value);
};

const setSortOrder = (order) => {
  sortOrder.value = order;
  setRegionId()
  emit('search', keyword.value, sortOrder.value, regionId.value);
};

function setRegionId() {
  if (selectedRegions.value.length === regions.value.length) {
    regionId.value = [];
  } else {
    regionId.value = selectedRegions.value;
  }
}

const toggleAllRegions = () => {
  if (selectedRegions.value.length === regions.value.length) {
    selectedRegions.value = [];
  } else {
    selectedRegions.value = regions.value.map(region => region.id);
  }
};

watch(selectedRegions, () => {
  if (selectedRegions.value.length === regions.value.length) {
    allCheckbox.value = true;
  } else {
    allCheckbox.value = false;
  }
});

</script>

<template>
  <!-- 검색 바 -->
  <div class="search-bar">
    <!-- 필터 버튼 -->
    <button class="btn" @click="filterModal">{{ showFilterModal ? '닫기' : '필터' }}</button>
    <!-- 검색 입력 필드 -->
    <input type="text" placeholder="검색어를 입력하세요" v-model="keyword">
    <!-- 검색 버튼 -->
    <button class="btn" @click="search">검색</button>
  </div>

  <!-- 정렬 버튼 -->
  <div class="sort-buttons">
    <button class="btn" :class="{ active: sortOrder === 'latest' }" @click="setSortOrder('latest')">최신순</button>
    <button class="btn" :class="{ active: sortOrder === 'like' }" @click="setSortOrder('like')">좋아요 순</button>
  </div>

  <!-- 필터 모달 -->
  <div v-if="showFilterModal" class="filter-modal">
    <h4>지역 선택</h4>
    <div class="filter-group">
      <input type="checkbox" id="all" value="전체" v-model="allCheckbox" @change="toggleAllRegions">
      <label for="all">전체</label><br>
      <div v-for="region in regions" :key="region.id" class="checkbox-item">
        <input type="checkbox" :id="region.id" :value="region.id" v-model="selectedRegions">
        <label :for="region.id">{{ region.name }}</label><br>
      </div>
    </div>
  </div>
</template>

<style scoped>
.search-bar {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  height: 40px;
  width: 70%;
  margin: 20px auto;
}

.search-bar input[type="text"] {
  flex: 1;
  margin-right: 10px;
  height: 45px;
  font-size: 20px;
  padding-left: 10px;
}

.search-bar button {
  margin-right: 10px;
  width: 100px;
  height: 45px;
  background-color: #E1CCEC;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.sort-buttons {
  display: flex;
  justify-content: center;
  margin: 10px auto;
}

.sort-buttons .btn {
  margin-right: 10px;
  border-radius: 20px;
  padding: 10px 20px;
  border: 0px;
}

.sort-buttons .btn.active {
  background-color: #E1CCEC;
  color: #fff;
}

.btn:hover {
  background-color: #E3EFFA;
}

.filter-modal {
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.filter-group {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.filter-group label {
  margin-right: 10px;
}

.filter-group input[type="checkbox"],
.filter-group input[type="radio"] {
  margin-right: 5px;
}
</style>