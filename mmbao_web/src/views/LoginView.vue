<template>
  <div class="profile-authentication-area">
    <div class="container">
      <div class="row">
        <div class="col-lg-6 col-md-12">
          <div class="profile-authentication-image">
            <div class="content-image">
              <div class="logo">
              </div>
              <div class="vector-image">
                <a href="../../index.html">
                  <img src="/src/assets/images/view-profile-bg.jpg" alt="image">
                </a>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-6 col-md-12">
          <div class="login-form">
            <h2>登录</h2>
            <form>
              <div class="form-group">

                <input type="text" class="form-control" placeholder="请输入用户名" v-model="form.username" >
              </div>
              <div class="form-group">

                <input type="password" class="form-control" placeholder="请输入密码" v-model="form.password">
              </div>
              <button type="button" class="default-btn" @click="doLogin">登录</button>
            </form>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
  import {ref} from 'vue';
  import axios from '/src/plugin/axios.js'
  import {ElMessage} from 'element-plus'
  import router from '/src/router/index'
  import userStore  from "/src/stores/user.js"
  let userModel = userStore();

  let form = ref({});

  let doLogin = ()=>{
    let param = {
      "username":form.value.username,
      "password":form.value.password
    }
    //访问 java接口
    axios.post("user/login",param).then(response =>{
      if (response != null && response.code === 200){
        //登录成功
        ElMessage({
          message:'登录成功',
          type:'success'
        })
        //想办法,把用户信息,传递到其他页面,SessionStorage
        userModel.user = response.data;
        //页面跳转到 首页
        router.push("/")
      }
    })
  }

</script>

<style scoped>

</style>