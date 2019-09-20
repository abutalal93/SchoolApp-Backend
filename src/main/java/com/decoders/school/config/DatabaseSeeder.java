package com.decoders.school.config;

import com.decoders.school.Utils.Utils;
import com.decoders.school.entities.*;
import com.decoders.school.entities.Class;
import com.decoders.school.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;

@Component
public class DatabaseSeeder {

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private SchoolRepo schoolRepo;

    @Autowired
    private AnnouncementTypeRepo announcementTypeRepo;

    @Autowired
    private AcademicYearRepo academicYearRepo;

    @Autowired
    private ClassRepo classRepo;

    @Autowired
    private SectionRepo sectionRepo;

    @Autowired
    private StudentRepo studentRepo;

    public void seed() {
        seedStatusTable();
        seedSchoolTable();
        seedAnnouncementTypeTable();
        seedAcademicYear();
        seedClass();
        seedSection();
        seedStudent();
    }

    private void seedStatusTable() {

        Status inactiveStatus = new Status();

        inactiveStatus.setCode("INACTIVE");
        inactiveStatus.setDescription("record is inactive");

        statusRepo.save(inactiveStatus);

        Status activeStatus = new Status();

        activeStatus.setCode("ACTIVE");
        activeStatus.setDescription("record is active");

        statusRepo.save(activeStatus);

        Status deletedStatus = new Status();

        deletedStatus.setCode("DELETED");
        deletedStatus.setDescription("record is deleted");

        statusRepo.save(deletedStatus);
    }

    private void seedSchoolTable() {

        Status activeStatus = statusRepo.findStatusByCode("ACTIVE");

        School abdallahSchool = new School();

        abdallahSchool.setCode("1001");
        abdallahSchool.setChairmanMessage("قد أخذت المدارس العمرية على عاتقها مسؤولية المساهمة في حمل رسالة التربية والتعليم وتوعية المجتمع من حولها في هذا الوطن المعطاء وسعت منذ تأسيسها إلى تبني سياسات تربوية وتعليمية مميزة وفق خطط تنفيذية تمت صياغتها بخبرة تربوية وتعليمية , ورؤى طموحة وعناصر متكاملة لتقديم خدمة تربوية وتعليمية لجميع الطلاب والطالبات في المدرسة في جميع المراحل العمرية . وتسعى المدارس العمرية إلى غرس الجوانب التربوية في نفوس أبنائها الطلاب والطالبات, بتوجيههم إلى الآداب الإسلامية والأخلاق الرفيعة بما تفتحه لهم من مجالات متنوعة من الأنشطة التربوية. إذ تقيم برامج تربوية وأنشطة مدرسية يقدم فيها للطلاب دورات علمية وبرامج ثقافية،وأنشطة رياضية،ويفتح المجال للطالب ليختار منها ما يناسبه بناء على رغباته وهواياته.\n" +
                " \n" +
                "ولذا فإنّ تكامل البرامج التعليمية مع الرعاية التربوية للطالب تسهم في تطوير قدراته الفكرية والجسمية، وتهذيب وجدانه، وتوجيه سلوكياته ليكون لبنة في بناء الأمة، وركناً في نهضتها، وأساساً في انطلاقتها، ومن هنا تنطلق المدارس العمرية في رعاية أبنائها الطلاب، وبهذا تنطق رسالتها، فكان اقتناع هيئة المدارس العمرية التعليمية والإدارية بأن أوجه التعليم كافة لابد وأن تتعانق مع الرعاية التربوية ليكون الطالب نموذجاً صالحاً في مجتمعه" +
                "\n" +
                "الدكتور عبدالله حسين الدباس");

        abdallahSchool.setCreateDate(Utils.getCurrentDateTimeJordan());
        abdallahSchool.setExpireDate(Utils.getCurrentDateTimeJordan().plusYears(1));
        abdallahSchool.setEmail("dabbas@hotmail.com");
        abdallahSchool.setFacebookUrl("https://www.facebook.com/abdallah.dabbas.1");
        abdallahSchool.setImage("http://www.omareyah.com/sites/all/themes/omaryah01/images/logonew.png");
        abdallahSchool.setLatitude("31.983875");
        abdallahSchool.setLongitude("35.888835");
        abdallahSchool.setMobile("+962786789496");
        abdallahSchool.setName("مدارس الأرقم");
        abdallahSchool.setStatus(activeStatus);
        abdallahSchool.setUsername("abutalal.1993");
        abdallahSchool.setPassword("12345");
        abdallahSchool.setTwitterUrl("https://twitter.com/abdallahdabbas");
        abdallahSchool.setAboutImage("http://www.omareyah.com/sites/all/themes/omaryah01/images/logonew.png");
        abdallahSchool.setAboutText("قد أخذت المدارس العمرية على عاتقها مسؤولية المساهمة في حمل رسالة التربية والتعليم وتوعية المجتمع من حولها في هذا الوطن المعطاء وسعت منذ تأسيسها إلى تبني سياسات تربوية وتعليمية مميزة وفق خطط تنفيذية تمت صياغتها بخبرة تربوية وتعليمية , ورؤى طموحة وعناصر متكاملة لتقديم خدمة تربوية وتعليمية لجميع الطلاب والطالبات في المدرسة في جميع المراحل العمرية . وتسعى المدارس العمرية إلى غرس الجوانب التربوية في نفوس أبنائها الطلاب والطالبات, بتوجيههم إلى الآداب الإسلامية والأخلاق الرفيعة بما تفتحه لهم من مجالات متنوعة من الأنشطة التربوية. إذ تقيم برامج تربوية وأنشطة مدرسية يقدم فيها للطلاب دورات علمية وبرامج ثقافية،وأنشطة رياضية،ويفتح المجال للطالب ليختار منها ما يناسبه بناء على رغباته وهواياته.\n" +
                " \n" +
                "ولذا فإنّ تكامل البرامج التعليمية مع الرعاية التربوية للطالب تسهم في تطوير قدراته الفكرية والجسمية، وتهذيب وجدانه، وتوجيه سلوكياته ليكون لبنة في بناء الأمة، وركناً في نهضتها، وأساساً في انطلاقتها، ومن هنا تنطلق المدارس العمرية في رعاية أبنائها الطلاب، وبهذا تنطق رسالتها، فكان اقتناع هيئة المدارس العمرية التعليمية والإدارية بأن أوجه التعليم كافة لابد وأن تتعانق مع الرعاية التربوية ليكون الطالب نموذجاً صالحاً في مجتمعه" +
                "\n" +
                "الدكتور عبدالله حسين الدباس");

        schoolRepo.save(abdallahSchool);
    }

    private void seedAnnouncementTypeTable() {

        Status activeStatus = statusRepo.findStatusByCode("ACTIVE");

        AnnouncementType publicAnnouncementType = new AnnouncementType();

        publicAnnouncementType.setStatus(activeStatus);
        publicAnnouncementType.setCode("PUBLIC");
        publicAnnouncementType.setCaptionAr("أخبار عامة");
        publicAnnouncementType.setCaptionEn("Public Announcement");

        announcementTypeRepo.save(publicAnnouncementType);


        AnnouncementType privateAnnouncementType = new AnnouncementType();

        privateAnnouncementType.setStatus(activeStatus);
        privateAnnouncementType.setCode("PRIVATE");
        privateAnnouncementType.setCaptionAr("أخبار خاصة");
        privateAnnouncementType.setCaptionEn("Private Announcement");

        announcementTypeRepo.save(privateAnnouncementType);

        AnnouncementType homeworkAnnouncementType = new AnnouncementType();

        homeworkAnnouncementType.setStatus(activeStatus);
        homeworkAnnouncementType.setCode("HOMEWORK");
        homeworkAnnouncementType.setCaptionAr("واجب بيتي");
        homeworkAnnouncementType.setCaptionEn("Homework");

        announcementTypeRepo.save(homeworkAnnouncementType);
    }

    private void seedAcademicYear() {
        Status activeStatus = statusRepo.findStatusByCode("ACTIVE");

        AcademicYear academicYear = new AcademicYear();
        academicYear.setStatus(activeStatus);
        academicYear.setStartDate(Utils.getCurrentDateTimeJordan());
        academicYear.setEndDate(Utils.getCurrentDateTimeJordan().plusYears(1));
        academicYear.setAlias(academicYear.getStartDate().getYear() + "-" + academicYear.getEndDate().getYear());

        academicYearRepo.save(academicYear);
    }

    private void seedClass() {
        Status activeStatus = statusRepo.findStatusByCode("ACTIVE");

        AcademicYear academicYear = academicYearRepo.findAcademicYear(Utils.getCurrentDateTimeJordan());

        com.decoders.school.entities.Class firstClass = new com.decoders.school.entities.Class();

        firstClass.setAcademicYear(academicYear);
        firstClass.setName("الصف الأول");
        firstClass.setStatus(activeStatus);

        firstClass = classRepo.save(firstClass);

        com.decoders.school.entities.Class secondClass = new com.decoders.school.entities.Class();

        secondClass.setAcademicYear(academicYear);
        secondClass.setName("الصف الثاني");
        secondClass.setStatus(activeStatus);

        secondClass = classRepo.save(secondClass);
    }

    private void seedSection() {
        Status activeStatus = statusRepo.findStatusByCode("ACTIVE");

        AcademicYear academicYear = academicYearRepo.findAcademicYear(Utils.getCurrentDateTimeJordan());

        Class clasS1 = classRepo.findClassByNameAndAcademicYearAndStatus("الصف الأول", academicYear, activeStatus);

        Section a1Section = new Section();
        a1Section.setAcademicYear(academicYear);
        a1Section.setClasS(clasS1);
        a1Section.setStatus(activeStatus);
        a1Section.setName("أ");

        sectionRepo.save(a1Section);

        Section b1Section = new Section();
        b1Section.setAcademicYear(academicYear);
        b1Section.setClasS(clasS1);
        b1Section.setStatus(activeStatus);
        b1Section.setName("ب");

        sectionRepo.save(b1Section);

        Class clasS2 = classRepo.findClassByNameAndAcademicYearAndStatus("الصف الثاني", academicYear, activeStatus);

        Section a2Section = new Section();
        a2Section.setAcademicYear(academicYear);
        a2Section.setClasS(clasS2);
        a2Section.setStatus(activeStatus);
        a2Section.setName("أ");

        sectionRepo.save(a2Section);

        Section b2Section = new Section();
        b2Section.setAcademicYear(academicYear);
        b2Section.setClasS(clasS2);
        b2Section.setStatus(activeStatus);
        b2Section.setName("ب");

        sectionRepo.save(b2Section);
    }

    private void seedStudent() {
        Status activeStatus = statusRepo.findStatusByCode("ACTIVE");

        AcademicYear academicYear = academicYearRepo.findAcademicYear(Utils.getCurrentDateTimeJordan());

        Class clasS1 = classRepo.findClassByNameAndAcademicYearAndStatus("الصف الأول", academicYear, activeStatus);

        Section a1Section = sectionRepo.findSectionByNameAndClasSAndAcademicYearAndStatus("أ", clasS1, academicYear, activeStatus);

        Student studenta = new Student();
        studenta.setName("Abdallah Dabbas");
        studenta.setCreateDate(Utils.getCurrentDateTimeJordan());
        studenta.setFatherMobile("+962786789496");
        studenta.setMotherMobile("+962786789496");
        studenta.setGender("M");
        studenta.setSection(a1Section);
        studenta.setAcademicYear(academicYear);
        studenta.setNationalNumber("9931065088");
        studenta.setStatus(activeStatus);
        studentRepo.save(studenta);

        Student studentb = new Student();
        studentb.setName("Wessam Rimawi");
        studentb.setCreateDate(Utils.getCurrentDateTimeJordan());
        studentb.setFatherMobile("+962796983230");
        studentb.setMotherMobile("+962796983230");
        studentb.setGender("M");
        studentb.setSection(a1Section);
        studentb.setAcademicYear(academicYear);
        studentb.setNationalNumber("9941065088");
        studentb.setStatus(activeStatus);
        studentRepo.save(studentb);
    }
}
