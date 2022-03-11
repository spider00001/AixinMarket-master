package com.bluemsun.web.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@CrossOrigin

public class UploadController {
    @RequestMapping("/upload")
    @ResponseBody
    public Map fileUpload(HttpServletRequest request, MultipartFile imgFile){
        Map map = new HashMap();

        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if (admin==null||!admin.equals("admin2020")){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }

        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        String projectServerPath = request.getScheme() + "://"+request.getServerName()+":" +
                request.getServerPort() + request.getContextPath() + "/upload/";
        //map.put("projectServerPath",projectServerPath);
        //map.put("realPath",realPath);
        String filename = imgFile.getOriginalFilename();
        String extendFilename = filename.substring(filename.lastIndexOf('.'));
        String randomFilename = UUID.randomUUID().toString();
        filename = randomFilename+extendFilename;
        //创建dir
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        try {
            imgFile.transferTo(new File(file,filename));
        } catch (IOException e) {
            e.printStackTrace();
            map.put("code",2004);
            map.put("msg",e.getMessage());
            return map;
        }
        map.put("code",0);
        Map data = new HashMap();
        data.put("filepath",projectServerPath+filename);
        map.put("data",data);
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            String jsonString = mapper.writeValueAsString(map);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        return map;
    }
}
