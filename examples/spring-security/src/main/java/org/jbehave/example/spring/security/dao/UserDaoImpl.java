package org.jbehave.example.spring.security.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.jbehave.example.spring.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;


@Repository("userDao")
public class UserDaoImpl implements UserDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Transactional
  public User load(Long id) {
    return (User) sessionFactory.getCurrentSession().load(User.class, id);
  }

  @Transactional
  public User persist(User user) {
    sessionFactory.getCurrentSession().saveOrUpdate(user);
    return user;
  }

  @SuppressWarnings("unchecked")
  @Transactional
  public User findUserByOrganizationAndUsername(Long organizationId, String username) {
    List<User> query = sessionFactory.getCurrentSession()
            .createQuery("from User where organization.id = ? and username = ?")
            .setParameter(0, organizationId)
            .setParameter(1, username)
            .list();
    if (query.size() == 1) {
      return query.get(0);
    }
    return null;
  }

}
