package com.q18idc.sbms.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/5/7 14:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiniuiResultJson implements Serializable {
    private int total = 0;
    private List data = new ArrayList();
}
