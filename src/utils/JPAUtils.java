package utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {

	private static final String PERSISTENT_UNIT_NAME = "pu";

	private static final EntityManagerFactory emf;

	static {
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
		} catch (Throwable ex) {
			System.out.println(ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static EntityManager getEm(){
		return emf.createEntityManager();
	}
	
	public static void startProfiling(EntityManager em){
		em.createNativeQuery("SET PROFILING=1;").executeUpdate();
		em.createNativeQuery("SET PROFILING_HISTORY_SIZE=0;").executeUpdate();
		em.createNativeQuery("SET PROFILING_HISTORY_SIZE=15;").executeUpdate();
	}
	
	public static QueryInfo completeProfiling(EntityManager em){
		QueryInfo queryInfo = new QueryInfo();
		
		@SuppressWarnings("unchecked")
		List<Object[]> res = em.createNativeQuery("SHOW PROFILES;").getResultList();
		
		double duration = 0;
		for(Object[] objects: res){
			duration += (double)objects[1];
		}
		
		queryInfo.setDuration(duration);
		queryInfo.setQueryCount(res.size());
				
		em.createNativeQuery("SET PROFILING=0;").executeUpdate();
		
		return queryInfo;
	}

	public static class QueryInfo{
		private int queryCount;
		private double duration;
		public int getQueryCount() {
			return queryCount;
		}
		public void setQueryCount(int queryCount) {
			this.queryCount = queryCount;
		}
		public double getDuration() {
			return duration;
		}
		public void setDuration(double duration) {
			this.duration = duration;
		}
	}
	
}