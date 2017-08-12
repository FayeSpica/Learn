package cn.tonlyshy.service;

import cn.tonlyshy.dao.InjectionDao;

public class InjectionServiceImpl implements InjectionService {

    private InjectionDao injectionDao;

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
