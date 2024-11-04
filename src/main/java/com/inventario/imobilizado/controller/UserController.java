package com.inventario.imobilizado.controller;

import com.inventario.imobilizado.model.User;
import com.inventario.imobilizado.repository.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInterface userInterface;

    @GetMapping("/")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sortBy) {
        Page<User> users = userInterface.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email){
        User user = userInterface.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<User> postUser(@RequestBody User user ){
        userInterface.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id_usuario}")
    public ResponseEntity<User> putUser(@PathVariable Integer idUsuario, @RequestBody User newUser) {
        userInterface.findById(idUsuario)
                .map(user -> {
                    user.setNome(newUser.getNome());
                    user.setSobrenome(newUser.getSobrenome());
                    user.setTipoUsuario(newUser.getTipoUsuario());
                    user.setEmail(newUser.getEmail());
                    user.setSenha(newUser.getSenha());
                    newUser.setIdUsuario(user.getIdUsuario());
                    return userInterface.save(user);
                })
                .orElseThrow();
        return ResponseEntity.ok(newUser);
    }

    @DeleteMapping("/{id_usuario}")
    public ResponseEntity<String> DeleteUser(@PathVariable Integer idUsuario){
        userInterface.deleteById(idUsuario);
        return ResponseEntity.ok("Usuário Deletado com Sucesso");
    }


    @GetMapping("/paged")
    public Page<User> pagedUser(Integer page, Integer pageSize, UserInterface userInterface,String order) {
        // http://localhost:8080/page/Usuarios?order=nome
        if (order.equals("nome")){
            Page<User> userList = userInterface.findAll(PageRequest.of(page,pageSize, Sort.by("nome")));
            return userList;
        }
        // http://localhost:8080/page/Usuarios?order=sobrenome
        if (order.equals("sobrenome")){
            Page<User> userList = userInterface.findAll(PageRequest.of(page,pageSize, Sort.by("sobrenome")));
            return userList;
        }
        // http://localhost:8080/page/Usuarios?order=email
        if (order.equals("email")){
            Page<User> userList = userInterface.findAll(PageRequest.of(page,pageSize, Sort.by("email")));
            return userList;
        }
        else {
            Page<User> userList = userInterface.findAll(PageRequest.of(page, pageSize));
            return userList;
        }
    }

}
