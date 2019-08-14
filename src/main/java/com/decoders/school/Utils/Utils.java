package com.decoders.school.Utils;

import com.decoders.school.exception.ResourceException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.http.HttpStatus;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utils {

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

            String coverPhotoUrl = host + currentTimeStampCover + "." + coverExtention;//amazonS3ClientService.getFileUrl(currentTimeStampCover + "." + coverExtention);

            return coverPhotoUrl;

        } catch (Exception ex) {
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
}