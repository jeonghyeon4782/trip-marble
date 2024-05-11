import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listReview(param, success, fail) {
  local.get(`/review`, { params: param }).then(success).catch(fail);
}

function detailReview(reviewid, success, fail) {
  local.get(`/review/${reviewid}`).then(success).catch(fail);
}

function registReview(review, success, fail) {
  console.log("reviewjs review", review);
  local.post(`/review`, JSON.stringify(reivew)).then(success).catch(fail);
}

function getModifyReview(reviewid, success, fail) {
  local.get(`/review/modify/${reviewid}`).then(success).catch(fail);
}

function modifyReview(review, success, fail) {
  local.put(`/review`, JSON.stringify(review)).then(success).catch(fail);
}

function deleteReview(reviewid, success, fail) {
  local.delete(`/review/${reviewid}`).then(success).catch(fail);
}

export {
  listReview,
  detailReview,
  registReview,
  getModifyReview,
  modifyReview,
  deleteReview,
};
