package service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.UserEntity;
import repository.UserRepository;
import util.MD5;

public class LoginService {
    private UserRepository userRepository = new UserRepository();

    // Kiểm tra đăng nhập
    public boolean checkLogin(String email, String password, String remember, HttpServletRequest req,
            HttpServletResponse resp) {
        UserEntity user = userRepository.findByEmailAndPassword(email, MD5.getMd5(password));

        if (user != null) {
            // Lưu thông tin userId và roleName vào cookie
            Cookie userIdCookie = new Cookie("userId", String.valueOf(user.getId()));
            userIdCookie.setMaxAge(7 * 24 * 60 * 60); // Lưu trong 7 ngày
            userIdCookie.setHttpOnly(true);
            resp.addCookie(userIdCookie);

            Cookie roleNameCookie = new Cookie("roleName", user.getRole().getRoleName());
            roleNameCookie.setMaxAge(7 * 24 * 60 * 60); // Lưu trong 7 ngày
            roleNameCookie.setHttpOnly(true);
            resp.addCookie(roleNameCookie);

            // Nếu "Remember Me" được chọn, lưu email vào cookie
            if ("on".equals(remember)) {
                Cookie emailCookie = new Cookie("email", MD5.getMd5(email));
                emailCookie.setMaxAge(7 * 24 * 60 * 60); // Lưu trong 7 ngày
                emailCookie.setHttpOnly(true);
                resp.addCookie(emailCookie);
            }

            return true; // Đăng nhập thành công
        }
        return false; // Đăng nhập thất bại
    }

    // Xử lý đăng xuất
    public void logout(HttpServletRequest req, HttpServletResponse resp) {
        // Xóa tất cả cookie
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setMaxAge(0); // Xóa cookie
                resp.addCookie(cookie);
            }
        }
    }
}
