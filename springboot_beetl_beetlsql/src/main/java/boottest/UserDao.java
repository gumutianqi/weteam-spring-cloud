package boottest;

import java.util.List;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.RowSize;
import org.beetl.sql.core.annotatoin.RowStart;
import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;


public interface UserDao extends BaseMapper<User> {
	
	@SqlStatement(params="name,age")
	public User findUser( String name,Integer age);
	//使用默认user.updateAge
	public int updateAge(User user);
	@Sql(value=" update user set age = ? where id = ? ")
	public int updateAge2(int id,int age);
	
	public List<User> selectUser(User query);
	
}
