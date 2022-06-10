import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springMVC.dao.MemberDAO;
import springMVC.dao.mybatis.MemberDAOImpl;
import springMVC.vo.ImageVO;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class DataSourceTest {

    @Inject
    private DataSource ds;

    @Test
    public void testConnection() throws Exception{
        try {
            Connection con = ds.getConnection();
            System.out.println("DB connected");
            System.out.println("DB 접속 성공");
            System.out.println("getConnection() => " + con);
        } catch (Exception e) {
            System.out.println("DB 접속 실패");
            e.printStackTrace();
        }
    }
    
    /* 이미지 등록 */
    /* @Test
	public void imageInsertTest() throws Exception {
		
		ImageVO imageVO = new ImageVO();
		
		imageVO.setMemberSeq(137);
		imageVO.setFileName("test");
		imageVO.setUuid("test2");
		
		MemberDAOImpl memberDAOImpl = new MemberDAOImpl();
		try {
			memberDAOImpl.insertImage(imageVO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} */
}
