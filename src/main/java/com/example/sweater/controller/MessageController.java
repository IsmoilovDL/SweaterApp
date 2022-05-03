package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repositories.MessageRepo;
import com.example.sweater.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MessageController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    MessageService messageService;

    @GetMapping("/")
    public String greeting(Map<String, Object> model)
    {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(
               @RequestParam(required = false, defaultValue = "") String filter,
               Model model,
              @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){

        Page<Message> page=messageService.messageList(pageable,filter);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
            ) throws IOException {

        message.setAuthor(user);

        if(bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtil.getErros(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message",message);

        }else {
            saveFile(message, file);
            messageRepo.save(message);
        }
        Iterable<Message> messages=messageRepo.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }

    private void saveFile(Message message, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            //проверяем каталог на существованые и создаем если не существует
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            //создаем уникальный имя файла
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));

            message.setFilename(resultFileName);
        }
    }

    @GetMapping("/user-messages/{author}")
    public String userMessages(@AuthenticationPrincipal User currantUser,
                               @PathVariable User author,
                               Model model,
                               @RequestParam(required = false) Message message,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){

        Page<Message> page =messageService.messageListForUser(pageable, currantUser, author);

        model.addAttribute("userChannel", author);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currantUser));
        model.addAttribute("page", page);
        model.addAttribute("message", message);
        model.addAttribute("isCurrantUser", currantUser.equals(author));
        model.addAttribute("url", "/user-messages/"+ author.getId());

        return "userMessages";
    }


    @PostMapping("/user-messages/{user}")
    public String editMessage(@AuthenticationPrincipal User currantUser,
                              @PathVariable Long user,
                              @RequestParam("id") Message message,
                              @RequestParam("text") String text,
                              @RequestParam("tag") String tag,
                              @RequestParam("file") MultipartFile file
                              ) throws IOException {

        if(message.getAuthor().equals(currantUser)){
            if(!StringUtils.isEmpty(text)){
                message.setText(text);
            }
            if(!StringUtils.isEmpty(tag)){
                message.setTag(tag);
            }

            saveFile(message, file);
            messageRepo.save(message);
        }

        return "redirect:/user-messages/"+user;

    }

}