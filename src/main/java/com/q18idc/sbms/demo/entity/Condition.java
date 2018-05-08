package com.q18idc.sbms.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/5/7 22:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Condition implements Serializable {
    @Transient
    private String key;

    @Transient
    private Integer pageIndex;

    @Transient
    private Integer pageSize;

    @Transient
    private String sortField;

    @Transient
    private String sortOrder;
}
