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
@TableName("competition")
public class Competition extends Model<Competition> {

    private static final long serialVersionUID = 1L;

    @TableField("version")
    @Version
    private Integer version;

    @TableField(value = "status", fill = FieldFill.INSERT)
    @TableLogic
    private Integer status;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableId(value = "cid", type = IdType.ASSIGN_ID)
    private Long cid;

    @TableField("cname")
    private String cname;

    @TableField("slogan")
    private String slogan;

    /**
     * 比赛是否结束。0: 未开始; 1：已结束
     */
    @TableField(value = "is_over", fill=FieldFill.INSERT)
    private Integer isOver;

    @Override
    public Serializable pkVal() {
        return this.cid;
    }
}
