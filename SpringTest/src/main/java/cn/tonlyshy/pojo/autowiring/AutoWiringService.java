package cn.tonlyshy.pojo.autowiring;

public class AutoWiringService {

    private AutoWiringDao autoWiringDao;

    public AutoWiringService(AutoWiringDao autoWiringDao) {
        System.out.println("AutoWiringService autoWiringDao");
        this.autoWiringDao = autoWiringDao;
    }

    public void setAutoWiringDao(AutoWiringDao autoWiringDao) {
        this.autoWiringDao = autoWiringDao;
    }

    public void say(String word) {
        autoWiringDao.say(word);
    }
}
