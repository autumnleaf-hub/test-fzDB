package com.fzy.fzprj.dto;

import com.fzy.fzprj.bean.Competition;
import com.fzy.fzprj.bean.User;
import lombok.Data;

import java.util.List;

/**
 * @author fzy
 * @version 1.0
 * 创建时间：2024-09-30 17:00
 */

@Data
public class OverCompetition extends Competition {
    List<User> scores;
}
