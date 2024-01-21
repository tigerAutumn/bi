# 钱报app 接口改造
## 需求描述  
> [tower需求地址](https://tower.im/projects/3d05349e05b2474dbd3198d04ac77804/todos/468069ff320b4a4f8093a1ddc4d1d063/)
## 业务逻辑
+ 钱报用户信任免登传过来子渠道标识channel
+ 将子渠道标识channel存入用户表bs_user 中的create_channel 中
+ 钱报用户下单时将子渠道标识channel 存入订单表bs_pay_orders 的order_no 末尾
+ 订单推送增加子渠道标识channel
+ 订单查询时从订单表bs_pay_orders 中的order_no 中截取出channel 并加入出参传递给钱报

> channel 为9位纯数字
## 修改代码
### 信任免登
> UserTrustLoginController.loginInit   
> 获取子渠道标识channel 存入用户表bs_user 中的create_channel 中 
### 钱报购买
> ProductBuyResultAspect.balanceBuyAfter   
> ProductBuyResultAspect.orderNoticeApp178   
> 下单时将子渠道标识channel 存入订单表bs_pay_orders 的order_no 末尾
### 订单通知
> APP178Facade.orderNotice
> 将子渠道标识channel 传递给钱报
### 订单查询
> Qb178ServiceDispatchController.orderList   
> BsPayOrdersMapper.queryOrderListApp178   
> 订单查询时从订单表bs_pay_orders 中的order_no 中截取出子渠道标识channel


