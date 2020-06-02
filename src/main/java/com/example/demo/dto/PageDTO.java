package com.example.demo.dto;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 有梦想的咸鱼
 * questions：当前页面需要展示的所有数据
 * showPrevious：控制是否显示回到首页的图标
 * showFirstPage：控制是否显示回到当前页面的上一页的图标
 * showNext：控制是否显示跳转到当前页面的下一页的图标
 * showEndPage：控制是否显示跳转到最后一页的图标
 * page：当前页面下标
 * pages：可以跳转的页面下标，如 page = 3，pages = [2, 3, 4, 5, 6, 7] 表示当前可以跳转到第 {2, 4, 5, 6, 7}页中的任意一页
 */
@Data
public class PageDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private int page;
    private int totalPage;
    private List<Integer> pages;

    public void setParam(int totalPage, int page) {
        this.totalPage = totalPage;
        this.page = page;
        /*计算pages，即pages中包含 page 前的三个页面和 page 后三个页面以及page页面*/
//        前三个页面
        pages = new LinkedList<>();
        for (int i = -3; i < 0; i++){
            int previousPage = page + i;
            if ( previousPage > 0){
                pages.add(previousPage);
            }
        }
        pages.add(page);
//        后三个页面
        for (int i = 1; i < 4; i++){
            if (page == 1 && i >= 3) {
                break;
            }
            int nextsPage = page + i;
            if (nextsPage <= totalPage){
                pages.add(nextsPage);
            }
        }


//      控制图标
        /*如果当前页面是第1页，则不展示showPrevious图标，不展示showFirstPage图标，否则展示*/
        if (page == 1){
            showFirstPage = false;
            showPrevious = false;
        } else {
            showFirstPage = true;
            showPrevious = true;
        }
        /*如果当前页面是最后一页，则不展示showNext图标，不展示showEndPage图标，否则展示*/
        if (page == totalPage){
            showEndPage = false;
            showNext = false;
        } else {
            showEndPage = true;
            showNext = true;
        }
    }
}
