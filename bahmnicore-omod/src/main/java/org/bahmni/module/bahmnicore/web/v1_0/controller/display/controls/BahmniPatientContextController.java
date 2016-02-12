package org.bahmni.module.bahmnicore.web.v1_0.controller.display.controls;

import org.bahmni.module.bahmnicore.model.bahmniPatientProgram.BahmniPatientProgram;
import org.bahmni.module.bahmnicore.service.BahmniProgramWorkflowService;
import org.bahmni.module.bahmnicore.web.v1_0.mapper.BahmniPatientContextMapper;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.bahmniemrapi.patient.PatientContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/bahmnicore/patientcontext")
public class BahmniPatientContextController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private BahmniPatientContextMapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PatientContext getPatientContext(@RequestParam(value = "patientUuid", required = true)String patientUuid,
                                            @RequestParam(value = "programUuid", required = false)String programUuid,
                                            @RequestParam(value = "personAttributes", required = false)List<String> configuredPersonAttributes,
                                            @RequestParam(value = "programAttributes", required = false)List<String> configuredProgramAttributes) {
        Patient patient = patientService.getPatientByUuid(patientUuid);
        BahmniPatientProgram bahmniPatientProgram = (BahmniPatientProgram) Context.getService(BahmniProgramWorkflowService.class).getPatientProgramByUuid(programUuid);
        return mapper.map(patient, bahmniPatientProgram, configuredPersonAttributes, configuredProgramAttributes);
    }
}