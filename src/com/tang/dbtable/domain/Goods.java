package com.tang.dbtable.domain;

import lombok.Data;

/**
 * @Classname ${NAME}
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/1/10 23:35
 * @Created by ASUS
 */
@Data
public class Goods {
    private Long goodsId;

    private String goodsName;

    private Long goodsType;
}