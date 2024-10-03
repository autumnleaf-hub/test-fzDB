package com.fzy.fzprj.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fzy.fzprj.bean.Competition;
import com.fzy.fzprj.bean.User;
import com.fzy.fzprj.bean.UserComp;
import com.fzy.fzprj.common.CommonValues;
import com.fzy.fzprj.common.R;
import com.fzy.fzprj.service.CompetitionService;
import com.fzy.fzprj.service.UserCompService;
import com.fzy.fzprj.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fzy
 * @since 2024-09-29
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    CompetitionService competitionService;

    @Resource
    UserCompService userCompService;

    @GetMapping("{name}")
    public R<User> getByUname(@PathVariable String name, HttpServletRequest request) {
        User one = userService.getOne(Wrappers.lambdaQuery(User.class).eq(User::getUname, name));
        if (one == null) {
            return R.error("用户不存在");
        } else {
            request.getSession().setAttribute("id", one.getUid());
            return R.success(one);
        }

    }

    @GetMapping("get")
    public R<User> get(HttpServletRequest request) {
        Object temp = request.getSession().getAttribute("id");
        if (temp == null) {
            return R.error("未登录");
        }
        Long id = Long.parseLong(temp.toString());
        User one = userService.getById(id);
        if (one == null) {
            return R.error("用户不存在");
        } else {
            return R.success(one);
        }
    }

    @PostMapping("signup")
    public R<String> signup(@RequestBody User user, HttpServletRequest request) {
        long count = userService.count(Wrappers.lambdaQuery(User.class).eq(User::getMail, user.getMail()));
        if (count != 0) return R.error("邮箱已被注册");
        userService.save(user);
        request.getSession().setAttribute("id", user.getUid());
        return R.success("注册成功");
    }

    @PostMapping("login")
    public R<String> login(@RequestBody User user, HttpServletRequest request) {
        User one = userService.getOne(Wrappers.lambdaQuery(User.class).eq(User::getMail, user.getMail()));
        if (one == null) return R.error("用户不存在");
        if (!one.getPassword().equals(user.getPassword())) return R.error("密码错误");
        request.getSession().setAttribute("id", one.getUid());
        return R.success("登录成功");
    }

    @GetMapping("/enterForComp/{cname}")
    public R<String> enterForComp(@PathVariable String cname, HttpServletRequest request) {
        Competition count = competitionService.getOne(Wrappers.lambdaQuery(Competition.class).eq(Competition::getCname, cname));
        if (count == null) return R.error("比赛不存在");
        Object temp = request.getSession().getAttribute("id");
        if (temp == null) return R.error("未登录");
        Long id = Long.parseLong(temp.toString());
        UserComp uc = new UserComp();
        uc.setUid(id);
        uc.setCid(count.getCid());
        if (userCompService.count(Wrappers.lambdaQuery(UserComp.class).eq(UserComp::getUid, id).eq(UserComp::getCid, count.getCid())) != 0) {
            return R.error("您已报名参加该赛事");
        }
        userCompService.save(uc);
        return R.success("报名成功");
    }

    @PostMapping("updateInf")
    public R<String> updateInf(@RequestBody User beUpdated, HttpServletRequest request) {
        Object temp = request.getSession().getAttribute("id");
        if (temp == null) return R.error("未登录");
        User updater = userService.getById(Long.parseLong(temp.toString()));

        if (beUpdated.getUid() == null) {
            beUpdated.setUid(updater.getUid());
        }

        if (beUpdated.getPosition() != null && updater.getPosition() != 1) {
            return R.error("无权修改职位");
        }

        userService.updateById(beUpdated);
        return R.success("修改成功");
    }
}
