import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listReview(param, success, fail) {
  local.get(`api/review`, { params: param }).then(success).catch(fail);
}

function detailReview(reviewid, success, fail) {
  local.get(`api/review/${reviewid}`).then(success).catch(fail);
}

function registReview(review, success, fail) {
  console.log("reviewjs review", review);
  local.post(`api/review`, JSON.stringify(reivew)).then(success).catch(fail);
}

function getModifyReview(reviewid, success, fail) {
  local.get(`api/review/${reviewid}`).then(success).catch(fail);
}

function modifyReview(review, reviewid, success, fail) {
  local.put(`api/review/${reviewid}`, JSON.stringify(review)).then(success).catch(fail);
}

function deleteReview(reviewid, success, fail) {
  local.delete(`api/review/${reviewid}`).then(success).catch(fail);
}

function createLike(reviewid, success, fail) {
  local.post(`api/like/${reviewid}`).then(success).catch(fail);
}

function deleteLike(reviewid, success, fail) {
  local.delete(`api/like/${reviewid}`).then(success).catch(fail);
}

function updateHits(reviewid, success, fail) {
  local.patch(`api/review/${reviewid}`).then(success).catch(fail);
}

export {
  listReview,
  detailReview,
  registReview,
  getModifyReview,
  modifyReview,
  deleteReview,
  createLike,
  deleteLike,
  updateHits
};
