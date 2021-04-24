package cn.luckywh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//实现Serializable接口为了序列化
public class Payment implements Serializable {
    private long id;
    private String serial;
}
