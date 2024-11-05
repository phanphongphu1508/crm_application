package service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.UserEntity;
import repository.UserRepository;
import util.MD5;

public class LoginService {

	private UserRepository userRespository = new UserRepository();

	public boolean checkLogin(String email, String password, String remember, HttpServletRequest req, HttpServletResponse resp) {

		String paswordMD5 = MD5.getMd5(password);

		List<UserEntity> listUser = userRespository.findByEmailAndPassword(email, paswordMD5);
		
		boolean isSuccess = listUser.size() > 0;

		if (isSuccess) {
			UserEntity userEntity = listUser.get(0);
			String role = userEntity.getRoleName();

			Cookie authen = new Cookie("rolename", role);
			resp.addCookie(authen);
		}
		return isSuccess;
	}
}