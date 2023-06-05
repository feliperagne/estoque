package com.example.demo.controller;

import com.example.demo.dao.FuncionarioDAO;
import com.example.demo.dao.UsuarioDao;
import com.example.demo.pojo.AppAuthority;
import com.example.demo.pojo.Funcionario;
import com.example.demo.pojo.Users;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
    FuncionarioDAO dao;

    @Autowired
    UsuarioDao daoUsuario;

    @GetMapping("/novo")
    // @PostAuthorize("hasAuthority('ADMIN')")
    public String novo(ModelMap model) {
        Funcionario funcionario = new Funcionario();
        funcionario.setUsuario(new Users());
        model.addAttribute("funcionario", funcionario);
        return "/funcionario/index";
    }

    @GetMapping("/listar")
    // @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    public String listar(ModelMap model) {
        model.addAttribute("lista", dao.findAll());
        return "/funcionario/listar";
    }

    @GetMapping("/prealterar")
    public String preAlterar(@RequestParam(name = "id") int id, ModelMap model) {
        model.addAttribute("funcionario", dao.findById(id));
        return "/funcionario/index";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam(name = "id") int id, ModelMap model) {
        try {
            model.addAttribute("mensagem", "Exclusão feita!");
            model.addAttribute("retorno", true);
            dao.delete(id);
        } catch (Exception e) {
            model.addAttribute("mensagem", "Exclusão não pode ser efetuada!");
            model.addAttribute("retorno", false);
        }

        model.addAttribute("lista", dao.findAll());
        return "/funcionario/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("funcionario") Funcionario funcionario, ModelMap model,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "isAdmin", required = false) boolean isAdmin) {
        try {
            Validator validator;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validate(funcionario);
            String errors = "";
            for (ConstraintViolation<Funcionario> constraintViolation : constraintViolations) {
                errors = errors + constraintViolation.getMessage() + ". ";
            }

            if (file.isEmpty()) {
                errors = errors + "Selecione uma imagem!";
            }

            // Verificar se o username já existe
            String username = funcionario.getUsuario().getUsername();
            if (daoUsuario.findByUserName(username) != null) {
                model.addAttribute("funcionario", funcionario);
                model.addAttribute("mensagem", "Esse nome de usuário já existe. Por favor, use outro.");
                model.addAttribute("retorno", false);
                return "/funcionario/index";
            }

            if (errors != "") {
                // tem erros
                model.addAttribute("funcionario", funcionario);
                model.addAttribute("mensagem", errors);
                model.addAttribute("retorno", false);
                return "/funcionario/index";
            } else {

                Users usuarios = funcionario.getUsuario();
                String senha = "{bcrypt}" + new BCryptPasswordEncoder(16).encode(usuarios.getPassword());
                usuarios.setPassword(senha);
                usuarios.setEnabled(true);
                usuarios.setAdmin(isAdmin);

                Set<AppAuthority> authorities = new HashSet<>();

                if (isAdmin) {
                    AppAuthority adminAuthority = new AppAuthority();
                    adminAuthority.setAuthority("ADMIN");
                    adminAuthority.setUsername(usuarios.getUsername());
                    authorities.add(adminAuthority);
                } else {
                    AppAuthority userAuthority = new AppAuthority();
                    userAuthority.setAuthority("USER");
                    userAuthority.setUsername(usuarios.getUsername());
                    authorities.add(userAuthority);
                }

                usuarios.setAppAuthorities(authorities);

                Random ran = new Random();
                String nomeArquivo = ran.nextInt() + file.getOriginalFilename();
                funcionario.setImagem(nomeArquivo);

                if (funcionario.getId() == null) {
                    dao.save(funcionario);
                } else {
                    dao.update(funcionario);
                    daoUsuario.update(funcionario.getUsuario());
                }

                model.addAttribute("mensagem", "Salvo com sucesso!");
                model.addAttribute("retorno", true);

                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image\\"
                            + nomeArquivo);
                    Files.write(path, bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {

            model.addAttribute("mensagem", "Erro ao salvar" + e.getMessage());
            model.addAttribute("retorno", false);

        }
        return "/funcionario/index";

    }

    @ResponseBody
    @RequestMapping(value = "/getImagem/{nome}", method = RequestMethod.GET)
    public HttpEntity<byte[]> download(@PathVariable(value = "nome") String nome) throws IOException {
        byte[] arquivo = Files.readAllBytes(
                Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image\\" + nome));
        HttpHeaders httpHeaders = new HttpHeaders();
        switch (nome.substring(nome.lastIndexOf(".") + 1).toUpperCase()) {
            case "JPG":
                httpHeaders.setContentType(MediaType.IMAGE_JPEG);
                break;
            case "GIF":
                httpHeaders.setContentType(MediaType.IMAGE_GIF);
                break;
            case "PNG":
                httpHeaders.setContentType(MediaType.IMAGE_PNG);
                break;
            default:
                break;
        }
        httpHeaders.setContentLength(arquivo.length);
        HttpEntity<byte[]> entity = new HttpEntity<byte[]>(arquivo, httpHeaders);
        return entity;
    }
}
