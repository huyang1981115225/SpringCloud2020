package com.atguigu.springcloud.item;

import com.atguigu.springcloud.entity.Article;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * @author huyang
 * @date 2020/7/13 10:42
 */
public class PrintWriterItem<T> implements ItemWriter<T> {
    @Override
    public void write(List<? extends T> list) throws Exception {
        list.stream().forEach(a->{
            Article article=(Article)a;
//            System.err.println(article.getTitle());
            System.out.println(article);
        });

    }
}
