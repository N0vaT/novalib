package ru.nova.novalib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String registerNewUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, Model model){
        model.addAttribute("user", new UserDto());
        if(bindingResult.hasErrors()){
            return "register";
        }
        if(!userDto.getUserPassword().equals(userDto.getRepeatUserPassword())){
            bindingResult.rejectValue("userPassword", "", "Пароли не совпадают");
            return "register";
        }
        userService.save(userDto);
        return "redirect:/";
    }

}
