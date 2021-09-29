package com.xttdr;

import com.xttdr.service.ExamServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XttdrApplicationTests {

    @Test
    void getAnswer() {
        ExamServiceImpl examService = new ExamServiceImpl();
        examService.getScore("f3a6928418334bef8457adbcb0180b67","XUESHENG");
    }

}
