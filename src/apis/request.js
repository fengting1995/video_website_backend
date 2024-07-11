import axios from "axios";
import {toast,aes_decrypt} from "@/utils/utils";
import {cleanToken,getUserInfo} from "@/utils/authUtils";

const request = axios.create({
    baseURL: "http://112.74.186.207:8081/api", // url = base url + request url
    //baseURL: "http://127.0.0.1:8081/api", // url = base url + request url
    timeout: 40000 // request timeout
})


request.interceptors.request.use(
    config => {
        let userInfo = getUserInfo()
        if (userInfo) {
            config.headers['token'] = getUserInfo().token
        }
        return config
    },
    error => {
        // do something with request error
        console.log(error) // for debug
        return Promise.reject(error)
    }
)

// response interceptor
request.interceptors.response.use(
    response => {
        //
        let res = response.data
        if (response.headers['encrypt-key']){
            const encrypt_text = res.slice(0, -32)
            const iv = res.slice(-32, -16)
            const key = res.slice(-16)
            const responseString = aes_decrypt(encrypt_text, key, iv)
            if (!responseString){
                toast('系统错误')
                return Promise.reject(new Error('系统错误'))
            }
            res = JSON.parse(responseString)
        }

        // if the custom code is not 20000, it is judged as an error.
        if (res.code !== 200){
            if (res.code === 402){
                cleanToken()
            }
            toast(res.msg)
            return Promise.reject(new Error(res.msg))
        } else {
            return res.data
        }
    },
    error => {
        toast('err' + error)
        return Promise.reject(error)
    }
)

export default request