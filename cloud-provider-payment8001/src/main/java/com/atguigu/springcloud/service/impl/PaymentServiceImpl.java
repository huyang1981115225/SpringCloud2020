package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.PaymentDao;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 第一个注解 @Cacheable:
 *              这个注解是最重要的,主要实现的功能再进行一个读操作的时候。
 *              就是先从缓存中查询，如果查找不到，就会走数据库的执行方法，
 *              这是缓存的注解最重要的一个方法，基本上我们的所有缓存实现都要依赖于它。
 *              它具有的属性为:
 *              1、cacheNames:缓存名字，
 *              2、condtion：缓存的条件，
 *              3、unless:不缓存的条件。
 *              可以指定SPEL表达式来实现,也可以指定缓存的key，缓存的内部实现一般都是key,value形式，
 *              类似于一个Map（实际上cacheable的缓存的底层实现就是concurrenHashMap）,
 *              指定了key，那么缓存就会以key作为键，以方法的返回结果作为值进行映射。
 *
 *  第二个注解 @CachePut：
 *              这个注解它总是会把数据缓存，而不会去每次做检查它是否存在，
 *              相比之下它的使用场景就比较少，毕竟我们希望并不是每次都把所有的数据都给查出来，
 *              我们还是希望能找到缓存的数据，直接返回，这样能提升我们的软件效率。
 *
 *   第三个注解 @CacheEvict：
 *              这个注解主要是配合@Cacheable一起使用的，它的主要作用就是清除缓存，
 *              当方法进行一些更新、删除操作的时候，这个时候就要删除缓存。如果不删除缓存，
 *              就会出现读取不到最新缓存的情况，拿到的数据都是过期的。它可以指定缓存的key和conditon，
 *              它有一个重要的属性叫做allEntries默认是false,也可以指定为true,主要作用就是清除所有的缓存，而不以指定的key为主。
 *
 * @author huyang
 * @date 2020/5/16 12:23
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    /**
     * 带有缓存的查询.
     * <p>
     * 第一次查询，从数据库获取，如果返回结果为空，则不缓存 第二次查询，从缓存获取。
     *
     * @param id
     * @return
     */
    @Cacheable(value = "Payment", key = "#id", unless = "#result == null")
    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }

    /**
     * 缓存修改.
     * <p>
     * 修改对象后，更新缓存对象.
     *
     * @param payment
     * @return
     */
    @CachePut(value = "Payment", key = "#payment.id", unless = "#result == null")
    @Override
    public Payment update(Payment payment) {
        paymentDao.update(payment);
        return payment;
    }

    /**
     * 缓存删除.
     * <p>
     * 删除数据库记录后，同时删除缓存信息.
     *
     * @param id
     * @return
     */
    @CacheEvict(value = "Payment", key = "#id")
    @Override
    public int delete(Long id) {
        return paymentDao.delete(id);
    }
}
