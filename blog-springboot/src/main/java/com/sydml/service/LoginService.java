package com.sydml.service;

import com.sydml.dao.LoginDao;
import com.sydml.dao.UserDao;
import com.sydml.model.pojo.Login;
import com.sydml.model.pojo.User;
import com.sydml.utils.DateUtil;
import com.sydml.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service
public class LoginService {

    @Autowired
    private LoginDao loginDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private RequestUtil requestUtil;

    /**
     * 保存登录信息
     *
     * @param user
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveLoginInfo(User user) {

        user = userDao.findUserByName(user.getName());
        Login login = new Login();
        login.setUser(user);//绑定用户
        login.setIp(requestUtil.getIpAddress(request));//获取操作ip
        login.setTime(dateUtil.getCurrentDate());//操作时间

        if (null == loginDao.findLoginByUserId(user.getId())) {
            loginDao.saveLogin(login);
        } else {
            loginDao.updateLogin(login);
        }
    }

}
