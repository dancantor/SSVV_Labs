package org.example.service;

import junit.framework.TestCase;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceTest {
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
        when(studentRepo.save(any())).thenReturn(null);
        String validId = "1";
        String validName = "Ion";
        int validGroup = 932;
        assert(service.saveStudent(validId, validName, validGroup) == 1);
    }

    @Test
    public void saveExistingStudent() {
        when(studentRepo.save(any())).thenReturn(new Student("1", "Ana",932));
        String validId = "1";
        String validName = "Ion";
        int validGroup = 932;
        assert(service.saveStudent(validId, validName, validGroup) == 0);
    }

    @Test
    public void saveStudentIdNull() {
        when(studentRepo.save(any())).thenReturn(new Student("1", "Ana",932));
        String validId = null;
        String validName = "Ion";
        int validGroup = 932;
        assert(service.saveStudent(validId, validName, validGroup) == 0);
    }

    @Test
    public void saveStudentGroupLessThen110() {
        when(studentRepo.save(any())).thenReturn(new Student("1", "Ana",932));
        String validId = "0";
        String validName = "Ion";
        int validGroup = 109;
        assert(service.saveStudent(validId, validName, validGroup) == 0);
    }

    @Test
    public void saveStudentGroupGreaterThen939() {
        when(studentRepo.save(any())).thenReturn(new Student("1", "Ana",932));
        String validId = "0";
        String validName = "Ion";
        int validGroup = 939;
        assert(service.saveStudent(validId, validName, validGroup) == 0);
    }

    @Test
    public void saveAssignmentRepoReturnsNullServiceReturns1() {
        when(temaRepo.save(any())).thenReturn(new Tema("0", "Lab1", 6, 4));
        String validId = "0";
        String validDescription = "Lab1";
        int validDeadline = 6;
        int validStartWeek = 4;
        assert(service.saveTema(validId, validDescription, validDeadline, validStartWeek) == 1);
    }

    @Test
    public void saveAssignmentRepoReturnsTemaServiceReturns0() {
        when(temaRepo.save(any())).thenReturn(null);
        String validId = "0";
        String validDescription = "Lab1";
        int validDeadline = 6;
        int validStartWeek = 4;
        assert(service.saveTema(validId, validDescription, validDeadline, validStartWeek) == 0);
    }

    @Test
    public void saveAssignmentRepoReturnsNullServiceReturns1IdInvalid() {
        when(temaRepo.save(any())).thenReturn(null);
        String invalidId = "";
        String validDescription = "Lab1";
        int validDeadline = 6;
        int validStartWeek = 4;
        assert(service.saveTema(invalidId, validDescription, validDeadline, validStartWeek) == 0);
    }

    @Test
    public void saveAssignmentRepoReturnsNullServiceReturns1DescriptionInvalid() {
        when(temaRepo.save(any())).thenReturn(null);
        String validId = "1";
        String invalidDescription = null;
        int validDeadline = 6;
        int validStartWeek = 4;
        assert(service.saveTema(validId, invalidDescription, validDeadline, validStartWeek) == 0);
    }

    @Test
    public void saveAssignmentRepoReturnsNullServiceReturns1DeadlineInvalid() {
        when(temaRepo.save(any())).thenReturn(null);
        String validId = "1";
        String validDescription = "Lab1";
        int invalidDeadline = 15;
        int validStartWeek = 4;
        assert(service.saveTema(validId, validDescription, invalidDeadline, validStartWeek) == 0);
    }

    @Test
    public void saveAssignmentRepoReturnsNullServiceReturns1StartDateInvalid() {
        when(temaRepo.save(any())).thenReturn(null);
        String validId = "1";
        String validDescription = "Lab1";
        int validDeadline = 4;
        int invalidStartWeek = 8;
        assert(service.saveTema(validId, validDescription, validDeadline, invalidStartWeek) == 0);
    }
}