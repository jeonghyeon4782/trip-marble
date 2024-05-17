import axios from "axios";

const { VITE_VUE_API_URL, VITE_POSTMAN_TEST_API_URL } = import.meta.env;

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8"
    },
    withCredentials: true
  });
  return instance;
}

export { localAxios };

