package com.example.demo.controller;

import com.example.demo.dao.ClientesDAO;
import com.example.demo.pojo.Clientes;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
@RequestMapping("/clientes")
public class ClientesController {

@Autowired
ClientesDAO dao;


    @GetMapping("/novo")
    public String novo(ModelMap model){
        model.addAttribute("clientes", new Clientes());
        return "/clientes/index";
    }


    @GetMapping("/listar")
    public String listar(ModelMap model){
        model.addAttribute("lista", dao.findAll());
        return "/clientes/listar";
    }


    @GetMapping("/prealterar")
    public String preAlterar(@RequestParam(name = "id") int id, ModelMap model){
        model.addAttribute("clientes" , dao.findById(id));
        return "/clientes/index";
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
        return "/clientes/listar";
    }
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute ("clientes") Clientes clientes, ModelMap model, @RequestParam("file")MultipartFile file) {
        try {
            Validator validator;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            Set<ConstraintViolation<Clientes>> constraintViolations =
                    validator.validate(clientes);
            String errors = "";
            for (ConstraintViolation<Clientes> constraintViolation : constraintViolations) {
                errors = errors + constraintViolation.getMessage() + ". ";
            }
            //VALIDAR IMAGEM
            if(file.isEmpty()){
                errors = errors + "Selecione uma imagem!";
            }

            if (errors != "") {
                //tem erros
                model.addAttribute("clientes", clientes);
                model.addAttribute("mensagem", errors);
                model.addAttribute("retorno", false);
                return "/clientes/index";
            } else {

                Random ran = new Random();
                String nomeArquivo = ran.nextInt() + file.getOriginalFilename();
                clientes.setImagem(nomeArquivo);

                if (clientes.getId() == null)
                    dao.save(clientes);
                else
                    dao.update(clientes);

                model.addAttribute("mensagem", "Salvo com sucesso!");
                model.addAttribute("retorno",true);
                try { byte[] bytes = file.getBytes();
                    Path path = Paths.get(System.getProperty("user.dir") +"\\src\\main\\resources\\static\\image\\"
                            + nomeArquivo);
                    Files.write(path, bytes);
                } catch (IOException e) {
                    e.printStackTrace(); }

            }
        }catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao salvar" + e.getMessage());
            model.addAttribute("retorno", false);

        }
        return "/clientes/index";

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
