package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huyang
 * @date 2020/5/16 0:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private Long id;

    private String serial;
}
