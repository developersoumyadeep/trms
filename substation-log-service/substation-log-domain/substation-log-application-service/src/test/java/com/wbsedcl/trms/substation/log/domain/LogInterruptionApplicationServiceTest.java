package com.wbsedcl.trms.substation.log.domain;


import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogInterruptionResponse;
import com.wbsedcl.trms.substation.log.domain.entity.*;
import com.wbsedcl.trms.substation.log.domain.exception.InterruptionValidationException;
import com.wbsedcl.trms.substation.log.domain.mapper.InterruptionDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.input.service.SubstationLogApplicationService;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.OfficeRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = SubstationLogTestConfiguration.class)
public class LogInterruptionApplicationServiceTest {

    @Autowired
    private SubstationLogApplicationService substationLogApplicationService;

    @Autowired
    private InterruptionDataMapper interruptionDataMapper;

    @Autowired
    private SubstationLogRepository substationLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeederRepository feederRepository;

    @Autowired
    private OfficeRepository officeRepository;

    private LogInterruptionCommand logInterruptionCommand;
    private LogInterruptionCommand logInterruptionCommandWrongHoursStartTimeAfterEndTime;
    private LogInterruptionCommand logInterruptionCommandWrongHoursRestoredWithoutEndTime;
    private LogInterruptionCommand logInterruptionCommandWrongHoursNotRestoredWithEndTime;
    private LogInterruptionCommand logInterruptionCommandWrongHoursStartTimeInFuture;


    private LogInterruptionCommand logInterruptionCommandWrongStatusTransientTrippingNotRestored;
    private LogInterruptionCommand logInterruptionCommandWrongStatusSourceChangeOverNotRestored;
    private LogInterruptionCommand logInterruptionCommandWrongStatusSustainedFaultRestored;

    private final String FEEDER_ID="3842";
    private final String SUBSTATION_OFFICE_ID="3412500";
    private final String CREATED_USER_ID="90014842";
    private final String RESTORED_USER_ID="90014842";
    private final UUID INTERRUPTION_UUID = UUID.fromString("fbaf9490-a112-4b9c-90c6-39141c4abfa3");
    private final InterruptionType INTERRUPTION_TYPE_TT = InterruptionType.TRANSIENT_TRIPPING;
    private final InterruptionType INTERRUPTION_TYPE_PS = InterruptionType.PLANNED_SHUTDOWN;
    private final FaultNature FAULT_NATURE_EF = FaultNature.EF;
    private final FaultNature FAULT_NATURE_EF_OC = FaultNature.EF_OC;
    private final InterruptionStatus INTERRUPTION_RESTORED = InterruptionStatus.RESTORED;
    private final InterruptionStatus INTERRUPTION_NOT_RESTORED = InterruptionStatus.NOT_RESTORED;
    private final LocalDate START_DATE = LocalDate.of(2023, 1,6);
    private final LocalTime START_TIME = LocalTime.of(8,53,52);
    private final LocalDate END_DATE = LocalDate.of(2023, 1,6);
    private final LocalTime END_TIME = LocalTime.of(9,15,23);
    private final String CAUSE = "";




    @BeforeAll
    public void init() {
        logInterruptionCommand = LogInterruptionCommand.builder()
                .faultyFeederId(FEEDER_ID)
                .substationOfficeId(SUBSTATION_OFFICE_ID)
                .interruptionType(INTERRUPTION_TYPE_TT)
                .faultNature(FAULT_NATURE_EF)
                .interruptionStatus(INTERRUPTION_RESTORED)
                .createdByUserId(CREATED_USER_ID)
                .restoredByUserId(RESTORED_USER_ID)
                .startDate(START_DATE)
                .startTime(START_TIME)
                .endDate(END_DATE)
                .endTime(END_TIME)
                .cause(CAUSE)
                .build();

        logInterruptionCommandWrongHoursStartTimeAfterEndTime = LogInterruptionCommand.builder()
                .faultyFeederId(FEEDER_ID)
                .substationOfficeId(SUBSTATION_OFFICE_ID)
                .interruptionType(INTERRUPTION_TYPE_TT)
                .faultNature(FAULT_NATURE_EF)
                .interruptionStatus(INTERRUPTION_RESTORED)
                .createdByUserId(CREATED_USER_ID)
                .restoredByUserId(RESTORED_USER_ID)
                .startDate(START_DATE)
                .startTime(START_TIME)
                .endDate(LocalDate.of(2023, 1,5))
                .endTime(END_TIME)
                .cause(CAUSE)
                .build();

        logInterruptionCommandWrongHoursRestoredWithoutEndTime=LogInterruptionCommand.builder()
                .faultyFeederId(FEEDER_ID)
                .substationOfficeId(SUBSTATION_OFFICE_ID)
                .interruptionType(INTERRUPTION_TYPE_TT)
                .faultNature(FAULT_NATURE_EF)
                .interruptionStatus(INTERRUPTION_RESTORED)
                .createdByUserId(CREATED_USER_ID)
                .restoredByUserId(RESTORED_USER_ID)
                .startDate(START_DATE)
                .startTime(START_TIME)
                .endDate(null)
                .endTime(null)
                .cause(CAUSE)
                .build();

        logInterruptionCommandWrongHoursNotRestoredWithEndTime = LogInterruptionCommand.builder()
                .faultyFeederId(FEEDER_ID)
                .substationOfficeId(SUBSTATION_OFFICE_ID)
                .interruptionType(InterruptionType.EMERGENCY_SHUTDOWN)
                .faultNature(FAULT_NATURE_EF)
                .interruptionStatus(INTERRUPTION_NOT_RESTORED)
                .createdByUserId(CREATED_USER_ID)
//                .restoredByUserId(RESTORED_USER_ID)
                .startDate(START_DATE)
                .startTime(START_TIME)
                .endDate(END_DATE)
                .endTime(END_TIME)
                .cause(CAUSE)
                .build();


        logInterruptionCommandWrongHoursStartTimeInFuture = LogInterruptionCommand.builder()
                .faultyFeederId(FEEDER_ID)
                .substationOfficeId(SUBSTATION_OFFICE_ID)
                .interruptionType(InterruptionType.EMERGENCY_SHUTDOWN)
                .faultNature(FAULT_NATURE_EF)
                .interruptionStatus(INTERRUPTION_NOT_RESTORED)
                .createdByUserId(CREATED_USER_ID)
//                .restoredByUserId(RESTORED_USER_ID)
                .startDate(LocalDate.of(2023,12,1))
                .startTime(START_TIME)
//                .endDate(END_DATE)
//                .endTime(END_TIME)
                .cause(CAUSE)
                .build();

        logInterruptionCommandWrongStatusTransientTrippingNotRestored = LogInterruptionCommand.builder()
                .faultyFeederId(FEEDER_ID)
                .substationOfficeId(SUBSTATION_OFFICE_ID)
                .interruptionType(INTERRUPTION_TYPE_TT)
                .faultNature(FAULT_NATURE_EF)
                .interruptionStatus(INTERRUPTION_NOT_RESTORED)
                .createdByUserId(CREATED_USER_ID)
//                .restoredByUserId(RESTORED_USER_ID)
                .startDate(START_DATE)
                .startTime(START_TIME)
                .endDate(END_DATE)
                .endTime(END_TIME)
                .cause(CAUSE)
                .build();


        logInterruptionCommandWrongStatusSourceChangeOverNotRestored = LogInterruptionCommand.builder()
                .faultyFeederId(FEEDER_ID)
                .substationOfficeId(SUBSTATION_OFFICE_ID)
                .interruptionType(InterruptionType.SOURCE_CHANGEOVER)
                .faultNature(FAULT_NATURE_EF)
                .interruptionStatus(INTERRUPTION_NOT_RESTORED)
                .createdByUserId(CREATED_USER_ID)
//                .restoredByUserId(RESTORED_USER_ID)
                .startDate(START_DATE)
                .startTime(START_TIME)
                .endDate(END_DATE)
                .endTime(END_TIME)
                .cause(CAUSE)
                .build();

        logInterruptionCommandWrongStatusSustainedFaultRestored = LogInterruptionCommand.builder()
                .faultyFeederId(FEEDER_ID)
                .substationOfficeId(SUBSTATION_OFFICE_ID)
                .interruptionType(InterruptionType.SUSTAINED_FAULT)
                .faultNature(FAULT_NATURE_EF)
                .interruptionStatus(INTERRUPTION_RESTORED)
                .createdByUserId(CREATED_USER_ID)
                .restoredByUserId(RESTORED_USER_ID)
                .startDate(START_DATE)
                .startTime(START_TIME)
                .endDate(END_DATE)
                .endTime(END_TIME)
                .cause(CAUSE)
                .build();



        User createdByUserMock = User.newBuilder().userId(new UserId(CREATED_USER_ID)).build();
        User restoredByUserMock = User.newBuilder().userId(new UserId(RESTORED_USER_ID)).build();
        Office substationOfficeMock = new Office(new OfficeId(SUBSTATION_OFFICE_ID));
        Feeder faultyFeederMock = Feeder.newBuilder().feederId(new FeederId(FEEDER_ID)).build();
        Interruption interruption = interruptionDataMapper.logInterruptionCommandToInterruption(logInterruptionCommand);

        //Mocking the application service dependencies i.e. repositories
        when(userRepository.findUser(CREATED_USER_ID)).thenReturn(Optional.of(createdByUserMock));
        when(userRepository.findUser(RESTORED_USER_ID)).thenReturn(Optional.of(restoredByUserMock));
        when(officeRepository.findOffice(SUBSTATION_OFFICE_ID)).thenReturn(Optional.of(substationOfficeMock));
        when(feederRepository.findFeeder(FEEDER_ID)).thenReturn(Optional.of(faultyFeederMock));
//        doAnswer(i->{
//            Interruption savedInterruption = (Interruption)i.getArgument(0);
//            savedInterruption.setInterruptionRefId(INTERRUPTION_REF_ID);
//            return null;
//        }).when(substationLogRepository).save(any(Interruption.class));
    }

    @Test
    void testLogInterruption() {
        LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logInterruption(logInterruptionCommand);
        assertEquals(INTERRUPTION_RESTORED, logInterruptionResponse.getInterruptionStatus());
        assertNotNull(logInterruptionResponse.getInterruptionId());
    }

    @Test
    void testLogInterruptionWithWrongHoursStartTimeAfterEndTime() {
        InterruptionValidationException exception = assertThrows(InterruptionValidationException.class, ()->{
            LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logInterruption(logInterruptionCommandWrongHoursStartTimeAfterEndTime);
        });

        assertEquals("The 'Restored' interruption end date & time should be greater than the end & time", exception.getMessage());

    }

    @Test
    void testLogInterruptionWithWrongHoursRestoredWithoutEndTime() {
        InterruptionValidationException exception = assertThrows(InterruptionValidationException.class, ()->{
            LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logInterruption(logInterruptionCommandWrongHoursRestoredWithoutEndTime);
        });

        assertEquals("Interruption in 'Restored' status must include both start date & time and end date & time", exception.getMessage());

    }

    @Test
    void testLogInterruptionWithWrongHoursNotRestoredWithEndTime() {
        InterruptionValidationException exception = assertThrows(InterruptionValidationException.class, ()->{
            LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logInterruption(logInterruptionCommandWrongHoursNotRestoredWithEndTime);
        });

        assertEquals("Interruption in 'Not Restored' status must include both start date & time only", exception.getMessage());

    }

    @Test
    void testLogInterruptionWithWrongHoursStartTimeInFuture() {
        InterruptionValidationException exception = assertThrows(InterruptionValidationException.class, ()->{
            LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logInterruption(logInterruptionCommandWrongHoursStartTimeInFuture);
        });

        assertEquals("The start date & time can not be in future", exception.getMessage());

    }


    @Test
    void testLogInterruptionWithWrongStatusTransientTrippingNotRestored() {
        InterruptionValidationException exception = assertThrows(InterruptionValidationException.class, ()->{
            LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logInterruption(logInterruptionCommandWrongStatusTransientTrippingNotRestored);

        });

        assertEquals("Interruption of type 'Transient Tripping' or 'Source Changeover' must be in 'Restored' status", exception.getMessage());
    }

    @Test
    void testLogInterruptionWithWrongStatusSourceChangeOverNotRestored() {
        InterruptionValidationException exception = assertThrows(InterruptionValidationException.class, ()->{
            LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logInterruption(logInterruptionCommandWrongStatusSourceChangeOverNotRestored);

        });

        assertEquals("Interruption of type 'Transient Tripping' or 'Source Changeover' must be in 'Restored' status", exception.getMessage());
    }

    @Test
    void testLogInterruptionWithWrongStatusSustainedFaultRestored() {
        InterruptionValidationException exception = assertThrows(InterruptionValidationException.class, ()->{
            LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logInterruption(logInterruptionCommandWrongStatusSustainedFaultRestored);

        });

        assertEquals("Interruption of type other than 'Transient Tripping' and 'Source Changeover' must be in 'Not Restored' status", exception.getMessage());
    }
}
