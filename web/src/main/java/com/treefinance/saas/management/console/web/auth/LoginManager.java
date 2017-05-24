package com.treefinance.saas.management.console.web.auth;

import com.treefinance.saas.management.console.common.domain.vo.LoginUserVO;
import org.springframework.stereotype.Component;

/**
 * Created by yh-treefinance on 2017/5/24.
 */
@Component
public class LoginManager {

    /**
     * 根据token获取用户
     * @return
     */
    public LoginUserVO getUserByToken(String token){
        return new LoginUserVO();
    }
}
