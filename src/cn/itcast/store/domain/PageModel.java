package cn.itcast.store.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PageModel<T> {
    private List<T> data;//分页数据
    private Integer currentPage;//当前页
    private Integer pageSize ;//页大小
    private Integer totalPage;//总页数
    private Integer totalCount;//总记录数
    private Integer startIndex;//开始索引
    private String url;//分页用的访问路径
    public PageModel(Integer currentPage,Integer totalCount,Integer pageSize){
        this.setPageSize(pageSize);
        this.setTotalCount(totalCount);
        this.setCurrentPage(currentPage);
}

    public void setCurrentPage(Integer currentPage) {
        if(this.totalCount>0){
            //计算总页数
            this.totalPage = (totalCount%pageSize)==0?(totalCount/pageSize):(totalCount/pageSize)+1;
            //设置当前页
            if (currentPage > totalPage) {
                this.currentPage = totalPage;
            } else {
                this.currentPage = currentPage;
            }
        }else{
            this.currentPage = 1;
        }
        //计算索引
        setStartIndex();
    }

    public void setStartIndex() {
        this.startIndex = (currentPage-1)*pageSize;
    }
}
