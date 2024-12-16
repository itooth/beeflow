import axios from "axios";
import { getToken } from "./auth";
import { Message, Modal } from "@arco-design/web-vue";
import { ref } from "vue";

// create an axios instance
const service = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_URL, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 60000, // request timeout
});

let isAuthError = ref(false);

// request interceptor
service.interceptors.request.use(
  (config) => {
    // do something before request is sent
    const token = getToken();
    if (token) {
      if (!config.headers) {
        config.headers = {};
      }
      // config.headers.Authorization = `Bearer ${token}`;
      config.headers.Authorization = `${token}`;
    }
    return config;
  },
  (error) => {
    // do something with request error
    console.log("request", error); // for debug
    return Promise.reject(error);
  }
);

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  (response) => {
    const res = response.data;
    console.log('API Response:', res);

    // Check success flag instead of code
    if (!res.success) {
      Message.error({
        content: res.message || "请求出错",
        duration: 5 * 1000,
      });
      return Promise.reject(new Error(res.message || "请求出错"));
    } else {
      return res;
    }
  },
  (error) => {
    // console.log("err", error); // for debug
    Message.error({
      content: error.msg || "请求出错",
      duration: 5 * 1000,
    });
    return Promise.reject(error);
  }
);

export default service;
