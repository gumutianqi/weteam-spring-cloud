package boottest;

import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
    UserDao dao; // mapper

    @Autowired
    SQLManager sql;

	@Transactional()
	public long total() {
		return dao.allCount();
	}

	@Transactional()
	public void save(User user) {
		
		dao.insert(user,true);
	
	}

	public void allUser(User query) {
		dao.selectUser(query);
	}
}
