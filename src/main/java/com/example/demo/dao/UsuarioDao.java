package com.example.demo.dao;


import com.example.demo.pojo.Clientes;
import com.example.demo.pojo.Funcionario;
import com.example.demo.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioDao extends AbstractDao<Users, Integer> implements UserDetailsService
{
    @Autowired
    private FuncionarioDAO funcionarioDAO;
    public Funcionario getUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nome;
        if (principal instanceof UserDetails) {
            nome = ((UserDetails)principal).getUsername();
        } else {
            nome = principal.toString();
        }
        return findByFuncionarioUserName(nome);
    }
    public Users findByUserName(String username) {
        List<Users> lista = this.createQuery("select u from Users u where u.username like ?1", username) ;
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Funcionario findByFuncionarioUserName(String username) {
        List<Funcionario> lista = funcionarioDAO.createQuery(
            "select c from Users u inner join u.funcionario c where u.username like ?1",
            username
        );
        return lista.isEmpty() ? null : lista.get(0);
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = findByUserName(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAppAuthorities());
    }
}
