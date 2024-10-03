package com.fzy.fzprj.dto;

import lombok.Data;

/**
 * @author fzy
 * @version 1.0
 * 创建时间：2024-09-29 17:16
 */

@Data
public class SignUpDto {
    String mail;
    String uname;
    String password;
    String tname;
    Integer position;
    String slogan;
}
