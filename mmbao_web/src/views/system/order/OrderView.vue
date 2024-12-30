<template>
  <h6>B2C销售管理 > B2C订单管理</h6>
  <el-form ref="queryFormRef" class="query-form" :inline="true" :model="requestData">
    <el-form-item label="订单编号" prop="orderCode">
      <el-input v-model="requestData.orderCode" placeholder="订单编号" />
    </el-form-item>
    <el-form-item label="订单类型" prop="orderType">
      <el-select v-model="requestData.orderType" placeholder="订单类型" style="width: 100px">
        <el-option label="内部订单" :value="0" />
        <el-option label="商城订单" :value="1" />
        <el-option label="淘宝网店" :value="2" />
        <el-option label="当当网店" :value="3" />
      </el-select>
    </el-form-item>
    <el-form-item label="支付方式" prop="cneeDeliMethod">
      <el-select v-model="requestData.cneeDeliMethod" placeholder="支付方式" style="width: 100px">
        <el-option label="货到付款" :value="1" />
        <el-option label="款到发货" :value="0" />
      </el-select>
    </el-form-item>
    <el-form-item label="业务类型" prop="bussinessType">
      <el-select v-model="requestData.bussinessType" placeholder="业务类型" style="width: 100px">
        <el-option label="普通类型" :value="1" />
        <el-option label="号卡套餐" :value="0" />
      </el-select>
    </el-form-item>
    <el-form-item label="下单日期：">
      <el-date-picker v-model="requestData.startDate" type="datetime" placeholder="开始日期" />
      <el-date-picker v-model="requestData.endDate" type="datetime" placeholder="结束日期" />
    </el-form-item>
    <el-form-item label="订单动作" prop="orderAction">
      <el-select v-model="requestData.orderAction" placeholder="订单动作" style="width: 100px">
        <el-option label="换购" :value="2" />
        <el-option label="订购" :value="1" />
        <el-option label="支付" :value="0" />
      </el-select>
    </el-form-item>
    <el-form-item label="订单状态" prop="orderState">
      <el-select v-model="requestData.orderState" placeholder="订单状态" style="width: 100px">
        <el-option label="已出库" :value="1" />
        <el-option label="待出库" :value="0" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
      <el-button @click="onReset">重置</el-button>
      <el-button type="primary" @click="addFormOpen">导入订单</el-button>
    </el-form-item>
  </el-form>
  <!-- 菜单主体 -->
  <el-table :data="tableList" style="width: 100%">
    <el-table-column prop="id" label="序号" width="180" />
    <el-table-column prop="orderCode" label="订单编号" width="180" />
    <el-table-column prop="goodsList" label="商品清单" width="180" />
    <el-table-column prop="orderType" label="订单类型" width="180">
      <template #default="scope">
        <!-- scope.row 当前行数据 -->
        <el-tag v-if="scope.row.orderType == 0"> 内部订单</el-tag>
        <el-tag v-else-if="scope.row.orderType == 1"> 商城订单</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="orderAction" label="订单动作" width="180">
      <template #default="scope">
        <el-tag v-if="scope.row.orderAction == 0"> 支付</el-tag>
        <el-tag v-else-if="scope.row.orderAction == 1"> 订购</el-tag>
        <el-tag v-else-if="scope.row.orderAction == 2"> 换购</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="bussinessType" label="业务类型" width="180">
      <template #default="scope">
        <el-tag v-if="scope.row.bussinessType == 0"> 号卡套餐</el-tag>
        <el-tag v-else-if="scope.row.bussinessType == 1"> 普通类型</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="cneeDeliMethod" label="支付方式" width="180">
      <template #default="scope">
        <el-tag v-if="scope.row.cneeDeliMethod == 0"> 款到发货</el-tag>
        <el-tag v-else-if="scope.row.cneeDeliMethod == 1"> 货到付款</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="cneeDeliWay" label="配送方式" width="180">
      <template #default="scope">
        <el-tag v-if="scope.row.cneeDeliWay == 0"> EMS</el-tag>
        <el-tag v-else-if="scope.row.cneeDeliWay == 1"> 其它</el-tag>
      </template>
    </el-table-column>>
    <el-table-column prop="orderState" label="订单状态" width="180">
      <template #default="scope">
        <el-tag v-if="scope.row.orderState == 0" type="warning"> 待出库</el-tag>
        <el-tag v-else-if="scope.row.orderState == 1"> 已出库</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="操作" width="200">
      <template #default="scope">
        <el-button type="warning" @click="checkFormOpen(scope.row)">查看</el-button>
        <el-button type="warning" @click="outBound(scope.row)">出库</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination v-model:current-page="pageInfo.current" v-model:page-size="pageInfo.pageSize" :page-sizes="[3, 6, 9]"
    layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total" @size-change="handleSizeChange"
    @current-change="handleCurrentChange" />

  <!-- 导入对话框 -->
  <el-dialog v-model="addDialogVisible" title="导入文件" width="500">
    <el-upload ref="uploadRef" action="http:///192.168.0.158:8081/mmbao/order/import" :on-success="handleSuccess"
      :on-error="handleError" :auto-upload="false" show-file-list accept=".csv, .xls, .xlsx">
      <el-button slot="trigger">选取文件</el-button>
    </el-upload>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button style="margin-left: 10px;" @click="submitUpload">立即上传</el-button>
      </div>
    </template>

  </el-dialog>

  <!-- 查看对话框 -->
  <el-dialog v-model="checkDialogVisible" width="800">
    <el-form ref="orderForm" :model="formData" label-width="120px">
      <h5>订单基本资料</h5>
      <el-divider style="margin: 15px 0;"></el-divider>
      <el-row>
        <el-col :span="12">
          <el-form-item label="订单编号">
            <el-input v-model="formData.orderCode" disabled placeholder="订单编号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="下单日期">
            <el-input v-model="formData.orderTime" disabled placeholder="选择日期时间" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="订单类型">
            <el-select v-model="formData.orderType" disabled placeholder="订单类型">
              <el-option label="内部订单" :value="0" />
              <el-option label="商城订单" :value="1" />
              <el-option label="淘宝网店" :value="2" />
              <el-option label="当当网店" :value="3" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="订单动作">
            <el-select v-model="formData.orderAction" disabled placeholder="订单动作">
              <el-option label="支付" :value="0" />
              <el-option label="订购" :value="1" />
              <el-option label="换购" :value="2" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="业务类型">
            <el-select v-model="formData.bussinessType" disabled placeholder="业务类型">
              <el-option label="号卡套餐" :value="0" />
              <el-option label="普通类型" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="订单状态">
            <el-select v-model="formData.orderState" disabled placeholder="订单状态">
              <el-option label="待出库"  :value="0" />
              <el-option label="已出库" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <h5>收货人资料</h5>
      <el-divider style="margin: 15px 0;"></el-divider>
      <el-row>
        <el-col :span="12">
          <el-form-item label="收货人姓名">
            <el-input v-model="formData.cneeName" disabled placeholder="收货人姓名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="会员账号">
            <el-input v-model="formData.cneeAccount" disabled placeholder="会员账号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系电话">
            <el-input v-model="formData.cneePhone" disabled placeholder="联系电话" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系手机">
            <el-input v-model="formData.cneeCellphone" disabled placeholder="联系手机" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="邮政编码">
            <el-input v-model="formData.cneePostalCode" disabled placeholder="邮政编码" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="收货地址">
            <el-input v-model="formData.cneeAddress" disabled placeholder="收货地址" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="配送地区">
            <el-input v-model="formData.cneeDeliArea" disabled placeholder="配送地区" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="配送方式">
            <el-select v-model="formData.cneeDeliWay" disabled placeholder="配送方式">
              <el-option label="EMS"  :value="0" />
              <el-option label="其他" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="配送费用">
            <el-input v-model="formData.cneeDeliFare" disabled placeholder="配送费用" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="支付方式">
            <el-select v-model="formData.cneeDeliMethod" disabled placeholder="支付方式">
              <el-option label="款到发货"  :value="0" />
              <el-option label="货到付款" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="自提时间">
            <el-input v-model="formData.cneePickTime" disabled placeholder="自提时间" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="电子邮箱">
            <el-input v-model="formData.cneeEmail" disabled placeholder="电子邮箱" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="汇款人">
            <el-input v-model="formData.remittor" disabled placeholder="汇款人" />
          </el-form-item>
        </el-col>
        <el-col :span="12" />
        <el-col :span="24">
          <el-form-item label="备注">
            <el-input v-model="formData.cneeName" disabled placeholder="无" />
          </el-form-item>
        </el-col>
      </el-row>
      <h5>订单商品信息</h5>
      <el-divider style="margin: 15px 0;"></el-divider>
      <el-table border stripe :data="formData.omOrderGoodsList" style="width: 100%">
        <el-table-column prop="goodsCode" label="商品编号" />
        <el-table-column prop="goodsName" label="商品名称" />
        <el-table-column prop="goodsPrice" label="单价" />
        <el-table-column prop="count" label="数量" />
        <el-table-column prop="price" label="总金额" />
      </el-table>
    </el-form>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted, getCurrentInstance } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from '/src/plugin/axios.js'
/**
 * 出库
 */
const outBound = async (currentRow) =>{
  await axios.get("/order/edit?"+"id="+currentRow.id+"&orderState="+1);
  queryData();
}
/**
 * 查看组件
 */
const checkDialogVisible = ref(false)
const checkFormOpen = async (currentRow) => {
  checkDialogVisible.value = true;
  const { data, code } = await axios.get("/order/id/" + currentRow.id);
  console.log(data);
  if (code === 200) {
    formData.value = data;
    console.log(formData.value);
  } else {
    ElMessage.error("数据加载失败，请稍后重试");
  }
}
const formData = ref({

});

/**
 * 导入组件
 */
const addDialogVisible = ref(false)
const addFormOpen = () => {
  addDialogVisible.value = true;
}

const handleSuccess = () => {
  ElMessage.success("文件上传成功")
  queryData();
}

const handleError = () => {
  ElMessage.error("文件上传失败")
}

const uploadRef = ref(null);

const submitUpload = () => {
  addDialogVisible.value = false;
  if (uploadRef.value) {
    uploadRef.value.submit();
  }
};

/**
 * 条件查询
 */
const onSubmit = () => {
  console.log(requestData);
  queryData();
}
const onReset = () => {
  queryFormRef.value.resetFields();
  requestData.startDate = null;
  requestData.endDate = null;
  queryData();
}
//获得查询表单组件对象
const queryFormRef = ref()

// 初始化tableList和pageInfo
const tableList = ref([]);
const pageInfo = reactive({
  current: 1,
  pageSize: 9,
  total: 0
});

const requestData = reactive({
  orderCode: "",
  orderType: "",
  cneeDeliMethod: "",
  bussinessType: "",
  orderTime: "",
  orderAction: "",
  orderState: "",
  goodsList: "",
  startDate: "",
  endDate: "",
});

// 更新请求参数
const updateQueryParams = (formValues) => {
  Object.assign(requestData, formValues);
};

// 处理页码变更
const handleCurrentChange = (val) => {
  requestData.pageNum = val;
  queryData();
};

// 处理每页显示数量变更
const handleSizeChange = (val) => {
  requestData.pageSize = val;
  requestData.pageNum = 1; // 重置页码
  queryData();
};

// 查询数据
const queryData = async () => {
  try {
    const postData = {
      pageNum: pageInfo.current.toString(), // 使用分页信息的当前页
      pageSize: pageInfo.pageSize.toString(), // 使用分页信息的每页大小
      omOrder: {
        ...requestData // 将查询条件展平到omOrder对象中
      }
    };
    const { data, code } = await axios.postJson("/order/list", postData);
    // console.log(data);
    if (code === 200) {
      const { records, total } = data; // 解构赋值
      tableList.value = records;
      pageInfo.total = total;
      console.log(tableList);
    } else {
      ElMessage.error("数据加载失败，请稍后重试");
    }
  } catch (error) {
    console.error("请求错误:", error);
    ElMessage.error("网络错误，请检查网络连接");
  }
};

// 组件挂载时初始化数据
onMounted(() => {
  updateQueryParams({
    pageNum: pageInfo.current,
    pageSize: pageInfo.pageSize
  });
  queryData();
});

</script>

<style scoped>
.el-divider {
  width: 100%;
}
</style>