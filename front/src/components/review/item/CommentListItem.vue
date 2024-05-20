<script setup>
import defaultImage from '@/assets/defaultImage.jpg'
import { ref, defineProps, onMounted, watch, computed } from 'vue';
import moment from 'moment-timezone';
import { listComment, registComment, modifyComment, deleteComment } from "@/api/comment.js";

import PageNavigation from '@/components/common/PageNavigation.vue'

const props = defineProps({ reviewId: Number });
const emit = defineEmits(['update-comment-count'])

const isLogin = ref({});
const comments = ref({});
const inputComment = ref({
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

  if (localStorage.getItem('isLogin') == "true") {
    isLogin.value = true;
  } else {
    isLogin.value = false;
  }
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
      comments.value = data.data.comments.map(comment => {
        return {
          data: comment,
          isEditing: false,
          newContent: ''
        };
      });
      console.log("comment.value", comments.value);
      currentPage.value = data.data.page;
      totalPage.value = data.data.pageTotal;
      total.value = data.data.total;

      console.log("iswirtMe", comments.value.writeByMe);
      updateCommentCount();
    },
    (error) => {
      console.log(error);
    }
  );
};

function writeComment() {
  console.log("댓글 등록 요청", inputComment.value)
  registComment(
    inputComment.value,
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
  () => inputComment.value.content,
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

function formatDate(date) {
  const day = moment(date).utc();
  return day.format('YYYY-MM-DD HH:mm');
};

function switchEditing(comment) {
  console.log(comment.isEditing);
  comment.isEditing = !comment.isEditing;
  if (comment.isEditing) {
    comment.newContent = comment.data.content;
  }
};

function saveEdit(comment) {
  watch(
    () => comment.newContent,
    (value) => {
      let len = value.length
      if (len == 0 || len > 500) {
        contentErrMsg.value = "댓글을 작성해 주세요"
      } else contentErrMsg.value = ""
    },
    { immediate: true }
  )

  if (contentErrMsg.value) {
    alert(contentErrMsg.value)
  } else {
    inputComment.value.content = comment.newContent;
    console.log(inputComment.value.content, comment.newContent)
    editComment(comment.data.commentId);
  }
  location.reload();
}

function editComment(commentId) {
  console.log("댓글 수정 요청", inputComment.value)
  modifyComment(
    inputComment.value,
    commentId,
    (response) => {
      let msg = "댓글수정 처리시 문제 발생했습니다."
      console.log(response)
      if (response.status == 201) msg = "댓글 수정이 완료되었습니다."
      alert(msg)
    },
    (error) => console.log(error)
  )
}

function onDeleteComment(commentId) {
  // comment의 id로 삭제
  if (window.confirm("정말 삭제하시겠습니까?")) {
    console.log("댓글 삭제 요청", commentId)
    deleteComment(
      commentId,
      (response) => {
        let msg = "댓글삭제 처리시 문제 발생했습니다."
        console.log(response)
        if (response.status == 204) msg = "댓글 삭제가 완료되었습니다."
        alert(msg)
      },
      (error) => console.log(error)
    )
    location.reload();
  }
};

</script>

<template>
  <section>
    <!-- 댓글 섹션 -->
    <div class="comments-section">
      <h2>댓글 댓글</h2>
      <!-- 댓글 목록 -->
      <div class="comments">
        <div class="comment" v-for="comment in comments" :key="comment.data.commentId">
          <div class="comment-info">
            <div class="user-info">
              <img :src="comment.data.imageUrl ? comment.data.imageUrl : defaultImage" class="profile-image">
              <span>{{ comment.data.nickname }}</span>
            </div>
            <div class="comment-time"> {{ formatDate(comment.data.createDate) }}</div>
          </div>
          <div class="comment-content" v-if="!comment.isEditing">{{ comment.data.content }}</div>
          <div class="comment-edit" v-if="comment.isEditing">
            <textarea v-model="comment.newContent"></textarea>
            <a @click="saveEdit(comment)">저장</a> |
            <a @click.prevent="switchEditing(comment)">취소</a>
          </div>
          <div class="comment-actions" v-if="comment.data.writeByMe && !comment.isEditing">
            <a @click.prevent="switchEditing(comment)">수정</a> |
            <a @click="onDeleteComment(comment.data.commentId)">삭제</a>
          </div>
        </div>
      </div>

      <div v-if="total==0">
        <p>댓글이 비어있어</p>
      </div>
      <div v-else>
        <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange">
        </PageNavigation>
      </div>

      <!-- 댓글 작성 폼 -->
      <form v-if="isLogin" class="comment-form" @submit="onSubmit">
        <textarea class="comment-input" placeholder="댓글을 입력하세요" v-model="inputComment.content"></textarea>
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

.comment-edit textarea {
  width: 100%;
}

.comment-actions,
.comment-edit {
  text-align: right;
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