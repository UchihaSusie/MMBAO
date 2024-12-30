<template>
  <hr/>
  <h3> 商品管理 > 商品管理</h3>
  <el-form ref="queryFormRef" class="query-form" :inline="true" :model="queryForm">
    <el-form-item label="商品名称：" prop="gname">
      <el-input v-model="queryForm.gname" placeholder="goods name"/>
    </el-form-item>
    <el-form-item label="商品型号：" prop="gmodel">
      <el-input v-model="queryForm.gmodel" placeholder="goods model"/>
    </el-form-item>
    <el-form-item label="商品颜色：" prop="gcolor">
      <el-select
          v-model="queryForm.gcolor"
          placeholder="请选择">
        <el-option label="白色" :value="0"/>
        <el-option label="黑色" :value="1"/>
        <el-option label="绚丽紫" :value="2"/>
        <el-option label="珍珠白" :value="3"/>
      </el-select>
    </el-form-item>
    <el-form-item label="商品品牌：" prop="gbrand">
      <el-select
          v-model="queryForm.gbrand"
          placeholder="请选择">
        <el-option v-for="(item, key) in brandMap" :label="item" :value="key"/>
      </el-select>
    </el-form-item>
    <el-form-item label="商品分类：" prop="gtype">
      <el-select
          v-model="queryForm.gtype"
          placeholder="请选择">
        <el-option v-for="(item, key) in typeMap" :label="item" :value="key"/>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onReset">重置</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="addClassOneOpen">创建一类产品</el-button>
      <el-button type="primary" @click="addClassTwoOpen">创建二类产品</el-button>
    </el-form-item>
  </el-form>

  <el-table ref="tableRef" :data="tableList.tableData" style="width: 90%">
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
        <el-button type="warning" @click="editFormOpen(scope.row)">修改</el-button>
        <el-button type="success" @click="detailFormOpen(scope.row)">查看</el-button>
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
    <el-form ref="editFormRef" :model="editForm.productData" label-width="auto" style="max-width: 400px">
      <el-form-item label="存货档案编码:" prop="coding">{{ editForm.productData.coding }}</el-form-item>
      <el-form-item label="基础库-分类:" prop="classify">{{ editForm.productData.classify }}</el-form-item>
      <el-form-item label="基础库品牌:" prop="brand">{{ editForm.productData.brand }}</el-form-item>
      <el-form-item label="基础库-型号:" prop="model">{{ editForm.productData.model }}</el-form-item>
      <el-form-item label="基础库-颜色:" prop="color">{{ editForm.productData.color }}</el-form-item>
      <el-form-item label="基础库-采购模式:" prop="purchasingPattern">{{ editForm.productData.purchasingPattern }}
      </el-form-item>
      <el-form-item label="基础库-铺货:" prop="distribution">{{ editForm.productData.distribution }}</el-form-item>
      <el-form-item label="基础库-备机:" prop="standby">{{ editForm.productData.standby }}</el-form-item>
    </el-form>
    <el-form ref="editFormRef" :model="editForm.goodData" label-width="auto" style="max-width: 400px">
      <el-form-item label="商品名称:" prop="gname">
        <el-input v-model="editForm.goodData.gname" placeholder="gname"/>
      </el-form-item>
      <el-form-item label="商品类别:" prop="gtype">
        <el-select
            v-model="editForm.goodData.gtype"
            placeholder="gtype">
          <el-option label="无" :value="0"/>
          <el-option v-for="options in optionsList.optionsData" :label="options.typeName" :value="options.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="市场价:" prop="marketPrice">
        <el-input v-model="editForm.goodData.marketPrice" placeholder="marketPrice"/>
      </el-form-item>
      <el-form-item label="商城价:" prop="shopPrice">
        <el-input v-model="editForm.goodData.shopPrice" placeholder="shopPrice"/>
      </el-form-item>
      <el-form-item label="最低库存量:" prop="minStock">
        <el-input v-model="editForm.goodData.minStock" placeholder="minStock"/>
      </el-form-item>
      <el-form-item label="是否可采购:" prop="remark">
        <el-select
            v-model="editForm.goodData.remark"
            placeholder="remark">
          <el-option label="可采购" :value="0"/>
          <el-option label="不可采购" :value="1"/>
        </el-select>
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

  <!-- 查看对话框 -->
  <el-dialog
      v-model="detailDialogVisible"
      title="查看"
      width="500"
  >
    <el-form ref="detailFormRef" :model="detailForm.productData" label-width="auto" style="max-width: 400px">
      <el-form-item label="存货档案编码:" prop="coding">{{ detailForm.productData.coding }}</el-form-item>
      <el-form-item label="基础库-分类:" prop="classify">{{ detailForm.productData.classify }}</el-form-item>
      <el-form-item label="基础库品牌:" prop="brand">{{ detailForm.productData.brand }}</el-form-item>
      <el-form-item label="基础库-型号:" prop="model">{{ detailForm.productData.model }}</el-form-item>
      <el-form-item label="基础库-颜色:" prop="color">{{ detailForm.productData.color }}</el-form-item>
      <el-form-item label="基础库-采购模式:" prop="purchasingPattern">{{ detailForm.productData.purchasingPattern }}
      </el-form-item>
      <el-form-item label="基础库-铺货:" prop="distribution">{{ detailForm.productData.distribution }}</el-form-item>
      <el-form-item label="基础库-备机:" prop="standby">{{ detailForm.productData.standby }}</el-form-item>
    </el-form>
    <el-form ref="detailFormRef" :model="detailForm.goodData" label-width="auto" style="max-width: 400px">
      <el-form-item label="商品名称:" prop="gname">{{ detailForm.goodData.gname }}</el-form-item>
      <el-form-item label="商品类别:" prop="gtype">{{ detailForm.goodData.gtype }}</el-form-item>
      <el-form-item label="市场价:" prop="marketPrice">{{ detailForm.goodData.marketPrice }}</el-form-item>
      <el-form-item label="商城价:" prop="shopPrice">{{ detailForm.goodData.shopPrice }}</el-form-item>
      <el-form-item label="最低库存量:" prop="minStock">{{ detailForm.goodData.minStock }}</el-form-item>
      <el-form-item label="是否可采购:" prop="remark">{{ detailForm.goodData.remark }}</el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="detailDialogVisible = false">返回</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 创建一类产品 -->
  <el-dialog
      v-model="OneDialogVisible"
      title="创建一类产品"
      width="500"
  >
    <el-form ref="oneFormRef" :model="oneForm.productData" label-width="auto" style="max-width: 400px">
      <el-form-item label="存货档案编码:" prop="coding">
        <el-select
            v-model="oneForm.productData.coding"
            placeholder="coding">
          <el-option v-for="options in productOptionsList.optionsData" :label="options.coding" :value="options.coding"/>
        </el-select>
        <el-button type="primary" @click="choosePro">请确认选择该存货档案</el-button>
      </el-form-item>
      <el-form-item label="基础库-分类:" prop="classify">{{ oneForm.productData.classify }}</el-form-item>
      <el-form-item label="基础库品牌:" prop="brand">{{ oneForm.productData.brand }}</el-form-item>
      <el-form-item label="基础库-型号:" prop="model">{{ oneForm.productData.model }}</el-form-item>
      <el-form-item label="基础库-颜色:" prop="color">{{ oneForm.productData.color }}</el-form-item>
      <el-form-item label="基础库-采购模式:" prop="purchasingPattern">{{ oneForm.productData.purchasingPattern }}
      </el-form-item>
      <el-form-item label="基础库-铺货:" prop="distribution">{{ oneForm.productData.distribution }}</el-form-item>
      <el-form-item label="基础库-备机:" prop="standby">{{ oneForm.productData.standby }}</el-form-item>
    </el-form>
    <el-form ref="oneFormRef" :model="oneForm.goodData" label-width="auto" style="max-width: 400px">
      <el-form-item label="商品名称:" prop="gname">
        <el-input v-model="oneForm.goodData.gname" placeholder="gname"/>
      </el-form-item>
      <el-form-item label="商品类别:" prop="gtype">
        <el-select
            v-model="oneForm.goodData.gtype"
            placeholder="gtype">
          <el-option v-for="options in optionsList.optionsData" :label="options.typeName" :value="options.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="市场价:" prop="marketPrice">
        <el-input v-model="oneForm.goodData.marketPrice" placeholder="marketPrice"/>
      </el-form-item>
      <el-form-item label="商城价:" prop="shopPrice">
        <el-input v-model="oneForm.goodData.shopPrice" placeholder="shopPrice"/>
      </el-form-item>
      <el-form-item label="最低库存量:" prop="minStock">
        <el-input v-model="oneForm.goodData.minStock" placeholder="minStock"/>
      </el-form-item>
      <el-form-item label="是否可采购:" prop="remark">
        <el-select
            v-model="oneForm.goodData.remark"
            placeholder="remark">
          <el-option label="可采购" :value="0"/>
          <el-option label="不可采购" :value="1"/>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="OneDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="OneFormSubmit">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 创建二类产品 -->
  <el-dialog
      v-model="TwoDialogVisible"
      title="创建二类产品"
      width="500"
  >
    <el-form ref="twoFormRef" :model="twoForm.goodData" label-width="auto" style="max-width: 400px">
      <el-form-item label="商品品牌:" prop="brand">
        <el-select
            v-model="twoForm.goodData.gbrand"
            placeholder="请选择:">
          <el-option v-for="item in brandMap" :label="item.value" :value="item"/>
        </el-select>
      </el-form-item>
      <el-form-item label="商品型号:" prop="gmodel">
        <el-input v-model="twoForm.goodData.gmodel" placeholder="gmodel"/>
      </el-form-item>
      <el-form-item label="商品颜色:" prop="coding">
        <el-select
            v-model="twoForm.goodData.gcolor"
            placeholder="请选择">
          <el-option label="白色" :value="0"/>
          <el-option label="黑色" :value="1" />
          <el-option label="绚丽紫" :value="2" />
          <el-option label="珍珠白" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="商品名称:" prop="gname">
        <el-input v-model="twoForm.goodData.gname" placeholder="gname"/>
      </el-form-item>
      <el-form-item label="商品类别:" prop="gtype">
        <el-select
            v-model="twoForm.goodData.gtype"
            placeholder="gtype">
          <el-option v-for="options in optionsList.optionsData" :label="options.typeName" :value="options.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="市场价:" prop="marketPrice">
        <el-input v-model="twoForm.goodData.marketPrice" placeholder="marketPrice"/>
      </el-form-item>
      <el-form-item label="商城价:" prop="shopPrice">
        <el-input v-model="twoForm.goodData.shopPrice" placeholder="shopPrice"/>
      </el-form-item>
      <el-form-item label="最低库存量:" prop="minStock">
        <el-input v-model="twoForm.goodData.minStock" placeholder="minStock"/>
      </el-form-item>
      <el-form-item label="是否可采购:" prop="remark">
        <el-select
            v-model="twoForm.goodData.remark"
            placeholder="remark">
          <el-option label="可采购" :value="0"/>
          <el-option label="不可采购" :value="1"/>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="TwoDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="TwoFormSubmit">
          保存
        </el-button>
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
 *
 * 创建二类产品
 *
 * */
//TODO:二类产品创建了没有办法查看和修改应为没有对应的productId，有逻辑匹配吗，没有就随机生成
const remarkMap = {
  0: "可采购",
  1: "不可采购"
}

const twoFormRef = ref();

const TwoDialogVisible = ref(false)

const twoForm = reactive({
  goodData: {}
})
const addClassTwoOpen = () => {
  TwoDialogVisible.value = true;
}
const TwoFormSubmit = () => {
  if (twoForm.goodData.remark in remarkMap) {
    twoForm.goodData.remark = remarkMap[twoForm.goodData.remark]
  }
  axios.postJson("/goods/save/update", twoForm.goodData).then(response => {
        if (response != null && response.code === 200) {
          //保存成功
          ElMessage({
            message: '添加成功',
            type: 'success'
          });
          //保存结束刷新页面
          onReset();
        }
      }
  )
  TwoDialogVisible.value = false;
}
/**
 *
 * 创建一类产品
 *
 * */

const productOptionsList = reactive({optionsData: []})

const oneFormRef = ref();

const OneDialogVisible = ref(false)

const oneForm = reactive({
  goodData: {},
  productData: {}
})

const addClassOneOpen = () => {
  axios.get("product/listAll").then((resp) => {
    productOptionsList.optionsData = resp.data;
  })
  OneDialogVisible.value = true;
}

const OneFormSubmit = () => {
  oneForm.goodData.productId = oneForm.productData.id;
  axios.postJson("/product/save/update", oneForm.productData).then(response => {
    if (response != null && response.code === 200) {
      //保存product成功，下面保存对应的good
      if (oneForm.goodData.remark in remarkMap) {
        oneForm.goodData.remark = remarkMap[oneForm.goodData.remark]
      }
      axios.postJson("/goods/save/update", oneForm.goodData).then(response => {
        if (response != null && response.code === 200) {
          //保存成功
          ElMessage({
            message: '添加成功',
            type: 'success'
          });
          //保存结束刷新页面
          onReset();
        }
      })
    } else {
      ElMessage({
        message: '添加失败',
        type: 'warning'
      });
    }
  })
  OneDialogVisible.value = false;
}
const choosePro = () => {
  axios.post("product/code", oneForm.productData)
      .then((resp) => {
        oneForm.productData = resp.data;
      })
}


/**
 * 修改功能
 *
 *
 */


const optionsList = reactive({optionsData: []})

const editFormRef = ref();

const editDialogVisible = ref(false)

const editForm = reactive({
  goodData: {},
  productData: {}
})

const editFormOpen = (currentRow) => {
  axios.get("product/" + currentRow.productId)
      .then((resp) => {
        editForm.productData = resp.data;
      })
  axios.get("goods/" + currentRow.id)
      .then((resp) => {
        editForm.goodData = resp.data;
      })
  editDialogVisible.value = true;
}
const editFormSubmit = () => {
  if (editForm.goodData.remark in remarkMap) {
    editForm.goodData.remark = remarkMap[editForm.goodData.remark]
  }
  axios.postJson("/goods/save/update", editForm.goodData).then(response => {
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
 * 查看功能
 *
 * */
const detailFormRef = ref();

const detailDialogVisible = ref(false)

const detailForm = reactive({
  goodData: {},
  productData: {}
})

const detailFormOpen = (currentRow) => {
  axios.get("product/" + currentRow.productId)
      .then((resp) => {
        detailForm.productData = resp.data;
      })
  axios.get("goods/" + currentRow.id)
      .then((resp) => {
        detailForm.goodData = resp.data;
      })
  detailDialogVisible.value = true;
}
/**
 * 删除行
 * */
const deleteListFormOpen = (currentRow) => {
  axios.delete("/goods/" + currentRow.id).then(response => {
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
  gname: '', gmodel: '', gcolor: '', gbrand: '', gtype: ''
})
//table列表数据
const tableList = reactive({tableData: []})

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

const typeMap = reactive({});

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
  listType();
})

const listCondition = (param) => {
  axios.postJson("goods/search", param).then((resp) => {
    productPage = resp.data;
    tableList.tableData = productPage.goodsList;
  })
}
const listType = () => {
  let param = {
    "pageTotal": 0,
    "pageNum": 1,
    "pageSize": 1000,
    "ggTypes": {"typeName": ""},
    "typeList": []
  }
  axios.postJson("type/search", param).then((resp) => {
    optionsList.optionsData = resp.data.typeList;
    listCondition(productPage)
    optionsList.optionsData.forEach(options => {
      typeMap[options.id] = options.typeName;
    })
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