package com.luan.vendas.controller;
import java.util.List;

import com.luan.vendas.dao.ClienteDao;
import com.luan.vendas.model.Cliente;

public class ClienteController {

    private final ClienteDao clienteDao;

    public ClienteController() {
        this.clienteDao = new ClienteDao();
    }
    
    public String salvarCliente ( String nome, String cpf, String rg, String endereco,String telefone) {
        if (nome == null || nome.trim().isEmpty()) {
            return "O nome do cliente não pode ser vazio.";
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            return "O CPF do cliente não pode ser vazio.";
        }
        if (rg == null || rg.trim().isEmpty()) {
            return "O RG do cliente não pode ser vazio.";
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            return "O endereço do cliente não pode ser vazio.";
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            return "O telefone do cliente não pode ser vazio.";
        }

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setRg(rg);
        cliente.setEndereco(endereco);
        cliente.setTelefone(telefone);

        boolean salvo = clienteDao.salvar(cliente);
        if (!salvo) {
            return "Erro ao salvar cliente no banco de dados.";
        }

        return null; // null indica sucesso
    }

    public String alterarCliente(int id, String nome, String cpf, String rg, String endereco,String telefone) {
        if (id <= 0) {
            return "ID do cliente inválido.";
        }
        if (nome == null || nome.trim().isEmpty()) {
            return "O nome do cliente não pode ser vazio.";
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            return "O CPF do cliente não pode ser vazio.";
        }
        if (rg == null || rg.trim().isEmpty()) {
            return "O RG do cliente não pode ser vazio.";
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            return "O endereço do cliente não pode ser vazio.";
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            return "O telefone do cliente não pode ser vazio.";
        }

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setRg(rg);
        cliente.setEndereco(endereco);
        cliente.setTelefone(telefone);

        boolean alterado = clienteDao.alterar(cliente);
        if (!alterado) {
            return "Erro ao alterar cliente no banco de dados.";
        }

        return null; // null indica sucesso
    }

    public String excluirCliente(int id) {
        if (id <= 0) {
            return "ID do cliente inválido.";
        }

        boolean excluido = clienteDao.excluir(id);
        if (!excluido) {
            return "Erro ao excluir cliente do banco de dados.";
        }

        return null; // null indica sucesso
    }

    public List<Cliente> pesquisarCliente() {
        return clienteDao.listarTodos();
    }
    
}
