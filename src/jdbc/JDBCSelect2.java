package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JDBCSelect2 {
	
	public static void main(String[] args) {
		
		/*
		 * 직원 테이블과 부서 테이블을 left join하고 
		 * 직원아이디, 직무아이디, 부서명, 이름만 출력한다
		 * 
		 * 조건 : 직원아이디를 입력받아서, 이 아이디에 해당하는 데이터만 출력
		 */
		 
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속 주소
		String uid = "HR"; // 계정명
		String upw = "HR"; // 비밀번호 
		
		Scanner scan = new Scanner(System.in);
		System.out.println("조회할 아이디> ");
		String id = scan.next();
		
		String sql = "SELECT E.EMPLOYEE_ID, \r\n"
				+ "       E.JOB_ID,\r\n"
				+ "       E.FIRST_NAME,\r\n"
				+ "       D.DEPARTMENT_NAME\r\n"
				+ "FROM EMPLOYEES E\r\n"
				+ "LEFT JOIN DEPARTMENTS D \r\n"
				+ "ON E.DEPARTMENT_ID = D.DEPARTMENT_ID\r\n"
				+ "WHERE EMPLOYEE_ID = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			
			conn = DriverManager.getConnection(url, uid, upw); // conn
			pstmt = conn.prepareStatement(sql); // pstmt
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) { // 결과가 없을 때까지 
				// 1행에 대한 처리
				int employeeId = rs.getInt("employee_id");
				String firstName = rs.getString("first_name");
				String jobId = rs.getString("job_id");
				String departmentName = rs.getString("department_name");
				
				System.out.println(employeeId);
				System.out.println(firstName);
				System.out.println(jobId );
				System.out.println(departmentName);
				System.out.println("---------------------");
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
			}
		}
	}	
}