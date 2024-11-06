package service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.UserEntity;
import repository.UserRepository;
import util.MD5;

public class LoginService {

    private UserRepository userRepository = new UserRepository(); // Khởi tạo Repository

    // Kiểm tra đăng nhập
    public boolean checkLogin(String email, String password, String remember, HttpServletRequest req, HttpServletResponse resp) {
        
        // Kiểm tra người dùng trong cơ sở dữ liệu
    	
        UserEntity user = userRepository.findByEmailAndPassword(email, MD5.getMd5(password));
        
        if (user != null) {
            // Nếu người dùng chọn "Remember Me", tạo cookie lưu trữ email
            if ("on".equals(remember)) {
                Cookie userCookie = new Cookie("userEmail", email); // Tạo cookie lưu email
                userCookie.setMaxAge(60 * 60 * 24 * 7); // Cookie sẽ hết hạn sau 7 ngày
                userCookie.setPath(req.getContextPath());
                userCookie.setSecure(true); // Chỉ gửi cookie qua HTTPS
                userCookie.setHttpOnly(true); // Ngăn chặn truy cập qua JavaScript
                resp.addCookie(userCookie);
            }
            Cookie roleCookie = new Cookie("role", user.getRoleName());
            resp.addCookie(roleCookie);
            // Lưu thông tin người dùng vào session
            req.getSession().setAttribute("user", user); 
            return true; // Đăng nhập thành công
        }
        
        return false; // Đăng nhập thất bại
    }
}
