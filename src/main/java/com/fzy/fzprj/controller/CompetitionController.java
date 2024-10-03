package com.fzy.fzprj.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fzy.fzprj.bean.Competition;
import com.fzy.fzprj.bean.User;
import com.fzy.fzprj.bean.UserComp;
import com.fzy.fzprj.common.CommonValues;
import com.fzy.fzprj.common.R;
import com.fzy.fzprj.dto.GroupingRes;
import com.fzy.fzprj.dto.OverCompetition;
import com.fzy.fzprj.service.CompetitionService;
import com.fzy.fzprj.service.UserCompService;
import com.fzy.fzprj.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fzy
 * @since 2024-09-29
 */
@RestController
@RequestMapping("comp")
public class CompetitionController {
    @Resource
    UserService userService;

    @Resource
    CompetitionService competitionService;

    @Resource
    UserCompService userCompService;

    @GetMapping("get")
    public R<Competition> get(Long cid, String cname, HttpServletRequest request) {
        Object temp = request.getSession().getAttribute("id");
        if (temp == null) {
            return R.error("未登录");
        }
        Long id = Long.parseLong(temp.toString());
        User user = userService.getById(id);
        if (user.getPosition() != 1) {
            return R.error("权限不足");
        }
        Competition one = competitionService.getOne(Wrappers.lambdaQuery(Competition.class)
                .eq(Competition::getCid, cid)
                .or()
                .eq(Competition::getCname, cname));
        if (one == null) {
            return R.error("比赛不存在");
        } else if( one.getIsOver() == 0) {
            return R.success(one);
        } else {
            List<Long> participants = userCompService.list(Wrappers.lambdaQuery(UserComp.class).eq(UserComp::getCid, one.getCid()))
                    .stream()
                    .map(UserComp::getUid)
                    .toList();
            OverCompetition overCompetition = new OverCompetition();
            BeanUtils.copyProperties(one, overCompetition);
            overCompetition.setScores(userService.listByIds(participants));
            return R.success(overCompetition);
        }
    }

    @PostMapping("create")
    public R<Long> create(@RequestBody Competition competition, HttpServletRequest request) {
        Object temp = request.getSession().getAttribute("id");
        if (temp == null) {
            return R.error("未登录");
        }
        Long id = Long.parseLong(temp.toString());
        User one = userService.getById(id);
        if (one.getPosition() != 1) {
            return R.error("权限不足");
        }
        competitionService.save(competition);
        return R.success(competition.getCid());
    }

    @GetMapping("draw/{cid}")
    public R<GroupingRes> draw(@PathVariable Long cid, HttpServletRequest request) {
        Object temp = (request.getSession().getAttribute("id"));
        if (temp == null) {
            return R.error("未登录");
        }
        Long id = Long.parseLong(temp.toString());
        User one = userService.getById(id);
        if (one.getPosition() != 1) {
            return R.error("权限不足");
        }

        List<UserComp> list = userCompService.list(Wrappers.lambdaQuery(UserComp.class).eq(UserComp::getCid, cid));
        if (list.size() < CommonValues.MIN_COMP_PARTICIPANTS) {
            return R.error("参赛人数不足");
        }

        List<Integer> selectedIndex = new ArrayList<>();
        Random random = new Random();
        random.ints(0, list.size()).distinct().limit(CommonValues.MIN_COMP_PARTICIPANTS).forEach(selectedIndex::add);

        List<Long> g1 = new ArrayList<>();
        List<Long> g2 = new ArrayList<>();
        for (int i = 0; i < CommonValues.MIN_COMP_PARTICIPANTS; i++) {
            if (i < CommonValues.MIN_COMP_PARTICIPANTS / 2) {
                g1.add(list.get(selectedIndex.get(i)).getUid());
            } else {
                g2.add(list.get(selectedIndex.get(i)).getUid());
            }
        }

        GroupingRes res = new GroupingRes();
        res.setGroup1(userService.listByIds(g1));
        res.setGroup2(userService.listByIds(g2));

        return R.success(res);
    }

    @PostMapping("updateInf")
    public R<String> updateInf(@RequestBody Competition competition, HttpServletRequest request) {
        Object temp = request.getSession().getAttribute("id");
        if (temp == null) {
            return R.error("未登录");
        }
        Long id = Long.parseLong(temp.toString());
        User one = userService.getById(id);
        if (one.getPosition() != 1) {
            return R.error("权限不足");
        }
        competitionService.updateById(competition);
        return R.success("更新成功");
    }


    @PostMapping("recordScore")
    public R<String> recordScore(@RequestBody List<UserComp> ucs, HttpServletRequest request) {
        Object temp = request.getSession().getAttribute("id");
        if (temp == null) {
            return R.error("未登录");
        }
        Long id = Long.parseLong(temp.toString());
        User one = userService.getById(id);
        if (one.getPosition() != 1) {
            return R.error("权限不足");
        }
        List<Long> cids = ucs.stream().map(UserComp::getCid).distinct().toList();
        competitionService.update(Wrappers.lambdaUpdate(Competition.class)
                .set(Competition::getIsOver, 1)
                .in(Competition::getCid, cids));
        userCompService.updateBatchByUidAndCid(ucs);
        return R.success("记录成功");
    }

    @GetMapping("list")
    public R<List<Competition>> list(HttpServletRequest request) {
        Object temp = request.getSession().getAttribute("id");
        if (temp == null) {
            return R.error("未登录");
        }
        Long id = Long.parseLong(temp.toString());
        User one = userService.getById(id);
        if (one.getPosition() != 1) {
            return R.error("权限不足");
        }
        return R.success(competitionService.list());
    }
}
