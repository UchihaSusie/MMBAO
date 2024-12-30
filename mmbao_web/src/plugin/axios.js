import axios from 'axios';
import qs from 'qs';
import router from '/src/router/index'

axios.defaults.baseURL = 'http://192.168.0.158:8081/mmbao'  //接口路径
//post请求头
axios.defaults.headers.post["Content-Type"] ="application/x-www-form-urlencoded;charset=UTF-8";
//axios发送跨域ajax请求时 携带cookie
axios.defaults.withCredentials = true
//设置超时
axios.defaults.timeout = 10000;

// //请求
// axios.interceptors.request.use(
//     config => {
//         console.log(config)
//         return config;
//     },
//     error => {
//         return Promise.reject(error);
//     }
// );
// //响应
// axios.interceptors.response.use(
//     response => {
//         //服务器 给前端的内容
//         if (response.status == 200) {
//             return Promise.resolve(response);
//         } else {
//             return Promise.reject(response);
//         }
//     },
//
//     error => {
//         alert(`异常请求：${JSON.stringify(error.message)}`)
//     }
// );

import userStore  from "/src/stores/user.js"

//请求
axios.interceptors.request.use(
    config => {
        //从本地获取token信息
        //从 localStorage中获取token字符串
        let token = userStore().token;
        //token 传到 服务器
        if (token !=null && token !== ''){
            config.headers.user_token = token;
        }
        // console.log(userStore().token)
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);
//响应
axios.interceptors.response.use(
    response => {
        let token = response.headers.user_token;
        //获取到了服务器响应的token数据,保存到localStorage中
        // console.log(token)
        if (token!=null && token !== ""){
            userStore().token = token;
        }
        //服务器 给前端的内容
        if (response.status == 200) {
            return Promise.resolve(response);
        } else {
            return Promise.reject(response);
        }
    },

    error => {
        alert(`异常请求：${JSON.stringify(error.message)}`)
    }
);

export default {
    //post方法
    post(url, data) {
        return new Promise((resolve, reject) => {
            axios({
                method: 'post',
                url,
                data: qs.stringify(data),//json数据 转换成  name=xiaoming&age=10&address=xxx
            }).then(res => {
                resolve(res.data)
            }).catch(err => {
                reject(err)
            });
        })
    },
    post2(url, data) {
        return new Promise((resolve, reject) => {
            axios({
                method: 'post',
                url,
                data: qs.stringify(data,{"indices":false}),
            }).then(res => {
                resolve(res.data)
            }).catch(err => {
                reject(err)
            });
        })
    },
    //get方法
    get(url, data) {
        return new Promise((resolve, reject) => {
            axios({
                method: 'get',
                url,
                params: data,
            }).then(res => {
                resolve(res.data)
            }).catch(err => {
                reject(err)
            })
        })
    },
    //传json参数
    postJson(url,data){
        // console.log(data)
        return new Promise((resolve, reject) => {
            axios({
                method: 'post',
                url,
                data: data,
                headers: {
                    'Content-Type': 'application/json',
                }
            }).then(res => {
                resolve(res.data)
            }).catch(err => {
                reject(err)
            })
        })
    },
    delete(url, data) {
        return new Promise((resolve, reject) => {
            axios({
                method: 'delete',
                url,
                params: data,
            }).then(res => {
                resolve(res.data)
            }).catch(err => {
                reject(err)
            })
        })
    }
};