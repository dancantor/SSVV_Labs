package org.example.service;

import org.example.domain.Student;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServiceTest {
    private Service service;
    @Mock
    private StudentXMLRepository studentRepo;
    @Mock
    private TemaXMLRepository temaRepo;
    @Mock
    private NotaXMLRepository notaRepo;
    @BeforeEach
    void initializeData() {
        studentRepo = mock(StudentXMLRepository.class);
        temaRepo = mock(TemaXMLRepository.class);
        notaRepo = mock(NotaXMLRepository.class);
        service = new Service(studentRepo, temaRepo, notaRepo);
    }

    @Test
    void saveStudentValid() {
        when(studentRepo.save(any())).thenReturn(null);
        String validId = "1";
        String validName = "Ion";
        int validGroup = 932;
        assert(service.saveStudent(validId, validName, validGroup) == 1);
    }

    @Test
    void saveExistingStudent() {
        when(studentRepo.save(any())).thenReturn(new Student("1", "Ana",932));
        String validId = "1";
        String validName = "Ion";
        int validGroup = 932;
        assert(service.saveStudent(validId, validName, validGroup) == 0);
    }
}