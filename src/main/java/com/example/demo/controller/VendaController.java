package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.pojo.*;

import jakarta.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/venda")
public class VendaController {
    @Autowired
    ProdutoDao produtoDao;
    @Autowired
    ClientesDAO clientesDAO;
    @Autowired
    DetalheVendaDAO detalheVendaDAO;
    @Autowired
    VendaDao vendaDao;
    @Autowired
    FuncionarioDAO funcionarioDAO;

    @GetMapping("/novo")
    public String index(ModelMap model) {
        return "/venda/index";
    }

    @ModelAttribute(name = "listaproduto")
    public List<Produto> listaProduto() {
        return produtoDao.findAll();
    }

    @ModelAttribute(name = "listacliente")
    public List<Clientes> listaCliente() {
        return clientesDAO.findAll();
    }

    @PostMapping(path = "/salvarProduto", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> salvar(@RequestBody Produto produto) {
        try {

            if (produto.getId() == null)
                produtoDao.save(produto);
            else
                produtoDao.update(produto);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return new ResponseEntity<Object>(produto, HttpStatus.OK);
    }

    @PostMapping(path = "/finalizarVenda", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> finalizarVenda(@RequestBody Venda venda) {
        try {
            if (venda.getId() == null) {
                vendaDao.save(venda);
            } else {
                vendaDao.update(venda);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(venda, HttpStatus.OK);
    }

}
