package org.example.service;

import org.example.domain.Nota;
import org.example.domain.Pair;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.example.validation.NotaValidator;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddEntityIT {
    private Service service;
    @Mock
    private StudentXMLRepository studentRepo;
    @Mock
    private TemaXMLRepository temaRepo;
    @Mock
    private NotaXMLRepository notaRepo;
    @Before
    public void initializeData() {
        studentRepo = mock(StudentXMLRepository.class);
        temaRepo = mock(TemaXMLRepository.class);
        notaRepo = mock(NotaXMLRepository.class);
        service = new Service(studentRepo, temaRepo, notaRepo);
    }

    @Test
    public void saveStudentValid() {
        when(studentRepo.save(any())).thenReturn(new Student("1", "Ion", 932));
        String validId = "1";
        String validName = "Ion";
        int validGroup = 932;
        assert(service.saveStudent(validId, validName, validGroup) == 1);
    }

    @Test
    public void saveAssignmentValid() {
        when(temaRepo.save(any())).thenReturn(new Tema("0", "Lab1", 6, 4));
        String validId = "0";
        String validDescription = "Lab1";
        int validDeadline = 6;
        int validStartWeek = 4;
        assert(service.saveTema(validId, validDescription, validDeadline, validStartWeek) == 1);
    }

    @Test
    public void saveGradeValid() {
        when(notaRepo.save(any())).thenReturn(new Nota(new Pair<String, String>("1", "1"), 10, 8, "bravo"));
        when(studentRepo.findOne(any())).thenReturn(new Student("1", "Ana",932));
        when(temaRepo.findOne(any())).thenReturn(new Tema("1", "Lab1", 6, 4));
        Pair<String, String> validId = new Pair<String, String>("1", "1");
        Double validGrade = 10.00;
        int validSaptamanaPredare = 8;
        String validFeedback = "bravo";
        assert(service.saveNota(validId.getObject1(), validId.getObject2() ,validGrade, validSaptamanaPredare, validFeedback) == 1);
    }

    @Test
    public void addEntitiesIT() throws IOException {
        StudentXMLRepository actualStudentRepo = new StudentXMLRepository(new StudentValidator(), "student_test.xml");
        TemaXMLRepository actualTemaRepo = new TemaXMLRepository(new TemaValidator(), "tema_test.xml");
        NotaXMLRepository actualNotaRepo = new NotaXMLRepository(new NotaValidator(), "nota_test.xml");
        if (actualStudentRepo.findOne("1") != null) {
            actualStudentRepo.delete("1");
        }
        if (actualTemaRepo.findOne("1") != null) {
            actualTemaRepo.delete("1");
        }
        if (actualNotaRepo.findOne(new Pair<String, String>("1", "1")) != null) {
            actualNotaRepo.delete(new Pair<String, String>("1", "1"));
        }
        Service actualService = new Service(actualStudentRepo, actualTemaRepo, actualNotaRepo);
        String validId = "1";
        String validName = "Ion";
        int validGroup = 932;
        assert (actualService.saveStudent(validId, validName, validGroup) == 1);
        String validIdTema = "1";
        String validDescription = "Lab1";
        int validDeadline = 6;
        int validStartWeek = 4;
        assert (actualService.saveTema(validId, validDescription, validDeadline, validStartWeek) == 1);
        Pair<String, String> validIdNota = new Pair<String, String>("1", "1");
        Double validGrade = 10.00;
        int validSaptamanaPredare = 8;
        String validFeedback = "bravo";
        assert (actualService.saveNota(validIdNota.getObject1(), validIdNota.getObject2() ,validGrade, validSaptamanaPredare, validFeedback) == 1);

    }
}
