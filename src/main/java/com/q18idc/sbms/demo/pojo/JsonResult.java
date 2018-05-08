package com.q18idc.sbms.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/5/3 21:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult implements Serializable {
    private boolean flag=false;
    private String msg="未知错误";
}
