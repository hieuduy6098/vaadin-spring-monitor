package com.az1.app.views.chat.service;

import com.az1.app.constant.QuestionConstant;
import com.az1.app.model.*;
import com.az1.app.service.*;
import com.vaadin.flow.component.messages.MessageListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class BotService {
    private final ServerService serverService = new ServerService();
    private final CpuService cpuService = new CpuService();
    private final MemService memService = new MemService();
    private final DiskService diskService = new DiskService();
    private final NetworkService networkService = new NetworkService();
    private final ApiHandleService apiHandleService = new ApiHandleService();

    public MessageListItem getBotResponse(MessageListItem userMessage) {
        String userText = userMessage.getText().toLowerCase();
        String reply = "";

        switch (userText) {
            case QuestionConstant.q1:
                reply = serverService.getOsInfo().toString();
                break;
            case QuestionConstant.q2:
                reply = cpuService.getCpuInfo().toString();
                break;
            case QuestionConstant.q3:
                reply = memService.getMemoryInfo().toString();
                break;
            case QuestionConstant.q4:
                reply = diskService.getDiskInfo().stream()
                        .map(DiskModel::toString)
                        .collect(Collectors.joining("\n"));
                break;
            case QuestionConstant.q5:
                reply = networkService.getNetworkInfo().stream()
                        .map(NetworkModel::toString)
                        .collect(Collectors.joining("\n"));
                break;
            case QuestionConstant.q6:
                reply = apiHandleService.getStations().stream()
                        .map(StationModel::toString)
                        .collect(Collectors.joining("\n"));
                break;
            case QuestionConstant.q7:
                reply = apiHandleService.getStations().stream()
                        .map(StationModel::getName)
                        .collect(Collectors.joining("\n"));
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


    // func call api get response from api ai
    public MessageListItem getAiResponse(MessageListItem userMessage){
        String userText = userMessage.getText().toLowerCase();
        String reply = apiHandleService.getResponseAi(userText);
        return new MessageListItem(reply, Instant.now(), "Chat Bot","./icons/logo.jpg");
    }
}
