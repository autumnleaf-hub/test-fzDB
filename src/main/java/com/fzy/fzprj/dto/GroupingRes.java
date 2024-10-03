package com.fzy.fzprj.dto;

import com.fzy.fzprj.bean.User;
import lombok.Data;

import java.util.List;

/**
 * @author fzy
 * @version 1.0
 * 创建时间：2024-09-30 15:05
 */

@Data
public class GroupingRes {
    List<User> group1;
    List<User> group2;
}
