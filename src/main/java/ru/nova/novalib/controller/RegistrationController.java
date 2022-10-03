package ru.nova.novalib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nova.novalib.domain.dto.UserDto;
import ru.nova.novalib.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping()
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid UserDto userDto, BindingResult bindingResult, Errors errors, Model model){
        if(bindingResult.hasErrors()){
            return "redirect:/#regModal";
        }
        if(!userDto.getUserPassword().equals(userDto.getRepeatUserPassword())){
            bindingResult.rejectValue("userPassword", "", "Пароли не совпадают");
            return "redirect:/#regModal";
        }
        userService.save(userDto);
        return "redirect:/#logModal";
    }

}
