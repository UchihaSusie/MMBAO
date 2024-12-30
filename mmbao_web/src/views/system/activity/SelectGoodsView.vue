<template>
    <hr />
    <h5> 活动管理 > 商品选择</h5>
    <el-form ref="queryFormRef" class="query-form" :inline="true" :model="queryForm">
        <el-form-item label="商品名称：" prop="gname">
            <el-input v-model="queryForm.gname" placeholder="goods name" />
        </el-form-item>
        <el-form-item label="商品型号：" prop="gmodel">
            <el-input v-model="queryForm.gmodel" placeholder="goods model" />
        </el-form-item>
        <el-form-item label="商品颜色：" prop="gcolor">
            <el-select v-model="queryForm.gcolor" placeholder="请选择">
                <el-option label="白色" :value="0" />
                <el-option label="黑色" :value="1" />
                <el-option label="绚丽紫" :value="2" />
                <el-option label="珍珠白" :value="3" />
            </el-select>
        </el-form-item>
        <el-form-item label="商品品牌：" prop="gbrand">
            <el-select v-model="queryForm.gbrand" placeholder="请选择">
                <el-option v-for="(item, key) in brandMap" :label="item" :value="key" />
            </el-select>
        </el-form-item>
        <el-form-item label="商品分类：" prop="gtype">
            <el-select v-model="queryForm.gtype" placeholder="请选择">
                <el-option v-for="(item, key) in typeMap" :label="item" :value="key" />
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onReset">重置</el-button>
        </el-form-item>
    </el-form>

    <el-table ref="tableRef" :data="tableList.tableData" style="width: 90%">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="序号" width="180" />
        <el-table-column prop="gname" label="商品名称" width="180" />
        <el-table-column prop="gmodel" label="商品型号" width="100" />
        <el-table-column prop="gcolor" label="商品颜色" width="100" />
        <el-table-column prop="gbrand" label="商品品牌" width="100" />
        <el-table-column prop="gtype" label="商品分类" width="100" />
        <el-table-column label="操作">
            <template #default="scope">
                <el-button type="warning" :disabled="scope.row.isSelected"
                    @click="comitSelect(scope.row)">选择</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="productPage.pageNum" v-model:page-size="productPage.pageSize"
        :page-sizes="[5, 10, 20]" layout="total, sizes, prev, pager, next, jumper" :total="productPage.pageTotal"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <el-button type="primary" @click="closeSelectGoods">关闭</el-button>

</template>


<script setup>
import { ref, reactive, onMounted, getCurrentInstance } from 'vue'

const { proxy } = getCurrentInstance()
import axios from '/src/plugin/axios.js'
import { ElMessage } from "element-plus";
import userStore from "/src/stores/user.js"
import router from '/src/router/index'

let userModel = userStore()

const isSelected = ref(false); 

// 确保activitygoods是一个数组
if (!Array.isArray(userModel.activitygoods)) {
    userModel.activitygoods = [];
}

const closeSelectGoods = () => {
    router.push("/activityapplication")
}
const comitSelect = (currentRow) => {
    console.log(currentRow);
    // 检查activitygoods中是否已存在相同id的项目
    const existingItem = userModel.activitygoods.find(item => item.id === currentRow.id);
    // 如果不存在相同的id，则添加数据并标记为已选中
    if (!existingItem) {
        userModel.activitygoods.push(currentRow);
        currentRow.isSelected = true; // 标记当前行已选择
        console.log(userModel.activitygoods);
    } else {
        // 可以选择处理已存在的情况，比如提示用户或忽略操作
        ElMessage.warning(`商品ID为${currentRow.id}的数据已存在。`);
    }
    // userModel.activitygoods = [];
}
/**
 * 条件查询
 *
 */

//获得查询表单组件对象
const queryFormRef = ref()

let queryForm = reactive({
    gname: '', gmodel: '', gcolor: '', gbrand: '', gtype: ''
})
//table列表数据
const tableList = reactive({ tableData: [] })

const brandMap = {
    0: "三星",
    1: "魅族",
    2: "小米",
    3: "华为",
    4: "苹果"
}

const typeMap = {
    0: "5G手机",
    1: "4G手机",
    2: "充电线",
    3: "手机壳",
    4: "充电器",
    5: "智能手表",
    6: "智能手环"
}

/**
 * 分页组件
 */
//页码查询
let productPage = reactive({
    pageTotal: 100,
    pageNum: 1,
    pageSize: 5,
    ggGoods: queryForm,
    goodsList: [],
})

//当前页码是多少
const handleCurrentChange = (val) => {
    productPage.pageNum = val;
    listCondition(productPage);
}
//当前页面显示多少条
const handleSizeChange = (val) => {
    productPage.pageNum = 1;
    productPage.pageSize = val;
    listCondition(productPage);
}


const onSubmit = () => {
    if (queryForm.gbrand in brandMap) {
        queryForm.gbrand = brandMap[queryForm.gbrand]
    }
    if (queryForm.gtype in typeMap) {
        queryForm.gtype = typeMap[queryForm.gtype]
    }
    let param = productPage;
    productPage.ggGoods = queryForm;
    listCondition(param);
}
const onReset = () => {
    queryForm.gcolor = '';
    queryForm.gmodel = '';
    queryForm.gbrand = '';
    queryForm.gname = '';
    queryForm.gtype = '';
    productPage.ggGoods = queryForm;
    listCondition(productPage)

}
onMounted(() => {
    listCondition(productPage)
    console.log(tableList);
})

const listCondition = (param) => {
    axios.postJson("goods/search", param).then((resp) => {
        productPage = resp.data;
        tableList.tableData = productPage.goodsList;
    })
}

</script>
<style scoped>
.query-form .el-input {
    --el-input-width: 220px;
}

.query-form .el-select {
    --el-select-width: 220px;
}
</style>