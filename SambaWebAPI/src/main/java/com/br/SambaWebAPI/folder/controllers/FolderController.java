//package com.br.SambaWebAPI.folder.controllers;
//
//import com.br.SambaWebAPI.config.Global;
//import com.br.SambaWebAPI.folder.models.Folder;
//import com.br.SambaWebAPI.folder.services.FolderService;
//import com.br.SambaWebAPI.group.services.GroupService;
//import com.br.SambaWebAPI.password.models.SudoAuthentication;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping(Global.API_URL_SAMBA + "/folder-config")
//public class FolderController {
//
//    final private ObjectMapper objectMapper;
//    final private FolderService folderService;
//
//    @Autowired
//    public FolderController(ObjectMapper objectMapper, FolderService folderService) {
//        this.objectMapper = objectMapper;
//        this.folderService = folderService;
//    }
//
//    @PostMapping(path = "/createFolder")
//    public ResponseEntity<?> folderCreation(@RequestBody Map<String,Object> json){
//        Folder folder = objectMapper.convertValue(json.get("folder"),Folder.class);
//        SudoAuthentication sudoAuthentication = objectMapper.convertValue(json.get("sudoAuthentication"), SudoAuthentication.class);
//
//        try{
//            folderService.createFolder(folder,sudoAuthentication);
//        }
//    }
//}
