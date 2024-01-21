package com.pinting.business.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 支持2003版和2007版及以上
 * @Project: business
 * @Title: ExcelUtil.java
 * @author dingpf
 * @date 2015-3-19 上午11:14:30
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ExcelUtil {
	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
	/**
	 * 
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * @param file 读取数据的源Excel
	 * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 */
	public static String[][] getData(File file, int ignoreRows){
		BufferedInputStream in = null;
		String[][] returnArray = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			Workbook wb = WorkbookFactory.create(in);
			returnArray = readBook(wb, ignoreRows);
		}catch(Exception e) {
			log.error("读取Excel失败", e);
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return returnArray;
	}

	private static String[][] readBook(Workbook wb, int ignoreRows) {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		Cell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			Sheet st = wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				Row row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}

				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					// 给单元格赋值
					cell = row.getCell(columnIndex);
					// 从单元格中获得数据
					if (cell != null) {
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd").format(date);
								} else {
									value = "";
								}
							} else {
								value = new DecimalFormat("0.00").format(cell.getNumericCellValue());
							}
							break;
						case Cell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							if (!cell.getStringCellValue().equals("")) {
								value = cell.getStringCellValue();

							} else {
								value = cell.getNumericCellValue() + "";
							}
							break;
						case Cell.CELL_TYPE_BLANK:
							break;
						case Cell.CELL_TYPE_ERROR:
							value = "";
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y" : "N");
							break;
						default:
							value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals("")) {
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}
				if (hasValue) {
					result.add(values);
				}
			}
		}
		// 将单元格中获得的数据放到returnArray中
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}

	/**
	 * 
	 * 去掉字符串右边的空格
	 * @param str 要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}
	
	
	public static void main(String[] args) throws Exception {
		// 获得xls文件路径
//		String path = URLDecoder.decode(
//				ExcelUtil.class.getResource("20150318.xls").getPath(), "UTF-8");
		File file = new File("c:/20150325.xls");
		// 从xls文件读取数据 存放在数组中
		String[][] result = getData(file, 1);

		int rowLength = result.length;
		// 打印xls文件获得的数据
		for (int i = 0; i < rowLength; i++) {
			for (int j = 0; j < result[i].length; j++) {
				System.out.print(result[i][j] + "\t\t");
			}
			System.out.println();
		}
	}
}
