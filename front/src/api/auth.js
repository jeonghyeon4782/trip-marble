import { localAxios } from "@/util/http-commons";

const local = localAxios();

function loginMember(member, success, fail) {
    console.log("authjs member", member);
    local.post(`/auth/login`, JSON.stringify(member)).then(success).catch(fail);
}

function registMember(member, success, fail) {
    console.log("authjs member", member);
    local.post(`/auth/register`, JSON.stringify(member)).then(success).catch(fail);
}

function oauthMember(requestData, success, fail){
    console.log("authjs member", requestData);
    local.post('http://localhost:8080/auth/login/social', JSON.stringify(requestData)).then(success).catch(fail);
}

export {
    loginMember,
    registMember,
    oauthMember
};
