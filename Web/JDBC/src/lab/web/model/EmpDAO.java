package lab.web.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class EmpDAO {

	static {
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			System.out.println("드라이버 로드 성공");
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private Connection getConnection() {
		DataSource ds = null;
		Connection con = null;
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle");
			con = ds.getConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	private void closeConnection(Connection con) {
		if(con!=null) {
			try {
				con.close();
			}catch(SQLException e) {}
		}
	}
	
	public List<EmpVO> selectEmployeeList(int num, boolean a){
		Connection con = null;
		List<EmpVO> list = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "";
			if(num==0) {
				sql = "select * from employees";
			}else if(num==1) {
				sql = "select * from employees order by salary";
				sql = a ? sql+" desc" : sql;
			}else if(num==2) {
				sql = "select * from employees order by department_id";
				sql = a ? sql+" desc" : sql;
			}
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EmpVO emp = new EmpVO();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble(9));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt(11));
				list.add(emp);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("EmpDAO - selectList");
		}finally {
			closeConnection(con);
		}
		return list;
	}
	
	public EmpDetailVO selectEmployee(int empId) {
		Connection con = null;
		EmpDetailVO emp = new EmpDetailVO();
		try {
			con = getConnection();
			String sql = "select * from employees emp "
					+ "left join jobs jobs "
					+ "on emp.job_id=jobs.job_id "
					+ "left join (select employee_id, first_name||' '||last_name as manager_name "
					+ "from employees where employee_id in"
					+ "(select distinct manager_id from employees)) man "
					+ "on emp.manager_id=man.employee_id "
					+ "left join departments dept "
					+ "on emp.department_id=dept.department_id "
					+ "where emp.employee_id=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, empId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble("commission_pct"));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("department_id"));
				emp.setJobTitle(rs.getString("job_title"));
				emp.setManagerName(rs.getString("manager_name"));
				emp.setDepartmentName(rs.getString("department_name"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("EmpDAO - select");
		}finally {
			closeConnection(con);
		}
		return emp;
	}
	
	public List<EmpVO> selectEmployeeByDeptId(int deptId){
		Connection con = null;
		List<EmpVO> list = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select * from employees where department_id=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, deptId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EmpVO emp = new EmpVO();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble(9));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt(11));
				list.add(emp);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("EmpDAO - selectEmployeeByDeptId");
		}finally {
			closeConnection(con);
		}
		return list;
	}
	
	public List<EmpVO> selectEmployeeByName(String name){
		Connection con = null;
		List<EmpVO> list = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select * from employees where first_name like ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			String word = "%"+name+"%";
			stmt.setString(1, word);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EmpVO emp = new EmpVO();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble(9));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt(11));
				list.add(emp);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("EmpDAO - selectEmployeeByName");
		}finally {
			closeConnection(con);
		}
		return list;
	}
	
	public List<JobVO> selectAllJobs(){
		Connection con = null;
		List<JobVO> list = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select job_id, job_title from jobs";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				JobVO job = new JobVO();
				job.setJobId(rs.getString("job_id"));
				job.setJobTitle(rs.getString("job_title"));
				list.add(job);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("EmpDAO - selectAllJobs");
		}finally {
			closeConnection(con);
		}
		return list;
	}
	
	public List<EmpVO> selectAllManagers(){
		Connection con = null;
		List<EmpVO> list = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select employee_id, "
					+ "first_name||' '||last_name as manager_name "
					+ "from employees "
					+ "where employee_id in"
					+ "(select distinct manager_id from employees)";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EmpVO emp = new EmpVO();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("manager_name"));
				list.add(emp);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("EmpDAO - selectAllManagers");
		}finally {
			closeConnection(con);
		}
		return list;
	}
	
	public List<DeptVO> selectAllDepartments(){
		Connection con = null;
		List<DeptVO> list = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select department_id, department_name from departments";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				DeptVO dept = new DeptVO();
				dept.setDepartmentId(rs.getInt("department_id"));
				dept.setDepartmentName(rs.getString("department_name"));
				list.add(dept);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("EmpDAO - selectAllJobs");
		}finally {
			closeConnection(con);
		}
		return list;
	}
	
	public void insertEmployee(EmpVO emp) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "insert into employees values(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, emp.getEmployeeId());
			stmt.setString(2, emp.getFirstName());
			stmt.setString(3, emp.getLastName());
			stmt.setString(4, emp.getEmail());
			stmt.setString(5, emp.getPhoneNumber());
			stmt.setDate(6, emp.getHireDate());
			stmt.setString(7, emp.getJobId());
			stmt.setDouble(8, emp.getSalary());
			stmt.setDouble(9, emp.getCommissionPct());
			stmt.setInt(10, emp.getManagerId());
			stmt.setInt(11, emp.getDepartmentId());
			stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("EmpDAO - insertEmployee");
		}finally {
			closeConnection(con);
		}
	}

	public void updateEmployee(EmpVO emp) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "update employees set first_name=?, last_name=?,"
					+ "email=?, phone_number=?, hire_date=?, job_id=?,"
					+ "salary=?, commission_PCT=?, manager_id=?, department_id=? "
					+ "where employee_id=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, emp.getFirstName());
			stmt.setString(2, emp.getLastName());
			stmt.setString(3, emp.getEmail());
			stmt.setString(4, emp.getPhoneNumber());
			stmt.setDate(5, emp.getHireDate());
			stmt.setString(6, emp.getJobId());
			stmt.setDouble(7, emp.getSalary());
			stmt.setDouble(8, emp.getCommissionPct());
			stmt.setInt(9, emp.getManagerId());
			stmt.setInt(10, emp.getDepartmentId());
			stmt.setInt(11, emp.getEmployeeId());
			if(stmt.executeUpdate()==0) {
				throw new RuntimeException("수정이 되지 않았습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("EmpDAO - updateEmployee");
		}finally {
			closeConnection(con);
		}
	}
	
}
























