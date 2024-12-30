import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/system/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import OrderView from '@/views/system/order/OrderView.vue'
import ProductView from '@/views/system/goods/ProductView.vue'
import GoodView from '@/views/system/goods/GoodView.vue'
import GoodTypeView from '@/views/system/goods/GoodTypeView.vue'
import PriceChangeView from '@/views/system/goods/PriceChangeView.vue'
import BrandView from '@/views/system/goods/BrandView.vue'
import SupplierView from '@/views/system/goods/SupplierView.vue'
import PriceVerifyView from '@/views/system/goods/PriceVerifyView.vue'
import GiftView from '@/views/system/gifts/GiftView.vue'
import Gift2GoodView from '@/views/system/gifts/Gift2GoodView.vue'
import Good2GiftView from '@/views/system/gifts/Good2GiftView.vue'
import Good2GiftVerifyView from '@/views/system/gifts/Good2GiftVerifyView.vue'
import Gift2GoodVerifyView from '@/views/system/gifts/Gift2GoodVerifyView.vue'
import OutboundOrder from '@/views/system/order/OutboundOrder.vue'
import ActivityView from '@/views/system/activity/ActivityView.vue'
import ActivityVerifyViwe from '@/views/system/activity/ActivityVerifyViwe.vue'
import SelectGoodsView from '@/views/system/activity/SelectGoodsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      children:[
          //销售订单管理
        {path: '/order',component: OrderView},
        {path: '/outbound',component: OutboundOrder},
          //活动管理
        {path: '/activityapplication',component: ActivityView},
        {path: '/activityapplicationverify',component: ActivityVerifyViwe},
        {path: '/selectGoodsView',component: SelectGoodsView},
          //商品管理
        {path: '/product',component: ProductView},
        {path: '/goods',component: GoodView},
        {path: '/goodsTypes',component: GoodTypeView},
        {path: '/pricechange',component: PriceChangeView},
        {path: '/brand',component: BrandView},
        {path: '/supplier',component: SupplierView},
        {path: '/pricechangeverify',component: PriceVerifyView},
          //赠品管理
        {path: '/gift',component: GiftView},
        {path: '/goods2Gift',component: Gift2GoodView},
        {path: '/gift2goods',component: Good2GiftView},
        {path: '/goods2giftVerify',component: Good2GiftVerifyView},
        {path: '/gift2GoodsVerify',component: Gift2GoodVerifyView},
      ]
      
    },
    {
      path: '/login',
      name: 'login',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: LoginView
    }
  ]
})

export default router
