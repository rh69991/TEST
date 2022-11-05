package coupon.dao;

import coupon.vo.Coupon;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public interface CouponDAO<P, I> {

    int insert(Coupon coupon);

    int deleteById(Integer couponId);

    int updateById(Coupon coupon);

    Coupon selectById(Integer couponId);

    List<Coupon> selectAll();

    default Session getSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    default Transaction beginTransaction() {
        return HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    }

    default void commit() {
        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    }

    default void rollback() {
        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
    }
}
