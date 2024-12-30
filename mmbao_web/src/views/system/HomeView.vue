<template>
  <el-container>
    <el-header>
      <el-row justify="space-between">
        <el-col :span="6"><img style="margin-left: -20px;" src="@/assets/imgs/mmbao.png"></el-col>
        <el-col :span="6">买卖宝管理系统</el-col>
        <el-col :span="6" class="textcenter">
          <el-button-group class="ml-4">
            <el-button type="primary" :icon="User">{{ userModel.user.userName}}</el-button>
            <el-button type="primary" :icon="Histogram" />
            <el-button type="primary" :icon="SwitchButton" @click="logout()" />
          </el-button-group>
        </el-col>
      </el-row>
    </el-header>
    <el-container>
      <el-aside width="258px">
        <!--router 开启菜单路由模式
                         把菜单跳转的url 直接写入index
                         点击时会跳转
              -->
        <el-menu router active-text-color="#ffd04b" background-color="#545c64" text-color="#fff" default-active="2">
          <el-sub-menu v-for="menu in userModel.user.menusList" :index="menu.id + ''">
            <template #title>
              <el-icon>
                <component :is="menu.menuFlag" />
              </el-icon>
              <span>{{ menu.title }}</span>
            </template>
            <el-menu-item v-for="subm in menu.childList" :index="subm.path">
              <el-icon>
                <component :is="subm.menuFlag" />
              </el-icon>
              <span>{{ subm.title }}</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>




</template>

<script setup>
import { ref, reactive, onMounted, getCurrentInstance } from 'vue'
import { User, Histogram, SwitchButton, Message, Setting } from '@element-plus/icons-vue'
import router from '/src/router/index'
import userStore from "/src/stores/user.js"
let userModel = userStore();


onMounted(() => {
  //从后端取出用户菜单数据 初始化页面使用
  // console.log(userModel.user);
  // console.log(userModel.token);
})

let logout= () =>{
  userModel.user = ref({ })
  userModel.token = ref()
  router.push("/login")
}

</script>

<style scoped>
.el-container {
  background-color: lightgray;
  height: 100vh;
}

.el-header {
  height: 100px;
  line-height: 100px;
  font-size: 35px;
}

.textcenter {
  text-align: center;
}
</style>