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
@TableName("user_comp")
public class UserComp extends Model<UserComp> {

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

    @TableId(value = "uc_id", type = IdType.ASSIGN_ID)
    private Long ucId;

    @TableField("uid")
    private Long uid;

    @TableField("cid")
    private Long cid;

    @TableField("standing")
    private Integer standing;

    @Override
    public Serializable pkVal() {
        return this.ucId;
    }
}
