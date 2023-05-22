package com.example.demo.controller;


import com.example.demo.dao.FuncionarioDAO;
import com.example.demo.pojo.AppAuthority;
import com.example.demo.pojo.Funcionario;
import com.example.demo.pojo.Users;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
@Controller
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
    FuncionarioDAO dao;


    @GetMapping("/novo")
    public String novo(ModelMap model){
        Funcionario funcionario = new Funcionario();
        funcionario.setUsuario(new Users());
        model.addAttribute("funcionario", new Funcionario());
        return "/funcionario/index";
    }


    @GetMapping("/listar")
    public String listar(ModelMap model){
        model.addAttribute("lista", dao.findAll());
        return "/funcionario/listar";
    }


    @GetMapping("/prealterar")
    public String preAlterar(@RequestParam(name = "id") int id, ModelMap model){
        model.addAttribute("funcionario" , dao.findById(id));
        return "/funcionario/index";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam(name = "id") int id, ModelMap model){
        try{
            model.addAttribute("mensagem" , "Exclusão feita!");
            model.addAttribute("retorno", true);
            dao.delete(id);
        }catch (Exception e){
            model.addAttribute("mensagem", "Exclusão não pode ser efetuada!");
            model.addAttribute("retorno" , false);
        }

        model.addAttribute("lista", dao.findAll());
        return "/funcionario/listar";
    }
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("funcionario") Funcionario funcionario, ModelMap model, @RequestParam("file")MultipartFile file) {
        try {
            Validator validator;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            Set<ConstraintViolation<Funcionario>> constraintViolations =
                    validator.validate(funcionario);
            String errors = "";
            for (ConstraintViolation<Funcionario> constraintViolation : constraintViolations) {
                errors = errors + constraintViolation.getMessage() + ". ";
            }
            if (errors != "") {
                //tem erros
                model.addAttribute("funcionario", funcionario);
                model.addAttribute("mensagem", errors);
                model.addAttribute("retorno", false);
                return "/funcionario/index";
            } else {

                Users usuarios = funcionario.getUsuario();
                String senha = "{bcrypt}" + new BCryptPasswordEncoder().encode(usuarios.getPassword());
                usuarios.setPassword(senha);
                usuarios.setEnabled(true);
                usuarios.setAdmin(false);

                Set<AppAuthority> appAuthorities = new HashSet<AppAuthority>();
                AppAuthority app = new AppAuthority();
                app.setAuthority("USER");
                app.setUsername(usuarios.getUsername());
                appAuthorities.add(app);
                usuarios.setAppAuthorities( appAuthorities);

                Random ran = new Random();
                String nomeArquivo = ran.nextInt() + file.getOriginalFilename();
                funcionario.setImagem(nomeArquivo);

                if (funcionario.getId() == null)
                    dao.save(funcionario);
                else
                    dao.update(funcionario);

                model.addAttribute("mensagem", "Salvo com sucesso!");
                model.addAttribute("retorno",true);
            }
        }catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao salvar" + e.getMessage());
            model.addAttribute("retorno", false);

        }
        return "/funcionario/index";

    }
    @ResponseBody
    @RequestMapping(value = "/getImagem/{nome}", method = RequestMethod.GET)
    public HttpEntity<byte[]> download(@PathVariable(value = "nome") String nome) throws IOException {
        byte[] arquivo =Files.readAllBytes( Paths.get(System.getProperty("user.dir") +"\\src\\main\\resources\\static\\image\\" + nome));
        HttpHeaders httpHeaders = new HttpHeaders();
        switch (nome.substring(nome.lastIndexOf(".") + 1).toUpperCase()) {
            case "JPG":
                httpHeaders.setContentType(MediaType.IMAGE_JPEG);break;
            case "GIF":
                httpHeaders.setContentType(MediaType.IMAGE_GIF); break;
            case "PNG":
                httpHeaders.setContentType(MediaType.IMAGE_PNG); break;
            default:
                break;
        } httpHeaders.setContentLength(arquivo.length);
        HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, httpHeaders);
        return entity;}
}
