import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getSidoList(success, fail) {
    local.get(`/api/sido`).then(success).catch(fail);
}

function drawGoldCard(requestData, success, fail){
    local.post(`/api/board/gold-card`, JSON.stringify(requestData)).then(success).catch(fail);
}

export {
    getSidoList
};
