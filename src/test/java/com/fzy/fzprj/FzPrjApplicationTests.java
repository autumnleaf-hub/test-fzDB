package com.fzy.fzprj;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fzy.fzprj.bean.Competition;
import com.fzy.fzprj.bean.User;
import com.fzy.fzprj.bean.UserComp;
import com.fzy.fzprj.common.CommonValues;
import com.fzy.fzprj.service.CompetitionService;
import com.fzy.fzprj.service.UserCompService;
import com.fzy.fzprj.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class FzPrjApplicationTests {

    @Resource
    UserService userService;

    @Resource
    UserCompService userCompService;

    @Resource
    CompetitionService competitionService;

    @Test
    void contextLoads() {
        //User fz = new User();
        //fz.setUname("fz");
        //fz.setMail("fz@123.com");
        //fz.setPassword("123");
        //fz.setPosition(1);
        //User fzy = new User();
        //fzy.setUname("fzy");
        //fzy.setMail("fzy@123.com");
        //fzy.setPassword("123");
        //userService.saveBatch(List.of(fz, fzy));
        //
        //Competition competition = new Competition();
        //competition.setCname("第一届唐人杯大赛");
        //competition.setSlogan("糖人堂，糖人王，糖人之中我最强");
        //competitionService.save(competition);
        //
        //UserComp userComp1 = new UserComp();
        //userComp1.setUid(fz.getUid());
        //userComp1.setCid(competition.getCid());
        //UserComp userComp2 = new UserComp();
        //userComp2.setUid(fzy.getUid());
        //userComp2.setCid(competition.getCid());
        //userCompService.saveBatch(List.of(userComp1, userComp2));
    }

    @Test
    void test01() {
        List<Integer> selectedIndex = new ArrayList<>();
        Random random = new Random();
        random.ints(0,10).distinct().limit(CommonValues.MIN_COMP_PARTICIPANTS).forEach(selectedIndex::add);
        System.out.println(selectedIndex);
    }

    @Test
    void test02() {
        //User one = userService.getOne(Wrappers.lambdaQuery(User.class).eq(User::getUname, "fz"));
        User one = userService.getById("1840402703781384194");
        System.out.println(one);

    }


}
