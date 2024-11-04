package com.inventario.imobilizado.controller;

import com.inventario.imobilizado.dto.LogonDTO;
import com.inventario.imobilizado.repository.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventario.imobilizado.model.User;

@RestController
public class LogonController {

    @Autowired
    private UserInterface userInterface;

    @PostMapping("/logon")
    public ResponseEntity<?> userLogon(@RequestBody LogonDTO user){
    if (user.getSenha().equals(user.getConfirmarSenha())) {
        User usuario = new User();
        usuario.setNome(user.getNome());
        usuario.setSobrenome(user.getSobrenome());
        usuario.setEmail(user.getEmail());
        usuario.setTipoUsuario(user.getTipoUsuario());
        usuario.setSenha(user.getSenha());
        userInterface.save(usuario);

        return ResponseEntity.ok().body("Usuário cadastrado com sucesso.");
    } else {
        return ResponseEntity.badRequest().body("As senhas não coincidem.");
    }
}

}