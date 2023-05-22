package com.example.demo.controller;


import com.example.demo.dao.CategoriaDAO;
import com.example.demo.dao.DetalheVendaDAO;
import com.example.demo.dao.FornecedorDAO;
import com.example.demo.dao.ProdutoDao;
import com.example.demo.pojo.Categoria;
import com.example.demo.pojo.Fornecedor;
import com.example.demo.pojo.Produto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Controller
@RequestMapping("/produto")
public class ProdutoController {
    @Autowired
    private FornecedorDAO daofornecedor;
    @Autowired
    private ProdutoDao dao;
    @Autowired
    private DetalheVendaDAO daodetalhevenda;
    @Autowired
    private CategoriaDAO daocategoria;


    @GetMapping("/novo")
    public String novo(ModelMap model){
        model.addAttribute("produto", new Produto());
        return "/produto/index";
    }

    @GetMapping(path = "/getProduto/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object>getProduto(@PathVariable(value = "id") int id)
    {
       Produto obj = dao.findById(id);
       return new ResponseEntity<Object>(obj, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public String listar(ModelMap model){
        model.addAttribute("lista", dao.findAll());
        return "/produto/listar";
    }


    @GetMapping("/prealterar")
    public String preAlterar(@RequestParam(name = "id") int id, ModelMap model){
        model.addAttribute("produto" , dao.findById(id));
        return "/produto/index";
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
        return "/produto/listar";
    }
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute ("produto") Produto produtos, ModelMap model, @RequestParam("file") MultipartFile file) {
        try {
            Validator validator;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            Set<ConstraintViolation<Produto>> constraintViolations =
                    validator.validate(produtos);
            String errors = "";
            for (ConstraintViolation<Produto> constraintViolation : constraintViolations) {
                errors = errors + constraintViolation.getMessage() + ". ";
            }
            //VALIDAR IMAGEM
            if(file.isEmpty()){
                errors = errors + "Selecione uma imagem!";
            }

            if (errors != "") {
                //tem erros
                model.addAttribute("produto", produtos);
                model.addAttribute("mensagem", errors);
                model.addAttribute("retorno", false);
                return "/produto/index";
            } else {
            	
            	produtos.setCategoria(daocategoria.findById(produtos.getCategoria().getId()));
            	produtos.setFornecedor(daofornecedor.findById(produtos.getFornecedor().getId()));

                Random ran = new Random();
                String nomeArquivo = ran.nextInt() + file.getOriginalFilename();
                produtos.setImagem(nomeArquivo);

                if (produtos.getId() == null)
                    dao.save(produtos);
                else
                    dao.update(produtos);

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
        return "/produto/index";

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

        @ModelAttribute(name = "listacategoria")
        public List<Categoria> listaCategoria() {
    return daocategoria.findAll();
}

    @ModelAttribute(name = "listafornecedor")
    public List<Fornecedor> listaFornecedor() {
        return daofornecedor.findAll();
    }
}

