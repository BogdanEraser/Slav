package main.java.slav.util;


import javafx.scene.control.Alert;
import main.java.slav.model.Tovar;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Eraser on 04.02.2016.
 */
public class ExcelUtils {
    private static HSSFSheet Excel2000WSheet;
    private static HSSFWorkbook Excel2000WBook;
    private static HSSFCell Cell2000;

    private static XSSFSheet Excel2010WSheet;
    private static XSSFWorkbook Excel2010WBook;
    private static XSSFCell Cell2010;

    public static boolean setExcel2000File(String Path, String SheetName) {
        try {
            FileInputStream ExcelFile = new FileInputStream(Path);
            Excel2000WBook = new HSSFWorkbook(ExcelFile);
            Excel2000WSheet = Excel2000WBook.getSheet(SheetName);
            return true;
        } catch (IOException e) {
            System.out.println("File open error. ������ �������� ����� " + Path);
            return false;
        }
    }

    public static HSSFCell getCell2000Data(int RowNum, int ColNum) throws Exception {
        try {
            Cell2000 = Excel2000WSheet.getRow(RowNum + 1).getCell(ColNum + 1);  //������ ��� ��� �������, � �� � 0:0
            //String CellData = Cell.getStringCellValue();
            return Cell2000;
        } catch (Exception e) {
            System.out.println("Get cell data error. ������ ������ ������: " + RowNum + "_" + ColNum);
            return null;
        }
    }

    public static void setCell2000Data(HSSFCell CellData, int RowNum, int ColNum, File Path) throws Exception {
        try {
            HSSFRow row2000 = Excel2000WSheet.createRow(RowNum - 1);
            Cell2000 = row2000.createCell(ColNum - 1);
            Cell2000.setCellValue(String.valueOf(CellData));
            FileOutputStream fileOut = new FileOutputStream(Path);
            Excel2000WBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            throw (e);
        }
    }

    //now with 2010 office XLSX
    public static boolean setExcel2010File(File Path, String SheetName) throws IOException {
        try {
            FileInputStream ExcelFile = new FileInputStream(Path);
            Excel2010WBook = new XSSFWorkbook(ExcelFile);
            Excel2010WSheet = Excel2010WBook.getSheet(SheetName);
            return true;
        } catch (IOException e) {
            System.out.println("File open error. ������ �������� ����� " + Path);
            return false;
        }
    }

    public static XSSFCell getCell2010Data(int RowNum, int ColNum) throws Exception {
        try {
            Cell2010 = Excel2010WSheet.getRow(RowNum + 1).getCell(ColNum + 1);  //������ ��� ��� �������, � �� � 0:0
            //String CellData = Cell.getStringCellValue();
            return Cell2010;
        } catch (Exception e) {
            System.out.println("Get cell data error. ������ ������ ������: " + RowNum + "_" + ColNum);
            return null;
        }
    }

    public static void setCell2010Data(XSSFCell CellData, int RowNum, int ColNum, File Path) throws Exception {
        try {
            XSSFRow row2010 = Excel2010WSheet.createRow(RowNum - 1);
            Cell2010 = row2010.createCell(ColNum - 1);
            Cell2010.setCellValue(String.valueOf(CellData));
            FileOutputStream fileOut = new FileOutputStream(Path);
            Excel2010WBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            throw (e);
        }
    }

    /**
     * ������ ������ �� ����� Excel
     *
     * @param excelFilePath
     * @param sheetName
     * @return
     */
    public static ArrayList<Tovar> getTovarFromExcel(String excelFilePath, String sheetName) {
        ArrayList<Tovar> tovarList = new ArrayList<>();
        int tIdx = 0;
        File file = new File(excelFilePath);

        if (file.exists() && !file.isDirectory()) {

            FileInputStream fileInputStream;
            Workbook workbook = null;
            try {
                fileInputStream = new FileInputStream(file);
                String fileExtension = excelFilePath.substring(excelFilePath.indexOf("."));

                if (fileExtension.equals(".xls")) {
                    workbook = new HSSFWorkbook(new POIFSFileSystem(fileInputStream));
                } else if (fileExtension.equals(".xlsx")) {
                    workbook = new XSSFWorkbook(fileInputStream);
                } else {
                    System.out.println("Wrong File Type");
                    return tovarList;
                }
                Sheet sheet = workbook.getSheet(sheetName);
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();


                // Decide which rows to process (� 3-�� �� 32000)
                //int rowStart = Math.min(3, sheet.getFirstRowNum());
                int rowStart = 3;
                int rowEnd = Math.max(32000, sheet.getLastRowNum());

                for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                    Row r = sheet.getRow(rowNum);
                    if (r == null) {
                        // This whole row is empty
                        //continue;
                        break; //��� ��������� ������ ������� �� �������
                    }
                    tovarList.add(new Tovar()); //�� ������ ������ - ��������� ����� ����� � ������ ��� �������
                    //�� 15 ��������
                    for (int cn = 0; cn < 15; cn++) {
                        Cell cell = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
                        if (cell != null) {
                            switch (cn) {
                                case 0: //tovarID
                                    switch (evaluator.evaluateInCell(cell).getCellType()) {
                                        case Cell.CELL_TYPE_NUMERIC:
                                            tovarList.get(tIdx).setID((long) cell.getNumericCellValue());
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            tovarList.get(tIdx).setID(Long.parseLong(cell.getStringCellValue()));
                                            break;
                                        case Cell.CELL_TYPE_BLANK:
                                            tovarList.get(tIdx).setID(0);
                                            break;
                                    }
                                    break;
                                case 1: //tovarName
                                    switch (evaluator.evaluateInCell(cell).getCellType()) {
                                        case Cell.CELL_TYPE_NUMERIC:
                                            tovarList.get(tIdx).setName(String.valueOf(cell.getNumericCellValue()));
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            tovarList.get(tIdx).setName(cell.getStringCellValue());
                                            break;
                                        case Cell.CELL_TYPE_BLANK:
                                            tovarList.get(tIdx).setName("");
                                            break;
                                    }
                                    break;
                                case 2: //tovarWeight
                                    switch (evaluator.evaluateInCell(cell).getCellType()) {
                                        case Cell.CELL_TYPE_NUMERIC:
                                            tovarList.get(tIdx).setWeight(BigDecimal.valueOf(cell.getNumericCellValue()));
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            tovarList.get(tIdx).setWeight(BigDecimal.valueOf(Double.parseDouble(cell.getStringCellValue())));
                                            break;
                                        case Cell.CELL_TYPE_BLANK:
                                            tovarList.get(tIdx).setWeight(BigDecimal.ZERO);
                                            break;
                                    }
                                    break;
                                case 3: //tovarUnits
                                    switch (evaluator.evaluateInCell(cell).getCellType()) {
                                        case Cell.CELL_TYPE_NUMERIC:
                                            tovarList.get(tIdx).setUnits(String.valueOf(cell.getNumericCellValue()));
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            tovarList.get(tIdx).setUnits(cell.getStringCellValue());
                                            break;
                                        case Cell.CELL_TYPE_BLANK:
                                            tovarList.get(tIdx).setUnits("");
                                            break;
                                    }
                                    break;
                                case 4: //tovarGost
                                    switch (evaluator.evaluateInCell(cell).getCellType()) {
                                        case Cell.CELL_TYPE_NUMERIC:
                                            tovarList.get(tIdx).setGost(String.valueOf(cell.getNumericCellValue()));
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            tovarList.get(tIdx).setGost(cell.getStringCellValue());
                                            break;
                                        case Cell.CELL_TYPE_BLANK:
                                            tovarList.get(tIdx).setGost("");
                                            break;
                                    }
                                    break;
                                case 7: //tovarPrice
                                    switch (evaluator.evaluateInCell(cell).getCellType()) {
                                        case Cell.CELL_TYPE_NUMERIC:
                                            tovarList.get(tIdx).setPrice(BigDecimal.valueOf(cell.getNumericCellValue()));
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            tovarList.get(tIdx).setPrice(BigDecimal.valueOf(Double.parseDouble(cell.getStringCellValue())));
                                            break;
                                        case Cell.CELL_TYPE_BLANK:
                                            tovarList.get(tIdx).setPrice(BigDecimal.ZERO);
                                            break;
                                    }
                                    break;
                                case 13: //tovarCategory
                                    switch (evaluator.evaluateInCell(cell).getCellType()) {
                                        case Cell.CELL_TYPE_NUMERIC:
                                            tovarList.get(tIdx).setCategory((int) cell.getNumericCellValue());
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            tovarList.get(tIdx).setCategory(Integer.parseInt(cell.getStringCellValue()));
                                            break;
                                        case Cell.CELL_TYPE_BLANK:
                                            tovarList.get(tIdx).setCategory(0);
                                            break;
                                    }
                                    break;
                            }
                        }
                    }
                    tIdx++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("������");
            alert.setHeaderText("���� '" + excelFilePath + "' �� ������ ��� �� ����� ���� ������");
            alert.showAndWait();
        }
        return tovarList;
    }

    /**
     * ������� ������ � ���� Excel
     *
     * @param excelFilePath
     * @param sheetName
     * @return
     */
    public static boolean updateDataInExcel(String excelFilePath, String sheetName, ArrayList<Tovar> tovarList) {
        //�������� ������������� �����
        File file = new File(excelFilePath);
        if (file.exists() && !file.isDirectory()) {
            //���� ����������, ����� ���������� ���������� � ��� ������
            FileInputStream fileInputStream;
            Workbook workbook = null;
            try {
                fileInputStream = new FileInputStream(file);
                String fileExtension = excelFilePath.substring(excelFilePath.indexOf("."));
                if (fileExtension.equals(".xls")) {
                    workbook = new HSSFWorkbook(new POIFSFileSystem(fileInputStream));

                } else if (fileExtension.equals(".xlsx")) {
                    workbook = new XSSFWorkbook(fileInputStream);

                } else {
                    System.out.println("Wrong File Type");
                    return false;
                }
                Sheet sheet = workbook.getSheet(sheetName);
                if (sheet == null) {
                    sheet = workbook.createSheet(sheetName); //���� ���� �� ���������� - ������� ���
                }
                ListIterator<Tovar> tovarListIterator = tovarList.listIterator();
                int rowIndex = 3;   //������ ������ � 4-�!!! ������
                //int colIndex = 0;   //� 1-��!!! �������
                Row row;
                while (tovarListIterator.hasNext()) {
                    row = sheet.createRow(rowIndex);    //������� ������ � ����������� � ��� ����� � �������
                    row.createCell(0, Cell.CELL_TYPE_NUMERIC).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getID());
                    row.createCell(1, Cell.CELL_TYPE_STRING).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getName());
                    row.createCell(2, Cell.CELL_TYPE_NUMERIC).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getWeight().doubleValue());
                    row.createCell(3, Cell.CELL_TYPE_STRING).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getUnits());
                    row.createCell(4, Cell.CELL_TYPE_STRING).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getGost());
                    row.createCell(5, Cell.CELL_TYPE_NUMERIC).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getPrice().doubleValue());
                    row.createCell(6, Cell.CELL_TYPE_NUMERIC).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getCategory());
                    tovarListIterator.next();
                    rowIndex++;
                }
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(4);

                fileInputStream.close();
                FileOutputStream outFile = new FileOutputStream(new File(excelFilePath));
                workbook.write(outFile);
                outFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("������");
            alert.setHeaderText("���� '" + excelFilePath + "' �� ������ ��� �� ����� ���� ������");
            alert.showAndWait();
            return false;
        }
    }


    /**
     * ������� ������ � ���� Excel
     *
     * @param excelFilePath
     * @param sheetName
     * @return
     */
    public static boolean exportDataToExcel(String excelFilePath, String sheetName, ArrayList<Tovar> tovarList) {
        //�������� �����
        File file = new File(excelFilePath);
        Workbook workbook = null;
        try {
            String fileExtension = excelFilePath.substring(excelFilePath.indexOf("."));
            if (fileExtension.equals(".xls")) {
                workbook = new HSSFWorkbook();

            } else if (fileExtension.equals(".xlsx")) {
                workbook = new XSSFWorkbook();

            } else {
                System.out.println("Wrong File Type");
                return false;
            }
            Sheet sheet = workbook.createSheet(sheetName); //���� ���� �� ���������� - ������� ���
            ListIterator<Tovar> tovarListIterator = tovarList.listIterator();
            int rowIndex = 3;   //������ ������ � 4-�!!! ������
            //int colIndex = 0;   //� 1-��!!! �������
            Row row;
            while (tovarListIterator.hasNext()) {
                row = sheet.createRow(rowIndex);    //������� ������ � ����������� � ��� ����� � �������
                row.createCell(0, Cell.CELL_TYPE_NUMERIC).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getID());
                row.createCell(1, Cell.CELL_TYPE_STRING).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getName());
                row.createCell(2, Cell.CELL_TYPE_NUMERIC).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getWeight().doubleValue());
                row.createCell(3, Cell.CELL_TYPE_STRING).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getUnits());
                row.createCell(4, Cell.CELL_TYPE_STRING).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getGost());
                row.createCell(5, Cell.CELL_TYPE_NUMERIC).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getPrice().doubleValue());
                row.createCell(6, Cell.CELL_TYPE_NUMERIC).setCellValue(tovarList.get(tovarListIterator.nextIndex()).getCategory());
                tovarListIterator.next();
                rowIndex++;
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(4);

            FileOutputStream outFile = new FileOutputStream(new File(excelFilePath));
            workbook.write(outFile);
            outFile.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("������");
            alert.setHeaderText("���� '" + excelFilePath + "' �� ����� ���� ������");
            alert.showAndWait();
            return false;
        }
    }
}