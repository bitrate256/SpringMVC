package springMVC.utils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import springMVC.vo.ImageVO;

@RestController
public class Image {

	// 로깅
	private Logger log = LogManager.getLogger(getClass());
	
	private boolean checkMIMETYPE;

	// 첨부파일 업로드 순서
//	1. 이미지 파일 저장
//	2. 썸네일 이미지 파일 생성 및 저장
//	3. 각 이미지 정보 List 객체에 저장
//	4. ResponseEntity 를 통해서 뷰(view)로 http 상태 코드가 200이고 http Body 에 이미지 정보가 담긴 List 객체를 전송

	/* 첨부 파일 업로드 */
	@PostMapping(value = "/member/uploadAjaxAction", produces = "application/json")
	public Map<String, Object> uploadAjaxActionPOST(MultipartFile[] uploadFile) {
		log.info("uploadAjaxActionPOST..........");

		ExceptionHandler exceptionHandler = new ExceptionHandler();

		Map<String, Object> params = new HashMap<String, Object>();

		// 기초경로 설정
		String uploadFolder = null;
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			uploadFolder = "C:\\upload\\";

		} else if (os.contains("mac")) {
			uploadFolder = "/users/upload/";
		}
//		else if (os.contains("linux")) {
//			System.out.println("Linux");
//		}
//		String path = "C:\\upload\\";
//		String path = "/users/upload/";

		// 오늘 날짜 획득
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		// 날짜 포맷 사이 대쉬(-) 를 운영체제에 맞는 구분자로 자동 변환
		String datePath = str.replace("-", File.separator);

		/* 폴더 생성 */
		// 만들고자 하는 경로의 디렉토리를 대상으로 하는 File 객체로 초기화
		// ex) "c:\\upload\yyyy\MM\dd"
		File uploadPath = new File(uploadFolder, datePath);

		log.debug("업로드 패스 uploadPath : " + uploadPath);

		// 해당 디렉토리가 없으면 폴더 생성, 이미 존재하면 폴더생성하지 않음
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}

		/* 이미지 정보 담는 객체 */
		ArrayList<ImageVO> imageList = new ArrayList<ImageVO>();

		// 향상된 for
		for (MultipartFile multipartFile : uploadFile) {

			log.info("파일 이름 : " + multipartFile.getOriginalFilename());
			log.info("파일 타입 : " + multipartFile.getContentType());
			log.info("파일 크기 : " + multipartFile.getSize());

			/* 이미지 정보 객체 */
			ImageVO imageVO = new ImageVO();

			/* 파일 이름 */
			String uploadFileName = multipartFile.getOriginalFilename();
			imageVO.setFileName(uploadFileName);
			imageVO.setUploadPath(datePath);

			/* uuid 적용 파일 이름 */
			String uuid = UUID.randomUUID().toString();
			imageVO.setUuid(uuid);

			uploadFileName = uuid + "_" + uploadFileName;

			/* 파일 위치, 파일 이름을 합친 File 객체 */
			File saveFile = new File(uploadPath, uploadFileName);

			log.info("MIME TYPE 체크 시작"); /////////////////////////////// IMG PDF EXCEL 검증
			try {
				InputStream inputStream = multipartFile.getInputStream();
				String mimeType = new Tika().detect(inputStream);

				checkMIMETYPE = Objects.requireNonNull(mimeType).startsWith("image");

				if (checkMIMETYPE) {
					log.info("MIME TYPE 체크 : 이미지 파일이 맞습니다");
					params.put("checkMIMETYPE", true);
				} else {
					log.info("MIME TYPE 체크 : 이미지 파일이 아닙니다");
					params.put("checkMIMETYPE", false);
					throw new IOException();
				}
			} catch (IOException e) {
				String errorMsg = "이미지 파일이 아닙니다 {}";
				exceptionHandler.printErrorLog(e, errorMsg);
			}
			log.info("MIME TYPE 체크 종료"); /////////////////////////////// IMG PDF EXCEL 검증

			/* 파일 저장 */
			if(checkMIMETYPE){
				try {
					multipartFile.transferTo(saveFile);

					// 썸네일 생성 및 저장 (ImageIO)
					// 썸네일 이미지 File 객체 초기화
					// 이름은 "s_"+"이미지 이름"
					File thumbnailFile = new File(uploadPath, "thumbnail_" + uploadFileName);

					// 원본 이미지 파일을 BufferedImage 타입으로 변경하고 참조변수를 선언해 해당 변수에 대입함
					BufferedImage original_image = ImageIO.read(saveFile);

					// 비율 축소 위한 설정 (업체별로 기능별로 줄일 비율이 다름)
					/* 비율 */
					double ratio = 3;
					/* 넓이 높이 */
					int width = (int) (original_image.getWidth() / ratio);
					int height = (int) (original_image.getHeight() / ratio);

					/* 방법 1 (직접 커스텀) */
					// BufferedImage 생성자를 사용해 썸네일 이미지인 BufferedImage 객체를 생성하고 참조변수에 대입함
					// 생성자 매개변수 : 넓이, 높이, 생성될 이미지의 타입 ( 타입은 공식문서 참조 : Java BufferedImage API )
//				BufferedImage thumbnail_image = new BufferedImage(300, 500, BufferedImage.TYPE_3BYTE_BGR);
					// 썸네일 객체에서 createGraphics 메서드 호출로 Graphics2D 객체 생성한 후 Graphics2D 타입의 참조변수에 대입함
//				Graphics2D graphic = thumbnail_image.createGraphics();
					// drawImage 메서드 호출해 원본 이미지를 썸네일에 지정한 크기로 변경해 좌상단 0,0 좌표부터 그림
//				graphic.drawImage(original_image, 0, 0, width, height, null);
					// 제작한 썸네일 이미지를 파일로 저장함
//				ImageIO.write(thumbnail_image, "jpg", thumbnailFile);

					/* 방법 2 (썸네일 라이브러리 사용) */
					Thumbnails.of(saveFile).size(width, height).toFile(thumbnailFile);
					log.info("파일 저장 종료");
				} catch (NullPointerException | IOException e) {
					String errorMsg = "이미지 파일 저장 실패 {}";
					exceptionHandler.printErrorLog(e, errorMsg);
				}

				// 이미지 정보가 저장된 ImageVO 객체를 imageList의 요소로 추가함
				// 이제 뷰로부터 이미지를 전달받은 만큼 ImageVO 객체가 생성되어 각 정보를 저장 한후 해당 객체가 List의 요소로 추가 되게 됨
				imageList.add(imageVO);
				params.put("imageList", imageList);
				params.put("fileName", uploadFileName);
				params.put("uploadPath", datePath);
				params.put("uuid", uuid);

				boolean isImageFile = isImageFile(saveFile);
				log.info("이미지 파일 2차 검증 isImageFile : " + isImageFile);
				// 파일을 isImageFile로 다시한번 검증
				if (isImageFile) // 이미지 파일이 아니라면 삭제
				{
					log.info("isImageFile 메서드 체크 : 이미지 파일이 맞습니다");
					params.put("isImageFile", true);
					log.info("params.get(\"isImageFile\") : " + params.get("isImageFile"));
				} else {
					log.info("isImageFile 메서드 체크 : 이미지 파일이 아닙니다");
					params.put("isImageFile", false);
					log.info("params.get(\"isImageFile\") : " + params.get("isImageFile"));
				}
				log.info("이미지 파일 2차 검증 종료");

				log.info("checkMIMETYPE : " + checkMIMETYPE);
				log.info("isImageFile : " + isImageFile);

				if (!checkMIMETYPE && !isImageFile) {
					params.put("ImageCheckResult", false);
				} else if (checkMIMETYPE && isImageFile) {
					params.put("ImageCheckResult", true);
				}

			} else {
				log.info("파일 저장 진행하지 않음 / checkMIMETYPE : " + false);
			}
		}
		// 기본 for
//			for(int i = 0; i < uploadFile.length; i++) {
//				log.info("-----------------------------------------------");
//				log.info("파일 이름 : " + uploadFile[i].getOriginalFilename());
//				log.info("파일 타입 : " + uploadFile[i].getContentType());
//				log.info("파일 크기 : " + uploadFile[i].getSize());
//			}
		return params;
	}

	// 이미지 출력
	@GetMapping("/display")
	public ResponseEntity<byte[]> getImage(String fileName) {

		ExceptionHandler exceptionHandler = new ExceptionHandler();

		// 컨셉 : '파일 경로(유동 경로)' + '파일 이름' 을 파라미터로 전달받고, 해당 데이터에 맞는 이미지 파일을 찾아서 뷰에 이미지
		// 데이터를 전송

		// 고려사항
		// byte[] : 바이트 배열, 이미지 파일을 주고 받기 위한 타입
		// 이미지 파일은 바이너리 파일(이진데이터), 바이너리는 텍스트 파일을 제외한 모든 파일을 지칭함, 바이너리는 byte로 주고받음

		// url 매핑 메서드 반환 타입
		// ResponseEntity 사용 : header/status 조작 가능, 따라서 해당 데이터의 타입(header의 contentType)이
		// 이미지 파일임을 명시

		// 파라미터로 전달 받은 '파일경로'와 '파일이름'을 활용해 대상 이미지 파일을 File 객체로 생성
		// 해당 File 객체 활용해 MIME TYPE 알아냄
		// 이후 ResponseEntity에 대상 이미지 데이터를 복사해 body에 추가하고 header의 'contentType'에 앞서 얻어낸
		// MIME TYPE으로 수정한 후 ResponseEntity 객체를 호출한 뷰로 전송함
		// 테스트 호출 : http://localhost:8080/display?fileName=테스트파일.jpg
//		File file = new File("C:\\upload\\" + fileName);
		File file = new File(fileName);

		// View로 반환할 ResponseEntity 객체의 주소를 저장할 참조 변수
		ResponseEntity<byte[]> result = null;

		try {
			// ResponseEntity에 Response의 header와 관련된 설정의 객체를 추가하기 위해 HttpHeader를 인스턴스화 한 후
			// 참조 변수를 선언해 대입함
			HttpHeaders header = new HttpHeaders();
			// contentType 속성값에 이미지파일 MIME TYPE 추가
			// 파라미터 1 : Response header의 '속성명' 파라미터 2 : 속성명에 부여할 값(value)
			header.add("Content-type", Files.probeContentType(file.toPath()));
			// 대상 이미지 파일, header 객체, 상태 코드를 인자 값으로 부여한 생성자 통해 ResponseEntity 객체를 생성해 앞서 선언한
			// ResponseEntity 참조 변수에 대입함
			// 파라미터1은 FileCopyUtils 클래스(파일과 stream 복사에 사용할 수 있는 메서드를 제공하는 클래스), 해당 클래스중
			// copyToByteArray() 메서드는 파라미터로 부여하는 File객체(대상파일) 을 복사해 Byte배열로 변환하는 클래스임
			// 파라미터2는 contentType속성을 지정해준 HeepHeader 객체(변수header)를 인자로 부여함.
			// 파라미터3은 코드200
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			String errorMsg = "image 파일 출력 오류 {}";
			exceptionHandler.printErrorLog(e, errorMsg);
		}
		// ResponseEntity 객체 (변수 result)
		return result;
	}

	/* 이미지 파일 삭제 */
	@PostMapping("/deleteFile")
	public ResponseEntity<Boolean> deleteFile(String existFileName, String existThumbNailFileName) {
		// write / modify 화면 에서의 삭제
		
		log.info("deleteFile........" + existFileName);
		log.info("deleteFile........" + existThumbNailFileName);

		ExceptionHandler exceptionHandler = new ExceptionHandler();
		
		boolean deleteResult = true;

		String path = null;
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			path = "C:\\upload\\";

		} else if (os.contains("mac")) {
			path = "/users/upload/";
		}
//		else if (os.contains("linux")) {
//			System.out.println("Linux");
//		}
//		String path = "C:\\upload\\";
//		String path = "/users/upload/";

		try {
			// 썸네일 파일 삭제
			// UTF-8 디코딩 필요 (경로 구분자들이 UTF-8로 인코딩 되어있기 때문)
			// 삭제할 파일을 대상으로 하는 File 클래스를 인스턴스화 하여 앞서 선언한 file 참조 변수가 참조하도록 함
			File file = new File(URLDecoder.decode(existFileName, "UTF-8"));
			File thumbNailFile = new File(URLDecoder.decode(existThumbNailFileName, "UTF-8"));

			// 원본 파일 삭제
			// 썸네일 파일 경로에서 썸네일 접두어를 공백으로 치환하여 원본파일 경로로 활용함
			String originFileName = path+file.getPath();
			log.info("originFileName : " + originFileName);
			
			String thumbNailFileName = path+thumbNailFile.getPath();
			log.info("thumbNailFileName : " + thumbNailFileName);

			// 서버, 혹은 로컬 드라이브의 실제 경로에 적용 필요함
			// 썸네일 파일과의 구분을 위해 전달 받을 때 부터 썸네일 파일을 전달받도록 해야 함
			
			file = new File(originFileName);
			boolean resultOriginalFile = file.delete();
			log.info("resultOriginalFile : " + resultOriginalFile);
			
			file = new File(thumbNailFileName);
			boolean resultThumbNailFile = file.delete();
			log.info("resultThumbNailFile : " + resultThumbNailFile);
			
			if (resultOriginalFile && resultThumbNailFile) {
				log.info("TRUE : " + true);
			} else {
				deleteResult = false;
				log.error("FALSE : " + false);
				throw new Exception();
			}

		} catch (Exception e) {
			String errorMsg = "PDF 파일 삭제 오류 {}";
			exceptionHandler.printErrorLog(e, errorMsg);
			log.error("deleteResult : " + deleteResult);
			return new ResponseEntity<Boolean>(deleteResult, HttpStatus.NOT_IMPLEMENTED);

		}
		log.info("deleteResult : " + true);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	// 이미지 파일 검사
	public boolean isImageFile(File file) {
		log.debug("isImageFile..........파일이 들어오는가? : " + file);
		ExceptionHandler exceptionHandler = new ExceptionHandler();
		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			log.debug("bufferedImage 읽었을 때 : " + bufferedImage);
			if (bufferedImage == null) {
				log.error("=====> no image : bufferedImage 통과 실패");
				return false;
			} else {
				log.info("=====> yes image : bufferedImage 통과 성공");
				return true;
			}
		} catch (Exception e) {
			String errorMsg = "image 파일 검증 오류 {}";
			exceptionHandler.printErrorLog(e, errorMsg);
			log.error("=====> no image : file 정보 null");
			return false;
		}
	}

	// PDF 파일 검사
	public boolean isPdfFile(File file){
		log.debug("isPdfFile..........파일이 들어오는가? : " + file);
		ExceptionHandler exceptionHandler = new ExceptionHandler();
		try(Scanner input = new Scanner(new FileReader(file));)
		{
			while (input.hasNextLine()) {
				final String checkline = input.nextLine();
				if(checkline.contains("%PDF-")) {
					log.info("=====> PDF 파일이 맞습니다");
					input.close();
					return true;
				}
			}
			input.close();
			log.error("=====> PDF 파일이 아닙니다");
			return false;
		}
		catch(Exception e)
		{
			String errorMsg = "PDF 파일 검증 오류 {}";
			exceptionHandler.printErrorLog(e, errorMsg);
			return false;
		}
	}
}
