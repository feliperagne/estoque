package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.pojo.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/venda")
public class VendaController {
    @Autowired
    ProdutoDao produtoDao;
    @Autowired
    ClientesDAO clientesDAO;
    @Autowired
    VendaDao vendaDao;
    @Autowired
    FuncionarioDAO funcionarioDAO;
    @Autowired
    DetalheVendaDao detalheVendaDao;
    

    @GetMapping("/novo")
    public String index(ModelMap model) {
        model.addAttribute("venda", new Venda());
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


    @PostMapping(path = "/salvar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> salvarVenda(@RequestBody VendaModel vendaModel, ModelMap model) {
        double total = 0;
        double valor_unit = 0;
        int qtde= 0;
        int idproduto=0;
        try{

            Venda vendas = new Venda();
            List<Produto> listaProdutos = new ArrayList<>();

            Clientes vendaCliente = clientesDAO.findById(vendaModel.getIdCliente());
            vendas.setCliente(vendaCliente);

            int idFuncionario = 1;//(int) model.getAttribute("idUsuario");
            Funcionario funcionario = funcionarioDAO.findById(idFuncionario);
            vendas.setFuncionarios(funcionario);

            vendas.setDataVenda(LocalDate.now());
 
            List<ProdutosModel> produtosModels = vendaModel.getProdutos();

            for (ProdutosModel produto : produtosModels){
                total += produto.getTotal();
            } 

            vendas.setValorTotal(total);

            vendaDao.save(vendas);

            int idVenda = vendas.getId();


            for (ProdutosModel produto : produtosModels) {

                DetalheVenda detalhe = new DetalheVenda();
  
                Produto produto_Venda = produtoDao.findById(produto.getCodigo());
  
                detalhe.setProdutos(produto_Venda);
                detalhe.setValor_unit(produto.valor_unitario);
                detalhe.setQtde(produto.getQtde());
                detalhe.setVendas(vendas);
                //baixa estoque
               produto_Venda.setQntd(produto_Venda.getQntd() - produto.qtde);

                produtoDao.update(produto_Venda);

                detalheVendaDao.save(detalhe);

            }

            return new ResponseEntity<>(idVenda, HttpStatus.OK);

        } catch (Exception e) {
            // Trate a exceção ou registre o erro adequadamente
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
