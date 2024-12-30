<template>
  <hr/>
  <h3> 商品管理 > 价格调整</h3>
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
        <el-button type="success" @click="addFormOpen(scope.row)">审核</el-button>
        <el-button type="success" @click="checkFormOpen(scope.row)">审核跟踪</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-pagination v-model:current-page="productPage.pageNum" v-model:page-size="productPage.pageSize"
                 :page-sizes="[3, 5, 10]" layout="total, sizes, prev, pager, next, jumper"
                 :total="productPage.pageTotal"
                 @size-change="handleSizeChange" @current-change="handleCurrentChange"/>

  <!-- 审核对话框 -->
  <el-dialog
      v-model="addDialogVisible"
      title="添加"
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
      <el-form-item label="审批意见：" prop="checkAdvice">
        <el-input v-model="addForm.adjustData.checkAdvice" placeholder="checkAdvice"/>
      </el-form-item>
      <el-form-item label="审批结果：" prop="processStatus">
        <el-radio-group v-model="addForm.adjustData.processStatus">
          <el-radio value="已通过">通过</el-radio>
          <el-radio value="未通过">不通过</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addDialogVisible = false">返回</el-button>
        <el-button type="primary" @click="addFromSubmit">保存</el-button>
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
      <el-form-item label="审批员工：" prop="processStatus">{{userMap[addForm.adjustData.applyUser]}}
      </el-form-item>
      <el-form-item label="审批状态：" prop="processStatus">{{addForm.adjustData.processStatus}}
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
import userStore  from "/src/stores/user.js"
let userModel = userStore();

const userMap = {
  1: "admin",
  3: "manager",
}

/**
 * 跟踪功能
 *
 * */

const checkDialogVisible = ref(false)
const checkFormOpen = (currentRow) => {
  checkDialogVisible.value = true;
  addFormOpen(currentRow);
  addDialogVisible.value = false;
}

/**
 * 审核功能
 *
 * */

const addDialogVisible = ref(false)

const addForm = reactive({
  goodData:{},
  adjustData: {}
})


const addFormOpen = (currentRow) => {
  addDialogVisible.value = true;
  addForm.adjustData = currentRow;
  axios.get("goods/" + currentRow.goodsId).then(response => {
    if (response != null && response.code === 200) {
      addForm.goodData = response.data;
    }
  })
}

const addFromSubmit = () => {
  addDialogVisible.value = false;
  axios.postJson("price/save/update", addForm.adjustData).then(response => {
    if (response != null && response.code === 200) {
      ElMessage({
        message: '审核成功',
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
})

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
  pageSize: 3,
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
  queryForm.applyUser = userModel.user.userId
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