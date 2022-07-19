package com.memo.user.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.user.dao.UserDAO;
import com.memo.user.model.User;

@Service
public class UserBO {
	
	@Autowired
	private UserDAO userDAO;
	public List<User> getuser(){
		return userDAO.selectuser();
	}
	public boolean existLoginId(String ioginId) {
		return userDAO.existLoginId(ioginId);
	}
	public void addUser(String loginId,String password , String name, String email) {
		userDAO.insertUser(loginId, password, name, email);
	}

}
