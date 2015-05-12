package com.baidu.st.model;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Traderec entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.baidu.st.model.Traderec
 * @author MyEclipse Persistence Tools
 */

public class TraderecDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(TraderecDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(Traderec transientInstance) {
		log.debug("saving Traderec instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Traderec persistentInstance) {
		log.debug("deleting Traderec instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Traderec findById(com.baidu.st.model.TraderecId id) {
		log.debug("getting Traderec instance with id: " + id);
		try {
			Traderec instance = (Traderec) getHibernateTemplate().get(
					"com.baidu.st.model.Traderec", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Traderec instance) {
		log.debug("finding Traderec instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Traderec instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Traderec as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Traderec instances");
		try {
			String queryString = "from Traderec";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findStringQuery(String queryString) {
		log.debug("finding object by sql string");
		try {
			String sqlQuery = "from Traderec " + queryString;
			return getHibernateTemplate().find(sqlQuery);

		} catch (RuntimeException re) {
			log.error("find sql string failed", re);
			throw re;
		}
	}
	
	public Traderec merge(Traderec detachedInstance) {
		log.debug("merging Traderec instance");
		try {
			Traderec result = (Traderec) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Traderec instance) {
		log.debug("attaching dirty Traderec instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Traderec instance) {
		log.debug("attaching clean Traderec instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TraderecDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TraderecDAO) ctx.getBean("TraderecDAO");
	}
}