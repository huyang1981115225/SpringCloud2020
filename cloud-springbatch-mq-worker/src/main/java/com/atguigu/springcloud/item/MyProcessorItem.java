package com.atguigu.springcloud.item;

import com.atguigu.springcloud.entity.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

/**
 * @author huyang
 * @date 2020/7/13 10:42
 */
@Service
public class MyProcessorItem implements ItemProcessor<Article,Article> {

    Logger logger = LoggerFactory.getLogger(MyProcessorItem.class);

    @Override
    public Article process(Article article)throws Exception{
       logger.info("【{}】经过处理器",article.getTitle());
        return article;
    }
}
