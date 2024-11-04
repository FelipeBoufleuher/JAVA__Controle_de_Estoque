package com.inventario.imobilizado.controller;

import com.inventario.imobilizado.model.*;
import com.inventario.imobilizado.repository.*;
import com.inventario.imobilizado.service.ExcelExportService;
import com.inventario.imobilizado.service.ExcelImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemInterface itemInterface;

    @Autowired
    private CategoryInterface categoryInterface;

    @Autowired
    private LocationInterface locationInterface;

    @Autowired
    private ExcelExportService excelExportService;
    @Autowired
    private ModeloInterface modeloInterface;
    @Autowired
    private BrandInterface brandInterface;

    @PostMapping("/")
    public ResponseEntity<?> registrarProduto(@RequestBody RequestItem data) {

        Brand brand = brandInterface.findById(data.getMarca()).get();

        Modelo modelo = modeloInterface.findById(data.getModelo()).get();

        Item item = new Item();

        item.setIdItem(data.getIdItem());

        item.setDescricao(data.getDescricao());

        item.setPotencia(data.getPotencia());

        item.setPatrimonio(data.getPatrimonio());

        item.setDataNotaFiscal(data.getDataNotaFiscal());

        item.setLocalizacaoAtual(data.getLocalizacaoAtual());

        item.setDataEntrada(data.getDataEntrada());

        item.setUltimaQualificacao(data.getUltimaQualificacao());

        item.setProximaQualificacao(data.getProximaQualificacao());

        item.setComentarioManutencao(data.getComentarioManutencao());

        item.setPrazoManutencao(data.getPrazoManutencao());

        item.setEstado(data.getEstado());

        item.setCategoria(categoryInterface.findById(data.getCategoria()).get());

        item.setStatus(data.getStatus());

        item.setNumeroDeSerie(data.getNumeroDeSerie());

        item.setModelo(modeloInterface.findByNomeAndMarca(modelo.getNome(), brand));

        item.setLocalizacao(locationInterface.findById(data.getLocalizacao()).get());

        item.setNumeroNotaFiscal(data.getNumeroNotaFiscal());

        itemInterface.save(item);
        return ResponseEntity.ok(item);

    }


    @GetMapping("/")
    public ResponseEntity<Page<Item>> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "descricao") String sortBy) {
        Page<Item> items = itemInterface.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{id_item}")
    public ResponseEntity<Item> putUser(@PathVariable Integer idItem,@RequestBody Item newItem){
        itemInterface.findById(idItem).map(item -> {
            item.setDescricao(newItem.getDescricao());
            item.setPotencia(newItem.getPotencia());
            item.setPatrimonio(newItem.getPatrimonio());
            item.setDataNotaFiscal(newItem.getDataNotaFiscal());
            item.setLocalizacaoAtual(newItem.getLocalizacaoAtual());
            item.setDataEntrada(newItem.getDataEntrada());
            item.setUltimaQualificacao(newItem.getUltimaQualificacao());
            item.setProximaQualificacao(newItem.getProximaQualificacao());
            item.setEstado(newItem.getEstado());
            item.setCategoria(newItem.getCategoria());
            item.setStatus(newItem.getStatus());
            item.setNumeroDeSerie(newItem.getNumeroDeSerie());
            item.setModelo(newItem.getModelo());
            item.setLocalizacao(newItem.getLocalizacao());
            item.setNumeroDeSerie(newItem.getNumeroDeSerie());
            item.setComentarioManutencao(newItem.getComentarioManutencao());
            item.setPrazoManutencao(newItem.getPrazoManutencao());
            return itemInterface.save(item);
        }).orElseThrow();
        return ResponseEntity.ok(newItem);
    }

    @DeleteMapping("/{id_item}")
    public  ResponseEntity<String> deleteUser(@PathVariable Integer idItem){
        itemInterface.deleteById(idItem);
        return ResponseEntity.ok("Item deletado com Sucesso");
    }

    @GetMapping("/paged")
    public Page<Item> pagedItem(Integer page, Integer pageSize, ItemInterface itemInterface, String order) {

        // http://localhost:8080/page/Usuarios?order=sobrenome
        if (order.equals("descricao")){
            Page<Item> itemList = itemInterface.findAll(PageRequest.of(page,pageSize, Sort.by("descricao")));
            return itemList;
        }
        else {
            Page<Item> itemList = itemInterface.findAll(PageRequest.of(page, pageSize));
            return itemList;
        }
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel() {
        List<Item> items = itemInterface.findAll();
        try {
            return excelExportService.exportItensToExcel(items);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Autowired
    private ExcelImportService excelImportService;

    @PostMapping("/import/excel")
    public ResponseEntity<?> importFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<Item> items = excelImportService.importItemsFromExcel(file.getInputStream());
            return ResponseEntity.ok().body(items);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    

}
