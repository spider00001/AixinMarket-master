package com.bluemsun.util;

public class PageUtil {
    public static int getTotalPage(int totalCount,int pageSize){
        int price = totalCount%pageSize;
        if(price==0){
            return (totalCount-price)/pageSize;
        }else {
            return (totalCount-price)/pageSize+1;
        }

    }
    public static int getRowIndex(int pageNum,int pageSize){
        return (pageNum-1)*pageSize;
    }

}
