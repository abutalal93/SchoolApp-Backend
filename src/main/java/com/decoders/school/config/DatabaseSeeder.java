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
//        seedStatusTable();
        seedSchoolTable();
//        seedAnnouncementTypeTable();
//        seedAcademicYear();
//        seedClass();
//        seedSection();
//        seedStudent();
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

        School abdallahSchool = schoolRepo.findAll().get(0);

        abdallahSchool.setCode("1001");
        abdallahSchool.setChairmanMessage("من نحن\n" +
                "قيمنا \n" +
                "•\tالعلم قيمة في حد ذاته؛\n" +
                "•\tالإنسان خلق حراً وبالتالي هو مسؤول ومحاسب؛\n" +
                "•\tالالتزام بأعلى معايير مكارم الأخلاق العربية في التعامل مع أفراد المجتمع؛\n" +
                "•\tإنتاج المعرفة واستخدامها بشكل فعال؛\n" +
                "•\tقبول واحترام الاختلاف والتنوع في المجتمع؛\n" +
                "•\tصيانة حقوق الفرد والجماعة ودفعهما لتأديت ما عليهما من واجبات؛\n" +
                "•\tتعزيز مفهوم الجماعة والعمل التعاوني وروح الفريق؛\n" +
                "•\tتنمية القدرة الابتكارية للطلاب التي هي قوام العملية التربوية لأننا نعد أبناءنا لمستقبل يصعب معرفة   طبيعته واحتياجاته.\n" +
                "رؤيتنا\n" +
                "أن تقدم المدرسة أساليب ذات كفاءة وموارد متميزة في التعليم والتدريس، وأن تساعد الطلاب والمعلمين على   تحقيق أهدافهم وطموحاتهم وأن تصبح منتجاتنا التعليمية موردا موثوقا يُنتفع به في العملية التربوية.\n" +
                "رسالتنا\n" +
                "المساهمة في بناء جيل قادر على انتاج المعرفة واستخدامها بشكل فاعل يخدم الإنسانية.\n" +
                "غايتنا \n" +
                "إعادة الاعتبار إلى العقل واحترام الانسان من جديد\n" +
                "منطلقاتنا\n" +
                "تنبع منطلقات رؤيتنا مما يلي: \n" +
                "•\tأن الأساليب المتبعة في التدريس تهتم بتعزيز جانب واحد من جوانب المعرفة لدى الطالب وهي   الرياضية إلا أن تنمية جميع الجوانب المعرفية (الفنية والموسيقية والأدائية واليدوية) بالتساوي تقود إلى بناء شخصية متوازنة ومتكاملة؛\n" +
                "•\tأن تنويع أساليب التدريس والتقويم وعرض المعرفة يعظم تأثير عملية التعلم على الطالب؛\n" +
                "•\tأن الطلاب جميعهم لهم القدرة على فهم جميع المواد واتقانها والاختلاف بينهم يكمن في اهتماماتهم  وميولهم؛ \n" +
                "•\tأن الدافعية للتعلم تكمن في ماذا يريد الطالب أن يعرف وليس ماذا يريد له المعلم أن يعرف؛ \n" +
                "•\tأن المعلم ميسر للمعرفة وليس مصدرا لها؛\n" +
                "•\tأن التحفيز الداخلي أكثر تأثيرا من التحفيز الخارجي في تحسين أداء الطلاب؛\n" +
                "•\tأن الاختلاف والتنوع هو ما يثري تجربة الطالب والمعلم؛ \n" +
                "•\tأن المعلم ركيزة أساسية في عملية التعلم، وبالتالي فإن تنمية مهاراته وتعميق ثقافته ومساعدته على ذلك هو ما يرفع من شأن العلم والعملية التربوية؛\n" +
                "•\tأن المناهج العالمية المختلفة وقدرتها على تحسين المستوى الأكاديمي للطلاب لا تتلاءم وحاجات مجتمعنا ومتطلباته؛\n" +
                "•\tأن عقل الإنسان لا يهتم إلا بما هو ضمن إدراكه، فيجب فهم العلاقة بين المفاهيم والموضوعات المختلفة من عدة زوايا (توسيع مدارك الطلاب)؛\n" +
                "•\tأن تنمية القدرة على التعبير عن المشاعر والأحاسيس والميول والاتجاهات والحركات لا تقل بأي شكل من الأشكال عن المعرفة؛\n" +
                "•\tأن البحث التجريبي والبرهان العقلي هو أساس التعلم للطالب والمعلم؛\n" +
                "•\tأن لغة المجتمع هي وعاؤه الحضاري ومصدر هويته؛\n" +
                "•\tأن من حق الإنسان تحديد هويته التي تميزه عن الآخرين وتنميتها والاعتزاز بها؛\n" +
                "•\tالدين ركيزة أساسية في بناء الفرد والجماعة؛\n" +
                "•\tأن التعلم أسلوب حياة.\n" +
                "الفينيق\n" +
                "في العربية هو السيد، وأساس السيادة (أس الفينيق) هي القدرة على النهوض مجددا بعد السقوط بفضل امتلاك العقل والقوة. لذلك عبر العرب قديما عن أس الفينيق برمز مركب من رأس الانسان (العقل) وجسد الأسد أو الثور بجناحي النسر (القوة). وهو رمز عثر عليه بكثرة وبأحجام مختلفة في العالم العربي من وادي الفرات (بوابة عشتار كمثال) إلى وادي النيل (أبو الهول كمثال) مرورا ببلاد الشام والجزيرة.\n" +
                "وقد انتشرت حول أس الفينيق أساطير كثيرة ورموز متعددة تعبر عن مفهوم النهوض من جديد كما في أسطورة طائر الفينيق أو العنقاء (فينكس) الذي عندما يموت يحترق ويخرج من رماده طائر فينيق أو عنقاء جديدة.\n" +
                "وقد استعنا بمفهوم الفينيق هذا ليعبر عن ايماننا بضرورة نهوض أمتنا مجددا والعقل وإنسانية الإنسان من جديد. \n" +
                "\n" +
                "المنهاج\n" +
                "تعتمد المدرسة منهاج أنتمي يدا بيد مع منهاج وزارة التربية والتعليم الأردنية، وتعتمد منهاج لغة انجليزية READING STREET و POCKET\n" +
                "كلمة أنتمي وجذرها \"نمو\"؛ حيث يمكن تصور طبيعة هذا المنهج الخاص من خلال العلاقة بين البذرة والتربة: فكما تقوم التربة باحتضان البذرة ورعايتها وتقديم بيئة متكاملة من الغذاء والماء والهواء لها لتنمو وتكبر، وكما تقوم بالمقابل الجذور على المساعدة في تماسك التربة وتمنع انجرافها، فذلك هي العلاقة بين المدرسة والطفل فيها؛ فالطفل، (البذرة) يتم الاعتناء به داخل المدرسة من جميع النواحي، ويتم تقديم كل الدعم له ومساندته لينشأ بشكل مُعافى وصحيح، وبالمقابل فهو عليه أن يقدم لمدرسته ومجتمعه (التربة) جميع سُبل الحماية، وأن يساهم في نمو بيئته وصلابتها، وتقدمها وازدهارها.\n" +
                "من هذا المبدأ تتوالد مفاهيم وقيم مختلفة لدى الأطفال مثل التعاون، المصالح المشتركة، الحب، الصبر، تقبل الاختلاف، التنوع، الابداع، الابتكار، الحرية والالتزام المجتمعي والعلمي.\n" +
                "يقسم العام الدراسي إلى أربع فترات بأربع مواضيع عامة\n" +
                "الفترة الأولى  موضوعها الإنسان \t\t\t\tالفترة الثانية موضوعها الكائنات الحية\n" +
                "الفترة الثالثة موضوعها الكون والأرض\t\t\t\tالفترة الرابعة موضوعها \n");

        abdallahSchool.setCreateDate(Utils.getCurrentDateTimeJordan());
        abdallahSchool.setExpireDate(Utils.getCurrentDateTimeJordan().plusYears(1));
        abdallahSchool.setEmail("dabbas@hotmail.com");
        abdallahSchool.setFacebookUrl("https://www.facebook.com/Phoenic.School/");
        abdallahSchool.setImage("https://i.ibb.co/qxV5PxY/feneq-logo.png");
        abdallahSchool.setLatitude("31.983875");
        abdallahSchool.setLongitude("35.888835");
        abdallahSchool.setMobile("+962786789496");
        abdallahSchool.setName("مدرسة الفينيق - Phoenic School");
        abdallahSchool.setStatus(activeStatus);
        abdallahSchool.setUsername("abutalal.1993");
        abdallahSchool.setPassword("12345");
        abdallahSchool.setTwitterUrl("https://twitter.com/abdallahdabbas");
        abdallahSchool.setAboutImage("https://i.ibb.co/qxV5PxY/feneq-logo.png");
        abdallahSchool.setAboutText("من نحن\n" +
                "قيمنا \n" +
                "•\tالعلم قيمة في حد ذاته؛\n" +
                "•\tالإنسان خلق حراً وبالتالي هو مسؤول ومحاسب؛\n" +
                "•\tالالتزام بأعلى معايير مكارم الأخلاق العربية في التعامل مع أفراد المجتمع؛\n" +
                "•\tإنتاج المعرفة واستخدامها بشكل فعال؛\n" +
                "•\tقبول واحترام الاختلاف والتنوع في المجتمع؛\n" +
                "•\tصيانة حقوق الفرد والجماعة ودفعهما لتأديت ما عليهما من واجبات؛\n" +
                "•\tتعزيز مفهوم الجماعة والعمل التعاوني وروح الفريق؛\n" +
                "•\tتنمية القدرة الابتكارية للطلاب التي هي قوام العملية التربوية لأننا نعد أبناءنا لمستقبل يصعب معرفة   طبيعته واحتياجاته.\n" +
                "رؤيتنا\n" +
                "أن تقدم المدرسة أساليب ذات كفاءة وموارد متميزة في التعليم والتدريس، وأن تساعد الطلاب والمعلمين على   تحقيق أهدافهم وطموحاتهم وأن تصبح منتجاتنا التعليمية موردا موثوقا يُنتفع به في العملية التربوية.\n" +
                "رسالتنا\n" +
                "المساهمة في بناء جيل قادر على انتاج المعرفة واستخدامها بشكل فاعل يخدم الإنسانية.\n" +
                "غايتنا \n" +
                "إعادة الاعتبار إلى العقل واحترام الانسان من جديد\n" +
                "منطلقاتنا\n" +
                "تنبع منطلقات رؤيتنا مما يلي: \n" +
                "•\tأن الأساليب المتبعة في التدريس تهتم بتعزيز جانب واحد من جوانب المعرفة لدى الطالب وهي   الرياضية إلا أن تنمية جميع الجوانب المعرفية (الفنية والموسيقية والأدائية واليدوية) بالتساوي تقود إلى بناء شخصية متوازنة ومتكاملة؛\n" +
                "•\tأن تنويع أساليب التدريس والتقويم وعرض المعرفة يعظم تأثير عملية التعلم على الطالب؛\n" +
                "•\tأن الطلاب جميعهم لهم القدرة على فهم جميع المواد واتقانها والاختلاف بينهم يكمن في اهتماماتهم  وميولهم؛ \n" +
                "•\tأن الدافعية للتعلم تكمن في ماذا يريد الطالب أن يعرف وليس ماذا يريد له المعلم أن يعرف؛ \n" +
                "•\tأن المعلم ميسر للمعرفة وليس مصدرا لها؛\n" +
                "•\tأن التحفيز الداخلي أكثر تأثيرا من التحفيز الخارجي في تحسين أداء الطلاب؛\n" +
                "•\tأن الاختلاف والتنوع هو ما يثري تجربة الطالب والمعلم؛ \n" +
                "•\tأن المعلم ركيزة أساسية في عملية التعلم، وبالتالي فإن تنمية مهاراته وتعميق ثقافته ومساعدته على ذلك هو ما يرفع من شأن العلم والعملية التربوية؛\n" +
                "•\tأن المناهج العالمية المختلفة وقدرتها على تحسين المستوى الأكاديمي للطلاب لا تتلاءم وحاجات مجتمعنا ومتطلباته؛\n" +
                "•\tأن عقل الإنسان لا يهتم إلا بما هو ضمن إدراكه، فيجب فهم العلاقة بين المفاهيم والموضوعات المختلفة من عدة زوايا (توسيع مدارك الطلاب)؛\n" +
                "•\tأن تنمية القدرة على التعبير عن المشاعر والأحاسيس والميول والاتجاهات والحركات لا تقل بأي شكل من الأشكال عن المعرفة؛\n" +
                "•\tأن البحث التجريبي والبرهان العقلي هو أساس التعلم للطالب والمعلم؛\n" +
                "•\tأن لغة المجتمع هي وعاؤه الحضاري ومصدر هويته؛\n" +
                "•\tأن من حق الإنسان تحديد هويته التي تميزه عن الآخرين وتنميتها والاعتزاز بها؛\n" +
                "•\tالدين ركيزة أساسية في بناء الفرد والجماعة؛\n" +
                "•\tأن التعلم أسلوب حياة.\n" +
                "الفينيق\n" +
                "في العربية هو السيد، وأساس السيادة (أس الفينيق) هي القدرة على النهوض مجددا بعد السقوط بفضل امتلاك العقل والقوة. لذلك عبر العرب قديما عن أس الفينيق برمز مركب من رأس الانسان (العقل) وجسد الأسد أو الثور بجناحي النسر (القوة). وهو رمز عثر عليه بكثرة وبأحجام مختلفة في العالم العربي من وادي الفرات (بوابة عشتار كمثال) إلى وادي النيل (أبو الهول كمثال) مرورا ببلاد الشام والجزيرة.\n" +
                "وقد انتشرت حول أس الفينيق أساطير كثيرة ورموز متعددة تعبر عن مفهوم النهوض من جديد كما في أسطورة طائر الفينيق أو العنقاء (فينكس) الذي عندما يموت يحترق ويخرج من رماده طائر فينيق أو عنقاء جديدة.\n" +
                "وقد استعنا بمفهوم الفينيق هذا ليعبر عن ايماننا بضرورة نهوض أمتنا مجددا والعقل وإنسانية الإنسان من جديد. \n" +
                "\n" +
                "المنهاج\n" +
                "تعتمد المدرسة منهاج أنتمي يدا بيد مع منهاج وزارة التربية والتعليم الأردنية، وتعتمد منهاج لغة انجليزية READING STREET و POCKET\n" +
                "كلمة أنتمي وجذرها \"نمو\"؛ حيث يمكن تصور طبيعة هذا المنهج الخاص من خلال العلاقة بين البذرة والتربة: فكما تقوم التربة باحتضان البذرة ورعايتها وتقديم بيئة متكاملة من الغذاء والماء والهواء لها لتنمو وتكبر، وكما تقوم بالمقابل الجذور على المساعدة في تماسك التربة وتمنع انجرافها، فذلك هي العلاقة بين المدرسة والطفل فيها؛ فالطفل، (البذرة) يتم الاعتناء به داخل المدرسة من جميع النواحي، ويتم تقديم كل الدعم له ومساندته لينشأ بشكل مُعافى وصحيح، وبالمقابل فهو عليه أن يقدم لمدرسته ومجتمعه (التربة) جميع سُبل الحماية، وأن يساهم في نمو بيئته وصلابتها، وتقدمها وازدهارها.\n" +
                "من هذا المبدأ تتوالد مفاهيم وقيم مختلفة لدى الأطفال مثل التعاون، المصالح المشتركة، الحب، الصبر، تقبل الاختلاف، التنوع، الابداع، الابتكار، الحرية والالتزام المجتمعي والعلمي.\n" +
                "يقسم العام الدراسي إلى أربع فترات بأربع مواضيع عامة\n" +
                "الفترة الأولى  موضوعها الإنسان \t\t\t\tالفترة الثانية موضوعها الكائنات الحية\n" +
                "الفترة الثالثة موضوعها الكون والأرض\t\t\t\tالفترة الرابعة موضوعها \n");

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
        studenta.setGender("Male");
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
        studentb.setGender("Male");
        studentb.setSection(a1Section);
        studentb.setAcademicYear(academicYear);
        studentb.setNationalNumber("9941065088");
        studentb.setStatus(activeStatus);
        studentRepo.save(studentb);
    }
}
