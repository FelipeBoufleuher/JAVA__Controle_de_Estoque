package com.inventario.imobilizado.controller;

import com.inventario.imobilizado.model.Action;
import com.inventario.imobilizado.model.ActionRequest;
import com.inventario.imobilizado.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/action")
public class ActionController {


    @Autowired
    private ItemInterface itemInterface;
    @Autowired
    private ActionInterface actionInterface;

    @Autowired
    private CategoryInterface categoryInterface;

    @Autowired
    private LocationInterface locationInterface;

    @Autowired
    private AttachmentInterface attachmentInterface;

    @Autowired
    private UserInterface userInterface;

    @PostMapping("/")
    public ResponseEntity<Action> postAction(@RequestBody ActionRequest data) {
        Action action = new Action();
        action.setRaRna(data.getRaRna());
        action.setEntidade(data.getEntidade());
        action.setUsuario(userInterface.findById(data.getIdUsuario()).get());
        action.setItem(itemInterface.findById(data.getIdItem()).get());
        action.setAnexos(attachmentInterface.findById(data.getIdAnexos()).get());
        actionInterface.save(action);

        return ResponseEntity.ok(action);
    }



    @GetMapping("/")
    public ResponseEntity<List<Action>> getAll(){
        List<Action> allItems = actionInterface.findAll();

        return ResponseEntity.ok(allItems);
    }



}
