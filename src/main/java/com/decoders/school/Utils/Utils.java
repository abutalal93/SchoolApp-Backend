package com.decoders.school.Utils;

import com.decoders.school.exception.ResourceException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.TimeZone;

public class Utils {

    private static ApplicationContext applicationContext;

    public static LocalDateTime parseLocalDateTime(String date, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            return dateTime;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String extractExtention(String encoded) {

        try {
            int extentionStartIndex = encoded.indexOf('/');
            int extensionEndIndex = encoded.indexOf(';');
            int filetypeStartIndex = encoded.indexOf(':');

            String fileType = encoded.substring(filetypeStartIndex + 1, extentionStartIndex);
            String fileExtension = encoded.substring(extentionStartIndex + 1, extensionEndIndex);

            System.out.println("fileType : " + fileType);
            System.out.println("file Extension :" + fileExtension);

            return fileExtension;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static BufferedImage decodeToImage(String imageString) {

        String[] imageData = imageString.split(",");

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageData[1]);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }


    public static String archiveFile(String host, String path, String data) {

        try {
            String coverExtention = extractExtention(data);

            if (coverExtention == null) {
                throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_image_extention");
            }

            BufferedImage bufferedImageCover = Utils.decodeToImage(data);

            if (bufferedImageCover == null) {
                throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_image_data");
            }

            long currentTimeStampCover = System.currentTimeMillis();

            File outputfile = new File(path + currentTimeStampCover + "." + coverExtention);

            ImageIO.write(bufferedImageCover, coverExtention, outputfile);

            String coverPhotoUrl = host + currentTimeStampCover + "." + coverExtention;

            return coverPhotoUrl;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String randomNumber(int length) {

        char[] characterSet = "0123456789".toCharArray();

        Random random = new SecureRandom();

        char[] result = new char[length];

        for (int i = 0; i < result.length; i++) {
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }

    public static String standardPhoneFormat(String countryCode, String number) {
        try {
            PhoneNumberUtil util = PhoneNumberUtil.getInstance();
            int parsedCountryCode = Integer.parseInt(countryCode);
            Phonenumber.PhoneNumber parsedNumber = util.parse(number,
                    util.getRegionCodeForCountryCode(parsedCountryCode));

            boolean validationFlag = PhoneNumberUtil.getInstance().isValidNumber(parsedNumber);

            if (!validationFlag) {
                throw new Exception();
            }
            return util.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static LocalDateTime getCurrentDateTimeJordan() {
        LocalDateTime localDateTime = LocalDateTime.now(TimeZone.getTimeZone("Asia/Amman").toZoneId());
        return localDateTime;
    }

    public static LocalDate getCurrentDateJordan() {
        LocalDate localDate = LocalDate.now(TimeZone.getTimeZone("Asia/Amman").toZoneId());
        return localDate;
    }

    public static File createExcelFile() {

        File file = new File(applicationContext.getEnvironment().getProperty("attachment_path") + "student_template.xlsx");

//        Workbook workbook = WorkbookFactory.create(file);
//
//        Sheet sheet = workbook.getSheetAt(0);
//
//        DataFormat fmt = workbook.createDataFormat();
//        CellStyle textStyle = workbook.createCellStyle();
//        textStyle.setDataFormat(fmt.getFormat("@"));
//
//        sheet.setDefaultColumnStyle(0, textStyle);
//        sheet.setDefaultColumnStyle(1, textStyle);
//        sheet.setDefaultColumnStyle(2, textStyle);
//        sheet.setDefaultColumnStyle(3, textStyle);
//        sheet.setDefaultColumnStyle(4, textStyle);
//        sheet.setDefaultColumnStyle(5, textStyle);
//        sheet.setDefaultColumnStyle(6, textStyle);
//        sheet.setDefaultColumnStyle(7, textStyle);
//        sheet.setDefaultColumnStyle(8, textStyle);
//        sheet.setDefaultColumnStyle(9, textStyle);
//        sheet.setDefaultColumnStyle(10, textStyle);
//        sheet.setDefaultColumnStyle(11, textStyle);
//
//        // Write the output to a file
//        FileOutputStream fileOut = new FileOutputStream(new File(applicationContext.getEnvironment().getProperty("attachment_path") + "student_template.xlsx"));
//
//        workbook.write(fileOut);
//        fileOut.close();
//        // Closing the workbook
//        workbook.close();
//
//        File generatedFile = new File(applicationContext.getEnvironment().getProperty("attachment_path") + "student_template.xlsx");

        return file;
    }

    public static String encodeFileToBase64Binary(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] bytes = loadFile(file);
        byte[] encoded = org.apache.commons.codec.binary.Base64.encodeBase64(bytes);
        String encodedString = new String(encoded);

        return encodedString;
    }

    public static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        Utils.applicationContext = applicationContext;
    }

    public static Long parseLong(String longStr){
        try {
            return Long.parseLong(longStr);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}