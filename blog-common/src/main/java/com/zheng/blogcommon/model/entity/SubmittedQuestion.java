package com.zheng.blogcommon.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * submitted question
 * @TableName submitted_question
 */
@TableName(value ="submitted_question")
@Data
public class SubmittedQuestion implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * programming language
     */
    private String language;

    /**
     * user code
     */
    private String code;

    /**
     * judge info
     */
    private String judgeInfo;

    /**
     * result status: 0-wait, 1-in progress, 2-success, 3-failure
     */
    private Integer status;

    /**
     * question id
     */
    private Long questionId;

    /**
     * user id
     */
    private Long userId;

    /**
     * creation time
     */
    private Date createTime;

    /**
     * update time
     */
    private Date updateTime;

    /**
     * is delete
     */
    private Integer idDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}