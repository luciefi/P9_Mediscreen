package com.mediscreen.riskservice.service;

import com.mediscreen.riskservice.client.NoteClient;
import com.mediscreen.riskservice.client.PatientClient;
import com.mediscreen.riskservice.model.NoteRead;
import com.mediscreen.riskservice.model.Patient;
import com.mediscreen.riskservice.model.RiskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class RiskServiceTest {

    @Mock
    private PatientClient client;

    @Mock
    private NoteClient noteClient;

    @InjectMocks
    private RiskService service;

    List<String> keywords = Arrays.asList(
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fumeur",
            "Anormal",
            "Cholestérol",
            "Vertige",
            "Rechute",
            "Réaction",
            "Anticorps");

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(service, "keywords", keywords);
    }

    // Under 30 - Male
    private static Stream<Arguments> sourceMaleUnder30() {
        return Stream.of(
                Arguments.of(0, RiskLevel.NONE),
                Arguments.of(1, RiskLevel.NONE),
                Arguments.of(2, RiskLevel.NONE),
                Arguments.of(3, RiskLevel.IN_DANGER),
                Arguments.of(4, RiskLevel.IN_DANGER),
                Arguments.of(5, RiskLevel.EARLY_ONSET)
        );
    }
    @ParameterizedTest
    @MethodSource("sourceMaleUnder30")
    void getRisk_Male_Under30(int numberOfTerms, RiskLevel expectedRiskLevel) {
        // Arrange
        Patient patient = getMalePatientUnder30();
        patient.setId(1l);
        when(client.findById(anyLong())).thenReturn(patient);
        List<NoteRead> notes = getSingletonNoteList(numberOfTerms);
        when(noteClient.findAll(anyLong())).thenReturn(notes);

        // Act
        RiskLevel riskLevel = service.getPatientRisk(1l);

        // Assert
        assertEquals(expectedRiskLevel, riskLevel);
        verify(client, times(1)).findById(1l);
        verify(noteClient, times(1)).findAll(1l);
    }

    // Under 30 - Female
    private static Stream<Arguments> sourceFemaleUnder30() {
        return Stream.of(
                Arguments.of(0, RiskLevel.NONE),
                Arguments.of(1, RiskLevel.NONE),
                Arguments.of(2, RiskLevel.NONE),
                Arguments.of(3, RiskLevel.NONE),
                Arguments.of(4, RiskLevel.IN_DANGER),
                Arguments.of(5, RiskLevel.IN_DANGER),
                Arguments.of(6, RiskLevel.IN_DANGER),
                Arguments.of(7, RiskLevel.EARLY_ONSET)
        );
    }
    @ParameterizedTest
    @MethodSource("sourceFemaleUnder30")
    void getRisk_Female_Under30(int numberOfTerms, RiskLevel expectedRiskLevel) {
        // Arrange
        Patient patient = getFemalePatientUnder30();
        patient.setId(1l);
        when(client.findById(anyLong())).thenReturn(patient);
        List<NoteRead> notes = getSingletonNoteList(numberOfTerms);
        when(noteClient.findAll(anyLong())).thenReturn(notes);

        // Act
        RiskLevel riskLevel = service.getPatientRisk(1l);

        // Assert
        assertEquals(expectedRiskLevel, riskLevel);
        verify(client, times(1)).findById(1l);
        verify(noteClient, times(1)).findAll(1l);
    }

    // Over 30
    private static Stream<Arguments> sourceMaleOver30() {
        return Stream.of(
                Arguments.of(0, RiskLevel.NONE),
                Arguments.of(1, RiskLevel.NONE),
                Arguments.of(2, RiskLevel.BORDERLINE),
                Arguments.of(3, RiskLevel.BORDERLINE),
                Arguments.of(4, RiskLevel.BORDERLINE),
                Arguments.of(5, RiskLevel.BORDERLINE),
                Arguments.of(6, RiskLevel.IN_DANGER),
                Arguments.of(7, RiskLevel.IN_DANGER),
                Arguments.of(8, RiskLevel.EARLY_ONSET)
        );
    }
    @ParameterizedTest
    @MethodSource("sourceMaleOver30")
    void getRisk_Male_Over30(int numberOfTerms, RiskLevel expectedRiskLevel) {
        // Arrange
        Patient patient = getPatientOver30();
        patient.setId(1l);
        when(client.findById(anyLong())).thenReturn(patient);
        List<NoteRead> notes = getSingletonNoteList(numberOfTerms);
        when(noteClient.findAll(anyLong())).thenReturn(notes);

        // Act
        RiskLevel riskLevel = service.getPatientRisk(1l);

        // Assert
        assertEquals(expectedRiskLevel, riskLevel);
        verify(client, times(1)).findById(1l);
        verify(noteClient, times(1)).findAll(1l);
    }


    // Private
    private Patient getMalePatientUnder30() {
        Patient patient = new Patient();
        patient.setDateOfBirth(LocalDate.now());
        patient.setSex("M");
        return patient;
    }

    private Patient getFemalePatientUnder30() {
        Patient patient = new Patient();
        patient.setDateOfBirth(LocalDate.now());
        patient.setSex("F");
        return patient;
    }

    private Patient getPatientOver30() {
        Patient patient = new Patient();
        patient.setDateOfBirth(LocalDate.now().minusYears(40));
        patient.setSex("M");
        return patient;
    }

    private List<NoteRead> getSingletonNoteList(int numberOfTerms) {
        NoteRead noteRead = new NoteRead();
        String content = this.keywords.stream().limit(numberOfTerms).collect(Collectors.joining(" ,"));
        noteRead.setContent(content);
        return Collections.singletonList(noteRead);
    }
}