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

	// ??????
	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private MemberService MemberService;

	@GetMapping("/excel/download")
	public void excelDownload(HttpServletResponse response, Criteria criteria, MemberVO memberVO,
			HttpServletRequest request) throws IOException, SQLException, ParseException, Exception {
		ExceptionHandler exceptionHandler = new ExceptionHandler();
		// ????????? ?????? ??????...
		// VO ????????? ?????? ???????????? mapper ????????? AS '??????' ?????? ????????? ???????????? ???????????? ???????????? ???????????? ?????? ??????
		List<MemberVO> selectDataListPageExcel = null;
		try {
			// ????????? ????????????
			selectDataListPageExcel = MemberService.selectDataListPageExcel(memberVO, criteria);
		} catch (DataAccessException e) {
			String errorMsg = "excel ?????? ???????????? ??? ?????? ?????? {}";
			exceptionHandler.printErrorLog(e, errorMsg);
		}
//        Workbook wb = new HSSFWorkbook();
//        Workbook wb = new XSSFWorkbook();
		// ????????? ??????
		SXSSFWorkbook workbook = new SXSSFWorkbook();
//        Sheet sheet = wb.createSheet("????????? ??????");
		// ?????? ??????
		SXSSFSheet sheet = workbook.createSheet("sheetName");
		// ?????? ??? ?????? ?????? (0, ????????? * 256)
//		sheet.setColumnWidth(0, 10 * 256);

		// row(???) ?????? ??????, cell(???) ?????? ??????
		int rowCount = 0;
		int cellCount = 0;

		// row(???) ??????
//		Row row = sheet.createRow(rowCount++);

		// cell(???) ??????
//		Cell cell = row.createCell(cellCount++);

		// cell(???) ??? ??????
//		cell.setCellValue('???');

		// ????????? ????????? ????????? ??????
        XSSFCellStyle headStyle = (XSSFCellStyle) workbook.createCellStyle();
        headStyle.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 86, (byte) 162, (byte) 207 }, null));
        headStyle.setFillPattern(FillPatternType.FINE_DOTS);
        headStyle.setAlignment(HorizontalAlignment.CENTER);

		// ????????? ????????? ????????? ??????
		CellStyle cellStyle = workbook.createCellStyle();

		// Style ????????? Cell??? ????????????
//		cell.setCellStyle(cellStyle);

		// Cell ??????
//    	sheet.addMergedRegion(new CellRangeAddress(?????????, ??????, ?????????, ??????);
		// ??????
//		headStyle.setAlignment(HorizontalAlignment.CENTER); // ????????? ??????
//    	cellStyle.setAlignment(HorizontalAlignment.RIGHT); // ?????? ??????
//    	cellStyle.setAlignment(HorizontalAlignment.LEFT); // ?????? ??????
//    	cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // ?????? ????????? ??????
//    	cellStyle.setVerticalAlignment(VerticalAlignment.TOP); // ?????? ?????? ??????

		// ????????? ???(???, ???, ???, ??????)
//    	cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//    	cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//    	cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//    	cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		// ?????? ???????????? ????????????.
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);

		// ????????? (?????????)
		XSSFCellStyle xssCellStyle = (XSSFCellStyle) workbook.createCellStyle();
//		xssCellStyle.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 86, (byte) 162, (byte) 207 }, null));
//		xssCellStyle.setFillPattern(FillPatternType.FINE_DOTS);
//		xssCellStyle.setAlignment(HorizontalAlignment.CENTER);

		// ????????????
		Font font = workbook.createFont();
		font.setFontName("????????????"); // ?????????
		font.setFontHeight((short) (14 * 20)); // ?????????
		font.setBold(true); // ??????
		// ?????? ??????
		headStyle.setFont(font);

		// ????????? ?????? ?????? (????????? ????????? ?????? cellStyle ????????? ??????)
		CellStyle cellStyleDate = workbook.createCellStyle();
		cellStyleDate.setAlignment(HorizontalAlignment.CENTER); // ????????? ??????
		cellStyleDate.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd")); // ??????

		// Header
		Row row = sheet.createRow(rowCount++);
		Cell cell = row.createCell(0);
		cell.setCellStyle(headStyle);
		cell.setCellValue("??????");
		cell = row.createCell(1);
		cell.setCellStyle(headStyle);
		cell.setCellValue("????????????");
		cell = row.createCell(2);
		cell.setCellStyle(headStyle);
		cell.setCellValue("????????????");
		cell = row.createCell(3);
		cell.setCellStyle(headStyle);
		cell.setCellValue("????????????");
		cell = row.createCell(4);
		cell.setCellStyle(headStyle);
		cell.setCellValue("?????????");
		
		// ????????? ?????? ??????
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

		// ????????? ??????
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String time = simpleDateFormat.format(date);
		String fileName = "excelFileName" + "_" + time + ".xlsx";

		// ???????????? ??????
		String browser = request.getHeader("User-Agent");

		// ??????????????? ?????? ????????? ????????? ??????
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

		// ??????????????? ?????? ??????????????? ??????
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

		// response ??????
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		// ?????? ?????? : OutputStream ??? ?????? ????????? ?????? write
		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);

		// ?????? ?????? : FileOutputStream
		// FileOutputStream fileOutputStream = new FileOutputStream("????????????");
		// workbook.write(fileOutputStream);
		// ????????? ??????
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
//	        //workbook ??????
//	        XSSFWorkbook workbook=new XSSFWorkbook();
//	        //sheet ??????
//	        XSSFSheet sheet=workbook.createSheet(menuName);
//	        //????????? ???
//	        XSSFRow row = null;
//	        //????????? ???
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
//	        //????????????
//	        row = sheet.createRow(0);
//	        sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
//	        cell = row.createCell(0);
//	        cell.setCellStyle(cellStyle_title);
//	        cell.setCellValue(menuName);
//	        
//	        
//	        
//	        //row ?????? ??????
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
//	        //?????????
//	        Calendar cal = Calendar.getInstance();
//	        SimpleDateFormat fm1 = new SimpleDateFormat("yyyyMMddhhmmss");
//	        todays = fm1.format(cal.getTime());
//	                    
//	        fileName = menuName + todays; 
//	        
//	        //?????? ??????
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
//	        //????????? ??????
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
//	        //????????? ?????????????????? 
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
