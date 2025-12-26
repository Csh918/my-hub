package cn.qr.book_manager.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R {
    private int code;
    private String msg;
    private Object data;

    public static R success(String msg,Object data){
        return new R(200,msg,data);
    }
    public static R success(Object data){
        return new R(200,"success",data);
    }
    public static R fail(String msg){
        return new R(500,msg,null);
    }
}
