package com.decoders.school.Utils;


public class ExcelError {

    private String row;
    private String column;
    private String sheet;
    private String descriptionAr;
    private String descriptionEn;

    public ExcelError() {
    }

    public ExcelError(String row, String column,String sheet, String descriptionEn, String descriptionAr) {
        this.row = row;
        this.column = column;
        this.sheet = sheet;
        this.descriptionAr = descriptionAr;
        this.descriptionEn = descriptionEn;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }
}
