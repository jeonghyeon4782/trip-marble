<script setup>
import { oauthMember } from '@/api/auth';
import { ref } from 'vue';
import { useRoute, useRouter } from "vue-router";

const urlParams = new URLSearchParams(window.location.search);
const code = urlParams.get('code');
console.log('인증코드: ', code);

const route = useRoute();
const router = useRouter();

const { oauth_server_type } = route.params;

const requestData = ref({
    code: code,
    oauthServerType: oauth_server_type
});

oauthMember(
    requestData.value,
    (response) => {
        if (response.status == 200) {
            console.log(response.data.data.isFirst);
            if (response.data.data.isFirst) {
                router.replace({
                    name: 'signup',
                    query: {
                        email: response.data.data.email,
                        oauthServerType: response.data.data.oauthServerType
                    }
                });
            } else {
                localStorage.setItem('isLogin', true);
                // router.replace({ name: 'main' });
                location.reload();
                location.href = '/';
            }
        }
    },
    (error) =>{
        console.log(error)
    }
)
</script>

<template>
</template>

<style scoped></style>