package com.yusys.springbatch.listener;

import com.yusys.springbatch.domain.Person;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

/**
 * Created by huyang on 2019/10/12.
 */
@Component
public class MySkipListener implements SkipListener<Person, Person> {
    @Override
    public void onSkipInRead(Throwable t) {

    }

    @Override
    public void onSkipInWrite(Person person, Throwable throwable) {

    }

    @Override
    public void onSkipInProcess(Person person, Throwable throwable) {
        System.out.println(person.getId() + " 监听到异常 " + throwable);
    }
}
