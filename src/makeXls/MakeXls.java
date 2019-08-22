package makeXls;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MakeXls {

	public String[][] readExcelData(File file, int ignoreRows,int ignoreRows2,short lieshu,short lieshu2) throws Exception{
        List<String[]> result = new ArrayList<String[]>();
        int rowSize = 0;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        //打开HSSWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFCell cell = null;
        
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            HSSFSheet st = wb.getSheetAt(sheetIndex);
            //从规定位置，开始逐行读取内容
            for (int rowIndex = ignoreRows; rowIndex <= ignoreRows2; rowIndex++) {
                HSSFRow row = st.getRow(rowIndex);
                if(row == null){
                    continue;
                }
                rowSize=lieshu2-lieshu+1;
                String[] values = new String[rowSize];
                Arrays.fill(values, "");
                boolean hasValue = false;
                for (short columnIndex = lieshu; columnIndex <= lieshu2; columnIndex++) {
               // for (short columnIndex = lieshu; columnIndex < row.getLastCellNum(); columnIndex++) {
                    String value = "";
                    cell = row.getCell(columnIndex);
                    
                    if(cell != null){
                        //设置字体编码
                        //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_STRING:
                            value = cell.getStringCellValue();
                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            if(HSSFDateUtil.isCellDateFormatted(cell)){
                                Date date = cell.getDateCellValue();
                                if(date != null){
                                    value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                }else{
                                    value = "";
                                }
                            }else{
                                value = new DecimalFormat("0").format(cell.getNumericCellValue());
                            }
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA:
                            //导入时如果为公式生成的数据则无值
                            if(!cell.getStringCellValue().equals("")){
                                value = cell.getStringCellValue();
                            }else{
                                value = cell.getNumericCellValue() + "";
                            }
                            break;
                        case HSSFCell.CELL_TYPE_BLANK:
                            break;
                        case HSSFCell.CELL_TYPE_ERROR:
                            value = "";
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            value = (cell.getBooleanCellValue() == true ? "Y":"N");
                            break;
                        default:
                            value = "0";
                            break;
                        }
                    }
                    if(columnIndex  == 0 && value.trim().equals("")){
                        break;
                    }
                    values[columnIndex-lieshu] = removeSpaceTrim(value);
                    hasValue = true;
                }
                if(hasValue){
                    result.add(values);
                }
            }
        }
        in.close();
        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) result.get(i);
        }
        return returnArray;
    }

    /**
     * 去掉字符串右边的空格
     * @param 要处理的字符串
     * @return 处理后的结果
     */
    private static String removeSpaceTrim(String value) {

		if (value == null){
			return "";
		}
		int length = value.length();
		for (int i = length -1;i>=0;i--){
			if (value.charAt(i)!=0x20){
				break;
			}
			length--;
		}
		return value.substring(0,length);
	}
    
	/*写入excel	
	 * 
	 * 
	 * 
	 */
	public void setData(String[] str,int i) throws FileNotFoundException,IOException{
		//确定写入的文件地址
        File file = new File("C:/MyEclipse 2017 CI/Check/WebRoot/jiexu.xls"); 
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		HSSFSheet sheet =workbook.getSheetAt(0);
		Row row = sheet.getRow(i);
		
		// 1.生成字体对象
				HSSFFont font = workbook.createFont();
				font.setFontHeightInPoints((short) 9);
				font.setFontName("宋体");
					
		

		HSSFCellStyle cellStyle = workbook.createCellStyle();//创建样式
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 
		cellStyle.setFont(font); 
		//该行的第一个cell是序号
		Cell cell16 = row.createCell(16);
		cell16.setCellValue(str[0]);
		cell16.setCellStyle(cellStyle);
		
		Cell cell17 = row.createCell(17);
		cell17 .setCellValue(str[1]);
		cell17.setCellStyle(cellStyle);
		
		Cell cell18 = row.createCell(18);
		cell18.setCellValue(str[2]);
		cell18.setCellStyle(cellStyle);
		
		Cell cell19 = row.createCell(19);
		cell19.setCellValue(str[3]);
		cell19.setCellStyle(cellStyle);
		
		Cell cell20 = row.createCell(20);
		cell20.setCellValue(str[4]);
		cell20.setCellStyle(cellStyle);
		
		Cell cell21 = row.createCell(21);
		cell21.setCellValue(str[5]);
		cell21.setCellStyle(cellStyle);
		
		Cell cell22 = row.createCell(22);
		cell22.setCellValue(str[6]);
		cell22.setCellStyle(cellStyle);
				
		sheet.setForceFormulaRecalculation(true);
       //写入、关闭
        FileOutputStream stream = FileUtils.openOutputStream(file);
		workbook.write(stream);
		stream.close();

	}
	
	public void setData1(String[] str,int i) throws FileNotFoundException,IOException{
		//确定写入的文件地址
        File file = new File("C:/MyEclipse 2017 CI/Check/WebRoot/jiexu.xls"); 
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		HSSFSheet sheet =workbook.getSheetAt(0);
		Row row = sheet.getRow(i);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName("宋体");
		HSSFCellStyle cellStyle = workbook.createCellStyle();//创建样式
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 
		cellStyle.setFont(font); 
		
		
		HSSFCellStyle cellStyle0 = workbook.createCellStyle();//创建样式
		cellStyle0.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
		cellStyle0.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
		cellStyle0.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
		cellStyle0.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
		cellStyle0.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 
		cellStyle0.setFont(font); 
		 HSSFDataFormat format= workbook.createDataFormat();
		cellStyle0.setDataFormat(format.getFormat("hh:mm:ss"));
		
		//该行的第一个cell是序号
		Cell cell25 = row.createCell(25);
		cell25.setCellValue(str[0]);
		cell25.setCellStyle(cellStyle);
		
		Cell cell26 = row.createCell(26);
		cell26.setCellValue(str[1]);
		cell26.setCellStyle(cellStyle0);
		
		Cell cell27 = row.createCell(27);
		cell27.setCellValue(str[2]);
		cell27.setCellStyle(cellStyle0);
		
		Cell cell28 = row.createCell(28);
		cell28.setCellValue(str[3]);
		cell28.setCellStyle(cellStyle0);
		
		Cell cell29 = row.createCell(29);
		cell29.setCellValue(str[4]);
		cell29.setCellStyle(cellStyle0);
		
		Cell cell30 = row.createCell(30);
		cell30 .setCellValue(Double.parseDouble(str[5].replace("%",""))*0.01);
		cell30.setCellStyle(cellStyle);
		sheet.setForceFormulaRecalculation(true);
		//写入、关闭
		FileOutputStream stream = FileUtils.openOutputStream(file);
		workbook.write(stream);
		stream.close();

	}
	
	
	public void setData2(String[] str,int i) throws FileNotFoundException,IOException{
		//确定写入的文件地址
        File file = new File("C:/MyEclipse 2017 CI/Check/WebRoot/jiexu.xls"); 
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		HSSFSheet sheet =workbook.getSheetAt(0);
		Row row = sheet.getRow(i);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName("宋体");
        HSSFCellStyle cellStyle = workbook.createCellStyle();//创建样式
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 		
		cellStyle.setFont(font); 
		//该行的第一个cell是序号
		//row.createCell(0).setCellValue(str[0]);
		Cell cell33 = row.createCell(33);
		cell33.setCellValue(Integer.valueOf(str[1]));//写入数字
		cell33.setCellStyle(cellStyle);
		
		Cell cell34 = row.createCell(34);
		cell34.setCellValue(Integer.valueOf(str[2]));
		cell34.setCellStyle(cellStyle);
		
		Cell cell35 = row.createCell(35);
		cell35.setCellValue(Double.parseDouble(str[3].replace("%",""))*0.01);
		cell35.setCellStyle(cellStyle);
		sheet.setForceFormulaRecalculation(true);
		//写入、关闭
		FileOutputStream stream = FileUtils.openOutputStream(file);
		workbook.write(stream);
		stream.close();

	}
	
	public void setData3(String[] str,int i) throws FileNotFoundException,IOException{
		//确定写入的文件地址
        File file = new File("C:/MyEclipse 2017 CI/Check/WebRoot/jiexu.xls"); 
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		HSSFSheet sheet =workbook.getSheetAt(0);
		Row row = sheet.getRow(i);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName("宋体");
		HSSFCellStyle cellStyle = workbook.createCellStyle();//创建样式
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 		
		cellStyle.setFont(font); 
		//该行的第一个cell是序号
		//row.createCell(0).setCellValue(str[0]);
		Cell cell2 = row.createCell(2);
		cell2.setCellValue(Integer.valueOf(str[1]));//写入数字
		cell2.setCellStyle(cellStyle);
		
		Cell cell3 = row.createCell(3);
		cell3.setCellValue(Integer.valueOf(str[2]));
		cell3.setCellStyle(cellStyle);
		
		Cell cell4 = row.createCell(4);
		cell4.setCellValue(str[3]);
		cell4.setCellStyle(cellStyle);
		
		sheet.setForceFormulaRecalculation(true);
		//写入、关闭
		FileOutputStream stream = FileUtils.openOutputStream(file);
		workbook.write(stream);
		stream.close();

	}
	
	public void setData4(String[] str,int i) throws FileNotFoundException,IOException{
		//确定写入的文件地址
        File file = new File("C:/MyEclipse 2017 CI/Check/WebRoot/jiexu.xls"); 
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		HSSFSheet sheet =workbook.getSheetAt(0);
		Row row = sheet.getRow(i);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName("宋体");
		HSSFCellStyle cellStyle = workbook.createCellStyle();//创建样式
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 		
		cellStyle.setFont(font); 
		//该行的第一个cell是序号
		//row.createCell(0).setCellValue(str[0]);
		Cell cell6 = row.createCell(6);
		cell6.setCellValue(Integer.valueOf(str[1]));//写入数字
		cell6.setCellStyle(cellStyle);
		
		Cell cell7 = row.createCell(7);
		cell7.setCellValue(Integer.valueOf(str[2]));
		cell7.setCellStyle(cellStyle);
		
		Cell cell8 = row.createCell(8);
		cell8.setCellValue(str[3]);
		cell8.setCellStyle(cellStyle);
		sheet.setForceFormulaRecalculation(true);
		//写入、关闭
		FileOutputStream stream = FileUtils.openOutputStream(file);
		workbook.write(stream);
		stream.close();

	}
	public void setData5(String str) throws FileNotFoundException,IOException{
		//确定写入的文件地址
        File file = new File("C:/MyEclipse 2017 CI/Check/WebRoot/jiexu.xls"); 
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		HSSFSheet sheet =workbook.getSheetAt(0);
		Row row = sheet.getRow(1);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("宋体");
		HSSFCellStyle cellStyle = workbook.createCellStyle();//创建样式
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 		
		cellStyle.setFont(font); 
		//该行的第一个cell是序号
		//row.createCell(0).setCellValue(str[0]);
		Cell cell6 = row.createCell(1);
		cell6.setCellValue(str);
		cell6.setCellStyle(cellStyle);
	
		//写入、关闭
		FileOutputStream stream = FileUtils.openOutputStream(file);
		workbook.write(stream);
		stream.close();

	}
	
	public void setData6(String[] str,int i) throws FileNotFoundException,IOException{
		//确定写入的文件地址
        File file = new File("C:/MyEclipse 2017 CI/Check/WebRoot/jiexu.xls"); 
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		HSSFSheet sheet =workbook.getSheetAt(0);
		Row row = sheet.getRow(i);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName("宋体");
		HSSFCellStyle cellStyle = workbook.createCellStyle();//创建样式
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 		
		cellStyle.setFont(font); 
		//该行的第一个cell是序号
		//row.createCell(0).setCellValue(str[0]);
		Cell cell11 = row.createCell(11);
		cell11.setCellValue(str[1]);//写入数字
		cell11.setCellStyle(cellStyle);
		
		Cell cell14 = row.createCell(14);
		cell14.setCellValue(str[4]);
		cell14.setCellStyle(cellStyle);
		
		Cell cell15 = row.createCell(15);
		cell15.setCellValue(str[5]);
		cell15.setCellStyle(cellStyle);
		sheet.setForceFormulaRecalculation(true);
		//写入、关闭
		FileOutputStream stream = FileUtils.openOutputStream(file);
		workbook.write(stream);
		stream.close();

	}
    /*
    public static void main(String[] args) throws Exception {  
    	File file =new File ("C:/MyEclipse 2017 CI/Check/WebRoot/jiexu.xls");
    	
    	System.out.println(MakeXls.readExcelData(file, 1));  	  


    }  
    */
}