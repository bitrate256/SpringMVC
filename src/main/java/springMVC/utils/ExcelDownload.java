package springMVC.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.model.Model;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import springMVC.service.MemberService;
import springMVC.vo.MemberVO;

@Controller
public class ExcelDownload {

	// 로깅
	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private MemberService MemberService;

	@GetMapping("/excel/download")
	public void excelDownload(HttpServletResponse response, Criteria criteria, MemberVO memberVO,
			HttpServletRequest request) throws IOException, SQLException, ParseException, Exception {
		ExceptionHandler exceptionHandler = new ExceptionHandler();
		// 모듈화 하기 위해...
		// VO 객체를 직접 받아오고 mapper 에서는 AS '제목' 해서 테이블 이름들을 가져오는 모듈화가 가능할지 분석 필요
		List<MemberVO> selectDataListPageExcel = null;
		try {
			// 게시판 목록조회
			selectDataListPageExcel = MemberService.selectDataListPageExcel(memberVO, criteria);
		} catch (DataAccessException e) {
			String errorMsg = "excel 파일 다운로드 중 오류 발생 {}";
			exceptionHandler.printErrorLog(e, errorMsg);
		}
//        Workbook wb = new HSSFWorkbook();
//        Workbook wb = new XSSFWorkbook();
		// 워크북 생성
		SXSSFWorkbook workbook = new SXSSFWorkbook();
//        Sheet sheet = wb.createSheet("첫번째 시트");
		// 시트 생성
		SXSSFSheet sheet = workbook.createSheet("sheetName");
		// 시트 열 너비 설정 (0, 글자수 * 256)
//		sheet.setColumnWidth(0, 10 * 256);

		// row(행) 순서 변수, cell(셀) 순서 변수
		int rowCount = 0;
		int cellCount = 0;

		// row(행) 생성
//		Row row = sheet.createRow(rowCount++);

		// cell(셀) 생성
//		Cell cell = row.createCell(cellCount++);

		// cell(셀) 값 입력
//		cell.setCellValue('값');

		// 테이블 헤더용 스타일 객체
        XSSFCellStyle headStyle = (XSSFCellStyle) workbook.createCellStyle();
        headStyle.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 86, (byte) 162, (byte) 207 }, null));
        headStyle.setFillPattern(FillPatternType.FINE_DOTS);
        headStyle.setAlignment(HorizontalAlignment.CENTER);

		// 테이블 데이터 스타일 객체
		CellStyle cellStyle = workbook.createCellStyle();

		// Style 정보를 Cell에 입력하기
//		cell.setCellStyle(cellStyle);

		// Cell 병합
//    	sheet.addMergedRegion(new CellRangeAddress(시작행, 끝행, 시작열, 끝열);
		// 정렬
//		headStyle.setAlignment(HorizontalAlignment.CENTER); // 가운데 정렬
//    	cellStyle.setAlignment(HorizontalAlignment.RIGHT); // 우측 정렬
//    	cellStyle.setAlignment(HorizontalAlignment.LEFT); // 좌측 정렬
//    	cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 높이 가운데 정렬
//    	cellStyle.setVerticalAlignment(VerticalAlignment.TOP); // 높이 상단 정렬

		// 테두리 선(좌, 우, 위, 아래)
//    	cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//    	cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//    	cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//    	cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		// 가는 경계선을 가집니다.
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);

		// 배경색 (제목단)
		XSSFCellStyle xssCellStyle = (XSSFCellStyle) workbook.createCellStyle();
//		xssCellStyle.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 86, (byte) 162, (byte) 207 }, null));
//		xssCellStyle.setFillPattern(FillPatternType.FINE_DOTS);
//		xssCellStyle.setAlignment(HorizontalAlignment.CENTER);

		// 폰트설정
		Font font = workbook.createFont();
		font.setFontName("나눔고딕"); // 글씨체
		font.setFontHeight((short) (14 * 20)); // 사이즈
		font.setBold(true); // 굵게
		// 폰트 적용
		headStyle.setFont(font);

		// 테이블 포맷 설정 (적용할 대상에 맞는 cellStyle 변수를 대입)
		CellStyle cellStyleDate = workbook.createCellStyle();
		cellStyleDate.setAlignment(HorizontalAlignment.CENTER); // 가운데 정렬
		cellStyleDate.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd")); // 날짜

		// Header
		Row row = sheet.createRow(rowCount++);
		Cell cell = row.createCell(0);
		cell.setCellStyle(headStyle);
		cell.setCellValue("번호");
		cell = row.createCell(1);
		cell.setCellStyle(headStyle);
		cell.setCellValue("회원이름");
		cell = row.createCell(2);
		cell.setCellStyle(headStyle);
		cell.setCellValue("회원등급");
		cell = row.createCell(3);
		cell.setCellStyle(headStyle);
		cell.setCellValue("사용유무");
		cell = row.createCell(4);
		cell.setCellStyle(headStyle);
		cell.setCellValue("등록일");
		
		// 데이터 부분 생성
		for (MemberVO vo : Objects.requireNonNull(selectDataListPageExcel)) {
			row = sheet.createRow(rowCount++);
			cell = row.createCell(0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(vo.getMemberSeq());
			cell = row.createCell(1);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(vo.getMemberName());
			cell = row.createCell(2);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(vo.getMemberGrade());
			cell = row.createCell(3);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(vo.getMemberUseYn());
			cell = row.createCell(4);
			cell.setCellStyle(cellStyleDate);
			cell.setCellValue(vo.getRegDate());

		}

		// 파일명 설정
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String time = simpleDateFormat.format(date);
		String fileName = "excelFileName" + "_" + time + ".xlsx";

		// 브라우저 얻기
		String browser = request.getHeader("User-Agent");

		// 브라우저에 따른 파일명 인코딩 설정
		if (browser.contains("MSIE")) {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.contains("Trident")) { // IE11
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.contains("Firefox")) {
			fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.contains("Opera")) {
			fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.contains("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < fileName.length(); i++) {
				char c = fileName.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			fileName = sb.toString();
		} else if (browser.contains("Safari")) {
			fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		} else {
			fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		}

		// 브라우저에 따른 컨텐츠타입 설정
		String contentType = "application/download;charset=utf-8";
		switch (browser) {
		case "Firefox":
		case "Opera":
			contentType = "application/octet-stream; text/html; charset=UTF-8;";
			break;
		default: // MSIE, Trident, Chrome, ..
			contentType = "application/x-msdownload; text/html; charset=UTF-8;";
			break;
		}
		response.setContentType("application/x-msdownload; text/html; charset=UTF-8;"); // msie, tRIDE
		response.setContentType("application/octet-stream; text/html; charset=UTF-8;");

		// response 설정
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		// 파일 생성 : OutputStream 을 얻어 생성한 엑셀 write
		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);

		// 파일 생성 : FileOutputStream
		// FileOutputStream fileOutputStream = new FileOutputStream("파일경로");
		// workbook.write(fileOutputStream);
		// 완료후 닫기
        workbook.close();
	}

//    @ResponseBody
//	@RequestMapping(value="/excel/download2",method = RequestMethod.POST)
//	public String excelDownload(HttpServletResponse response, HttpServletRequest req, Model model,@RequestParam Map<String,Object> params) throws Exception{
//	    String headData = (String) params.get("head");
//	    String[] headDataList = headData.split(",");
//
//	    String bodyData =  (String) params.get("body");
//	    
//	    Map<String, Object> retMap = new Gson().fromJson(
//	            bodyData, new TypeToken<HashMap<String, Object>>() {}.getType()
//	        );
//
//	    String menuName = (String) params.get("menuName");
//	    
//	    String msg = "";
//	    String returnUrl = "";
//	    
//	    try {
//	        //workbook 생성
//	        XSSFWorkbook workbook=new XSSFWorkbook();
//	        //sheet 생성
//	        XSSFSheet sheet=workbook.createSheet(menuName);
//	        //엑셀의 행
//	        XSSFRow row = null;
//	        //엑셀의 셀
//	        XSSFCell cell =null;
//	        
//	        XSSFCellStyle cellStyle_d9d9d9 = workbook.createCellStyle();			
//	        XSSFColor myColor_d9d9d9 = new XSSFColor(Color.decode("#d9d9d9"));
//	        cellStyle_d9d9d9.setFillForegroundColor(myColor_d9d9d9);
//	        cellStyle_d9d9d9.setFillPattern(CellStyle.SOLID_FOREGROUND);
//	        cellStyle_d9d9d9.setBorderRight(XSSFCellStyle.BORDER_THIN);             	        
//	        cellStyle_d9d9d9.setBorderLeft(XSSFCellStyle.BORDER_THIN);   
//	        cellStyle_d9d9d9.setBorderTop(XSSFCellStyle.BORDER_THIN);   
//	        cellStyle_d9d9d9.setBorderBottom(XSSFCellStyle.BORDER_THIN); 
//	        
//	        XSSFCellStyle cellStyle_border = workbook.createCellStyle();
//	        cellStyle_border.setAlignment(HorizontalAlignment.CENTER);
//	        cellStyle_border.setBorderRight(XSSFCellStyle.BORDER_THIN);             	        
//	        cellStyle_border.setBorderLeft(XSSFCellStyle.BORDER_THIN);   
//	        cellStyle_border.setBorderTop(XSSFCellStyle.BORDER_THIN);   
//	        cellStyle_border.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//	        
//	        XSSFCellStyle cellStyle_number = workbook.createCellStyle();
//	        cellStyle_number.setAlignment(HorizontalAlignment.RIGHT);
//	        cellStyle_number.setBorderRight(XSSFCellStyle.BORDER_THIN);             	        
//	        cellStyle_number.setBorderLeft(XSSFCellStyle.BORDER_THIN);   
//	        cellStyle_number.setBorderTop(XSSFCellStyle.BORDER_THIN);   
//	        cellStyle_number.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//	        cellStyle_number.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
//	        
//	        XSSFCellStyle cellStyle_title = workbook.createCellStyle();
//	        cellStyle_title.setAlignment(HorizontalAlignment.CENTER);
//	        cellStyle_title.setBorderRight(XSSFCellStyle.BORDER_THIN);             	        
//	        cellStyle_title.setBorderLeft(XSSFCellStyle.BORDER_THIN);   
//	        cellStyle_title.setBorderTop(XSSFCellStyle.BORDER_THIN);   
//	        cellStyle_title.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//	        
//	        //제목추가
//	        row = sheet.createRow(0);
//	        sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
//	        cell = row.createCell(0);
//	        cell.setCellStyle(cellStyle_title);
//	        cell.setCellValue(menuName);
//	        
//	        
//	        
//	        //row 크기 설정
//	        for(int i=0; i<headDataList.length;i++) {
//	            sheet.setColumnWidth(i, 5000);
//	        }
//	        
//
//	        
//	        for(String key:retMap.keySet()) {
//	            List<String> value = (List<String>) retMap.get(key);
//	            row = sheet.createRow(Integer.parseInt(key));
//	            int i =0;
//	            for(String data:value) {
//	                cell = row.createCell(i);
//	                cell.setCellValue(data);
//	                cell.setCellStyle(cellStyle_border);
//	                i++;
//	            }
//	            
//	        }
//	        
//	        
//	        
//	        String todays = "";
//	        String fileName = "";
//	        String filePath = "";
//	        FileOutputStream fileoutputstream = null;
//	        String logicalName = "";
//	        String physicalName = "";
//	        
//	        
//	        //파일명
//	        Calendar cal = Calendar.getInstance();
//	        SimpleDateFormat fm1 = new SimpleDateFormat("yyyyMMddhhmmss");
//	        todays = fm1.format(cal.getTime());
//	                    
//	        fileName = menuName + todays; 
//	        
//	        //파일 저장
//	        FileUtil fileUtil = new FileUtil(req);
//	        filePath = fileUtil.getRealPath("/uploadFiles") + fileUtil.getFileSeparator() +"excel"+ fileUtil.getFileSeparator();
//	        
//	        FileOutputStream fos = new FileOutputStream(filePath+fileName+".xlsx");
//	        
//	        
//	        
//	        fileUtil.makeDir(filePath);
//	        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
//	        fileoutputstream = new FileOutputStream(filePath+fileName+".xlsx");
//	        fileoutputstream.close();
//	        //파일을 쓴다
////				workbook.write(fileoutputstream);
//	        
//	        workbook.write(fileOut);
//	        InputStream filein = new ByteArrayInputStream(fileOut.toByteArray());
//	        OPCPackage opc = OPCPackage.open(filein);
//	        
//	        POIFSFileSystem fileSystem = new POIFSFileSystem();
//	        
//	        EncryptionInfo encryptionInfo = new EncryptionInfo(EncryptionMode.agile);
//	        Encryptor encryptor = encryptionInfo.getEncryptor();
//	        encryptor.confirmPassword("2021");
//	        
//	        opc.save(encryptor.getDataStream(fileSystem));
//	         fileSystem.writeFilesystem(fos);
//	        //필수로 닫아주어야함 
//	        
//	        logicalName = fileName+".xlsx";
//	        physicalName = "/uploadFiles/excel/"+fileName+".xlsx";
//	        
//	        logicalName = URLEncoder.encode(logicalName, "UTF-8");
//	        physicalName = URLEncoder.encode(physicalName, "UTF-8");
//	        
//	        returnUrl = "/cms/filedown/download?logicalName="+logicalName+"&physicalName="+physicalName;
//	        
//	        
//
//	    } catch (FileNotFoundException e) {
//	        e.printStackTrace();
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    } finally{
//	     
//	    } 
//	     
//	    return returnUrl;
//	}
}
