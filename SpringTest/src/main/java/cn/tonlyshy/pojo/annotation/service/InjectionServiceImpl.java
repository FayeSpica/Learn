package cn.tonlyshy.pojo.annotation.service;

import cn.tonlyshy.pojo.annotation.dao.InjectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class InjectionServiceImpl implements InjectionService {

    private InjectionDao injectionDao;

    public InjectionServiceImpl(){

    }

    @Autowired
    public InjectionServiceImpl(InjectionDao injectionDao) {
        this.injectionDao = injectionDao;
    }

    public void save(String arg) {
        //模拟业务操作
        System.out.println("Service接收参数: " + arg);
        arg = arg + ": " + this.hashCode();
        injectionDao.save(arg);
    }

    public void setInjectionDao(InjectionDao injectionDao) {
        this.injectionDao = injectionDao;
    }
}
