## 项目背景
线上首先执行每日利息计算定时，首先查询所有数据，在查询之后并发线程：用户的存量数据回款。将bs_user.current_interest修改掉。之后每日利息计算根据之前查出来的数据又进行了覆盖更新操作，导致数据不正确。

## 项目内容
将覆盖更新变成累加

## 项目设计

老sql：

	update bs_user set current_interest = #{amount}, total_interest = #{totalAmount} where id = #{userId};

新sql

	update bs_user set current_interest = current_interest + #{currentInterest} , total_interest = #{totalAmount} where id = #{userId};