<template>
  <hr/>
  <h3> 商品管理 > 供应商管理</h3>
  <el-form ref="queryFormRef" class="query-form" :inline="true" :model="queryForm">
    <el-form-item label="供应商名称：" prop="gname">
      <el-input v-model="queryForm.supplierName" placeholder="goods name"/>
    </el-form-item>
    <el-form-item label="联系人姓名：" prop="gmodel">
      <el-input v-model="queryForm.contacts" placeholder="goods model"/>
    </el-form-item>
    <el-form-item>
      <el-select
          v-model="conditionForm.condition_type"
          placeholder="请选择查询条件">
        <el-option v-for="(item, key) in conditionMap" :label="item" :value="key"/>
      </el-select>
      <el-input v-model="conditionForm.condition_val" placeholder=""/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onReset">重置</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="addFormOpen">新增供应商</el-button>
    </el-form-item>
  </el-form>

  <el-table ref="tableRef" :data="tableList.tableData" style="width: 90%">
    <el-table-column type="selection" width="55"/>
    <el-table-column type="index" :index="indexMethod" label="序号" width="80"/>
    <el-table-column prop="supplierName" label="供应商名称" width="150"/>
    <el-table-column prop="contacts" label="联系人" width="100"/>
    <el-table-column prop="contactPhone" label="联系电话" width="100"/>
    <el-table-column prop="contactAddress" label="联系地址" width="100"/>
    <el-table-column prop="accountNo" label="开户账号" width="180"/>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button type="warning" @click="editFormOpen(scope.row)">修改</el-button>
        <el-button type="success" @click="chooseFormOpen(scope.row)">选用供应商品</el-button>
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
    <el-form :model="editForm.editData" label-width="auto" style="max-width: 400px">
      <el-form-item label="供应商名称:" prop="supplierName">
        <el-input v-model="editForm.editData.supplierName" placeholder="supplierName"/>
      </el-form-item>
      <el-form-item label="联系人:" prop="contacts">
        <el-input v-model="editForm.editData.contacts" placeholder="contacts"/>
      </el-form-item>
      <el-form-item label="联系电话:" prop="contactPhone">
        <el-input v-model="editForm.editData.contactPhone" placeholder="contactPhone"/>
      </el-form-item>
      <el-form-item label="联系邮箱:" prop="contactEmail">
        <el-input v-model="editForm.editData.contactEmail" placeholder="contactEmail"/>
      </el-form-item>
      <el-form-item label="联系地址:" prop="contactAddress">
        <el-input v-model="editForm.editData.contactAddress" placeholder="contactAddress"/>
      </el-form-item>
      <el-form-item label="开户银行:" prop="bankOfDeposit">
        <el-input v-model="editForm.editData.bankOfDeposit" placeholder="bankOfDeposit"/>
      </el-form-item>
      <el-form-item label="开户账号:" prop="accountNo">
        <el-input v-model="editForm.editData.accountNo" placeholder="accountNo"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="editFormSubmit">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 添加对话框 -->
  <el-dialog
      v-model="addDialogVisible"
      title="新增供应商"
      width="500"
  >
    <el-form :model="addForm.addData" label-width="auto" style="max-width: 400px">
      <el-form-item label="供应商名称:" prop="supplierName">
        <el-input v-model="addForm.addData.supplierName" placeholder="supplierName"/>
      </el-form-item>
      <el-form-item label="联系人:" prop="contacts">
        <el-input v-model="addForm.addData.contacts" placeholder="contacts"/>
      </el-form-item>
      <el-form-item label="联系电话:" prop="contactPhone">
        <el-input v-model="addForm.addData.contactPhone" placeholder="contactPhone"/>
      </el-form-item>
      <el-form-item label="联系邮箱:" prop="contactEmail">
        <el-input v-model="addForm.addData.contactEmail" placeholder="contactEmail"/>
      </el-form-item>
      <el-form-item label="联系地址:" prop="contactAddress">
        <el-input v-model="addForm.addData.contactAddress" placeholder="contactAddress"/>
      </el-form-item>
      <el-form-item label="开户银行:" prop="bankOfDeposit">
        <el-input v-model="addForm.addData.bankOfDeposit" placeholder="bankOfDeposit"/>
      </el-form-item>
      <el-form-item label="开户账号:" prop="accountNo">
        <el-input v-model="addForm.addData.accountNo" placeholder="accountNo"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addFormSubmit">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 选用供应商品对话框 -->
  <el-dialog
      v-model="chooseDialogVisible"
      title="新增供应商"
      width="800"
  >
    <h5 :model="chooseSupplier" label-width="auto" style="max-width: 400px">
       供应商名称: {{chooseSupplier.supplierName}}
    </h5>
    <el-button type="primary" @click="addGoodsFromOpen()">选择商品</el-button>
    <el-table ref="tableRef" :data="chooseForm.chooseData" style="width: 90%">
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="id" label="商品id" width="80"/>
      <el-table-column prop="gname" label="商品名称" width="150"/>
      <el-table-column prop="purchasePrice" label="采购价" width="100"/>
      <el-table-column prop="marketPrice" label="市场价" width="100"/>
      <el-table-column label="操作" >
        <template #default="scope">
          <el-button type="danger" @click="deleteGoods(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="chooseDialogVisible = false">返回</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 商品话框 -->
  <el-dialog
      v-model="goodDialogVisible"
      title="商品话框"
      width="1200"
  >

    <el-form class="query-form" :inline="true" :model="goodsQueryForm">
      <el-form-item label="商品名称：" prop="gname">
        <el-input v-model="goodsQueryForm.gname" placeholder="goods name"/>
      </el-form-item>
      <el-form-item label="商品型号：" prop="gmodel">
        <el-input v-model="goodsQueryForm.gmodel" placeholder="goods model"/>
      </el-form-item>
      <el-form-item label="商品颜色：" prop="gcolor">
        <el-select
            v-model="goodsQueryForm.gcolor"
            placeholder="请选择">
          <el-option label="白色" :value="0"/>
          <el-option label="黑色" :value="1"/>
          <el-option label="绚丽紫" :value="2"/>
          <el-option label="珍珠白" :value="3"/>
        </el-select>
      </el-form-item>
      <el-form-item label="商品品牌：" prop="gbrand">
        <el-select
            v-model="goodsQueryForm.gbrand"
            placeholder="请选择">
          <el-option v-for="(item, key) in brandMap" :label="item" :value="key"/>
        </el-select>
      </el-form-item>
      <el-form-item label="商品分类：" prop="gtype">
        <el-select
            v-model="goodsQueryForm.gtype"
            placeholder="请选择">
          <el-option v-for="(item, key) in typeMap" :label="item" :value="key"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onGoodsSubmit">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onGoodsReset">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table ref="tableRef" :data="goodsTableList.tableData" style="width: 90%">
      <el-table-column type="selection" width="55"/>
      <el-table-column type="index" :index="indexMethod" label="序号" width="130"/>
      <el-table-column prop="gname" label="商品名称" width="180"/>
      <el-table-column prop="gmodel" label="商品型号" width="100"/>
      <el-table-column prop="gcolor" label="商品颜色" width="100">
        <template #default="scope">
          {{ colorMap[scope.row.gcolor] }}
        </template>
      </el-table-column>
      <el-table-column prop="gbrand" label="商品品牌" width="100"/>
      <el-table-column prop="gtype" label="商品分类" width="100">
        <template #default="scope">
          {{ typeMap[scope.row.gtype] }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="success" @click="chooseGoods(scope.row)">选择</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="goodsProductPage.pageNum" v-model:page-size="goodsProductPage.pageSize"
                   :page-sizes="[5, 10, 20]" layout="total, sizes, prev, pager, next, jumper"
                   :total="goodsProductPage.pageTotal"
                   @size-change="goodsHandleSizeChange" @current-change="goodsHandleCurrentChange"/>
  </el-dialog>
</template>


<script setup>
import {ref, reactive, onMounted, getCurrentInstance} from 'vue'

const {proxy} = getCurrentInstance()
import axios from '/src/plugin/axios.js'
import {ElMessage} from "element-plus";


/**
 * 选择供应商品
 *
 *
 */

const chooseDialogVisible = ref(false)

const chooseForm = reactive({
  chooseData: {},
})

const chooseSupplier = reactive({})


const chooseFormOpen = (currentRow) => {
  chooseSupplier.id = currentRow.id;
  chooseSupplier.supplierName = currentRow.supplierName;
  axios.post("/supplier/goodBySupply/"+chooseSupplier.id).then(response=>{
    if (response != null && response.code === 200) {
      chooseForm.chooseData = response.data;
      chooseDialogVisible.value = true;
    }
  })
}

const deleteGoods = (row) =>{
  axios.post("/supplier/deleteGoods?gid="+ row.id + "&sid=" +  chooseSupplier.id).then(response=>{
    if (response != null && response.code === 200) {
      //删除成功
      ElMessage({
        message: '删除成功',
        type: 'success'
      })
      axios.post("/supplier/goodBySupply/"+chooseSupplier.id).then(response=>{
        if (response != null && response.code === 200) {
          chooseForm.chooseData = response.data;
          chooseDialogVisible.value = true;
        }
      })
    }
  })
}

/**
 * Good表单搜索选中
 *
 */

const goodDialogVisible = ref(false)

let goodsQueryForm = reactive({
  gname: '', gmodel: '', gcolor: '', gbrand: '', gtype: ''
})
//table列表数据
const goodsTableList = reactive({tableData: []})

const colorMap = {
  0: "白色",
  1: "黑色",
  2: "炫丽紫",
  3: "珍珠白",
}
const brandMap = {
  0: "三星",
  1: "魅族",
  2: "小米",
  3: "华为",
  4: "苹果"
}
const optionsList = reactive({optionsData: []})
const typeMap = reactive({})

/**
 * 分页组件
 */
//页码查询
let goodsProductPage = reactive({
  pageTotal: 100,
  pageNum: 1,
  pageSize: 5,
  ggGoods: goodsQueryForm,
  goodsList: [],
})

//当前页码是多少
const goodsHandleCurrentChange = (val) => {
  goodsProductPage.pageNum = val;
  goodsListCondition(goodsProductPage);
}
//当前页面显示多少条
const goodsHandleSizeChange = (val) => {
  goodsProductPage.pageNum = 1;
  goodsProductPage.pageSize = val;
  goodsListCondition(goodsProductPage);
}


const onGoodsSubmit = () => {
  if (goodsQueryForm.gbrand in brandMap) {
    goodsQueryForm.gbrand = brandMap[goodsQueryForm.gbrand]
  }
  let param = goodsProductPage;
  console.log(goodsQueryForm)
  goodsProductPage.ggGoods = goodsQueryForm;
  goodsListCondition(param);
}
const onGoodsReset = () => {
  goodsQueryForm.gcolor = '';
  goodsQueryForm.gmodel = '';
  goodsQueryForm.gbrand = '';
  goodsQueryForm.gname = '';
  goodsQueryForm.gtype = '';
  goodsProductPage.ggGoods = goodsQueryForm;
  goodsListCondition(goodsProductPage)
}

const goodsListCondition = (param) => {
  axios.postJson("goods/search", param).then((resp) => {
    goodsProductPage = resp.data;
    goodsTableList.tableData = goodsProductPage.goodsList;
  })
}
const goodslistType = () => {
  let param = {
    "pageTotal": 0,
    "pageNum": 1,
    "pageSize": 1000,
    "ggTypes": {"typeName": ""},
    "typeList": []
  }
  axios.postJson("type/search", param).then((resp) => {
    optionsList.optionsData = resp.data.typeList;
    goodsListCondition(goodsProductPage)
    optionsList.optionsData.forEach(options => {
      typeMap[options.id] = options.typeName;
    })
  })
}


const addGoodsFromOpen = () => {
  goodDialogVisible.value = true;
  goodslistType();
}

const chooseGoods = (currentRow) => {
  goodDialogVisible.value = false;
  axios.post("/supplier/addGoods?sid="+ chooseSupplier.id + "&gid="+  currentRow.id).then(response=>{
    if (response != null && response.code === 200) {
      //添加成功
      ElMessage({
        message: '添加成功',
        type: 'success'
      })
      axios.post("/supplier/goodBySupply/"+chooseSupplier.id).then(response=>{
        if (response != null && response.code === 200) {
          chooseForm.chooseData = response.data;
          chooseDialogVisible.value = true;
        }
      })
    }
  })
}



/**
 * 修改功能
 *
 *
 */

const editDialogVisible = ref(false)

const editForm = reactive({
  editData: {},
})

const editFormOpen = (currentRow) => {
  axios.get("/supplier/" + currentRow.id)
      .then((resp) => {
        editForm.editData = resp.data;
      })
  editDialogVisible.value = true;
}
const editFormSubmit = () => {
  axios.postJson("/supplier/save/update", editForm.editData).then(response=>{
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
 *
 */

const addDialogVisible = ref(false)

const addForm = reactive({
  addData: {},
})

const addFormOpen = () => {
  editDialogVisible.value = true;
}
const addFormSubmit = () => {
  axios.postJson("/supplier/save/update", addForm.addData).then(response=>{
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
 * 删除行
 * */
const deleteListFormOpen = (currentRow) => {
  axios.delete("/supplier/" + currentRow.id).then(response => {
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

let conditionForm = reactive({})
let queryForm = reactive({
  supplierName: '', contacts: ''
})
//table列表数据
const tableList = reactive({tableData: []})

const conditionMap = {
  0: "contactPhone",
  1: "contactAddress",
  2: "bankOfDeposit",
  3: "accountNo",
}

/**
 * 分页组件
 */
//页码查询
let productPage = reactive({
  pageTotal: 100,
  pageNum: 1,
  pageSize: 5,
  ggSupplier: queryForm,
  supList: [],
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
  if (conditionForm.condition_type in conditionMap) {
    conditionForm.condition_type = conditionMap[conditionForm.condition_type];
    queryForm[conditionForm.condition_type] = conditionForm.condition_val;
  }
  let param = productPage;
  productPage.ggSupplier = queryForm;
  listCondition(param);
}
const onReset = () => {
  queryForm.supplierName = '';
  queryForm.contacts = '';
  queryForm.contactPhone = '';
  queryForm.contactAddress = '';
  queryForm.accountNo = '';
  conditionForm.condition_type = "";
  conditionForm.condition_val = "";
  productPage.ggSupplier = queryForm;
  listCondition(productPage)

}
onMounted(() => {
  listCondition(productPage)
})

const listCondition = (param) => {
  axios.postJson("supplier/search", param).then((resp) => {
    productPage = resp.data;
    tableList.tableData = productPage.supList;
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