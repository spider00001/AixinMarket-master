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
            resp = list.subList(((pageNum-1)*pageSize), len - ((pageNum-1)*pageSize));
        }else if(len < ((pageNum-1)*pageSize)){
            resp = null;
        }
        else {
            resp = list.subList(((pageNum-1)*pageSize),pageSize);
        }
        return resp;
    }
}
