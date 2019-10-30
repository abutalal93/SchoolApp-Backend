package com.decoders.school.Utils;

import com.decoders.school.entities.*;
import com.decoders.school.entities.Class;
import com.decoders.school.service.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.ApplicationContext;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExcelHandler {

    private Workbook workbook;
    private String base64File;
    private String fileName;
    private String fileType;
    private List<Student> studentList;
    private List<ExcelError> excelErrorList;
    private ApplicationContext applicationContext;

    public ExcelHandler(String base64File, String fileName, String fileType, ApplicationContext applicationContext) {
        this.base64File = base64File;
        this.fileName = fileName;
        this.fileType = fileType;
        this.studentList = new ArrayList<>();
        this.excelErrorList = new ArrayList<>();
        this.applicationContext = applicationContext;
        try {
            this.initWorkbook();
        } catch (Exception ex) {
            ex.printStackTrace();
            ExcelError excelError = new ExcelError("0", "0", "0", "Error while uploading file", "حدث خطأ أثناء رفع الملف");
            excelErrorList.add(excelError);
        }
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public String getBase64File() {
        return base64File;
    }

    public void setBase64File(String base64File) {
        this.base64File = base64File;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void initWorkbook() throws IOException {

        String tempFileName = System.currentTimeMillis() + "";

        String path = this.applicationContext.getEnvironment().getProperty("attachment_path");

        File tempFile = new File(path + tempFileName + ".xlsx");

        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decodedBytes = decoder.decodeBuffer(this.base64File);

            FileOutputStream fop = new FileOutputStream(tempFile);

            fop.write(decodedBytes);
            fop.flush();
            fop.close();

            workbook = WorkbookFactory.create(tempFile);

            workbook.close();

            this.validateExcelData();

            this.parseStudentData();

        } catch (Exception ex) {
            ex.printStackTrace();
            ExcelError excelError = new ExcelError("0", "0", "0", "Error while uploading file", "حدث خطأ أثناء رفع الملف");
            excelErrorList.add(excelError);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

    private void validateExcelData() {

        Sheet sheet = workbook.getSheetAt(0);

        if (sheet == null) {
            ExcelError excelError = new ExcelError("1", "1", sheet.getSheetName(), "Sheet not found", "لايوجد صفحات");
            excelErrorList.add(excelError);
            return;
        }

        if (sheet.getLastRowNum() <= 2) {
            ExcelError excelError = new ExcelError("2", "1", sheet.getSheetName(), "Student not found", "لايوجد طلاب");
            excelErrorList.add(excelError);
            return;
        }
    }

    private void parseStudentData() {

        Sheet sheet = workbook.getSheetAt(0);

        DataFormatter dataFormatter = new DataFormatter();

        for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {

            System.out.println("rowIndex: "+rowIndex+" sheet max row: "+sheet.getLastRowNum());

            Row studentRow = sheet.getRow(rowIndex);

            Student student = new Student();

            if (studentRow == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "1", sheet.getSheetName(), "Student row empty", "صف الطالب فارغ");
                excelErrorList.add(excelError);
                return;
            }

            //------------------------------------------------------------//
            Cell studentNameCell = studentRow.getCell(0);

            if (studentNameCell == null) {
//                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "1", sheet.getSheetName(), "Invalid student name cell", "خطأ في خلية اسم الطالب");
//                excelErrorList.add(excelError);
                return;
            }

            String studentNameValue = dataFormatter.formatCellValue(studentNameCell);

            if (studentNameValue == null || studentNameValue.isEmpty()) {
//                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "1", sheet.getSheetName(), "Invalid student name cell", "خطأ في خلية اسم الطالب");
//                excelErrorList.add(excelError);
                return;
            }

            student.setName(studentNameValue.trim());

            //------------------------------------------------------------//
            Cell nationalNumberCell = studentRow.getCell(1);

            if (nationalNumberCell == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "2", sheet.getSheetName(), "Invalid national number cell", "خطأ في خلية الرقم الوطني");
                excelErrorList.add(excelError);
                return;
            }

            String nationalNumberValue = dataFormatter.formatCellValue(nationalNumberCell);

            if (nationalNumberValue == null || nationalNumberValue.isEmpty()) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "2", sheet.getSheetName(), "Invalid national number cell", "خطأ في خلية الرقم الوطني");
                excelErrorList.add(excelError);
                return;
            }

            nationalNumberValue = nationalNumberValue.trim().replaceAll("\\s", "");

            student.setNationalNumber(nationalNumberValue);

            //------------------------------------------------------------//
            Cell sexCell = studentRow.getCell(2);

            if (sexCell == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "3", sheet.getSheetName(), "Invalid sex cell", "خطأ في خلية الجنس");
                excelErrorList.add(excelError);
                return;
            }

            String sexValue = dataFormatter.formatCellValue(sexCell).trim();

            if (sexValue == null || sexValue.isEmpty()) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "3", sheet.getSheetName(), "Invalid sex cell", "خطأ في خلية الجنس");
                excelErrorList.add(excelError);
                return;
            }

            if (!sexValue.equals("0") && !sexValue.equals("1")) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "3", sheet.getSheetName(), "Invalid sex cell", "خطأ في خلية الجنس");
                excelErrorList.add(excelError);
                return;
            }

            student.setGender((sexValue.equals("0") ? ("male") : ("female")));
            //------------------------------------------------------------//
            Cell fatherMobileCell = studentRow.getCell(3);

            if (fatherMobileCell == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "4", sheet.getSheetName(), "Invalid father mobile cell", "خطأ في خلية رقم هاتف الأب");
                excelErrorList.add(excelError);
                return;
            }

            String fatherMobileValue = dataFormatter.formatCellValue(fatherMobileCell).trim();

            if (fatherMobileValue == null || fatherMobileValue.isEmpty()) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "4", sheet.getSheetName(), "Invalid father mobile cell", "خطأ في خلية رقم هاتف الأب");
                excelErrorList.add(excelError);
                return;
            }

            fatherMobileValue = Utils.standardPhoneFormat("+962", fatherMobileValue);

            if (fatherMobileValue == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "4", sheet.getSheetName(), "Invalid father mobile cell", "خطأ في خلية رقم هاتف الأب");
                excelErrorList.add(excelError);
                return;
            }

            student.setFatherMobile(fatherMobileValue);

            //------------------------------------------------------------//
            Cell motherMobileCell = studentRow.getCell(4);

            if (motherMobileCell == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "5", sheet.getSheetName(), "Invalid mother mobile cell", "خطأ في خلية رقم هاتف الأم");
                excelErrorList.add(excelError);
                return;
            }

            String motherMobileValue = dataFormatter.formatCellValue(motherMobileCell).trim();

            if (motherMobileValue == null || motherMobileValue.isEmpty()) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "5", sheet.getSheetName(), "Invalid mother mobile cell", "خطأ في خلية رقم هاتف الأم");
                excelErrorList.add(excelError);
                return;
            }

            motherMobileValue = Utils.standardPhoneFormat("+962", motherMobileValue);

            if (motherMobileValue == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "5", sheet.getSheetName(), "Invalid mother mobile cell", "خطأ في خلية رقم هاتف الأم");
                excelErrorList.add(excelError);
                return;
            }

            student.setMotherMobile(motherMobileValue);

            //------------------------------------------------------------//
            Cell academicYearCell = studentRow.getCell(5);

            if (academicYearCell == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "6", sheet.getSheetName(), "Invalid academic year cell", "خطأ في خليةالسنة الدراسية");
                excelErrorList.add(excelError);
                return;
            }

            String academicYearValue = dataFormatter.formatCellValue(academicYearCell).trim();

            if (academicYearValue == null || academicYearValue.isEmpty()) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "6", sheet.getSheetName(), "Invalid academic year cell", "خطأ في خليةالسنة الدراسية");
                excelErrorList.add(excelError);
                return;
            }

            Long academicYearId = Utils.parseLong(academicYearValue);

            if (academicYearId == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "6", sheet.getSheetName(), "Invalid academic year cell", "خطأ في خليةالسنة الدراسية");
                excelErrorList.add(excelError);
                return;
            }

            AcademicYear academicYear = applicationContext.getBean(AcademicYearService.class).findAcademicYearById(academicYearId);

            if (academicYear == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "6", sheet.getSheetName(), "Invalid academic year cell", "خطأ في خليةالسنة الدراسية");
                excelErrorList.add(excelError);
                return;
            }

            student.setAcademicYear(academicYear);

            //------------------------------------------------------------//
            Cell classCell = studentRow.getCell(6);

            if (classCell == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "7", sheet.getSheetName(), "Invalid class cell", "خطأ في خلية الصف");
                excelErrorList.add(excelError);
                return;
            }

            String classCellValue = dataFormatter.formatCellValue(classCell).trim();

            if (classCellValue == null || classCellValue.isEmpty()) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "7", sheet.getSheetName(), "Invalid class cell", "خطأ في خلية الصف");
                excelErrorList.add(excelError);
                return;
            }

            Long classId = Utils.parseLong(classCellValue);

            if (classId == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "7", sheet.getSheetName(), "Invalid class cell", "خطأ في خلية الصف");
                excelErrorList.add(excelError);
                return;
            }

            Class clasS = applicationContext.getBean(ClassService.class).findClassById(classId);

            if (clasS == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "7", sheet.getSheetName(), "Invalid class cell", "خطأ في خلية الصف");
                excelErrorList.add(excelError);
                return;
            }

            if (!clasS.getAcademicYear().getId().equals(academicYearId)) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "7", sheet.getSheetName(), "Invalid class cell", "خطأ في خلية الصف");
                excelErrorList.add(excelError);
                return;
            }

            //------------------------------------------------------------//
            Cell sectionCell = studentRow.getCell(7);

            if (sectionCell == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "8", sheet.getSheetName(), "Invalid section cell", "خطأ في خلية الشعبة");
                excelErrorList.add(excelError);
                return;
            }

            String sectionValue = dataFormatter.formatCellValue(sectionCell).trim();

            if (sectionValue == null || sectionValue.isEmpty()) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "8", sheet.getSheetName(), "Invalid section cell", "خطأ في خلية الشعبة");
                excelErrorList.add(excelError);
                return;
            }

            Long sectionId = Utils.parseLong(sectionValue);

            if (sectionId == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "8", sheet.getSheetName(), "Invalid section cell", "خطأ في خلية الشعبة");
                excelErrorList.add(excelError);
                return;
            }

            Section section = applicationContext.getBean(SectionService.class).findSectionById(sectionId);

            if (section == null) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "8", sheet.getSheetName(), "Invalid section cell", "خطأ في خلية الشعبة");
                excelErrorList.add(excelError);
                return;
            }

            if (!section.getClasS().getId().equals(classId)) {
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "8", sheet.getSheetName(), "Invalid section cell", "خطأ في خلية الشعبة");
                excelErrorList.add(excelError);
                return;
            }

            //-------------------------------------------------------------//

            Status deleteStatus = applicationContext.getBean(StatusService.class).findStatusByCode("DELETED");

            Student currentStudent = applicationContext.getBean(StudentService.class).findStudentByNationalNumberAndAcademicYearAndStatusNot(nationalNumberValue,academicYear,deleteStatus);

            if(currentStudent != null){
                ExcelError excelError = new ExcelError(Integer.toString(rowIndex + 1), "1", sheet.getSheetName(), "Student exist", "الطالب موجود");
                excelErrorList.add(excelError);
                return;
            }

            student.setSection(section);

            student.setCreateDate(LocalDateTime.now());

            Status activeStatus = applicationContext.getBean(StatusService.class).findStatusByCode("ACTIVE");

            student.setStatus(activeStatus);
            //------------------------------------------------------------//
            this.studentList.add(student);
        }
    }

    public List<ExcelError> getExcelErrorList() {
        return excelErrorList;
    }

    public void setExcelErrorList(List<ExcelError> excelErrorList) {
        this.excelErrorList = excelErrorList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
