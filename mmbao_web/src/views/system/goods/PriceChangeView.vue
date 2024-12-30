<template>
  <hr/>
  <h3> 商品管理 > 价格调整管理</h3>
  <el-form ref="queryFormRef" class="query-form" :inline="true" :model="queryForm">
    <el-form-item label="商品名称：" prop="goodsName">
      <el-input v-model="queryForm.goodsName" placeholder="goodsName"/>
    </el-form-item>
    <el-form-item label="审核状态：" prop="processStatus">
      <el-select
          v-model="queryForm.processStatus"
          placeholder="请选择">
        <el-option v-for="(item, key) in statusMap" :label="item" :value="key"/>
      </el-select>
    </el-form-item>
    <el-form-item label="价格生效时间：" prop="beginTime">
      <el-date-picker
          v-model="queryForm.beginTime"
          type="date"
          placeholder="开始日期"
          format="YYYY/MM/DD"
          value-format="YYYY-MM-DD"
      />
    </el-form-item>
    <el-form-item label="价格失效时间：" prop="endTime">
      <el-date-picker
          v-model="queryForm.endTime"
          type="date"
          placeholder="失效日期"
          format="YYYY/MM/DD"
          value-format="YYYY-MM-DD"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onReset">重置</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="addFormOpen">新增价格调整申请</el-button>
    </el-form-item>
  </el-form>

  <el-table ref="tableRef" :data="tableList.tableData" style="width: 90%">
    <el-table-column type="selection" width="55"/>
    <el-table-column type="index" :index="indexMethod" label="序号" width="80"/>
    <el-table-column prop="goodsName" label="商品名称" width="180"/>
    <el-table-column prop="adjustMarketPrice" label="商城原价" width="100"/>
    <el-table-column prop="adjustShoppingPrice" label="调整后商城价格" width="100"/>
    <el-table-column prop="beginTime" label="希望价格生效时间" width="200"/>
    <el-table-column prop="processStatus" label="审批状态" width="130"/>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button type="warning" @click="editFormOpen(scope.row)">修改</el-button>
        <el-button type="danger" @click="deleteFormOpen(scope.row)">删除</el-button>
        <el-button type="success" @click="checkFormOpen(scope.row)">审核跟踪</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-pagination v-model:current-page="productPage.pageNum" v-model:page-size="productPage.pageSize"
                 :page-sizes="[5, 10, 20]" layout="total, sizes, prev, pager, next, jumper"
                 :total="productPage.pageTotal"
                 @size-change="handleSizeChange" @current-change="handleCurrentChange"/>

  <!-- 添加对话框 -->
  <el-dialog
      v-model="addDialogVisible"
      title="添加对话框"
      width="500"
  >

    <el-form :model="addForm.goodData" label-width="auto" style="max-width: 400px">
      <el-form-item label="商品ID">
        <el-input v-model="addForm.goodData.id" />
        <el-button type="primary" @click="addGoodsFromOpen">选择商品</el-button>
      </el-form-item>
      <el-form-item label="商品名称">
        <el-input v-model="addForm.goodData.gname" />
      </el-form-item>
      <el-form-item label="商品库存">
        <el-input v-model="addForm.goodData.stock" />
      </el-form-item>
      <el-form-item label="商品原始价格">
        <el-form-item label="市场价">
          <el-input v-model="addForm.goodData.marketPrice" />
        </el-form-item>
        <el-form-item label="商城价">
          <el-input v-model="addForm.goodData.purchasePrice" />
        </el-form-item>
      </el-form-item>
    </el-form>

    <el-form :model="addForm.adjustData" label-width="auto" style="max-width: 400px">
      <el-form-item label="商品调价幅度：">
        <el-radio-group v-model="addForm.adjustData.adjustWay">
          <el-radio value="绝对值">绝对值</el-radio>
          <el-radio value="百分比">百分比
            <el-input v-model="addForm.adjustData.percent" placeholder="percent"/>
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="市场价" prop="adjustMarketPrice">
        <el-input v-model="addForm.adjustData.adjustMarketPrice" placeholder="adjustMarketPrice"/>
      </el-form-item>
      <el-form-item label="商城价" prop="adjustShoppingPrice">
        <el-input v-model="addForm.adjustData.adjustShoppingPrice" placeholder="adjustShoppingPrice"/>
      </el-form-item>
      <el-form-item label="希望价格生效时间:" prop="beginTime">
        <el-date-picker
            v-model="addForm.adjustData.beginTime"
            type="date"
            placeholder="失效日期"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="希望价格失效时间:" prop="endTime">
        <el-date-picker
            v-model="addForm.adjustData.endTime"
            type="date"
            placeholder="失效日期"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="调价原因:" prop="reason">
        <el-input v-model="addForm.adjustData.reason" placeholder="reason"/>
      </el-form-item>
      <el-form-item label="备注:" prop="remark">
        <el-input v-model="addForm.adjustData.remark" placeholder="remark"/>
      </el-form-item>
      <el-form-item label="审批人：" prop="applyUser">
        <el-select
            v-model="addForm.adjustData.applyUser"
            placeholder="请选择">
          <el-option label="admin" :value="1"/>
          <el-option label="manager" :value="3"/>
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addDialogVisible = false">返回</el-button>
        <el-button type="primary" @click="addFromSubmit">保存</el-button>
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

  <!-- 修改对话框 -->
  <el-dialog
      v-model="editDialogVisible"
      title="修改对话框"
      width="500"
  >
    <el-form :model="addForm.goodData" label-width="auto" style="max-width: 400px">
      <el-form-item label="商品ID:">{{addForm.goodData.id}}
      </el-form-item>
      <el-form-item label="商品名称:">{{addForm.goodData.gname}}
      </el-form-item>
      <el-form-item label="商品库存:">{{addForm.goodData.stock}}
      </el-form-item>
      <el-form-item label="商品原始价格 - 市场价:">{{addForm.goodData.marketPrice}}
      </el-form-item>
      <el-form-item label="商品原始价格 - 商城价">{{addForm.goodData.purchasePrice}}
      </el-form-item>
    </el-form>
    <el-form :model="addForm.adjustData" label-width="auto" style="max-width: 400px">
      <el-form-item label="商品调价幅度：">
        <el-radio-group v-model="addForm.adjustData.adjustWay">
          <el-radio value="绝对值">绝对值</el-radio>
          <el-radio value="百分比">百分比
            <el-input v-model="addForm.adjustData.percent" placeholder="percent"/>
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="市场价" prop="adjustMarketPrice">
        <el-input v-model="addForm.adjustData.adjustMarketPrice" placeholder="adjustMarketPrice"/>
      </el-form-item>
      <el-form-item label="商城价" prop="adjustShoppingPrice">
        <el-input v-model="addForm.adjustData.adjustShoppingPrice" placeholder="adjustShoppingPrice"/>
      </el-form-item>
      <el-form-item label="希望价格生效时间:" prop="beginTime">
        <el-date-picker
            v-model="addForm.adjustData.beginTime"
            type="date"
            placeholder="失效日期"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="希望价格失效时间:" prop="endTime">
        <el-date-picker
            v-model="addForm.adjustData.endTime"
            type="date"
            placeholder="失效日期"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="调价原因:" prop="reason">
        <el-input v-model="addForm.adjustData.reason" placeholder="reason"/>
      </el-form-item>
      <el-form-item label="备注:" prop="remark">
        <el-input v-model="addForm.adjustData.remark" placeholder="remark"/>
      </el-form-item>
      <el-form-item label="审批人：" prop="applyUser">
        <el-select
            v-model="addForm.adjustData.applyUser"
            placeholder="请选择">
          <el-option label="admin" :value="1"/>
          <el-option label="manager" :value="3"/>
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="editDialogVisible = false">返回</el-button>
        <el-button type="primary" @click="editFromSubmit">保存</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 审核跟踪对话框 -->
  <el-dialog
      v-model="checkDialogVisible"
      width="500"
  >
    <h5>调整信息</h5>
    <hr/>
    <el-form :model="addForm.goodData" label-width="auto" style="max-width: 400px">
      <el-form-item label="商品ID:">{{addForm.goodData.id}}
      </el-form-item>
      <el-form-item label="商品名称:">{{addForm.goodData.gname}}
      </el-form-item>
      <el-form-item label="商品库存:">{{addForm.goodData.stock}}
      </el-form-item>
      <el-form-item label="商品原始价格 - 市场价:">{{addForm.goodData.marketPrice}}
      </el-form-item>
      <el-form-item label="商品原始价格 - 商城价">{{addForm.goodData.purchasePrice}}
      </el-form-item>
    </el-form>
    <el-form :model="addForm.adjustData" label-width="auto" style="max-width: 400px">
      <el-form-item label="商品调价幅度：">{{addForm.adjustData.adjustWay}}  {{addForm.adjustData.percent}}%
      </el-form-item>
      <el-form-item label="市场价" prop="adjustMarketPrice">{{addForm.adjustData.adjustMarketPrice}}
      </el-form-item>
      <el-form-item label="商城价" prop="adjustShoppingPrice">{{addForm.adjustData.adjustShoppingPrice}}
      </el-form-item>
      <el-form-item label="希望价格生效时间:" prop="beginTime">{{addForm.adjustData.beginTime}}
      </el-form-item>
      <el-form-item label="希望价格失效时间:" prop="endTime">{{addForm.adjustData.endTime}}
      </el-form-item>
      <el-form-item label="调价原因:" prop="reason">{{addForm.adjustData.reason}}
      </el-form-item>
      <el-form-item label="备注:" prop="remark">{{addForm.adjustData.remark}}
      </el-form-item>
      <h5>审核记录</h5>
      <hr/>
      <el-form-item label="审批员工：" prop="processStatus">{{addForm.adjustData.applyUser}}
      </el-form-item>
      <el-form-item label="审批状态：" prop="processStatus" >{{addForm.adjustData.processStatus}}
      </el-form-item>
      <el-form-item label="审批时间：" prop="processStatus" v-if="addForm.adjustData.processStatus != '审核中'">{{addForm.adjustData.updateTime}}
      </el-form-item>
      <el-form-item label="审批时间：" prop="processStatus" v-if ="addForm.adjustData.processStatus === '审核中'">
      </el-form-item>
      <el-form-item label="审批意见：" prop="checkAdvice">{{addForm.adjustData.checkAdvice}}
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="checkDialogVisible = false">返回</el-button>
      </div>
    </template>
  </el-dialog>

</template>



<script setup>
import {ref, reactive, onMounted, getCurrentInstance} from 'vue'
const {proxy} = getCurrentInstance()
import axios from '/src/plugin/axios.js'
import {ElMessage} from "element-plus";

const userMap = {
  1: "admin",
  3: "manager",
}

/**
 * 审核跟踪功能
 *
 * */

const checkDialogVisible = ref(false)
const checkFormOpen = (currentRow) => {
  checkDialogVisible.value = true;
  editFormOpen(currentRow);
  editDialogVisible.value = false;
}


/**
 * 修改功能
 *
 * */
const editDialogVisible = ref(false)
const editFormOpen = (currentRow) => {
  editDialogVisible.value = true;
  if(currentRow.applyUser in userMap){
    currentRow.applyUser = userMap[currentRow.applyUser];
  }
  addForm.adjustData = currentRow;
  axios.get("goods/" + currentRow.goodsId).then(response => {
    if (response != null && response.code === 200) {
      addForm.goodData = response.data;
    }
  })
}
const editFromSubmit =()=>{
  editDialogVisible.value = false;
  //只要修改后就算自动提交变成审核中;
  addForm.adjustData.processStatus = "审核中";
  if(addForm.adjustData.adjustWay = "绝对值"){
    addForm.adjustData.percent = 0;
  }
  axios.postJson("price/save/update", addForm.adjustData).then(response => {
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
  addForm.goodData = currentRow;
}



/**
 * 添加功能
 *
 * */

const addDialogVisible = ref(false)

const addForm = reactive({
  goodData:{},
  adjustData: {}
})


const addFormOpen = () => {
  addDialogVisible.value = true;
}

const addFromSubmit = () => {
  addDialogVisible.value = false;
  addForm.adjustData.goodsId = addForm.goodData.id;
  addForm.adjustData.goodsName = addForm.goodData.gname;
  addForm.adjustData.processStatus = "审核中";
  if(addForm.adjustData.adjustWay = "绝对值"){
    addForm.adjustData.percent = 0;
  }
  axios.postJson("price/save/update", addForm.adjustData).then(response => {
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

let queryForm = reactive({})

//table列表数据
const tableList = reactive({tableData: []})

const statusMap = {
  0: "已通过",
  1: "未通过",
  2: "审核中",
}


/**
 * 分页组件
 */
//页码查询
let productPage = reactive({
  pageTotal: 100,
  pageNum: 1,
  pageSize: 5,
  ggAdjust: queryForm,
  adjustList: [],
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
  if (queryForm.processStatus in statusMap) {
    queryForm.processStatus = statusMap[queryForm.processStatus]
  }
  let param = productPage;
  productPage.ggAdjust = queryForm;
  listCondition(param);
}
const onReset = () => {
  queryForm.goodsName = '';
  queryForm.adjustMarketPrice = '';
  queryForm.beginTime = '';
  queryForm.endTime = '';
  queryForm.processStatus = '';
  productPage.ggAdjust = queryForm;
  listCondition(productPage)

}
onMounted(() => {
  listCondition(productPage)
})

const listCondition = (param) => {
  axios.postJson("price/search", param).then((resp) => {
    productPage = resp.data;
    tableList.tableData = productPage.adjustList;
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