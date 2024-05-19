<script setup>
import defaultImage from '@/assets/defaultImage.jpg'
import { ref, defineProps, onMounted, watch,computed } from 'vue';
import { useRoute, useRouter } from "vue-router";
import moment from 'moment';
import { listComment, registComment, modifyComment, deleteComment } from "@/api/comment.js";

import PageNavigation from '@/components/common/PageNavigation.vue'

const props = defineProps({ reviewId: Number });
const emit = defineEmits(['update-comment-count'])

const route = useRoute();
const router = useRouter();

const comments = ref({});
const comment = ref({
  reviewId: props.reviewId,
  content: ''
});

const currentPage = ref(1);
const totalPage = ref(0);
const total = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pageno: currentPage.value,
  pagesize: VITE_ARTICLE_LIST_SIZE
});


onMounted(() => {
  getCommentList();
})


const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 요청");
  currentPage.value = val;
  param.value.pageno = val;
  getCommentList();
};


function getCommentList() {
  console.log("서버에 comment 목록 요청", props.reviewId, param.value);
  listComment(
    props.reviewId,
    param.value,
    ({ data }) => {
      console.log(data.msg)
      console.log(data);
      console.log(data.data.comments);
      comments.value = data.data.comments;
      currentPage.value = data.data.page;
      totalPage.value = data.data.pageTotal;
      total.value = data.data.total;

      updateCommentCount();
    },
    (error) => {
      console.log(error);
    }
  );
};

function writeComment() {
  console.log("댓글 등록 요청", comment.value)
  registComment(
    comment.value,
    (response) => {
      let msg = "댓글등록 처리시 문제 발생했습니다."
      console.log(response)
      if (response.status == 201) msg = "댓글 등록이 완료되었습니다."
      alert(msg)
    },
    (error) => console.log(error)
  )
}

const contentErrMsg = ref("")

watch(
  () => comment.value.content,
  (value) => {
    let len = value.length
    if (len == 0 || len > 500) {
      contentErrMsg.value = "댓글을 작성해 주세요"
    } else contentErrMsg.value = ""
  },
  { immediate: true }
)

function onSubmit() {
  if (contentErrMsg.value) {
    alert(contentErrMsg.value)
  } else {
    writeComment();
  }
}

function updateCommentCount() {
  console.log(total.value);
  emit('update-comment-count', total.value);
}

function formatDate(date){
  return moment(comment.value.createDate).format('YYYY-MM-DD HH:mm:ss');
};

</script>

<template>
  <section>
    <!-- 댓글 섹션 -->
    <div class="comments-section">
      <h2>댓글 댓글</h2>
      <!-- 댓글 목록 -->
      <div class="comments">
        <div class="comment" v-for="comment in comments" :key="comment.commentId">
          <div class="comment-info">
            <div class="user-info">
              <img :src="comment.imageUrl ? comment.imageUrl : defaultImage" class="profile-image">
              <span>{{ comment.nickname }}</span>
            </div>
            <div class="comment-time"> {{ formatDate(comment.createDate) }}</div>
          </div>
          <div class="comment-content">{{ comment.content }}</div>
          <div class="comment-actions">
            <a href="#" @click.prevent="editComment(comment.id)">수정</a> |
            <a href="#" @click.prevent="deleteComment(comment.id)">삭제</a>
          </div>
        </div>
      </div>

      <div>
        <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></PageNavigation>
      </div>

      <!-- 댓글 작성 폼 -->
      <form class="comment-form" @submit="onSubmit">
        <textarea class="comment-input" placeholder="댓글을 입력하세요" v-model="comment.content"></textarea>
        <button type="submit" class="comment-button">댓글 작성</button>
      </form>
    </div>
  </section>
</template>

<style scoped>
section {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.container {
  /* 폼 스타일 생략 */
}

.login-link {
  margin-top: 20px;
}

.comments-section {
  margin-top: 40px;
  width: 100%;
}

.comments-section h2 {
  text-align: center;
}

.comments {
  margin-top: 20px;
}

.comment {
  border-bottom: 1px solid #ccc;
  padding-bottom: 20px;
  margin-bottom: 20px;
}

.comment-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
}

.user-info {
  font-weight: bold;
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.user-info img {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
}

.comment-time {
  color: #666;
}

.comment-content {
  margin-top: 5px;
}

.comment-actions{

}

.comment-form {
  margin-top: 30px;
}

.comment-input {
  width: 100%;
  height: 100px;
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  resize: none;
}

.comment-button {
  padding: 10px 20px;
  background-color: #E1CCEC;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
}

.comment-button:hover {
  background-color: #E3EFFA;
}
</style>