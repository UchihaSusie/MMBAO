<template>
  <hr/>
  <h3> 商品管理 > 商品品牌管理</h3>
  <el-form ref="queryFormRef" class="query-form" :inline="true" :model="queryForm">
    <el-form-item label="品牌名称：" prop="brandName">
      <el-input v-model="queryForm.brandName" placeholder="brand name"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onReset">重置</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="success" @click="addFormOpen">新增品牌</el-button>
    </el-form-item>
  </el-form>

  <el-table ref="tableRef" :data="tableList.tableData" style="width: 90%">
    <el-table-column type="selection" width="55"/>
    <el-table-column type="index" :index="indexMethod" label="序号" width="130"/>
    <el-table-column prop="brandName" label="品牌名称" width="180"/>
    <el-table-column prop="brandUrl" label="品牌网址" width="180"/>
    <el-table-column prop="brandDescribe" label="品牌描述" width="180"/>
    <el-table-column label="操作" width="200">
      <template #default="scope">
        <el-button type="warning" @click="editFormOpen(scope.row)">修改</el-button>
        <el-button type="danger" @click="deleteListFormOpen(scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination v-model:current-page="productPage.pageNum" v-model:page-size="productPage.pageSize"
                 :page-sizes="[5, 10, 20]" layout="total, sizes, prev, pager, next, jumper"
                 :total="productPage.pageTotal"
                 @size-change="handleSizeChange" @current-change="handleCurrentChange"/>


  <!-- 修改对话框 -->
  <el-dialog
      v-model="editDialogVisible"
      title="修改"
      width="500"
  >
    <el-form ref="editFormRef" :model="editForm.editData" label-width="auto" style="max-width: 400px">
      <el-form-item label="品牌名称:" prop="brandName">
        <el-input v-model="editForm.editData.brandName" placeholder="brandName"/>
      </el-form-item>
      <el-form-item label="品牌网址:" prop="brandUrl">
        <el-input v-model="editForm.editData.brandUrl" placeholder="brandUrl"/>
      </el-form-item>
      <el-form-item label="品牌描述:" prop="brandDescribe">
        <el-input v-model="editForm.editData.brandDescribe" placeholder="brandDescribe"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="editDialogVisible = false">返回</el-button>
        <el-button type="primary" @click="editFormSubmit">保存</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 添加对话框 -->
  <el-dialog
      v-model="addDialogVisible"
      title="添加"
      width="500"
  >
    <el-form ref="addFormRef" :model="addForm.addData" label-width="auto" style="max-width: 400px">
      <el-form-item label="品牌名称:" prop="brandName">
        <el-input v-model="addForm.addData.brandName" placeholder="brandName"/>
      </el-form-item>
      <el-form-item label="品牌网址:" prop="brandUrl">
        <el-input v-model="addForm.addData.brandUrl" placeholder="brandUrl"/>
      </el-form-item>
      <el-form-item label="品牌描述:" prop="brandDescribe">
        <el-input v-model="addForm.addData.brandDescribe" placeholder="brandDescribe"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addDialogVisible = false">返回</el-button>
        <el-button type="primary" @click="addFromSubmit">添加</el-button>
      </div>
    </template>
  </el-dialog>
</template>


<script setup>
import {ref, reactive, onMounted, getCurrentInstance} from 'vue'

const {proxy} = getCurrentInstance()
import axios from '/src/plugin/axios.js'
import {ElMessage} from "element-plus";


/**
 * 修改功能
 *
 *
 */

const editFormRef = ref();

const editDialogVisible = ref(false)

const editForm = reactive({
  editData: {}
})

const editFormOpen = (currentRow) => {
  axios.get("brand/" + currentRow.id)
      .then((resp) => {
        editForm.editData = resp.data;
      })
  editDialogVisible.value = true;
}
const editFormSubmit = () => {
  axios.postJson("/brand/save/update", editForm.editData).then(response=>{
    if (response != null && response.code === 200) {
      //修改成功
      ElMessage({
        message: '修改成功',
        type: 'success'
      })
      onReset();
    }
  })
  editDialogVisible.value = false;
}

/**
 * 添加功能
 *
 * */

const addFormRef = ref();

const addDialogVisible = ref(false)

const addForm = reactive({
  addData: {
    brandName: '', brandUrl: '', brandDescribe: '',
  }
})

const addFormOpen = () => {
  addDialogVisible.value = true;
}

const addFromSubmit = () => {
  axios.postJson("/brand/save/update", addForm.addData).then(response=>{
    if (response != null && response.code === 200) {
      //添加成功
      ElMessage({
        message: '添加成功',
        type: 'success'
      })
      onReset();
    }
  })
  addDialogVisible.value = false;
}

/**
 * 删除行
 * */
const deleteListFormOpen = (currentRow) => {
  axios.delete("/brand/" + currentRow.id).then(response => {
    if (response != null && response.code === 200) {
      //删除成功
      ElMessage({
        message: '删除成功',
        type: 'success'
      })
      onReset();
    }
  })
}

/**
 * 条件查询
 *
 */

//获得查询表单组件对象
const queryFormRef = ref()

let queryForm = reactive({
  typeName: ''
})
//table列表数据
const tableList = reactive({tableData: []})

/**
 * 分页组件
 */
//页码查询
let productPage = reactive({
  pageTotal: 100,
  pageNum: 1,
  pageSize: 5,
  ggBrand: queryForm,
  brandList: [],
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
  productPage.ggBrand = queryForm;
  listCondition(productPage);
}
const onReset = () => {
  queryForm.brandName = '';
  productPage.ggBrand = queryForm;
  listCondition(productPage)

}
onMounted(() => {
  listCondition(productPage)
})

const listCondition = (param) => {
  axios.postJson("brand/search", param).then((resp) => {
    productPage = resp.data;
    tableList.tableData = productPage.brandList;
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