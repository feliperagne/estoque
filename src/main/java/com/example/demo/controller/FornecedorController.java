package com.example.demo.controller;

import com.example.demo.dao.FornecedorDAO;
import com.example.demo.pojo.Fornecedor;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
@Controller
@RequestMapping("/fornecedor")
public class FornecedorController {
    @Autowired
    FornecedorDAO dao;


    @GetMapping("/novo")
    public String novo(ModelMap model){
        model.addAttribute("fornecedor", new Fornecedor());
        return "/fornecedor/index";
    }


    @GetMapping("/listar")
    public String listar(ModelMap model){
        model.addAttribute("lista", dao.findAll());
        return "/fornecedor/listar";
    }


    @GetMapping("/prealterar")
    public String preAlterar(@RequestParam(name = "id") int id, ModelMap model){
        model.addAttribute("fornecedor" , dao.findById(id));
        return "/fornecedor/index";
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
        return "/fornecedor/listar";
    }
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("fornecedor") Fornecedor fornecedor, ModelMap model) {
        try {
            Validator validator;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            Set<ConstraintViolation<Fornecedor>> constraintViolations =
                    validator.validate(fornecedor);
            String errors = "";
            for (ConstraintViolation<Fornecedor> constraintViolation : constraintViolations) {
                errors = errors + constraintViolation.getMessage() + ". ";
            }
            if (errors != "") {
                //tem erros
                model.addAttribute("fornecedor", fornecedor);
                model.addAttribute("mensagem", errors);
                model.addAttribute("retorno", false);
                return "/fornecedor/index";
            } else {

                if (fornecedor.getId() == null)
                    dao.save(fornecedor);
                else
                    dao.update(fornecedor);

                model.addAttribute("mensagem", "Salvo com sucesso!");
                model.addAttribute("retorno",true);
            }
        }catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao salvar" + e.getMessage());
            model.addAttribute("retorno", false);

        }
        return "/fornecedor/index";

    }
}
