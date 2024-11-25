package com.ningf.ningfojbackendmodel.model.dto.question;

import lombok.Data;

/**
 * @description:题目配置
 * @author: Lenovo
 * @time: 2024/10/15 下午7:44
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制(ms)
     * */
    private long timeLimit;
    /**
     * 内存限制(kb)
     * */
    private long memoryLimit;
    /**
     * 堆栈限制(kb)
     * */
    private long stackLimit;
}
