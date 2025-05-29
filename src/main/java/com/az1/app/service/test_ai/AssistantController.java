//package vn.viettelai.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import vn.viettelai.data.ResponseData;
//import vn.viettelai.data.ResponseUtils;
//import vn.viettelai.exception.BaseException;
//import vn.viettelai.model.CreateAssistantRequest;
//import vn.viettelai.model.ai.ChatResponse;
//import vn.viettelai.service.AssistantService;
//
//import javax.validation.Valid;
//
//@RestController
//@Log4j2
//@RequestMapping("/assistant")
//@Api(tags = "AI Assistant", description = "Assistant for Env System")
//public class AssistantController {
//
//    private final AssistantService assistantService;
//
//    @Autowired
//    public AssistantController(AssistantService assistantService) {
//        this.assistantService = assistantService;
//    }
//
//    @ApiOperation("Get System Health Status")
//    @GetMapping("/system/health")
//    public ResponseEntity<ResponseData<String>> getSystemHealth() throws BaseException {
//        return ResponseUtils.success(this.assistantService.getSystemHealth());
//    }
//
//    @ApiOperation("Retrieve System Configuration")
//    @GetMapping("/system/config")
//    public ResponseEntity<ResponseData<String>> getSystemConfig() throws BaseException {
//        return ResponseUtils.success(this.assistantService.getSystemConfig());
//    }
//
//    @ApiOperation("Receive alert from monitoring system (e.g., Zabbix)")
//    @PostMapping("/system/alert")
//    public ResponseEntity<ResponseData<String>> processAlert(@RequestBody @Valid CreateAssistantRequest request) throws BaseException {
//        return ResponseUtils.success(this.assistantService.processAlert(request));
//    }
//
//    @ApiOperation("Chat with System Operator Assistant")
//    @PostMapping("/system/chat")
//    public ResponseEntity<ResponseData<String>> processSystemQuery(@RequestBody @Valid CreateAssistantRequest request) throws BaseException {
//        return ResponseUtils.success(this.assistantService.processSystemQuery(request));
//    }
//
//}
