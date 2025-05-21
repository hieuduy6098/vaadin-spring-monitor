package com.az1.app.views.chat.service;

import com.az1.app.constant.QuestionConstant;
import com.az1.app.model.CpuModel;
import com.az1.app.model.ServerModel;
import com.az1.app.service.CpuService;
import com.az1.app.service.ServerService;
import com.vaadin.flow.component.messages.MessageListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

public class BotService {
    private ServerService serverService = new ServerService();
    private CpuService cpuService = new CpuService();

    public MessageListItem getBotResponse(MessageListItem userMessage) {
        String userText = userMessage.getText().toLowerCase();
        String reply = "";

        switch (userText) {
            case QuestionConstant.q1:
                ServerModel osInfo = serverService.getOsInfo();
                reply = osInfo.toString();
                break;
            case QuestionConstant.q2:
                CpuModel cpuInfo = cpuService.getCpuInfo();
                reply = cpuInfo.toString();
                break;
            case QuestionConstant.q3:
                reply = "Không có gì đâu!";
                break;
            case QuestionConstant.q4:
                reply = "Không có gì đâu!";
                break;
            default:
                List<String> questions = QuestionConstant.getAllQuestion();
                StringBuilder stringBuild = new StringBuilder();
                stringBuild.append("I don't understand your question. Please choose from the list questions: \n");
                for(String question : questions){
                    stringBuild.append(String.format("%s%s",question,"\n"));
                }
                reply = stringBuild.toString();
                break;
        }

        return new MessageListItem(reply, Instant.now(), "Chat Bot","./icons/logo.jpg");
    }
}
