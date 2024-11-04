package com.inventario.imobilizado.controller;


import com.inventario.imobilizado.model.*;
import com.inventario.imobilizado.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import com.inventario.imobilizado.utils.DateUtils;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("page")
public class HtmlController {
    private UserController userController = new UserController();
    private ItemController itemController = new ItemController();

    private final String CATEGORIES = "categories";
    private final String BRANDS = "brands";
    private final String LOCATIONS = "locations";

    private final String REDIRECT_INFO_GERAL = "redirect:/page/infoGeral";

    @Autowired
    private ItemInterface itemInterface;
    @Autowired
    private UserInterface userInterface;
    @Autowired
    private ModeloInterface modeloInterface;
    @Autowired
    private BrandInterface brandInterface;

    @GetMapping("/login")
    public ModelAndView loginForm(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/logon")
    public ModelAndView logonForm(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("logon");
        return modelAndView;
    }

    @Autowired
    private CategoryInterface categoryInterface;

    @GetMapping("/infoGeral")
    public ModelAndView infoGeral(@RequestParam(name = "pageSize", required = false, defaultValue = "12") Integer pageSize,
                                  @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(name = "order", required = false, defaultValue = "nome") String order) {
        ModelAndView modelAndView = new ModelAndView();

        // Ensure page size and page number are within valid range
        pageSize = Math.max(1, pageSize);
        page = Math.max(0, page);

        Page<Item> itemList = itemController.pagedItem(page, pageSize, itemInterface, order);
        List<Category> categories = categoryInterface.findAll(); // Fetch categories

        modelAndView.addObject("item", itemList.getContent()); // Add items to the model
        modelAndView.addObject("page", itemList); // Add page information to the model
        modelAndView.addObject(CATEGORIES, categories); // Add categories to the model
        modelAndView.setViewName("infoGeral");

        return modelAndView;
    }

    @GetMapping("/Usuarios")
    public ModelAndView usuarios(@RequestParam(name = "pageSize", required = false, defaultValue = "12") Integer pageSize,
                                 @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                 @RequestParam(name = "order", required = false, defaultValue = "nome") String order) {
        ModelAndView modelAndView = new ModelAndView();

        // Ensure page size and page number are within valid range
        pageSize = Math.max(1, pageSize);
        page = Math.max(0, page);


        Page<User> userList = userController.pagedUser(page, pageSize, userInterface, order);

        modelAndView.addObject("usuarios", userList.getContent()); // Add users to the model
        modelAndView.addObject("page", userList); // Add page information to the model
        modelAndView.setViewName("Usuarios");

        return modelAndView;
    }


    @GetMapping("/Avisos")
    public ModelAndView getAvisos() {
        List<Item> items = itemInterface.findAll();
        Date currentDate = new Date();
        List<Item> overdueItems = new ArrayList<>();

        for (Item item : items) {
            if (item.getProximaQualificacao().before(currentDate)) {
                overdueItems.add(item);
            }
        }

        List<Boolean> isOverdue = overdueItems.stream()
                .map(item -> item.getProximaQualificacao().before(currentDate))
                .collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("overdueItems", overdueItems);
        modelAndView.addObject("isOverdue", isOverdue);
        modelAndView.addObject("dateUtils", new DateUtils());
        modelAndView.setViewName("Avisos");

        return modelAndView;
    }


    @GetMapping("/infoEspecifica")
    public ModelAndView infoEspecifica(@RequestParam(name = "id") Integer id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Item item = itemInterface.findById(id).get();
        modelAndView.addObject("item", item);
        modelAndView.setViewName("infoEspecifica");


        List<Action> actions = actionInterface.findByItemIdItem(id);
        Collections.reverse(actions);
        modelAndView.addObject("actions", actions);



        return modelAndView;
    }

   @GetMapping("/NovoCadastro")
    public ModelAndView novoCadastro(Model model) {
        ModelAndView modelAndView = new ModelAndView();

        List<Item> allItens = itemInterface.findAll();
        Integer id = (allItens.isEmpty()) ? 1 : allItens.get(allItens.size() - 1).getIdItem() + 1;

        List<Category> categories = categoryInterface.findAll();
        List<Location> locations = locationInterface.findAll();
        List<Modelo> modelo = modeloInterface.findAll();
        List<Brand> brands = brandInterface.findAll();

        modelAndView.addObject("id", id);
        modelAndView.addObject(BRANDS, brands);
        modelAndView.addObject("modelo", modelo);
        modelAndView.addObject(CATEGORIES, categories);
        modelAndView.addObject(LOCATIONS, locations);

        modelAndView.addObject("data", new RequestItem());
        modelAndView.addObject("isEdit", false);

        modelAndView.setViewName("NovoCadastro");

        return modelAndView;
    }

    @GetMapping("/EditarItem")
    public ModelAndView editarItem(@RequestParam(name = "id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();

        Item item = itemInterface.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));

        List<Category> categories = categoryInterface.findAll();
        List<Location> locations = locationInterface.findAll();
        List<Modelo> modelo = modeloInterface.findAll();
        List<Brand> brands = brandInterface.findAll();

        modelAndView.addObject("id", id);
        modelAndView.addObject(BRANDS, brands);
        modelAndView.addObject("modelo", modelo);
        modelAndView.addObject(CATEGORIES, categories);
        modelAndView.addObject(LOCATIONS, locations);

        modelAndView.addObject("data", item);
        modelAndView.addObject("isEdit", true);

        modelAndView.setViewName("NovoCadastro");

        return modelAndView;
    }

    @GetMapping("/Emprestimo")
    public ModelAndView emprestimo(@RequestParam(name = "id") Integer id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Action action = new Action();

        Item item = itemInterface.findById(id).get();
        if (item.getEstado().equals("Emprestado") || item.getEstado().equals("Manutenção")) {
            logger.info("Item Indisponível");

            return new ModelAndView(REDIRECT_INFO_GERAL);
        }
        action.setItem(item);

        modelAndView.addObject("action", action);

        List<Location> locations = locationInterface.findAll();
        List<User> users = userInterface.findAll();

        modelAndView.addObject(LOCATIONS, locations);
        modelAndView.addObject("users", users);


        modelAndView.setViewName("Emprestimo");
        //      guardando id item
        ActionRequest actionRequest = new ActionRequest();
        actionRequest.setIdItem(id);

        modelAndView.addObject("data", actionRequest);

        return modelAndView;
    }

    @GetMapping("/Devolucao")
    public ModelAndView devolucao(@RequestParam(name = "id") Integer id, Model model) {

        List<Action> actionDevolucao = actionInterface.findByItemIdItem(id);

        Action lastAction;
        if (!actionDevolucao.isEmpty()) {
            lastAction = actionDevolucao.get(actionDevolucao.size() - 1);
            if (lastAction.getTipoAcao() == 0){
                System.out.println("item já devolvido");
                return new ModelAndView(REDIRECT_INFO_GERAL);
            }
            else {
                Action devolver = new Action();
                devolver.setRaRna(lastAction.getRaRna());
                devolver.setEntidade(lastAction.getEntidade());
                devolver.setDataEmprestimo(lastAction.getDataEmprestimo());
                devolver.setDataDevolucao(lastAction.getDataDevolucao());
                devolver.setUsuario(lastAction.getUsuario());
                devolver.setItem(lastAction.getItem());
                devolver.setTipoAcao(0);
                devolver.setAnexos(lastAction.getAnexos());
                devolver.setLocalizacaoIdLocalizacao(lastAction.getLocalizacaoIdLocalizacao());

                actionInterface.save(devolver);
            }
        } else {
            System.out.println("Nenhuma ação encontrada para o item com ID: " + id);
        }

        itemInterface.findById(id).map(item -> {
            item.setEstado("Disponível");
            return itemInterface.save(item);
        }).orElseThrow();


        return new ModelAndView(REDIRECT_INFO_GERAL);
    }


    @GetMapping("/activeFieldRegistration")
    public ModelAndView activeFieldRegistration(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<Brand> brands = brandInterface.findAll();
        List<Modelo> modelos = modeloInterface.findAll();
        List<Category> categories = categoryInterface.findAll();
        modelAndView.addObject(BRANDS, brands);
        modelAndView.addObject("modelos", modelos);
        modelAndView.addObject(CATEGORIES, categories);
        modelAndView.addObject("activeField", new ActiveFieldForm());
        modelAndView.setViewName("activeFieldRegistration");
        return modelAndView;
    }

    @Autowired
    private LocationInterface locationInterface;

    @GetMapping("/location")
    public ModelAndView novaLocalizacao(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<Location> locations = locationInterface.findAll();
        modelAndView.addObject(LOCATIONS, locations);
        modelAndView.addObject("location", new Location());
        modelAndView.setViewName("location");
        return modelAndView;
    }

    private static final Logger logger = LoggerFactory.getLogger(HtmlController.class);

    @Autowired
    private AttachmentInterface attachmentInterface;

    @Autowired
    private ActionInterface actionInterface;

    @PostMapping("/Emprestimo")
    public ModelAndView registrarEmprestimo(@ModelAttribute ActionRequest action, @RequestParam(name = "id") Integer id) {
        action.setIdItem(id);

        Action postAction = new Action();
        postAction.setRaRna(action.getRaRna());
        postAction.setEntidade(action.getEntidade());
        postAction.setDataEmprestimo(action.getData_emprestimo());
        postAction.setDataDevolucao(action.getData_devolucao());
        postAction.setUsuario(userInterface.findById(action.getIdUsuario()).get());
        postAction.setItem(itemInterface.findById(action.getIdItem()).get());
        postAction.setTipoAcao(1);
        postAction.setAnexos(attachmentInterface.findById(1).get());
        postAction.setLocalizacaoIdLocalizacao(action.getIdLocalizacaoAtual());

        actionInterface.save(postAction);
        // altera a disponibilidade do item
        itemInterface.findById(id).map(item -> {
            item.setEstado(action.getEstado());
            item.setPrazoManutencao(action.getPrazoManutencao());
            return itemInterface.save(item);
        }).orElseThrow();

        return new ModelAndView(REDIRECT_INFO_GERAL);
    }


    @PostMapping("/NovoCadastro")
    public ModelAndView registrarProduto(@ModelAttribute RequestItem data) {
        Item item;

        if (data.getIdItem() != null && itemInterface.existsById(data.getIdItem())) {
            item = itemInterface.findById(data.getIdItem()).orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + data.getIdItem()));
        } else {
            item = new Item();
        }

        item.setIdItem(data.getIdItem());
        item.setDescricao(data.getDescricao());
        item.setPotencia(data.getPotencia());
        item.setPatrimonio(data.getPatrimonio());
        item.setDataNotaFiscal(data.getDataNotaFiscal());
        item.setLocalizacaoAtual(data.getLocalizacaoAtual());
        item.setDataEntrada(data.getDataEntrada());
        item.setUltimaQualificacao(data.getUltimaQualificacao());
        item.setProximaQualificacao(data.getProximaQualificacao());
        item.setNumeroDeSerie(data.getNumeroDeSerie());
        item.setComentarioManutencao(data.getComentarioManutencao());
        item.setPrazoManutencao(data.getPrazoManutencao());
        item.setNumeroNotaFiscal(data.getNumeroNotaFiscal());
        item.setEstado(data.getEstado());
        item.setStatus(data.getStatus());

        item.setModelo(modeloInterface.findById(data.getModelo()).get());
        item.setCategoria(categoryInterface.findById(data.getCategoria()).get());
        item.setLocalizacao(locationInterface.findById(data.getLocalizacao()).get());

        System.out.println("item:" + item.toString());
        System.out.println("data:" + data.toString());

        itemInterface.save(item);
        return new ModelAndView(REDIRECT_INFO_GERAL);
    }
}