package com.example.labshop.controller;

import com.example.labshop.enumeration.OrderStatus;
import com.example.labshop.model.*;
import com.example.labshop.repository.OrderRepository;
import com.example.labshop.repository.ProductCategoryRepository;
import com.example.labshop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final OrderRepository orderRepository;

    private static final String UPLOAD_DIRECTORY = "/Users/valeroni/IdeaProjects/lab-shop/src/main/resources/templates/uploads";

    public ProductController(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/add-to-cart")
    public String addProductToCart(@ModelAttribute ProductModel productModel,@ModelAttribute UserModel userModel, Model model, HttpSession session){
        String uuid = UUID.randomUUID().toString();
        OrderModel orderModel = new OrderModel(null,
                userModel.getEmail(),
                LocalDateTime.now(),
                List.of(productModel),
                productModel.getCost(),
                uuid,
                OrderStatus.IN_CART);
        orderRepository.saveOrder(orderModel);
        session.setAttribute("uuidCart", uuid);
        return "/index";
    }

    @GetMapping("/product-info")
    public String productInfo(@RequestParam ProductModel productModel, Model model){
        List<OrderModel> orderModels = orderRepository.findAllOrders();
        List<ProductCategoryModel> productCategoryModels = productCategoryRepository.findAllCategories();
        model.addAttribute("categories", productCategoryModels);
        model.addAttribute("orders", orderModels);
        model.addAttribute("addCategory", new ProductCategoryModel());
        model.addAttribute("product", productModel);
        return "/card";
    }

    @GetMapping("/products")
    public String products(@ModelAttribute ProductCategoryModel productCategoryModel, Model model) {
        List<ProductModel> products =
                productRepository.findProductsByCategory(productCategoryModel.getCategoryName());
        model.addAttribute("products", products);
        return "/products";
    }

    @GetMapping("/create-product-form")
    public String createProductForm(Model model) {
        List<ProductCategoryModel> categories = productCategoryRepository.findAllCategories();
        model.addAttribute("product", new ProductModel());
        model.addAttribute("categories", categories);
        model.addAttribute("addCategory", new ProductCategoryModel());
        return "/create-product-form";
    }

    @PostMapping("/create-product")
    public String createProduct(@ModelAttribute ProductModel productModel,
                                @RequestParam("image") MultipartFile multipartFile,
                                Model model) throws IOException {
        PhotoModel photoModel = new PhotoModel();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, multipartFile.getOriginalFilename());
        Files.write(fileNameAndPath, multipartFile.getBytes());
        photoModel.setPath(fileNameAndPath.toString());
        productModel.setPhotos(photoModel);
        productModel.setPostDate(LocalDateTime.now());
        boolean isExistCategory = productCategoryRepository.findCategoryByName(productModel.getCategory()) == null;
        if (isExistCategory){
            ProductCategoryModel productCategoryModel = new ProductCategoryModel();
            productCategoryModel.setCategoryName(productModel.getCategory());
            productCategoryRepository.saveCategory(productCategoryModel);
        }
        productRepository.saveProduct(productModel);
        List<ProductCategoryModel> productCategoryModels = productCategoryRepository.findAllCategories();
        model.addAttribute("categories", productCategoryModels);
        model.addAttribute("addCategory", new ProductCategoryModel());
        return "/index";
    }

//    @PostMapping("/upload-photo")
//    public void uploadPhoto
}
