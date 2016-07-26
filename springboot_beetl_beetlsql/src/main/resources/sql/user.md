selectUser
===

* 根据用户姓名查找

	select * from user where 1=1 
	@if(!isEmpty(name)){
	and name = #name#
	@}
	
queryNewUser
===

* 用一个sql做翻页查询，page将输出 count(1) 或者 u.*

	select #page("u.*")# from user u

updateAge
===

	update user set age = #age# where id = #id#


findUser
===

	select * from user where name=#name#  and age=#age#

