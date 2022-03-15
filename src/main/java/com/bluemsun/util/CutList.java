package com.bluemsun.util;

import com.bluemsun.dto.CartItemDto;

import java.util.ArrayList;
import java.util.List;

public class CutList {
    public static List<CartItemDto> cutList(List list, int pageNum, int pageSize){
        int len = list.size();
        int cut;
        List resp = new ArrayList();
        if (len - ((pageNum-1)*pageSize) < pageSize && len > ((pageNum-1)*pageSize)){
            resp = list.subList(((pageNum-1)*pageSize), len);
        }else if(len <= ((pageNum-1)*pageSize)){
            resp = new ArrayList();
        }
        else {
            resp = list.subList(((pageNum-1)*pageSize),((pageNum-1)*pageSize)+pageSize);
        }
        return resp;
    }
}
