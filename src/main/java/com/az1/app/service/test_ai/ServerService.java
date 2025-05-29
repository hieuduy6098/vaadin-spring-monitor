//package vn.viettelai.service;
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.ai.tool.annotation.Tool;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import vn.viettelai.data.BaseService;
//import vn.viettelai.entity.Server;
//import vn.viettelai.model.CreateServerRequest;
//import vn.viettelai.repository.ServerRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Log4j2
//public class ServerService extends BaseService {
//
//    private final ServerRepository serverRepository;
//
//    @Autowired
//    public ServerService(ServerRepository serverRepository) {
//        this.serverRepository = serverRepository;
//    }
//
//    @Tool(description = "Create a new server and insert into the table server")
//    @Transactional
//    public Server createServer(CreateServerRequest request) {
//        log.info("Creating new server: {}", request.getServerName());
//
//        Server server = Server.builder()
//                .serverName(request.getServerName())
//                .ipAddress(request.getIpAddress())
//                .serverType(request.getServerType())
//                .cpu(request.getCpu())
//                .memory(request.getMemory())
//                .disk(request.getDisk())
//                .applicationName(request.getApplicationName())
//                .applicationPort(request.getApplicationPort())
//                .description(request.getDescription())
//                .serverStatus("UP")
//                .operatingSystem("Linux") // default value
//                .applicationStatus("STOPPED")
//                .build();
//
//        return serverRepository.save(server);
//    }
//
//    @Tool(description = "Get the data of server in the table server")
//    @Transactional(readOnly = true)
//    public Optional<Server> getServerById(String identifier) {
//        log.debug("Looking up server by identifier: {}", identifier);
//
//        Optional<Server> byName = serverRepository.findByServerName(identifier);
//        if (byName.isPresent()) {
//            return byName;
//        }
//        return serverRepository.findByIpAddress(identifier);
//    }
//
//    @Tool(description = "Delete the server in the table server")
//    @Transactional
//    public boolean deleteServer(String identifier) {
//        log.info("Deleting server by identifier: {}", identifier);
//
//        Optional<Server> serverOpt = serverRepository.findByServerName(identifier);
//
//        if (serverOpt.isEmpty()) {
//            serverOpt = serverRepository.findByIpAddress(identifier);
//        }
//
//        if (serverOpt.isPresent()) {
//            serverRepository.delete(serverOpt.get());
//            log.info("Server deleted: {}", identifier);
//            return true;
//        }
//
//        log.warn("No server found for deletion: {}", identifier);
//        return false;
//    }
//
//    @Tool(description = "Get all servers in the table server")
//    @Transactional(readOnly = true)
//    public List<Server> getAllServers() {
//        log.info("Fetching all servers");
//        return serverRepository.findAll();
//    }
//
//}
