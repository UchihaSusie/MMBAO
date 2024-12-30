import { ref } from 'vue'
import { defineStore } from 'pinia'
export default defineStore('userStore', () => {
    const user = ref({
        // uid: -1,
    })
    const token = ref("")
    const activitygoods = ref([])
    return { 
        user,token,activitygoods
    }
}, {
    persist: {
        storage: sessionStorage,//存入SessionStorage中
        paths: ['user','token','activitygoods']//把哪个属性,存入SessionStorage
        //paths: ['user','其他属性名']  多属性需要存储
    }
})