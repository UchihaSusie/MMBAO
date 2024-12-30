<template>
  <hr/>
  <h3> 商品管理 > 产品查询</h3>
  <el-form ref="queryFormRef" class="query-form" :inline="true" :model="queryForm">
    <el-form-item label="档案编码：" prop="coding">
      <el-input v-model="queryForm.coding" placeholder="coding" />
    </el-form-item>
    <el-form-item label="产品型号：" prop="model">
      <el-input v-model="queryForm.model" placeholder="model" />
    </el-form-item>
    <el-form-item label="基础库-颜色：" prop="color">
      <el-select
          v-model="queryForm.color"
          placeholder="请选择">
        <el-option label="白色" :value="0"/>
        <el-option label="黑色" :value="1" />
        <el-option label="绚丽紫" :value="2" />
        <el-option label="珍珠白" :value="3" />
      </el-select>
    </el-form-item>
    <el-form-item label="产品品牌：" prop="brand">
      <el-select
          v-model="queryForm.brand"
          placeholder="请选择">
        <el-option v-for="(item, key) in brandMap" :label="item" :value="key"/>
      </el-select>
    </el-form-item>
    <el-form-item label="产品分类：" prop="classify">
      <el-select
          v-model="queryForm.classify"
          placeholder="请选择">
        <el-option v-for="(item, key) in classifyMap" :label="item" :value="key"/>
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
    <el-table-column prop="coding" label="档案编码" width="180" />
    <el-table-column prop="model" label="基础库-型号" width="180" />
    <el-table-column prop="color" label="基础库-颜色" width="180">
      <template #default="scope">
        {{ colorMap[scope.row.color] }}
      </template>
    </el-table-column>
    <el-table-column prop="brand" label="基础库-品牌" width="180" />
    <el-table-column prop="classify" label="基础库-分类" width="180" />
  </el-table>
  <el-pagination v-model:current-page="productPage.pageNum" v-model:page-size="productPage.pageSize"
                 :page-sizes="[5, 10, 20]" layout="total, sizes, prev, pager, next, jumper" :total="productPage.pageTotal"
                 @size-change="handleSizeChange" @current-change="handleCurrentChange" />
</template>


<script setup>
import { ref, reactive, onMounted, getCurrentInstance } from 'vue'
const { proxy } = getCurrentInstance()
import axios from '/src/plugin/axios.js'


/**
 * 条件查询
 *
 */

//获得查询表单组件对象
const queryFormRef = ref()

let queryForm = reactive({
  coding:'',model:'',color:'',brand:'',classify:''
})
//table列表数据
const tableList = reactive({ tableData: [] })

const colorMap = {
  0: "白色",
  1: "黑色",
  2: "炫丽紫",
  3: "珍珠白",
}
const brandMap = {
  0:"三星",
  1:"LG",
  2:"小米",
  3:"华为",
  4:"苹果",
  5:"一加",
  6:"魅族"
}

const classifyMap = {
  0:"5G手机",
  1:"4G手机",
  2:"充电线",
  3:"手机壳",
  4:"充电器",
  5:"智能手表",
  6:"智能手环"
}

/**
 * 分页组件
 */
//页码查询
let productPage = reactive({
  pageTotal:100,
  pageNum:1,
  pageSize:5,
  ggProduct:queryForm,
  proList:[],
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


const onSubmit = ()=>{
  if(queryForm.brand in brandMap){
    queryForm.brand = brandMap[queryForm.brand]
  }
  if(queryForm.classify in classifyMap){
    queryForm.classify = classifyMap[queryForm.classify]
  }
  let param = productPage;
  productPage.ggProduct = queryForm;
  listCondition(param);
}
const onReset = ()=>{
  queryForm.color = '';
  queryForm.model = '';
  queryForm.brand = '';
  queryForm.coding = '';
  queryForm.classify = '';
  productPage.ggProduct = queryForm;
  listCondition(productPage)

}
onMounted(() => {
  listCondition(productPage)
})

const listCondition = (param) => {
  axios.postJson("product/search",param).then((resp) => {
        productPage = resp.data;
        tableList.tableData = productPage.proList;
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