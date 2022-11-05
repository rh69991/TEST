package coupon.dao.impl;

import coupon.dao.CouponDAO;
import coupon.vo.Coupon;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import util.HibernateUtil;

import java.util.List;

@Transactional(readOnly = true)
public class CouponDAOImpl implements CouponDAO {

    public CouponDAOImpl() {

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int insert(Coupon coupon) {
        try {
            beginTransaction();
            getSession().persist(coupon);
            commit();
            return 1;
        } catch (Exception e) {
            rollback();
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int deleteById(Integer couponId) {

        try {
            beginTransaction();
            Session session = getSession();
            Coupon coupon = session.load(Coupon.class, couponId);
            session.remove(coupon);
            commit();
            return 1;
        } catch (Exception e) {
            rollback();
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int updateById(Coupon coupon) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.merge(coupon);
            transaction.commit();
            return 1;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Coupon selectById(Integer couponId) {
        return getSession().get(Coupon.class, couponId);
    }

    @Override
//    @Transactional(readOnly = true)
    public List<Coupon> selectAll() {
        List<Coupon> list = null;
        try {
            beginTransaction();
            final String sql = "SELECT * FROM coupon";
            list = getSession()
                    .createNativeQuery(sql, Coupon.class)
                    .list();
            commit();
        } catch (Exception e) {
            rollback();
            e.printStackTrace();
        }
        return list;

    }
}
