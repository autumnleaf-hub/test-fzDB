package com.fzy.fzprj.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author fzy
 * @since 2024-09-29
 */
@Getter
@Setter
@TableName("user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableField("version")
    @Version
    @JsonIgnore
    private Integer version;

    @TableField(value = "status", fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    private Integer status;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonIgnore
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private LocalDateTime updateTime;

    @TableId(value = "uid", type = IdType.ASSIGN_ID)
    private Long uid;

    @TableField("uname")
    private String uname;

    @TableField("mail")
    private String mail;

    @TableField("password")
    private String password;

    /**
     * 职位。0：普通用户; 1：管理员
     */
    @TableField(value = "position", fill = FieldFill.INSERT)
    private Integer position;

    @Override
    public Serializable pkVal() {
        return this.uid;
    }
}
