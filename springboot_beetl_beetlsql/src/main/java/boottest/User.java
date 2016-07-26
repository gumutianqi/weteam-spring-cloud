package boottest;

import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

/*
* CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET latin1 DEFAULT NULL,
  `age` int(11) DEFAULT NULL COMMENT '年纪123',
  `bir` datetime DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
* gen by beetlsql 2016-03-02
*/
public class User  {
	private Integer id ;
	//年纪123
	private Integer age ;
	private String name ;
	//生日
	private Timestamp bir ;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getBir() {
		return bir;
	}
	public void setBir(Timestamp bir) {
		this.bir = bir;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
	
	

}