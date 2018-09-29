package th.co.bookstore.common.constant;

public class ProjectConstants {

	public static class TransactionManagerRef {
		public static final String MYSQl_DB = "mySqlTransactionManager";
	}
	
	public static class EntityFactoryRef {
		public static final String MYSQL_DB = "entityManagerFactory";
	}
	
	public static class JDBC_TEMPLATE {
		public static final String MYSQL_DB = "mysqlJdbcTemplate";
	}
	
	public static class STATUS {
		public static final String SUCCESS = "succcess";
		public static final String FAIL = "fail";
	}
	
	public static final String SYSTEM = "system";
	
	public static class FLAG {
		public static final String N_FLAG = "N";
		public static final String Y_FLAG = "Y";
	}
}
