import axios from 'axios'
import router from "@/router";

const request = axios.create({
    baseURL: "/api",
    timeout: 5000
})

// 请求白名单，如果请求在白名单里面，将不会被拦截校验权限
const whiteUrls = ["/login", '/register']

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    // 取出sessionStorage里面缓存的用户信息
    let userJson = sessionStorage.getItem("user")

    if (!whiteUrls.includes(config.url)) {  // 校验请求白名单
        console.log("校验请求白名单")
        if(!userJson) {
            router.push("/login")
        } else {
            let user = JSON.parse(userJson);
            config.headers['token'] = user.token;  // 设置请求头
        }
    }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        console.log(response);
        let res = response.data;
        console.log(res)
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            console.log("isfile")
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        // 验证token
        if (res.code === '401') {
            console.error("token过期，重新登录")
            router.push("/login")
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request

