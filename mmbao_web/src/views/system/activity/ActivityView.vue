<template>
    <h6>活动管理 > 促销活动申请</h6>
    <el-form ref="queryFormRef" class="query-form" :inline="true" :model="requestData">
        <el-form-item label="活动名称" prop="activityName">
            <el-select v-model="requestData.activityName" placeholder="活动名称" style="width: 100px">
                <el-option v-for="options in tableList" :label="options.activityName" :value="options.activityName" />
            </el-select>
        </el-form-item>
        <el-form-item label="审批状态" prop="reviewStatus">
            <el-select v-model="requestData.reviewStatus" placeholder="审批状态" style="width: 100px">
                <el-option label="未审核" :value="0" />
                <el-option label="审核通过" :value="1" />
                <el-option label="审核未通过" :value="2" />
            </el-select>
        </el-form-item>
        <el-form-item label="活动类型" prop="activityType">
            <el-select v-model="requestData.activityType" placeholder="活动类型" style="width: 100px">
                <el-option label="连续促销" :value="0" />
                <el-option label="每日限量" :value="1" />
            </el-select>
        </el-form-item>
        <el-form-item label="活动开始时间">
            <el-date-picker v-model="requestData.activityBeginTime" type="date" placeholder="限时开始时间" />
        </el-form-item>
        <el-form-item label="希望价格生效时间">
            <el-date-picker v-model="requestData.priceBeginTime" type="datetime" placeholder="希望价格生效时间" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
            <el-button @click="onReset">重置</el-button>
            <el-button type="primary" @click="addFormOpen">新增促销申请</el-button>
        </el-form-item>
    </el-form>
    <!-- 菜单主体 -->
    <el-table :data="tableList" style="width: 90%">
        <el-table-column prop="id" label="序号" width="180" />
        <el-table-column prop="activityName" label="活动名称" width="180" />
        <el-table-column prop="activityBeginTime" label="促销活动开始时间" width="180" />
        <el-table-column prop="priceBeginTime" label="希望价格生效时间" width="180" />
        <el-table-column prop="activityType" label="活动类型" width="180">
            <template #default="scope">
                <el-tag v-if="scope.row.activityType == 0"> 连续促销</el-tag>
                <el-tag v-else-if="scope.row.activityType == 1"> 每日限量</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="reviewStatus" label="审批状态" width="180">
            <template #default="scope">
                <el-tag v-if="scope.row.reviewStatus == 0"> 未审核</el-tag>
                <el-tag v-else-if="scope.row.reviewStatus == 1"> 审核通过</el-tag>
                <el-tag v-else-if="scope.row.reviewStatus == 2"> 审核未通过</el-tag>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="300" :center="true">
            <template #default="scope">
                <el-button type="warning" @click="checkFormOpen(scope.row)">查看</el-button>
                <el-button type="warning">修改</el-button>
                <el-button type="warning">删除</el-button>
                <el-button type="warning">审核</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="pageInfo.current" v-model:page-size="pageInfo.pageSize" :page-sizes="[3, 6, 9]"
        layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total" @size-change="handleSizeChange"
        @current-change="handleCurrentChange" />
    <!-- 添加对话框 -->
    <el-dialog v-model="addDialogVisible" title="添加促销商品信息" width="500">

        <el-form ref="addFormRef" :model="addForm" label-width="auto" style="max-width: 400px">
            <el-form-item label="活动开始时间">
                <el-date-picker v-model="addForm.activityBeginTime" type="date" placeholder="限时开始时间" />
            </el-form-item>
            <el-form-item label="活动结束时间">
                <el-date-picker v-model="addForm.activityEndTime" type="date" placeholder="活动结束时间" />
            </el-form-item>
            <el-form-item label="活动名称" prop="activityName">
                <el-input v-model="addForm.activityName" placeholder="活动名称" />
            </el-form-item>
            <el-form-item label="活动类型" prop="activityType">
                <el-select v-model="addForm.activityType" placeholder="活动类型" style="width: 100px">
                    <el-option label="连续促销" :value="0" />
                    <el-option label="每日限量" :value="1" />
                </el-select>
            </el-form-item>
            <el-form-item label="希望价格生效时间">
                <el-date-picker v-model="addForm.priceBeginTime" type="date" placeholder="希望价格生效时间" />
            </el-form-item>
            <el-form-item label="希望价格失效时间">
                <el-date-picker v-model="addForm.priceEndTime" type="date" placeholder="希望价格失效时间" />
            </el-form-item>
            <el-form-item label="活动原因" prop="activityReason">
                <el-input v-model="addForm.activityReason" placeholder="活动原因" />
            </el-form-item>
            <el-form-item label="备注" prop="remark">
                <el-input v-model="addForm.remark" placeholder="备注" />
            </el-form-item>
            <h5>订单商品信息</h5>
            <el-divider style="margin: 15px 0;"></el-divider>
            <el-button type="primary" @click="showSelectGoods">选择商品</el-button>
            <!-- 动态表单项容器 -->
            <div v-for="(item, index) in addForm.activityGoodsList" :key="index" style="max-width: 900px;">
                <el-form-item :label="'商品 ' + (index + 1)">
                    <el-form-item label="商品id">
                        <el-input v-model="item.id" disabled />
                    </el-form-item>
                    <el-form-item label="商品名称">
                        <el-input v-model="item.gname" disabled />
                    </el-form-item>
                    <el-form-item label="商品库存">
                        <el-input v-model="item.stock" disabled />
                    </el-form-item>
                    <el-form-item label="促销数量">
                        <el-input v-model="item.promotionAmount" />
                    </el-form-item>
                    <el-form-item label="折扣类型">
                        <el-select v-model="item.discountType" placeholder="活动类型" style="width: 100px">
                            <el-option label="百分比" :value="0" />
                            <el-option label="绝对值" :value="1" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="市场价">
                        <el-input v-model="item.marketPrice" disabled />
                    </el-form-item>
                    <el-form-item label="商城价">
                        <el-input v-model="item.shopPrice" disabled />
                    </el-form-item>
                    <el-form-item label="促销价">
                        <el-input v-model="item.mallPromotionPrice" />
                    </el-form-item>
                </el-form-item>
            </div>
            <el-form-item label="审核人" prop="reviewer">
                <el-select v-model="addForm.reviewerId" placeholder="审核人">
                    <el-option
                        v-for="options in reviewerList.reviewerData.filter(item => item.userId !== userModel.user.userId)"
                        :label="options.userName" :value="options.userId" />
                </el-select>
            </el-form-item>
        </el-form>

        <template #footer>
            <div class="dialog-footer">
                <el-button @click="addDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="addFormSubmit">保存</el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted, getCurrentInstance } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from '/src/plugin/axios.js'
import userStore from "/src/stores/user.js"
import router from '/src/router/index'
let userModel = userStore()

/**
 * 新增组件
 */
//选择活动商品弹窗组件
const showSelectGoods = () => {
    userModel.activitygoods = [];
    router.push("/selectGoodsView")
}

const addFormRef = ref();
const addDialogVisible = ref(false)
const addForm = reactive({
    activityBeginTime: "",
    activityEndTime: "",
    activityGoodsList: [],
    activityName: "",
    activityReason: "",
    activityType: "",
    priceBeginTime: "",
    priceEndTime: "",
    remark: "",
    reviewerId: "",
    reviewerName: "",
})
const addFormOpen = () => {
    // 使用Vue的reactive方法来正确更新activityGoodsList
    addForm.activityGoodsList = userModel.activitygoods
    .map(item => ({
        // 将id重命名为goodsId，保留其他所有属性
        ...item,
        goodsId: item.id,
    }));

    console.log(addForm.activityGoodsList);
    addDialogVisible.value = true;

    // addForm.activityGoodsList = userModel.activitygoods
}

const addFormSubmit = async () => {
    //关闭对话框
    addDialogVisible.value = false;
    //发送添加请求
    addForm.creatorId = userModel.user.userId
    console.log(addForm);
    try {
        const { code } = await axios.postJson("/activity/add", addForm);
        // console.log(data);
        if (code === 200) {
            ElMessage.success("添加成功");
        } else {
            ElMessage.error("数据加载失败，请稍后重试");
        }
    } catch (error) {
        console.error("请求错误:", error);
        ElMessage.error("网络错误，请检查网络连接");
    }

}
/**
* 条件查询
*/
const onSubmit = () => {
    //   console.log(requestData);
    queryData();
}
const onReset = () => {
    queryFormRef.value.resetFields();
    requestData.activityBeginTime = null;
    requestData.priceBeginTime = null;
    queryData();
}
//获得查询表单组件对象
const queryFormRef = ref()
//审核人下拉列表
const reviewerList = reactive({ reviewerData: [] })
const selectReviewerList = () => {
    axios.get("/user/reviewer")
        .then((resp) => {
            if (resp.code == 200) {
                reviewerList.reviewerData = resp.data
            } else {
                ElMessage.error("数据加载失败，请稍后重试");
            }
        })
    console.log(reviewerList);
}

// 初始化tableList和pageInfo
const tableList = ref([]);
const pageInfo = reactive({
    current: 1,
    pageSize: 9,
    total: 0
});

const requestData = reactive({

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
            activity: {
                ...requestData
            }
        };
        const { data, code } = await axios.postJson("/activity/list", postData);
        // console.log(data);
        if (code === 200) {
            const { records, total } = data;
            tableList.value = records;
            pageInfo.total = total;
            // console.log(tableList.value);
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
    selectReviewerList();
});

</script>

<style scoped></style>