<template>
  <hr/>
  <h3> 商品管理 > 商品类型管理</h3>
  <el-form ref="queryFormRef" class="query-form" :inline="true" :model="queryForm">
    <el-form-item label="商品类型名称：" prop="typeName">
      <el-input v-model="queryForm.typeName" placeholder="type name"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onReset">重置</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="addFormOpen">新增类型</el-button>
    </el-form-item>
  </el-form>

  <el-table ref="tableRef" :data="tableList.tableData" style="width: 90%">
    <el-table-column type="selection" width="55"/>
    <el-table-column type="index" :index="indexMethod" label="序号" width="130"/>
    <el-table-column prop="typeName" label="商品类型名称" width="180"/>
    <el-table-column prop="descri" label="商品类型描述" width="100"/>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button type="success" @click="chooseFormOpen(scope.row)">选用品牌</el-button>
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
      <el-form-item label="商品类型名称:" prop="typeName">
        <el-input v-model="editForm.editData.typeName" placeholder="typeName"/>
      </el-form-item>
      <el-form-item label="商品类型描述:" prop="descri">
        <el-input v-model="editForm.editData.descri" placeholder="descri"/>
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
      <el-form-item label="商品类型名称:" prop="typeName">
        <el-input v-model="addForm.addData.typeName" placeholder="typeName"/>
      </el-form-item>
      <el-form-item label="商品类型描述:" prop="descri">
        <el-input v-model="addForm.addData.descri" placeholder="descri"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addDialogVisible = false">返回</el-button>
        <el-button type="primary" @click="addFromSubmit">保存</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 选用穿梭框（数据库goods type brand关系添加） -->
  <el-dialog
      v-model="chooseDialogVisible"
      title="选用品牌"
      width="800"
  >
    <el-transfer
        :titles="['可选用商品品牌', '已选用商品品牌']"
        :button-texts="['取消选择', '选择品牌']"
        v-model="chooseValue"
        :props="{
                  key: 'id',
                  label: 'brandName',
                }"
        :data="chooseForm.chooseData"
        @change="correlationChange"
    />
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="chooseDialogVisible = false">返回</el-button>
        <el-button type="primary" @click="chooseFormSubmit">保存</el-button>
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
 * 选用品牌功能
 *
 * */

const chooseDialogVisible = ref(false)


const chooseValue = ref([])

const chooseForm = reactive({
  chooseData: []
})

let par = {};


const chooseFormOpen = (currentRow) => {
  par.gtypeId = currentRow.id
  chooseValue.value = []
  axios.post("brand/brandByType?id="+ currentRow.id).then((resp)=>{
    let list = resp.data;
    list.forEach(function (brand){
      chooseValue.value.push(brand.id)
    })
    chooseDialogVisible.value = true;
  })
}
const chooseFormSubmit = () => {
  chooseDialogVisible.value = false;
  par.brandIdList = chooseValue.value;
  axios.postJson("/goodBrand/saveBrands", par).then(response => {
    if (response != null && response.code === 200) {
      ElMessage({
        message: '选择成功',
        type: 'success'
      });
    }
  })
}

/**
 * 删除功能
 *
 * */

const deleteListFormOpen =(currentRow)=>{
  axios.delete("/type/"+currentRow.id).then(response => {
    if (response != null && response.code === 200) {
      ElMessage({
        message: '删除',
        type: 'success'
      });
      //保存结束刷新页面
      onReset();
    }
  })
}
/**
 * 修改功能
 *
 *
 */

const editFormRef = ref();

const editDialogVisible = ref(false)

const editForm = reactive({
  editData: {pid:-1}
})

const editFormOpen = (currentRow) => {
  editForm.editData.id = currentRow.id;
  editForm.editData.typeName = currentRow.typeName;
  editForm.editData.descri = currentRow.descri;
  editDialogVisible.value = true;
}
const editFormSubmit = () => {
  editDialogVisible.value = false;
  axios.postJson("type/save/update", editForm.editData).then(response => {
    if (response != null && response.code === 200) {
      ElMessage({
        message: '修改成功',
        type: 'success'
      });
      //保存结束刷新页面
      onReset();
    }
  })
}

/**
 * 添加功能
 *
 * */

const addFormRef = ref();

const addDialogVisible = ref(false)

const addForm = reactive({
  addData: {
    typeName: '', descri: ''
  }
})

const addFormOpen = () => {
  addDialogVisible.value = true;
}

let integerId = 1010
const addFromSubmit = () => {
  addDialogVisible.value = false;
  addForm.addData.id = integerId;
  integerId = integerId + 1;
  axios.postJson("type/save/update", addForm.addData).then(response => {
    if (response != null && response.code === 200) {
      ElMessage({
        message: '添加成功',
        type: 'success'
      });
      //保存结束刷新页面
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
  ggTypes: queryForm,
  typeList: [],
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
  productPage.ggTypes = queryForm;
  listCondition(productPage);
}
const onReset = () => {
  queryForm.typeName = '';
  productPage.ggTypes = queryForm;
  listCondition(productPage)

}
onMounted(() => {
  listCondition(productPage)
  axios.get("brand/listAll").then((resp)=>{
    chooseForm.chooseData = resp.data;
  })
})

const listCondition = (param) => {
  axios.postJson("type/search", param).then((resp) => {
    productPage = resp.data;
    tableList.tableData = productPage.typeList;
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