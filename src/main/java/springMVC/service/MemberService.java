package springMVC.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import springMVC.dao.MemberDAO;
import springMVC.utils.Criteria;
import springMVC.utils.ExceptionHandler;
import springMVC.utils.Image;
import springMVC.vo.ImageVO;
import springMVC.vo.MemberVO;

@Service
public class MemberService {

	// 로깅
	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private MemberDAO MemberDAO;

	public List<Map<String, Object>> selectDataListPage(MemberVO memberVO, Criteria criteria) throws Exception {
		return MemberDAO.selectDataListPage(memberVO, criteria);
	}

	public int getTotalCount(Criteria criteria) throws Exception {
		return MemberDAO.getTotalCount(criteria);
	}

	public MemberVO memberDetail(MemberVO memberVO) throws Exception {
		return MemberDAO.memberDetail(memberVO);
	}

	public int regMember(MemberVO memberVO) throws Exception {
		return MemberDAO.regMember(memberVO);
	}

	public int modifyMember(MemberVO memberVO) throws Exception {
		return MemberDAO.modifyMember(memberVO);
	}

	public int deleteMember(MemberVO memberVO) throws Exception {
		return MemberDAO.deleteMember(memberVO);
	}

	public MemberVO getMemberSeq(MemberVO memberVO) throws Exception {
		return MemberDAO.getMemberSeq(memberVO);
	}
	// 이미지 전송
	public Map<String, Object> insertImage(Map<String, Object> params, MemberVO memberVO, ImageVO imageVO, MultipartFile[] uploadFile) throws Exception {

		log.debug("void insertImage..........");
		log.debug("uploadFile.........." + Arrays.toString(uploadFile));

		ExceptionHandler exceptionHandler = new ExceptionHandler();

		// 신규 작성글인지 판단
		boolean NewRegYN = params.get("NewRegYN") != null;
		log.debug("NewRegYN {}", NewRegYN);

		// 이미지 파일 있는지 검사
		if (uploadFile == null || uploadFile.length <= 0) {
			// 이미지 없으면? 업로드 없이 다시 글쓰기로 return
			params.put("imageExist", true);
		} else {
			// 기존 이미지 무조건 삭제
			MemberDAO.deleteImageOnly(memberVO.getMemberSeq());
			
			// 이미지 Util
			Image image = new Image();
			// 실제 이미지 파일 전달
			params = image.uploadAjaxActionPOST(uploadFile);
			
			// params 결과 따라 이미지 검증 실패 구분하기
			boolean imageVerify = params.get("ImageCheckResult") != null;

			if (!imageVerify) {
				log.debug("이미지 검증 imageVerif : " + false);
				log.debug("이미지 검증 실패");
				
				log.debug("insertImage - memberVO.........." + memberVO);

				// 새로 작성한 글이면 글 삭제
				// 새로 작성한 글이 아니면 글 삭제하지 않음
				log.debug("NewRegYN {}", NewRegYN);
				try {
					if(NewRegYN){
						this.deleteMember(memberVO);
						log.debug("이미지 검증 실패로 신규 작성글 다시 삭제");
						params.put("NewRegYN", true);
						throw new Exception();
					} else {
						log.debug("이미지 검증 실패로, 현재글 삭제하지 않음");
						params.put("NewRegYN", false);
						throw new Exception();
					}
				} catch (DataAccessException e){
					String errorMsg = "이미지 DB 저장중 오류 발생, 잘못된 이미지 입니다 {}";
					exceptionHandler.printErrorLog(e, errorMsg);
				}


				params.put("imageVerify", false);

			} else {
				log.debug("이미지 검증 imageVerify : " + true);
				log.debug("이미지 검증 성공");
				// imageVO 값 세팅
				imageVO.setMemberSeq(memberVO.getMemberSeq());
				imageVO.setFileName(String.valueOf(params.get("fileName")));
				imageVO.setUploadPath(String.valueOf(params.get("uploadPath")));
				imageVO.setUuid(String.valueOf(params.get("uuid")));
				
				log.debug("uploadFile DB 데이터 전달 fileName .........." + params.get("fileName"));
				log.debug("uploadFile DB 데이터 전달 uploadPath .........." + params.get("uploadPath"));
				log.debug("uploadFile DB 데이터 전달 uuid .........." + params.get("uuid"));
				
				try {
					MemberDAO.insertImage(imageVO);
					params.put("imageVerify", true);
				} catch (DataAccessException e) {
					String errorMsg = "이미지 DB 저장중 오류 발생 {}";
					exceptionHandler.printErrorLog(e, errorMsg);
					params.put("imageVerify", false);
				}
			}
			
		}
		return params;
	}
	// 이미지 출력
	public ImageVO getImage(int memberSeq) throws SQLException {
		log.info("ImageVO getImage..........");
		log.info("memberSeq.........." + memberSeq);
		return MemberDAO.getImage(memberSeq);
	}
	// 이미지 삭제
	public void deleteImageOnly(int memberSeq) throws SQLException {
		log.info("ImageVO deleteImageOnly..........");
		log.info("memberSeq.........." + memberSeq);
		MemberDAO.deleteImageOnly(memberSeq);
	}

	public List<MemberVO> selectDataListPageExcel(MemberVO memberVO, Criteria criteria) throws SQLException {
		log.info("List<MemberVO> selectDataListPageExcel..........");
		log.info("memberVO.........." + memberVO);
		log.info("criteria.........." + criteria);
		return MemberDAO.selectDataListPageExcel(memberVO, criteria);
	}
}
