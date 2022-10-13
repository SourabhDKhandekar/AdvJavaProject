package com.homework.goi.np.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.homework.goi.np.entity.NationalParkIndia;

@Repository
public class NationalParkIdiaDaoImpl implements NationalParkIndiaDao {

	@Autowired
	private SessionFactory sessionFactory;

	List<NationalParkIndia> list = new ArrayList<>();

	@Override
	public boolean saveNationalPark(NationalParkIndia nationalParkIndia) {
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			NationalParkIndia npi = session.get(NationalParkIndia.class, nationalParkIndia.getParkId());
			if (npi == null) {
				session.save(nationalParkIndia);
				transaction.commit();
				isAdded = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isAdded;
	}

	@Override
	public boolean updateNationalPark(NationalParkIndia nationalParkIndia) {
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			NationalParkIndia npi = session.get(NationalParkIndia.class, nationalParkIndia.getParkId());
			if (npi != null) {
				session.evict(npi);
				session.update(nationalParkIndia);
				transaction.commit();
				isUpdated = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isUpdated;
	}

	@Override
	public int updateNationalParkList(List<NationalParkIndia> list) {
		Session session = null;
		Transaction transaction = null;
		int count = 0;
		try {

			for (NationalParkIndia nationalParkIndia : list) {
				session = sessionFactory.openSession();
				transaction = session.beginTransaction();
				session.save(nationalParkIndia);
				transaction.commit();
				count = count + 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return count;
	}

	@Override
	public List<NationalParkIndia> getAllNationalPark() {
		Session session = sessionFactory.openSession();
		List<NationalParkIndia> list = null;
		try {

			Criteria criteria = session.createCriteria(NationalParkIndia.class);

			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	@Override
	public List<NationalParkIndia> getNationalParkByState(String state) {
		Session session = sessionFactory.openSession();
		List<NationalParkIndia> list = null;
		try {

			Criteria criteria = session.createCriteria(NationalParkIndia.class);

			Criterion state1 = Restrictions.eq("state", state);

			criteria.add(state1);

			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	@Override
	public List<NationalParkIndia> getNationalParkByYear(int establishmentYear) {

		Session session = sessionFactory.openSession();
		List<NationalParkIndia> list = null;
		try {

			Criteria criteria = session.createCriteria(NationalParkIndia.class);

			Criterion state1 = Restrictions.eq("establishmentYear", establishmentYear);

			criteria.add(state1);

			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	@Override
	public boolean deleteNationalPark(int parkId) {
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			NationalParkIndia nationalParkIndia = session.get(NationalParkIndia.class, parkId);
			if (nationalParkIndia != null) {
				session.delete(nationalParkIndia);
				transaction.commit();
				isDeleted = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isDeleted;
	}

}
